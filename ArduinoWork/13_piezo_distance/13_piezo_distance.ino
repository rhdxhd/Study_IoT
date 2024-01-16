int trig = 7;
int echo = 8;
int piezo = 10;
int tempo = 200;
#define B 494

void setup() {
  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
  pinMode(piezo, OUTPUT);

  Serial.begin(9600);
}

void loop() {
 digitalWrite(trig, HIGH);
 delay(10);
 digitalWrite(trig, LOW);

 int duration = pulseIn(echo, HIGH);
 int distance = (duration/2) /29.1;

 Serial.print(distance);
 Serial.println("cm");
 
    if ( distance > 0 && distance <= 30) {
      if( distance > 0 && distance <= 10) {
          tone(piezo, B, tempo);
          delay(200);
      }else if ( distance > 10 && distance <= 20) {
          tone(piezo, B, tempo);
          delay(500);
      }else if ( distance > 20 && distance <= 30) {
          tone(piezo, B, tempo);
          delay(1000);
      }

      delay(100);
  }
}