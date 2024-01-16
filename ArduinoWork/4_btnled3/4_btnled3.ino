const int btnPin = 12;    // 버튼이 연결된 디지털 핀 번호
const int ledPin1 = 10;  // 첫 번째 LED가 연결된 디지털 핀 번호
const int ledPin2 = 7;  // 두 번째 LED가 연결된 디지털 핀 번호

int ledState1 = LOW;     // 첫 번째 LED의 상태를 저장하는 변수
int ledState2 = LOW;     // 두 번째 LED의 상태를 저장하는 변수
int btnState = LOW;      // 버튼 상태를 저장하는 변수
int lastBtnState = LOW;  // 이전 버튼 상태를 저장하는 변수

void setup() {
  pinMode(btnPin, INPUT);
  pinMode(ledPin1, OUTPUT);
  pinMode(ledPin2, OUTPUT);
}

void loop() {
  btnState = digitalRead(btnPin);

  // 버튼이 눌렸을 때
  if (btnState == HIGH && lastBtnState == LOW) {
    if (ledState1 == LOW) {
      digitalWrite(ledPin1, HIGH);
      ledState1 = HIGH;
    } else if (ledState2 == LOW) {
      digitalWrite(ledPin2, HIGH);
      ledState2 = HIGH;
    } else {
      // 두 개의 LED가 모두 켜진 경우, 모든 LED 끄기
      digitalWrite(ledPin1, LOW);
      digitalWrite(ledPin2, LOW);
      ledState1 = LOW;
      ledState2 = LOW;
    }
  }

  lastBtnState = btnState;
}
