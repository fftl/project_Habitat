#include <Adafruit_NeoPixel.h>

//고정적으로 사용할 내용을 미리 선언
#define LED_PIN 12     //네오픽셀에 신호를 줄 핀번호
#define LED_COUNT 9  //아두이노에 연결된 네오픽셀의 개수

// 우리가 사용하는 모듈은 NEO_RGBW!!!
Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_RGBW + NEO_KHZ800);
// 라이브러리에서 네오픽셀 객체를 선언
// 첫번째 인자 = 네오픽셀의 개수
// 두번째 인자 = 신호를 출력할 핀번호
// 세번째 인자 = 네오픽셀의 종류에따라 맞는 것을 아래대로 설정해주면 된다.
    /*
     * 종류에 따른 설정
     * NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs) 
     * NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
     * 
     * NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
     * NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)
     * NEO_RGBW    Pixels are wired for RGBW bitstream (NeoPixel RGBW products)
     */
    
void setup() {
  Serial.begin(115200);
  strip.begin();           // INITIALIZE NeoPixel strip object (REQUIRED)
  strip.show();            // 네오픽셀에 빛을 출력하기 위한 것인데 여기서는 모든 네오픽셀을 OFF하기 위해서 사용한다.
  //colorWipe(strip.Color(200, 200, 200), 50);
  strip.setBrightness(50); // 네오픽셀의 밝기 설정(최대 255까지 가능)
  // delay(4000);
  // rainbow(5); 
}

void loop() {
  
  // 스트립 길이를 따라서 설정된 색으로 채운다.
  // strip.Color(Green,   Red,   Blue) 스트립의 색상을 RGB순서대로 세팅해준다.각RGB마다 0~255까지 설정가능
  // colorWipe(스트립 색상, 딜레이 시간)
  // colorWipe(strip.Color(30,   0,   255), 50); // Red
  // colorWipe(strip.Color(  0, 255,   0), 50); // Green
  // colorWipe(strip.Color(  0,   0, 255), 50); // Blue
  // colorWipe(strip.Color(255, 255, 255), 50); // White

  
  // // 극장간판에 달린 전구가 빛나는것과 유사한 효과
  // // theaterChase(스트립 색상, 딜레이 시간)
  // theaterChase(strip.Color(127, 127, 127), 50); // White, half brightness
  // theaterChase(strip.Color(127,   0,   0), 50); // Red, half brightness
  // theaterChase(strip.Color(  0,   0, 127), 50); // Blue, half brightness

  // // 전체 스트립에 색을 흐르는 무지개빛처럼 돌아가며 출력
  for(int i = 0; i<3; i++)
  {
    colorWipe(strip.Color(0, 255, 0), 50, 9);
    delay(500);
  } 
  // // 위에 theaterChase효과를 무지개빛으로 출력
  // theaterChaseRainbow(50); 
  
  //해당 함수들은 밑에 구현되어있다.
}

void colorWipe(uint32_t color, int wait, int gauge) {
  for (int i = 0; i < gauge; i++) { // For each pixel in strip...
    strip.setPixelColor(i, color);         //  Set pixel's color (in RAM)
    strip.show();                          //  Update strip to match
    delay(wait);                           //  Pause for a moment
  }
  delay(2000);
  strip.clear();
  strip.show();
}

void theaterChase(uint32_t color, int wait) {
  for (int a = 0; a < 10; a++) { // Repeat 10 times...
    for (int b = 0; b < 3; b++) { //  'b' counts from 0 to 2...
      strip.clear();         //   Set all pixels in RAM to 0 (off)
      // 'c' counts up from 'b' to end of strip in steps of 3...
      for (int c = b; c < strip.numPixels(); c += 3) {
        strip.setPixelColor(c, color); // Set pixel 'c' to value 'color'
      }
      strip.show(); // Update strip with new contents
      delay(wait);  // Pause for a moment
    }
  }
}
// 코스터에 사용할 함수
void rainbow(int wait) {  
  for (long firstPixelHue = 0; firstPixelHue < 3*65536; firstPixelHue += 256) {
    for (int i = 0; i < strip.numPixels(); i++) { 
      int pixelHue = firstPixelHue + (i * 65536L / strip.numPixels());      
      strip.setPixelColor(i, strip.gamma32(strip.ColorHSV(pixelHue)));
    }
    strip.show();
    delay(wait);
  
  }
    delay(3000);
    strip.clear();
    strip.show();
}

void theaterChaseRainbow(int wait) {
  int firstPixelHue = 0;
  for (int a = 0; a < 30; a++) { 
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