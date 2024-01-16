const int btnPin = 12;  // 버튼이 연결된 디지털 핀 번호
const int ledPin = 13; // LED가 연결된 디지털 핀 번호

int btnState = 0;      // 버튼 상태를 저장하는 변수
int lastBtnState = 0;  // 이전 버튼 상태를 저장하는 변수
int ledState = LOW;  // LED 상태를 저장할 변수

void setup() {
  pinMode(btnPin, INPUT); //버튼
  pinMode(ledPin, OUTPUT); //led
}

void loop() {
  btnState = digitalRead(btnPin);

  // 버튼 상태가 바뀌면 동작 수행
  if (btnState != lastBtnState) {
    delay(50); // 디바운싱을 위한 짧은 딜레이

    // 버튼이 눌렸을 때
    if (btnState == HIGH) {
      digitalWrite(ledPin, !digitalRead(ledPin)); // LED 상태를 토글
    }
  }

  lastBtnState = btnState; // 현재 버튼 상태를 이전 버튼 상태로 저장
}
