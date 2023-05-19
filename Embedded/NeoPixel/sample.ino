#include <Adafruit_NeoPixel.h>


#define LED_PIN   13
#define LED_COUNT 1


Adafruit_NeoPixel pixel(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);


void setup() {
  pixel.begin();           // INITIALIZE NeoPixel strip object (REQUIRED)
  pixel.show();            // Turn OFF all pixels ASAP
  pixel.setBrightness(200); // Set BRIGHTNESS to about 1/5 (max = 255)
}


void loop() {
  pixel.setPixelColor(0, pixel.Color(255, 255, 0));
  pixel.show();
  delay(500); // 0.5초 대기

  pixel.setPixelColor(0, pixel.Color(0, 0, 0));  
  pixel.show();
  delay(500); // 0.5초 대기

}


