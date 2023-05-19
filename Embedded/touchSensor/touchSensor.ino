const int touchsensor = 25;      // 터치센서 SIG를 14번 핀에 연결
int state = 0;                  // 현재 상태 변수 선언

int cnt=0;
void setup() {

  Serial.begin (115200);        // 시리얼 모니터 통신 설정
  pinMode (touchsensor, INPUT);     // 터치센서 신호값을 입력으로 설정 

}

void loop() {
  state = digitalRead (touchsensor); 
  if(state == HIGH)
  {
    cnt++;
  }
  if(cnt>=2)
  {
    Serial.println("w");
    cnt=0;
  }

  delay(1000);
}