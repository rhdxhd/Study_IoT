#define jodo  A0
int ledB = 11;

void setup() {
  pinMode(jodo, INPUT);
  pinMode(ledB, OUTPUT);

  Serial.begin(9600);
}

void loop() {
  int val = analogRead(jodo);

  Serial.println(val);

  // int value = map(val, 590, 910, 255, 0);
  // analogWrite(ledB, value);

  if(val <700) {
    digitalWrite(ledB, HIGH);
  }else {
    digitalWrite(ledB, LOW);
  }
    delay(100);


}
