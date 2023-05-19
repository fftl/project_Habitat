const int pressSensor = 26;
const int touchsensor = 14; 

int state=0;
int cnt=0;
int Goal = 1000;
int init_water=0;
int sensing_flag = 0;
int value_sum=0;
int value_cnt=0;
int cork=0;
int drink_before=0;
int liter10 = 9;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  pinMode (touchsensor, INPUT);
}

void loop() {

  int presssensor_value = analogRead(pressSensor);
  state = digitalRead (touchsensor); 

  ////----------터치센서---------////
  if(state == HIGH)
  {
    cnt++;
  }

  // cork가 누르는 압력이 최대 600 == 컵이 있는 경우
  if(presssensor_value >= 700)
  {
    ////----------터치센서-----------////
    // 터치센서 누름
    if(cnt>=2)
    {
      Serial.println("w");
      cnt=0;
      // 터치센서 동작하게되면 압력센서 출력값이 올라감
      init_water = presssensor_value - 750 - cork;
      Serial.println(presssensor_value - 750);
      
      drink_before = init_water;
      value_sum=0;
      value_cnt=0;
    }

    // 영점 세팅 이후에 물을 마신 후의 데이터 처리
    if(sensing_flag == 1)
    {
      if(value_cnt>=10)
      {
        value_sum = value_sum/value_cnt;
        changeLiter(value_sum);
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

  Serial.println(presssensor_value);

  delay(1000);
}


void changeLiter(int drink){
  int now_drink = drink_before - drink;
  drink_before = drink;

  int changeliter = now_drink/liter10;

  Serial.print("nowdrink: ");
  Serial.println(now_drink);
  Serial.print("changeliter: ");
  Serial.println(changeliter);

}