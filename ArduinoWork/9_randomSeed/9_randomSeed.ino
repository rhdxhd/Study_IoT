void printRandom();

int randomNum = 0;  //랜덤으로 만들어진 수
int chkNum = 0;     //내가 입력한 수

void setup() {
  Serial.begin(9600);
  //아날로그핀 0번을 seed값으로 초기화 한다
  randomSeed(analogRead(A0));


  printRandom();
  // Serial.println();
  // Serial.println("Making Random Number ... ");
  // Serial.println("Let's Start !!!");

  // //1~9 까지수에서 난수를 만든다
  // randomNum = random(1, 10);
}

void loop() {
  // 시리얼 버퍼에 데이터가 존재하는지 확인
  if (Serial.available()) {
    chkNum = Serial.parseInt();
    // Serial.print(randomNum);
    // Serial.print(" : ");

    if (randomNum == chkNum) {
      Serial.print(chkNum);
      Serial.print(" : ");
      Serial.println("일치");


      printRandom();
      // Serial.println();
      // Serial.println("Making Random Number ... ");
      // Serial.println("Let's Start !!!");
      // //1~9 까지수에서 난수를 만든다
      // randomNum = random(1, 10);


    } else if (randomNum != chkNum) {
      Serial.print(chkNum);
      Serial.print(" : ");
      Serial.println("다름");
    }
  }
}



// 반복되는 코드를 함수로 만든다
void printRandom(){

      Serial.println();
      Serial.println("Making Random Number ... ");
      Serial.println("Let's Start !!!");
      //1~9 까지수에서 난수를 만든다
      randomNum = random(1, 10);

}