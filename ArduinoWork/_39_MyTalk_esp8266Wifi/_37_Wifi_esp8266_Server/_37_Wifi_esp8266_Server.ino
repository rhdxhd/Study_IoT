// ESP8266 USING SERVER

#include <SoftwareSerial.h>
#include <ArduinoJson.h>

void printResponse();
void connectWifi();

// 3.3V 연결
SoftwareSerial ESP8266(10, 11);  //RX:9번핀  TX:10번핀
String SSID = "hanul201";
String PASSWORD = "hanul201";

// 
int preLedVal = 0;  // 기존 거실등 상태 => 보드에서 직접 값 얻음
int preGasVal = 0;  // 기존 가스불 상태 => 보드에서 직접 값 얻음

int firstIndex = 0; // 받은 데이터에서 "[" 에서 시작 인덱스 얻음 
int lastIndex = 0;  // 받은 데이터에서 "]" 에서 끝 인덱스 얻음 
// 

int Btn1 = 3;
int curBtnState = 0;
int before = 0;
int sendDataState = 0;


void setup() {
  Serial.begin(9600);
  ESP8266.begin(9600);
  delay(2000);

  pinMode(Btn1, INPUT);   //서버로 데이터 보내는 버튼

  pinMode(5, OUTPUT);  //전등
  pinMode(6, OUTPUT);  //가스

  connectWifi();
      
}

void loop() { 
      
    if (Serial.available()) { 
      
    } // if (Serial.available()) 
     
    if (ESP8266.available()) { 
      Serial.println("ESP8266.available()!!!"); 

      String result = "";
//      char ch;
//      while(ch = ESP8266.read()){
//        if(ch == '\r'){
//          break;
//        }else{
//          result += ch;          
//        }
//      }

      //Serial.println("수신 정보 << " + ESP8266.readStringUntil('?') + " >> ");       
      result = ESP8266.readStringUntil('?');
      Serial.println("수신 정보 << " + result + " >> "); 

      firstIndex = result.indexOf('[');
      lastIndex = result.indexOf(']');
     
      String data = result.substring(firstIndex, lastIndex + 1); 
      delay(200);
      Serial.println("Json 정보 << "+data+" >> "); 
      delay(200);
      char result_char[data.length()];
        strcpy(result_char, data.c_str());     
       
        Serial.println(result_char); 
          
        StaticJsonDocument<500> doc; 
        DeserializationError error = deserializeJson(doc, result_char);  
        if (error) {
          Serial.print(F("deserializeJson() failed: "));
          Serial.println(error.c_str());
          return;
        }

        JsonObject css0 = doc[0];
          
        const char* id0 = css0["id"];
        const char* name0 = css0["name"];
        const char* value0 = css0["value"];
        const char* updatetime0 = css0["updatetime"];
        delay(100);
        Serial.println(id0); 
        Serial.println(name0); 
        Serial.println(value0); 
        Serial.println(updatetime0);     

        JsonObject css1 = doc[1];
          
        const char* id1 = css1["id"];
        const char* name1 = css1["name"];
        const char* value1 = css1["value"];
        const char* updatetime1 = css1["updatetime"];
        delay(100);
        Serial.println(id1); 
        Serial.println(name1); 
        Serial.println(value1); 
        Serial.println(updatetime1); 

        char* idLed = "CSSLed01";
        char* idSec = "CSSSec01";

        if(strcmp(id0, idLed) == 0){
          digitalWrite(5, atoi(value0)); // 전등값
          digitalWrite(6, atoi(value1)); // 쎄콤값
          delay(100);          
        }else if(strcmp(id0, idSec) == 0){
          digitalWrite(5, atoi(value1));
          digitalWrite(6, atoi(value0));
          delay(100);
        }  

        return 0;
    } // if (ESP8266.available())
       

    // 버튼이 눌리면 서버로 데이터보내기
//    curBtnState = digitalRead(Btn1); 
//    if(curBtnState == 1){
//      if(before == 0){        
//        sendDataState = 1;
//        before = 1;
//      }      
//    }
//    if(curBtnState == 0){
//       before = 0;
//    }
//    
//    if(sendDataState == 1){
//      ESP8266.println("AT+CIPSTART=0,\"TCP\",\"192.168.0.59\",80");
//      delay(1000);
//      printResponse(); 
//      delay(2000);
//
//      String cmd = "GET /app/arduGetLed HTTP/1.0\r\n HOST:http://192.168.0.59:80\r\n\r\n";
//      ESP8266.println("AT+CIPSEND=0," + String(cmd.length() + 4));
//      delay(1000);
//      printResponse();
//      ESP8266.println(cmd); 
//      delay(1000);
//      printResponse();      
//
//      sendDataState = 0;
//      
//      ESP8266.println("AT+CIPCLOSE=0");
//      delay(100);
//      printResponse();

//      ESP8266.println("AT+CIPSTATUS");
//      delay(1000);
//      printResponse();
//    }

    delay(100);
  
} // loop()

void printResponse() {
  while (ESP8266.available()) {
    Serial.println(ESP8266.readStringUntil('\n')); 
  }
}

// esp8266 at command 명령어 찾아보기
void connectWifi(){

  while(1){
    Serial.println("Wifi connecting ...");
//    String cmd = "AT+RST";  // 초기화     
//    ESP8266.println(cmd);
//    delay(1000);
//    printResponse(); 
    
    String cmd = "AT+CWMODE=3";  // Server로 접속     
    ESP8266.println(cmd);
    delay(1000);
    printResponse(); 
    
    cmd ="AT+CWJAP=\""+SSID+"\",\""+PASSWORD+"\""; // WIFI 접속    
    ESP8266.println(cmd);   
    delay(1000);
    printResponse();
    delay(3000);
    
    if(ESP8266.find("OK")){
      Serial.println("Wifi connected!!!"); 
       
      cmd = "AT+CIFSR";   // IP 주소 알아내기
      ESP8266.println(cmd);
      delay(1500); 
      printResponse(); 
      delay(1500);

      ESP8266.println("AT+CIPMUX=1"); // 다중접속
      delay(1500);
      printResponse();

      // start server on port 80
      Serial.println("Start the server ..."); 
      cmd = "AT+CIPSERVER=1,80";  // 0:close mode  1:open mode 
      ESP8266.println(cmd);
      printResponse();
      delay(1500);

       ESP8266.println("AT+CIPSTO=3"); // SET SERVER TIMEOUT
       delay(1000);
       printResponse();

      Serial.println("Waiting the server ..."); 
      
      break;
    }
  }
  
}
