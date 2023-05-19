////---------NeoPixel---------////
#include <Adafruit_NeoPixel.h>
//고정적으로 사용할 내용을 미리 선언
#define LED_PIN 13     //네오픽셀에 신호를 줄 핀번호
#define LED_COUNT 10  //아두이노에 연결된 네오픽셀의 개수
Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_RGBW + NEO_KHZ800);
// GRB

////---------압력센서---------////
int init_water=0;
int init_coffee=0;
int init_noncoffee=0;
int drink_before=0;
int drink_now=0;
int changeliter=0;
int total_drink=0;
const int pressSensor = 26;    // SIG를 26번 핀에 연결
int sensing_flag = 0;
int value_sum=0;
int value_cnt=0;
int cork=0;
int liter10 = 9;

////---------터치센서---------////
const int watertouch = 14;     // SIG를 14번 핀에 연결
const int coffeetouch = 33;     // SIG를 33번 핀에 연결
const int noncoffeetouch = 32;     // SIG를 32번 핀에 연결

int watercnt=0;
int coffeecnt=0;
int noncoffeecnt=0;

String drink_type="w";


////---------타이머---------////
#include "esp_system.h"
unsigned long Sec, Min, Hour, time5sec, time5min;
unsigned long timeVal=0;



////---------SPIFFS---------////
#include "SPIFFS.h"
String strValue = "";
void writeFile(const char * path, const char * message);
void deleteFile(const char * path);


////---------블루투스---------////
#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

bool deviceConnected = false;
bool oldDeviceConnected = false;

int cnt = 0;

int Goal = -1;
String Alarm = ""; //on:T off:
String account = ""; // 계정

// 한 번 연결된 적이 있는 기기
///////// 블루투스가 연결되지 않은 상타에서도 사용자의 목표설정값으로 코스터 동작
int user_exist = 0;

BLEServer *pServer = NULL;
BLEService *pService = NULL;
BLECharacteristic *pCharacteristic = NULL;
BLEAdvertising *pAdvertising = NULL;

#define SERVICE_UUID        "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
#define CHARACTERISTIC_UUID "beb5483e-36e1-4688-b7f5-ea07361b26a8"

class MyServerCallbacks: public BLEServerCallbacks {
  void onConnect(BLEServer* pServer) {deviceConnected = true;
  Serial.println("connect");};
  void onDisconnect(BLEServer* pServer) {deviceConnected = false;}
};

class MyCallbackHandler : public BLECharacteristicCallbacks {
  void onWrite(BLECharacteristic *pCharacteristic) {
    // 수신된 데이터 받아오기
    std::string value = pCharacteristic->getValue();
    int isvalue = 0;
    if (value.length() > 0) {
      Goal=0;
      user_exist = 1;
      Serial.print("Receive");
      Serial.print("Received value: ");
      for (int i = 0; i < value.length(); i++) {
        Serial.print(value[i]);

        // lable : data 
        if((char)value[i]==' ')   isvalue=1;
        else if(isvalue==0)
          Goal = Goal*10 + (int)value[i];
        else if(isvalue==1)
          Alarm = (char)value[i];
      }
      
      // 목표설정값, 알람여부 저장
      String goal_alarm = (String)Goal + " " + (String)Alarm;
      const char* user_info = goal_alarm.c_str();
      deleteFile("/user_info.txt");
      writeFile("/user_info.txt", user_info);
      Serial.println();
    }
  }
};



