const int pressSensor = 26;

int Goal = 1000;
int init_drink;
int before_drink = -1;
int now_drink;
int total_drink;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  int presssensor_value = analogRead(pressSensor);

  changeLiter(presssensor_value);
  Serial.println(presssensor_value);
  delay(1000);
}


void changeLiter(int drink){
  now_drink = init_drink - drink;

  /// 값 변경

}