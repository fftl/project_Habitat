#include "SPIFFS.h"

void setup() {
  Serial.begin(115200);
  Serial.println();
  SPIFFS.begin();
  SPIFFS.format();
  Serial.println("Format complete!");

}

void loop() {
  // put your main code here, to run repeatedly:

}
