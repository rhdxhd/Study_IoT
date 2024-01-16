// 시리얼모니터 열고
// AT 엔터 -> 응답 OK
// AT+NAMEXXXXXX -> 응답 OKsetname : 이름변경
// AT+PIN1234 -> 응답 OKsetPIN : 비밀번호변경
// AT+BAUD4 ->   응답 OK9600 : 통신속도변경 
//               1 -> 1200  2 -> 2400  3 -> 4800  4 -> 9600  5 ->19200  6 -> 38400  7 -> 57600  8 -> 115200
// AT+ROLE=S ->  응답 OK+ROLE:S   =>   M: Master S: Slave (기본 SLAVE 변경안해줘도 됨)

#include <SoftwareSerial.h>
int led = 7;

SoftwareSerial BT(10, 11);   // RX:11, TX:10 => 연결 반대로

void setup() {
  pinMode(led, OUTPUT);
  Serial.begin(9600);
  BT.begin(9600);
}

void loop(){

  if (BT.available()) {  //블루투스에서 받는 것
    char ch = BT.read();
    Serial.println(ch);

    if(ch == '1') {
      digitalWrite(led, HIGH);
    }else if(ch == '2') {
      digitalWrite(led, LOW);
    }
  }

  if (Serial.available())  // 시리얼에서 블루투스로 보내는 것
  {
    BT.write(Serial.read());
  }
}