void setup() {
  Serial.begin(115200);

  ////--------목표설정, 알람여부 데이터---------////
  

  ////---------NeoPixel---------////
  strip.begin();           // INITIALIZE NeoPixel strip object (REQUIRED)
  strip.show();            // 네오픽셀에 빛을 출력하기 위한 것인데 여기서는 모든 네오픽셀을 OFF하기 위해서 사용한다.
  strip.setBrightness(200); // 네오픽셀의 밝기 설정(최대 255까지 가능)
  

  ////---------SPIFFS---------////
  Serial.println();
  if (!SPIFFS.begin(true)) {
    Serial.println("Failed to mount file system");
    return;
  }
  //SPIFFS.format();
  listDir("/"); 
  infoFile("/user_info.txt");
  deleteFile("/drink_log.txt");
  writeFile("/drink_log.txt", "");


  ////---------터치센서---------////
  pinMode (watertouch, INPUT);     // 터치센서 신호값을 입력으로 설정
  pinMode (coffeetouch, INPUT);
  pinMode (noncoffeetouch, INPUT);


  ////---------블루투스---------////
  Serial.println("Starting BLE...");
  // BLE 장치 이름
  BLEDevice::init("랼랴");
  // BLE 장치를 BLE 서버로 설정
  pServer = BLEDevice::createServer();
  pServer->setCallbacks(new MyServerCallbacks());
  // 미리 정의한 UUID로 서버의 서비스 생성    
  pService = pServer->createService(SERVICE_UUID);
  // UUID 전달, 읽기, 쓰시 속성으로 설정
  pCharacteristic = pService->createCharacteristic(
                                         CHARACTERISTIC_UUID,
                                         BLECharacteristic::PROPERTY_READ   |
                                         BLECharacteristic::PROPERTY_WRITE  |
                                         BLECharacteristic::PROPERTY_NOTIFY |
                                         BLECharacteristic::PROPERTY_INDICATE
                                       );
                                       
  pCharacteristic->setValue("Hello World says Neil");
  // characteristic의 특성을 설명하는 메타데이터를 설정할 수 있음
  // 이 문장이 없으면 notification이 제대로 이뤄지지 않는다
  // 데이터가 갱신될 때 마다 client가 적절한 callback을 수행하도록 하기 위해서 사용 필수 
  pCharacteristic->addDescriptor(new BLE2902());
  // 데이터 수신 콜백함수
  pCharacteristic->setCallbacks(new MyCallbackHandler());
  // 서비스 시작
  pService->start();

  // 광고 설정 객체 생성 후 pAdvertising 포인터 변수에 할당
  // advertising 생성
  pAdvertising = pServer->getAdvertising();
  // 클라이언트가 UUID를 통해 서비스를 식별하고 연결을 요청할 수 있음
  pAdvertising->addServiceUUID(SERVICE_UUID);
  // 스캔 기능하도록 설정
  pAdvertising->setScanResponse(false);
  // 아이폰 관련 연결 보조 설정(최대, 최소 주파수 대역폭 설정)
  pAdvertising->setMinPreferred(0x06);
  pAdvertising->setMaxPreferred(0x12);
  // advertising 시작
  pAdvertising->start();
  Serial.println("BLE server is ready.");



  ////---------타이머---------////
  timeVal = millis();
  Sec = 0;
  Min = 0;
  Hour = 0;
  time5sec = 0;
  time5min = 0;
}

