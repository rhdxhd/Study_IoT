//YWROBOT
//Compatible with the Arduino IDE 1.0
//Library version:1.1
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>

// 0x3F, 0x27
LiquidCrystal_I2C lcd(0x27,16,2);  // set the LCD address to 0x27 for a 16 chars and 2 line display

void setup(){
  lcd.init();      // initialize the lcd 

  // Print a message to the LCD.
  lcd.backlight(); // 뒤에 불 밝혀줌
  lcd.setCursor(0,0);
  lcd.print("Hello, world!");
  // lcd.setCursor(0,1);
  // lcd.print("Ywrobot Arduino!");

}


void loop(){
  // 글자를 왼쪽으로 움직이기
  for(int posCnt=0; posCnt<13; posCnt++) {
    lcd.scrollDisplayLeft();
    delay(400);
  }

  // 글자를 왼쪽으로 움직이기
  for(int posCnt=0; posCnt<29; posCnt++) {
    lcd.scrollDisplayRight();
    delay(150);
  }

  // 글자를 왼쪽으로 움직이기
  for(int posCnt=0; posCnt<16; posCnt++) {
    lcd.scrollDisplayLeft();
    delay(150);
  }

  // 10번 깜박이게 만들기
  for(int i=0; i<10; i++) {
    lcd.clear();
    delay(300);
    lcd.setCursor(0,0);
    lcd.print("Hello, World");
    lcd.setCursor(0,1);
    lcd.print(millis()/1000);
    delay(500);
  }



}
