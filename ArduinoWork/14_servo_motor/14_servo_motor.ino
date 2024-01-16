//라이브러리 가져오기
#include <Servo.h>
//서보모터 객체를 만든다
Servo myservo;

void setup() {
  // 서보모터 포트를 지정한다
  myservo.attach(10);
  // 서보모터를 0도로 초기화시킨다 (0~179)
  myservo.write(0);

}

void loop() {
  for(int i=0; i<180; i++) {
    myservo.write(i);
    delay(10);
  }

  for(int i=179; i>=0; i--) {
    myservo.write(i);
    delay(10);
  }

}
