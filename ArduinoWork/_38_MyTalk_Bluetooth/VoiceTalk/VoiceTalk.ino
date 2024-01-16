// 블루투스
#include <SoftwareSerial.h>
SoftwareSerial bluetooth(10 , 11); // RX:11, TX:10 => 연결 반대로

int livingRoomLight = 5;
int gasLight = 6;

int isLRLight = 0;
int isGasLight = 0;

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);
  
  pinMode(livingRoomLight, OUTPUT);   
  pinMode(gasLight, OUTPUT); 
    

}

char android;
char unoBoard;

void loop() {
  
  if (bluetooth.available()) {
      android = bluetooth.read();

      delay(500);
      
      Serial.println(android);

      // if(android == 'a'){
    
      //   // 불 켜진건가 꺼진건가 물어보기
        
      //       Serial.println("거실등여부시작");
      //       //거실 전원 ON, OFF여부
      //       int isLRLight = digitalRead(livingRoomLight);
      //       Serial.println(isLRLight);
      //       if(isLRLight == LOW){
      //         Serial.println('b'); // 거실전원 꺼진상태
      //         bluetooth.println('b');
      //         delay(500);
      //       }else{
      //         Serial.println('c'); // 거실전원 켜진상태
      //         bluetooth.println('c');
      //         delay(500);
      //       }        
      //   }
    
      //   if (android == 'd') {      
          
      //       Serial.println("가스여부시작");
      //       //가스 전원 ON, OFF여부
      //       int isGasLight = digitalRead(gasLight);
      //       if(isGasLight == LOW){
      //         Serial.print('e'); // 가스 전원 꺼진상태
      //         bluetooth.println('e');
      //         delay(500);
      //       }else{
      //         Serial.print('f'); // 가스 전원 켜진상태
      //         bluetooth.println('f');
      //         delay(500);
      //       }
          
      //   }
      
      







      // 불켜고 끄기
      if (android == 'g') {
        Serial.println("거실전원 ON");
        //거실 전원ON
        digitalWrite(livingRoomLight, HIGH);
      } else if (android == 'h') {
        //거실 전원OFF
        digitalWrite(livingRoomLight, LOW);
      }
  
      if (android == 'i') {
        //가스 전원ON
        digitalWrite(gasLight, HIGH);
      } else if (android == 'j') {
        //가스 전원OFF
        digitalWrite(gasLight, LOW);
      }

    }
  
 }
  
  

  

  




