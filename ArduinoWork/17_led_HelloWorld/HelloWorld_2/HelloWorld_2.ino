//YWROBOT
//Compatible with the Arduino IDE 1.0
//Library version:1.1
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>

int trig = 7;
int echo = 8;

// 0x3F, 0x27
LiquidCrystal_I2C lcd(0x27,16,2);  // set the LCD address to 0x27 for a 16 chars and 2 line display

void setup(){
  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
  Serial.begin(9600);
 
  lcd.init();      // initialize the lcd 
  // Print a message to the LCD.
  lcd.backlight(); // 뒤에 불 밝혀줌
  lcd.setCursor(0,0);
  // lcd.setCursor(0,1);
  // lcd.print("Ywrobot Arduino!");

}

void loop(){
  digitalWrite(trig, LOW);
  delay(10);
  digitalWrite(trig, HIGH);
  delay(10);
  digitalWrite(trig, LOW);
  int duration = pulseIn(echo, HIGH);
  int distance = (duration/2) /29.1;
  Serial.print(distance);
  Serial.println("cm");


  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print(distance);
  lcd.print("cm");

  delay(500);
}
