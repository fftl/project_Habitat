#include "esp_system.h"
/// 만약 millis()함수 초기화가 안된다면 블투연결 후 보드 자체를 reboot시키는 것도 방법
const int wdtTimeout = 3000;  //time in ms to trigger the watchdog
hw_timer_t *timer = NULL;
int Min, Hour;
void IRAM_ATTR resetModule() {
  ets_printf("reboot\n");
  esp_restart();
}

void setup() {
  Serial.begin(115200);
  Serial.println();
  Serial.println("running setup");
  timer = timerBegin(0, 80, true);                  //timer 0, div 80
  timerAttachInterrupt(timer, &resetModule, true);  //attach callback
  timerAlarmWrite(timer, wdtTimeout * 1000, false); //set time in us
  timerAlarmEnable(timer);                          //enable interrupt

  Min = 0;
  Hour = 0;
}

void loop() {
  Serial.println("running main loop");

  timerWrite(timer, 0); //reset timer (feed watchdog)
  if((millis()/1000) % 60 == 0 && millis() != 0)
  {
    Serial.print("Min = ");
    Serial.println(Min); 
    Min++;
  }
  if(Min % 60 == 0 && Min != 0)
  {
    Min = 0;
    Hour++;
    Serial.print("Hour = ");
    Serial.println(Hour); 
  }

  delay(1000); //simulate work
  
  Serial.print("Time to = ");
    Serial.println(millis()/1000); 
  
}
