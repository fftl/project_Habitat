const int pressSensor = 27;      // 압력센서 SIG를 26번 핀에 연결

int Goal;
int init_drink;
int before_drink = -1;
int now_drink;
int total_drink;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);

}

void loop() {
  int presssensor_value = analogRead(pressSensor);
  int gram = 7.7712*exp(0.0022*presssensor_value);

  changeLiter(presssensor_value);
  Serial.print("presssensor_value");
  Serial.println(presssensor_value);
  Serial.print("gram");
  Serial.println(gram);
  delay(1000);
}

void changeLiter(int drink){
  int gram = 7.7712*exp(0.0022*presssensor_value);

}