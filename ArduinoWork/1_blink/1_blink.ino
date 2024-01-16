//실행시 한번만 실행
void setup() {
  // put your setup code here, to run once:
  // 각 포트에 대한 모드를 지정해야 한다.
  pinMode(13, OUTPUT);
}

//계속해서 실행
void loop() {
  // put your main code here, to run repeatedly:
  // LED를 켠다
  digitalWrite(13, HIGH);
  // 1초간 불이 켜질 시간을 준다 - 1000분의 1초
  delay(1000); // -> 1초
  // LED를 끈다
  digitalWrite(13, LOW);
  // 1초간 불이 유지할 시간을 준다
  delay(1000);


}
