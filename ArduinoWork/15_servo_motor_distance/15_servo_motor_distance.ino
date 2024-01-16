// 라이브러리 가려오기
#include <Servo.h>
// 서보모터 객체를 만든다
Servo myservo;

int trig = 7;
int echo = 8;

void setup() {
  Serial.begin(9600);

  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT); 

  // 서보모터 포트를 지정한다
  myservo.attach(10);
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

  if(distance <= 10){
    myservo.write(90);
    //delay(300);
  }else if(distance >10){
    myservo.write(0);
  }

  // for(int i=0; i<180; i++){
  //   myservo.write(i);
  //   delay(10);
  // }

  // for(int i=179; i>=0; i--){
  //   myservo.write(i);
  //   delay(10);
  // }


}