void loop() {
  if(user_exist==1){
    ////--------압력센서--------////
    // 센서값 받아오면 now_drink에 저장
    int presssensor_value = analogRead(pressSensor);


    ////--------터치센서---------////
    int touch_water_state = digitalRead (watertouch);   // 터치센서의 입력값을 state라는 변수에 저장
    int touch_coffee_state = digitalRead (coffeetouch);
    int touch_noncoffee_state = digitalRead (noncoffeetouch);
    // 버튼 high
    if(touch_water_state==HIGH) watercnt++;
    if(touch_coffee_state==HIGH) coffeecnt++;
    if(touch_noncoffee_state==HIGH) noncoffeecnt++;

    // cork가 누르는 압력이 최대 600 == 컵이 있는 경우
    if(presssensor_value >= 700)
    {
      // 영점조절  
      if(watercnt>=1)
      {
        // 터치센서 동작하게되면 압력센서 출력값이 올라감
        init_water = presssensor_value - 750 - cork;

        // 터치센서 변수 초기화
        watercnt=0;
        coffeecnt=0;
        noncoffeecnt=0;

        // led색상 변경
        Serial.println("water");
        drink_type = "w";
        colorWipe(strip.Color(0, 0, 205), 50, 10); // blue
        delay(2000);
        strip.clear();
        strip.show();

        drink_before = init_water;
        value_sum=0;
        value_cnt=0;

      }
      if(coffeecnt>=1)
      {
        // 터치센서 동작하게되면 압력센서 출력값이 올라감
        init_coffee = presssensor_value - 750 - cork;

        // 터치센서 변수 초기화
        watercnt=0;
        coffeecnt=0;
        noncoffeecnt=0;

        // led색상 변경
        Serial.println("coffee");
        drink_type = "c";
        colorWipe(strip.Color(43, 138, 220), 50, 10); //pink
        delay(2000);
        strip.clear();
        strip.show();

        drink_before = init_coffee;
        value_sum=0;
        value_cnt=0;
      }
      if(noncoffeecnt>=1)
      {
        // 터치센서 동작하게되면 압력센서 출력값이 올라감
        init_noncoffee = presssensor_value - 750 - cork;

        // 터치센서 변수 초기화
        watercnt=0;
        coffeecnt=0;
        noncoffeecnt=0;

        // led색상 변경
        Serial.println("noncoffee");
        drink_type = "d";
        colorWipe(strip.Color(255, 255, 0), 50, 10); //yellow
        delay(2000);
        strip.clear();
        strip.show();

        drink_before = init_noncoffee;
        value_sum=0;
        value_cnt=0;
      }

      // 영점 세팅 이후에 물을 마신 후의 데이터 처리
      if(sensing_flag == 1)
      {
        if(value_cnt>=10)
        {
          drink_now = value_sum/value_cnt;
          changeLiter(drink_now);
          Serial.println("change");
          value_sum=0;
          value_cnt=0;
          sensing_flag=0;
        }
        else
        {
          value_sum+=(presssensor_value - cork);
          value_cnt++;
        } 

      }

    }
    // 음료를 마시기 위해 컵을 들었음(가정)
    else if(presssensor_value < 700 && init_water>0)
    {
      sensing_flag = 1;
      Serial.println("check");
      value_sum=0;
      value_cnt=0;
    }
    // 음료가 없음
    else if(presssensor_value < 700 )
    {
      cork = presssensor_value;
    }


    ///---------NeoPixel---------////
    if(Goal == total_drink) rainbow(5);
    // 게이지 상승 
    if(Goal*0.1 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 1);
    else if(Goal*0.2 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 2);
    else if(Goal*0.3 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 3);
    else if(Goal*0.4 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 4);
    else if(Goal*0.5 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 5);
    else if(Goal*0.6 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 6);
    else if(Goal*0.7 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 7);
    else if(Goal*0.8 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 8);
    else if(Goal*0.9 == total_drink)    colorWipe(strip.Color(255, 255, 255), 50, 9);

    
    time5sec++;
    if(time5sec==60){
      time5sec=0;
      time5min++;
    }

    ////---------블루투스---------////
    // 연결이 잘 되고 있는 상황
    // 데이터 갱신 후 notification
    if(deviceConnected){
      // 물을 마셨음이 확인되면 플러터에 전송
      if(drink_before != drink_now && time5min%3==0 && time5sec == 0){
        String min_data = (String)time5min;
        String stst = (String)changeliter;
        String data = min_data + drink_type + stst;
        const char* Data = data.c_str();

        pCharacteristic->setValue(Data);
        pCharacteristic->notify();
        delay(3);   // client가 올바르게 정보를 수산할 수 있도록 여유의 시간(레퍼런스에서 3ms)  
        drink_before = drink_now;
      }
    }
    // 이전에 연결한 기록이 있는 상태에서 연결이 끊긴 상황
    if (!deviceConnected && oldDeviceConnected) {	// disconnecting
      delay(500); // give the bluetooth stack the chance to get things ready
      pServer->startAdvertising(); // 블루투스 재탐색 가능하도록 설정
      Serial.println("start advertising");
      oldDeviceConnected = deviceConnected;

      // 블루투스가 끊긴 후 부터 메모리 시스템에 데이터 저장하도록 설정
      // 끊김 확인한 후에 메모리 초기화
      deleteFile("/drink_log.txt");
      // 시간도 초기화
      Sec = 0;
      Min = 0;
    }
    // 연결은 되었지만 이전에 연결한 기록이 없는 상황
    // 연결된 기기에 데이터 전송
    if (deviceConnected && !oldDeviceConnected) {	// connecting
      oldDeviceConnected = deviceConnected;
      
      // 메모리에 데이터 저장 중단
      
      readFile("/drink_log.txt");
      Serial.println("send data");
      delay(5000);
      // 연결된 기기에 블루투스가 끊긴 후 부터 저장된 데이터 전송
      pCharacteristic->setValue((uint8_t*)strValue.c_str(), strValue.length());
      pCharacteristic->notify();
    }

    // 끊김 확인 이후 메모리 시스템이 데이터 저장, 코스터의 유저가 존재하면서, 블루투스에 연결되지 않았을 때
    if(!deviceConnected && user_exist == 1){
      ////---------타이머---------////
      String min_data = "";
      String newline = "\r\n";
      String data  = "";

      Sec++;

      if(Sec % 60 == 0 && Sec != 0)
      {
        Sec = 0;
        Min++;
        Serial.print("Min = ");
        Serial.println(Min);
      }

      // m음료타입수분섭취량
      // 으로 데이터 저장
      if(drink_before != drink_now){
        min_data = (String)Min;
        String drink_st = (String)changeliter;
        data = min_data + drink_type + drink_st + newline;
        const char* Data = data.c_str();

        appendFile("/drink_log.txt", Data);

        drink_before = drink_now; 

      }
    }
      Serial.println(presssensor_value);
      delay(1000);
  }
  
}

