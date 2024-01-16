int ledR = 9,  ledG = 10, ledB = 11;

void setup() {
  // put your setup code here, to run once:
    
    pinMode(ledR, OUTPUT);
    pinMode(ledG, OUTPUT);
    pinMode(ledB, OUTPUT);

    Serial.begin(9600);
}

void loop() {
  if(Serial.available()) {
      char ch = Serial.read();

      if(ch == '1') {
        analogWrite(ledR, 255);
        analogWrite(ledG, 0);
        analogWrite(ledB, 0);
      }else if(ch == '2') {
        analogWrite(ledG, 255);
        analogWrite(ledR, 0);
        analogWrite(ledB, 0);
      }else if(ch == '3') {
        analogWrite(ledB, 255);
        analogWrite(ledR, 0);
        analogWrite(ledG, 0);
      }else if (ch =='4') {
        analogWrite(ledR, 0);
        analogWrite(ledG, 0);
        analogWrite(ledB, 0);
      }     
  }
}
