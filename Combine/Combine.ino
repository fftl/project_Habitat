////---------NeoPixel---------////
#include <Adafruit_NeoPixel.h>

//고정적으로 사용할 내용을 미리 선언
#define LED_PIN 13     //네오픽셀에 신호를 줄 핀번호
#define LED_COUNT 10  //아두이노에 연결된 네오픽셀의 개수

////---------압력센서---------////
int before_drink=0;
int now_drink=0;
int total_drink=0;

////---------타이머---------////
#include "esp_system.h"

const int wdtTimeout = 3000;  //time in ms to trigger the watchdog
hw_timer_t *timer = NULL;
int Min, Hour;

void IRAM_ATTR resetModule() {
  ets_printf("reboot\n");
  esp_restart();
}

////---------SPIFFS---------////
#include "SPIFFS.h"
String strValue = "";

////---------블루투스---------////
#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

bool deviceConnected = false;
bool oldDeviceConnected = false;
bool write_drink_SPIFFS = false;
int cnt = 0;
String receive_label = "";
String receive_data = "";
int goal = 0;
int alarm = 0;
String account = ""; // 계정

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
    int isvalue=0;
    if (value.length() > 0) {
      Serial.print("Receive");
      Serial.print("Received value: ");
      for (int i = 0; i < value.length(); i++) {
        Serial.print(value[i]);

        // lable : data
        if(value[i]==":")   isvalue=1;
        
        if(isvalue==0)
          receive_label = receive_label + (char)value[i];
        else if(isvalue==1)
          receive_data = receive_data + (int)value[i];
      }

      if(receive_label=="goal")
        goal = receive_data;
      if(receive_label=="alarm")
        alarm = receive_data;
      
      Serial.println();
    }
  }
};

void setup() {
  Serial.begin(115200);

  ////---------NeoPixel---------////
  strip.begin();           // INITIALIZE NeoPixel strip object (REQUIRED)
  strip.show();            // 네오픽셀에 빛을 출력하기 위한 것인데 여기서는 모든 네오픽셀을 OFF하기 위해서 사용한다.
  strip.setBrightness(50); // 네오픽셀의 밝기 설정(최대 255까지 가능)
  
  ////---------SPIFFS---------////
  Serial.println();
  if (!SPIFFS.begin()) {
    Serial.println("Failed to mount file system");
    return;
  }

  SPIFFS.format();
  listDir("/"); 
  writeFile("/hello.txt", "");



  ////---------블루투스---------////
  Serial.println("Starting BLE...");
  // BLE 장치 이름
  BLEDevice::init("률류");
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
  Serial.println("running setup");
  timer = timerBegin(0, 80, true);                  //timer 0, div 80
  timerAttachInterrupt(timer, &resetModule, true);  //attach callback
  timerAlarmWrite(timer, wdtTimeout * 1000, false); //set time in us
  timerAlarmEnable(timer);                          //enable interrupt

  Min = 0;
  Hour = 0;
}

void loop() {
  ////--------압력센서--------////
  // 센서값 받아오면 now_drink에 저장
  // now_drink = ???;

  if(goal == total_drink) theaterChaseRainbow(50);
  // 게이지 상승 
  if(goal*0.1 == total_drink)    theaterChaseRainbow(1);
  else if(goal*0.2 == total_drink)    theaterChaseRainbow(2);
  else if(goal*0.3 == total_drink)    theaterChaseRainbow(3);
  else if(goal*0.4 == total_drink)    theaterChaseRainbow(4);
  else if(goal*0.5 == total_drink)    theaterChaseRainbow(5);
  else if(goal*0.6 == total_drink)    theaterChaseRainbow(6);
  else if(goal*0.7 == total_drink)    theaterChaseRainbow(7);
  else if(goal*0.8 == total_drink)    theaterChaseRainbow(8);
  else if(goal*0.9 == total_drink)    theaterChaseRainbow(9);

  








  ////---------블루투스---------////
  // 연결이 잘 되고 있는 상황
  // 데이터 갱신 후 notification
  if(deviceConnected){	// notify changed value
    if(before_drink != now_drink){
      pCharacteristic->setValue((uint8_t*)strValue.c_str(), strValue.length());
      pCharacteristic->notify();
      cnt++;
      delay(3);   // client가 올바르게 정보를 수산할 수 있도록 여유의 시간(레퍼런스에서 3ms)  
      before_drink = now_drink;
    }
  }
  // 이전에 연결한 기록이 있는 상태에서 견결이 끊기 상황
  if (!deviceConnected && oldDeviceConnected) {	// disconnecting
    delay(500); // give the bluetooth stack the chance to get things ready
    pServer->startAdvertising(); // restart advertising
    Serial.println("start advertising");
    oldDeviceConnected = deviceConnected;
    write_drink_SPIFFS = true;
    // 끊김 확인한 후에 메모리 초기화
    SPIFFS.format();
  }
  // 연결은 되었지만 이전에 연결한 기록이 없는 상황
  // 연결된 기기에 데이터 전송
  if (deviceConnected && !oldDeviceConnected) {	// connecting
    oldDeviceConnected = deviceConnected;
    write_drink_SPIFFS = false;
    pCharacteristic->setValue((uint8_t*)strValue.c_str(), strValue.length());
    pCharacteristic->notify();
    delay(3);
  }
  // 끊김 확인 이후
  if(!deviceConnected && write_drink_SPIFFS){
    ////---------타이머---------////
    String data1 = "";
    String data2 =  "";
    String data3 =  "";
    String data = "";
    timerWrite(timer, 0); //reset timer (feed watchdog)
    if((millis()/1000) % 60 == 0)
    {
      Min++;
      Serial.print("Min = ");
      Serial.println(Min);

      // 파일 생성 및 데이터 쓰기
      data1 = "min : ";
      data2 =  (String)Min;
      data3 =  "\r\n";
      data = data1 + data2 + data3;
      const char* Data = data.c_str();

    }
    if(Min % 60 == 0 && Min != 0)
    {
      Min = 0;
      Hour++;
      Serial.print("Hour = ");
      Serial.println(Hour); 
    }

    Serial.print("Time to = ");
    Serial.println(millis()/1000); 
    
    if(before_drink != now_drink){
      String st = ":";
      String drink_st = (String)now_drink;
      String d = Data + st + drink_st;

      appendFile("/hello.txt", d);

      before_drink = now_drink;
    }
    
  }

  delay(1000); //simulate work
  
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
// NeoPixel에 달린 LED를 각각 다른색으로 시작하여 다양한색으로 5번 반복한다
void theaterChaseRainbow(int wait) {
  int firstPixelHue = 0;
  for (int a = 0; a < 5; a++) { 
    for (int b = 0; b < 3; b++) {
      strip.clear();         
      for (int c = b; c < strip.numPixels(); c += 3) {        
        int hue = firstPixelHue + c * 65536L / strip.numPixels();
        uint32_t color = strip.gamma32(strip.ColorHSV(hue)); // hue -> RGB
        strip.setPixelColor(c, color);
      }
      strip.show();                
      delay(wait);                 
      firstPixelHue += 65536 / 90; 
    }
  }
}