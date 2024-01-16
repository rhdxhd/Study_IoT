#define C 262  // 도
#define D 294  // 레
#define E 330  // 미
#define F 349  // 파
#define G 392  // 솔
#define A 440  // 라
#define B 494  // 시

int piezoPin = 7;
int temp = 200;

int notes[25] = {G, G, A, A, G, G, E, G, G, E, E, D, G, G, A, A, G, G, E, G, E, D, E, C };

void setup() {
  pinMode(piezoPin, OUTPUT);

}

void loop() {
  for(int i=0; i<12; i++) {
    // 음을 내주는 함수
    tone(piezoPin, notes[i], temp);
    delay(300);
  }

  for(int i=12; i<25; i++) {
    // 음을 내주는 함수
    tone(piezoPin, notes[i], temp);
    delay(300);
  }
}
