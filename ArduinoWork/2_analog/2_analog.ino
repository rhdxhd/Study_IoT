// 포트번호를 전역변수로 선언한다
int ledR = 10, ledY = 11;

void setup() {
  // put your setup code here, to run once:
  pinMode(ledR, OUTPUT);
  pinMode(ledY, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  analogWrite(ledR, 100);
  delay(100);
  analogWrite(ledR, 170);
  delay(100);
  analogWrite(ledR, 255);
  delay(100);
  analogWrite(ledR, 0);
  delay(100);

  analogWrite(ledY, 100);
  delay(100);
  analogWrite(ledY, 170);
  delay(100);
  analogWrite(ledY, 255);
  delay(100);
  analogWrite(ledY, 0);
  delay(100);

}