////---------압력센서--------////
void changeLiter(int drink){
  int now_drink = drink_before - drink;

  changeliter = now_drink/liter10;
  total_drink += changeliter;

  Serial.print("nowdrink: ");
  Serial.println(now_drink);
  Serial.print("changeliter: ");
  Serial.println(changeliter);

}

////---------SPIFFS---------////

void listDir(const char * dirname){
  Serial.printf("Listing directory: %s\r\n", dirname);
  File root = SPIFFS.open(dirname); // ESP8266은 확장자 "Dir"과 "File"로 구분해서 사용, ESP32는 "File"로 통합
  File file = root.openNextFile();
  while(file){ // 다음 파일이 있으면(디렉토리 또는 파일)
    if(file.isDirectory()){ // 다음 파일이 디렉토리 이면
      Serial.print("  DIR : "); Serial.println(file.name()); // 디렉토리 이름 출력
    } else {                // 파일이면
      Serial.print("  FILE: "); Serial.print(file.name());   // 파일이름
      Serial.print("\tSIZE: "); Serial.println(file.size()); // 파일 크기
    }
    file = root.openNextFile();
  }
}

void readFile(const char * path){
  Serial.printf("Reading file: %s\r\n", path);
  File file = SPIFFS.open(path, "r");
  if(!file || file.isDirectory()){
    Serial.println("- failed to open file for reading");
    return;
  }
  
  // 문자열 초기화
  strValue="";
  Serial.println("read from file:");
  while(file.available()){
    int data = file.read();
    Serial.write(data);
    strValue = strValue + (char)data;
  }

  Serial.println("string");
  Serial.println(strValue);  
}


void infoFile(const char * path){
  Serial.printf("Reading file: %s\r\n", path);
  File file = SPIFFS.open(path, "r");
  if(!file || file.isDirectory()){
    Serial.println("- failed to open file for reading");
    user_exist = 0;
    return;
  }
  
  // 데이터 구분
  int next_idx = 0;
  // 목표 초기화
  Goal = 0;
  // 데이터가 존재하는 것은 사용자에 대한 정보가 있다는 것임
  user_exist = 1;
  
  Serial.println("read from file:");
  while(file.available()){
    int data = file.read();
    Serial.write(data);

    if(data == ' ') next_idx=1;
    else if(next_idx==0)
      Goal = Goal*10 + (int)data;
    else if(next_idx==1)
      Alarm = (char)data;
  }

  Serial.println("Goal, Alarm");
  Serial.print(Goal);
  Serial.print(", ");
  Serial.print(Alarm);
}


void writeFile(const char * path, const char * message){
  Serial.printf("Writing file: %s\r\n", path);
  File file = SPIFFS.open(path, "w");
  if(!file){
    Serial.println("failed to open file for writing");
    return;
  }
  if(file.print(message)){
    Serial.println("file written");
  } else {
    Serial.println("frite failed");
  }
}

void appendFile(const char * path, const char * message){
  Serial.printf("Appending to file: %s\r\n", path);
  File file = SPIFFS.open(path, "a");
  if(!file){
    Serial.println("failed to open file for appending");
      return;
  }
  if(file.print(message)){
    Serial.println("message appended");
  } else {
    Serial.println("append failed");
  }
}

void renameFile(const char * path1, const char * path2){
  Serial.printf("Renaming file %s to %s\r\n", path1, path2);
  if (SPIFFS.rename(path1, path2)) {
    Serial.println("file renamed");
  } else {
    Serial.println("rename failed");
  }
}

void deleteFile(const char * path){
  Serial.printf("Deleting file: %s\r\n", path);
  if(SPIFFS.remove(path)){
    Serial.println("file deleted");
  } else {
    Serial.println("delete failed");
  }
}




////---------NeoPixel--------////
void colorWipe(uint32_t color, int wait, int gauge) {
  for (int i = 0; i < gauge; i++) { // For each pixel in strip...
    strip.setPixelColor(i, color);         //  Set pixel's color (in RAM)
    strip.show();                          //  Update strip to match
    delay(wait);                           //  Pause for a moment
  }
}

void rainbow(int wait) {  
  for (long firstPixelHue = 0; firstPixelHue < 2 * 65536; firstPixelHue += 256) {
    for (int i = 0; i < strip.numPixels(); i++) { 
      int pixelHue = firstPixelHue + (i * 65536L / strip.numPixels());      
      strip.setPixelColor(i, strip.gamma32(strip.ColorHSV(pixelHue)));
    }
    strip.show();
    delay(wait);
  }
}
