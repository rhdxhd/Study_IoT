int A_motor_speed = 5;
int A_motor_L = 6;
int A_motor_R = 7;

int touch = 2;

void setup() {
  pinMode(A_motor_L, OUTPUT);
  pinMode(A_motor_R, OUTPUT);
  pinMode(touch, INPUT);

  Serial.begin(9600);
}

void loop() {

int val = digitalRead(touch);
Serial.println(val);

 if(val == 1) {

  digitalWrite(A_motor_L, HIGH);
  digitalWrite(A_motor_R, LOW);
  analogWrite(A_motor_speed, 100);
  delay(3000);
  digitalWrite(A_motor_L, LOW);
  digitalWrite(A_motor_R, LOW);
  analogWrite(A_motor_speed, 0);
  delay(1000);
  digitalWrite(A_motor_L, LOW);
  digitalWrite(A_motor_R, HIGH);
  analogWrite(A_motor_speed, 100);
  delay(3000);

 }else{
    digitalWrite(A_motor_L, LOW);
    digitalWrite(A_motor_R, LOW);
    analogWrite(A_motor_speed, 0);
 }

 delay(1000);
}
