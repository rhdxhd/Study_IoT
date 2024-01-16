// 라이브러리 가려오기
#include <Servo.h>
// 서보모터 객체를 만든다
Servo myservo;

int trig = 7;
int echo = 8;

int interval = 3; //3초
int lastMillis = 0;

void setup() {
  Serial.begin(9600);

  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT); 

  // 서보모터 포트를 지정한다
  myservo.attach(13);
  // 서보모터를 0도로 초기화시킨다(0~179)
  myservo.write(0);
}

void loop() {
  digitalWrite(trig, HIGH);
  delay(10);
  digitalWrite(trig, LOW);

  int duration = pulseIn(echo, HIGH);
  int distance = (duration/2) / 29.1;

  Serial.print(distance);
  Serial.println("cm");
  delay(100); 

  if(distance > 0 && distance <= 100){
    if( distance <= 10 ) { //차가 진입하면 9
      Serial.println("aaa");
      myservo.write(90);  //90도로 차단기 올린다. 
      //delay(300);
      lastMillis = millis()/1000; // 초단위로 바뀐다 / 시간을 lastmillis 변수에 저장
    }else if(distance > 10){  //차가 지나가면
    
      if(millis()/1000 - lastMillis >= interval ) {
        Serial.println("bbb");
          myservo.write(0);
      }
    }
  }
}