int count=0;
String str="";
void setup() {
  
Serial.begin(9600);
}

void loop() {
  //시리얼 버퍼에 데이터가 있는지 확인하는 함수-> 있으면 1/ 없으면 0
  while(Serial.available()){
     // 시리얼 버퍼에서 문자 하나씩 읽어오는 함수
     // char ch = Serial.read();
     // 시리얼버퍼에서 숫자를 읽어오는 함수
      int num = Serial.parseInt();


      count++;
      Serial.print(count);
      Serial.print(" : ");
      Serial.println(num);
      
      
      // str+=ch;

      //Serial.println(str);
     delay(10);//while 문 안에서 돌려야한다
  }
  //  //delay(50);- 여기는 x
  // count=0;
  // if(str != ""){
  //   Serial.println(str);
  //   str="";
  // }
}