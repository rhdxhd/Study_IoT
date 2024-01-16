// 시리얼 모니터에 가변저항값을 출력해보세요
// A0 아날로그 인 -> 초기화 안해줘도 됨, 해도 됨
#define gabun  A0
int ledR = 9;

void setup() {
  pinMode(gabun, INPUT);
  pinMode(ledR, OUTPUT);

  //시리얼 모니터 초기화
  Serial.begin(9600);
}

void loop() {
  int val = analogRead(gabun);


  Serial.println(val);
  // 서로 다른 범위의 값을 매칭시켜준다
  int value = map(val, 0, 1023, 0, 255);
  analogWrite(ledR, value);

  delay(500);
}


