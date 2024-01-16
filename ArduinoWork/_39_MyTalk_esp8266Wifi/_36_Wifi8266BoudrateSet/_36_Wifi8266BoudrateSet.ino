#include <SoftwareSerial.h>

SoftwareSerial myWifi(10, 11); // RX:9번핀, TX:10번핀

void setup() {
  Serial.begin(9600);
  // 일단 Both NL & CR 로 변경 115200 보트레이트로 변경
  // 새로 구입한 것은 통신속도가 115200 이므로 글자가 깨져 보이므로 
  // 처음에는 ESP8266.begin(115200);로 해주고 AT 엔터 OK 확인
  // AT+UART_DEF=9600,8,1,0,0으로 해준후 OK가 뜨면 
  // 통신속도를 9600 으로 변경한다
  myWifi.begin(9600);//기본 통신 속도가 9600인 제품은 9600으로 수정해 주세요
}

void loop() {
  if (myWifi.available()) {
    Serial.write(myWifi.read());
  }
  if (Serial.available()) {
    myWifi.write(Serial.read());
  }
}
