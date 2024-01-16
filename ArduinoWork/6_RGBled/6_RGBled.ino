int ledR = 11, ledG = 10, ledB = 9;

void setup() {
  // put your setup code here, to run once:
  for(int i = 9; i <= 11; i++ ){
    pinMode(i, OUTPUT);
  }


}

void loop() {
  // put your main code here, to run repeatedly:

  analogWrite(ledR, 255);
  delay(1000);
  analogWrite(ledR, 0);
  delay(1000);


  analogWrite(ledG, 255);
  delay(1000);
  analogWrite(ledG, 0);
  delay(1000);


  analogWrite(ledB, 255);
  delay(1000);
  analogWrite(ledB, 0);
  delay(1000);


  analogWrite(ledR, 10);
  analogWrite(ledG, 100);
  analogWrite(ledB, 255);
  delay(3000);


  for(int i=0; i<256; i=i+10){
    for(int j=255; j>=0; j=j-10){
      for(int k=0; k<256; k=k+10){
        analogWrite(ledR, i);
        analogWrite(ledG, j);
        analogWrite(ledB, k);
        delay(10);
      }
    }
  }



}
