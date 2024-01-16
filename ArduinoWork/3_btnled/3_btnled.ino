int btn1 = 12;
int ledR = 13;

void setup() {
  // put your setup code here, to run once:
  pinMode(btn1, INPUT);
  pinMode(ledR, OUTPUT);

  // 시리얼모니터에 값을 출력하기위해 초기화 시킨다
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:

  int val = digitalRead(btn1);
  Serial.println(val); // 0,1
  digitalWrite(ledR, val);


  // if(val ==1) {
  //   digitalWrite(ledR, HIGH);

  // }else{
  //   digitalWrite(ledR, LOW);
  // }

  delay(50);

}
