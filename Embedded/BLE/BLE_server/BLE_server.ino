/*
    Based on Neil Kolban example for IDF: https://github.com/nkolban/esp32-snippets/blob/master/cpp_utils/tests/BLE%20Tests/SampleServer.cpp
    Ported to Arduino ESP32 by Evandro Copercini
    updates by chegewara
*/

#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>
#include <BLE2902.h>
// 해당 사이드에서 uuid 생성 가능:
// https://www.uuidgenerator.net/
uint32_t value = 0;
bool deviceConnceted = false;
bool oldDeviceConnected = false;

BLEServer* pServer = NULL;
BLECharacteristic* pCharacteristic = NULL;

#define SERVICE_UUID        "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
#define CHARACTERISTIC_UUID "beb5483e-36e1-4688-b7f5-ea07361b26a8"

class MyServerCallbacks: public BLEServerCallbacks {
  void onConnect(BLEServer* pServer) {deviceConnected = true;};
  void onDisconnect(BLEServer* pServer) {deviceConnected = false;}
};

void setup() {
  Serial.begin(115200);
  Serial.println("Starting BLE work!");
  // BLE 장치 이름
  BLEDevice::init("률류");
  // BLE 장치를 BLE 서버로 설정
  pServer = BLEDevice::createServer();
  pServer->setCallbacks(new MyServerCallbacks());
  // 미리 정의한 UUID로 서버의 서비스 생성
  BLEService *pService = pServer->createService(SERVICE_UUID);
  // UUID 전달, 읽기, 쓰기 속성으로 설정
  pCharacteristic = pService->createCharacteristic(
                                         CHARACTERISTIC_UUID,
                                         BLECharacteristic::PROPERTY_READ |
                                         BLECharacteristic::PROPERTY_WRITE|
                                         BLECharacteristic::PROPERTY_NOTIFY|
                                         BLECharacteristic::PROPERTY_INDICATE
                                       );
  // 전달하고자 하는 값을 넣으면 됨, 데이터 갱신
  pCharacteristic->setValue("Hello World says Neil");
  // characteristic의 특성을 설명하는 메타데이터를 설정할 수 있음
  // 이 문장이 없으면 notification이 제대로 이뤄지지 않는다
  // 데이터가 갱신될 때 마다 client가 적절한 callback을 수행하도록 하기 위해서 사용 필수
  pCharacteristic->addDescriptor(new BLE2902());

  // 서비스 시작
  pService->start();

  // advertising 을 하여 BLE 장치를 스캔하고 찾을 수 있도록 함
  // adversing 생성
  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  // advertising 의 UUID 설정
  pAdvertising->addServiceUUID(SERVICE_UUID);
  // 스캔 기능하도록 설정
  pAdvertising->setScanResponse(false);
  // 아이폰 관련 연결 보조 설정(최대, 최소 주파수 대역폭 설정)
  pAdvertising->setMinPreferred(0x06);
  pAdvertising->setMaxPreferred(0x12);
  // advertising 시작
  BLEDevice::startAdvertising();
  Serial.println("Characteristic defined! Now you can read it in your phone!");
}

void loop() {
  // 연결이 잘 되고 있는 상황
  // 데이터 갱신 후 notification
  if(deviceConnected){	// notify changed value
    pCharacteristic->setValue((uint8_t*)&value, 4);
    pCharacteristic->notify();
    value++;
    delay(3);   // client가 올바르게 정보를 수산할 수 있도록 여유의 시간(레퍼런스에서 3ms)  
  }
  // 이전에 연결한 기록이 있는 상태에서 견결이 끊기 상황
  if (!deviceConnected && oldDeviceConnected) {	// disconnecting
    delay(500); // give the bluetooth stack the chance to get things ready
    pServer->startAdvertising(); // restart advertising
    Serial.println("start advertising");
    oldDeviceConnected = deviceConnected;
  }
  // 연결은 되었지만 이전에 연결한 기록이 없는 상황
  if (deviceConnected && !oldDeviceConnected) {	// connecting
    oldDeviceConnected = deviceConnected;
  }
  delay(2000);
}