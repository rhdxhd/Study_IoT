// ESP8266 USING CLIENT
#include <SoftwareSerial.h>
#include <ArduinoJson.h>

void printResponse();
void connectWifi();

// 3.3V 연결
SoftwareSerial ESP8266(10, 9);
String SSID = "teacherHanul";
String PASSWORD = "teacherhanul";

unsigned long lastTimeMillis = 0;
// 상태가 변하였는가? // 0:변하지 않음. 1:거실등상태변함, 2:가스불상태변함, 3:둘다변함
String stateChange = "0";   

int preLedVal = 0;  // 기존 거실등 상태 => 보드에서 직접 값 얻음
int preGasVal = 0;  // 기존 가스불 상태 => 보드에서 직접 값 얻음

void setup() {
  Serial.begin(9600);
  ESP8266.begin(9600);
  delay(2000);

  pinMode(13, OUTPUT);  //전등
  pinMode(12, OUTPUT);  //가스

//  preLedVal = digitalRead(13);
//  preGasVal = digitalRead(12);
  
  connectWifi();
}

void loop() {
    // 30초마다 한번씩
    if (millis() - lastTimeMillis > 30000) {      
       
      lastTimeMillis = millis(); // 마지막시간을 다시 셋팅  
  
      ESP8266.println("AT+CIPMUX=1");
      delay(1000);
      printResponse();
  
      ESP8266.println("AT+CIPSTART=4,\"TCP\",\"192.168.0.59\",8989");
      delay(1000);
      printResponse();      

      Serial.println(stateChange);
      String cmd = "";
      if(stateChange.equals("0")){ // 아무값도 변하지 않았을때
        cmd = "GET /app/arduGetLed HTTP/1.0";
        ESP8266.println("AT+CIPSEND=4," + String(cmd.length() + 4));
      }else if(stateChange.equals("1")){ // 거실등만 변했을때
        stateChange = "0";
        String ledVal = (String)digitalRead(13); 
        String gasVal = (String)digitalRead(12);
        delay(100);
        cmd = "GET /app/arduSetLed?ledVal=" + ledVal
                    + "&gasVal=" + gasVal
                    + " HTTP/1.0";    
      }else if(stateChange.equals("2")){ // 가스불만 변했을때
        stateChange = "0";
        String ledVal = (String)digitalRead(13); 
        String gasVal = (String)digitalRead(12);
        delay(100);
        cmd = "GET /app/arduSetLed?ledVal=" + ledVal
                    + "&gasVal=" + gasVal
                    + " HTTP/1.0"; 
      }else if(stateChange.equals("3")){ // 둘다 값이 변했을때
        stateChange = "0";
        String ledVal = (String)digitalRead(13); 
        String gasVal = (String)digitalRead(12);
        delay(100);
        cmd = "GET /app/arduSetLed?ledVal=" + ledVal
                    + "&gasVal=" + gasVal
                    + " HTTP/1.0"; 
      }
      Serial.println(cmd);     
      delay(1000);
  
      ESP8266.println(cmd);
      delay(1000);
      ESP8266.println(""); 
  
    }  // if (millis() - lastTimeMillis > 30000)   
  
    // 서버에서 데이터 내려 받는곳
    if (ESP8266.available()) { 
      String result = "";   

      // 배열로 온경우
      if(ESP8266.read() == '['){
        result += "[";
        result += ESP8266.readStringUntil('\r');
  
        char result_char[result.length()];
        strcpy(result_char, result.c_str());     
       
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
        const char* ledVal = css0["value"];
        const char* updatetime0 = css0["updatetime"];
        delay(100);
        Serial.println(id0); 
        Serial.println(name0); 
        Serial.println(ledVal); 
        Serial.println(updatetime0);     

        JsonObject css1 = doc[1];
          
        const char* id1 = css1["id"];
        const char* name1 = css1["name"];
        const char* gasVal = css1["value"];
        const char* updatetime1 = css1["updatetime"];
        delay(100);
        Serial.println(id1); 
        Serial.println(name1); 
        Serial.println(gasVal); 
        Serial.println(updatetime1);  

        if(atoi(ledVal) == preLedVal && atoi(gasVal) == preGasVal){
            digitalWrite(13, atoi(ledVal));
            digitalWrite(12, atoi(gasVal));
            delay(100);
            preLedVal = atoi(ledVal);
            preGasVal = atoi(gasVal);
            stateChange = "0";
            
            Serial.println("0");
        }else if(atoi(ledVal) != preLedVal && atoi(gasVal) == preGasVal){
            digitalWrite(13, atoi(ledVal));
            digitalWrite(12, atoi(gasVal));
            delay(100);
            preLedVal = atoi(ledVal);
            preGasVal = atoi(gasVal);
            stateChange = "1";
           
            Serial.println("1");
        }else if(atoi(ledVal) == preLedVal && atoi(gasVal) != preGasVal){
            digitalWrite(13, atoi(ledVal));
            digitalWrite(12, atoi(gasVal));
            delay(100);
            preLedVal = atoi(ledVal);
            preGasVal = atoi(gasVal);
            stateChange = "2";
           
            Serial.println("2");
        }else if(atoi(ledVal) != preLedVal && atoi(gasVal) != preGasVal){
            digitalWrite(13, atoi(ledVal));
            digitalWrite(12, atoi(gasVal));
            delay(100);
            preLedVal = atoi(ledVal);
            preGasVal = atoi(gasVal);
            stateChange = "3";
            
            Serial.println("3");
        }
      
      } // if(ESP8266.read() == '[')     

      
      // Object가 1개인경우
//      if(ESP8266.read() == '['){
//        result = ESP8266.readStringUntil('\r');
//        result.replace("]", " ");
//  
//        char result_char[result.length()];
//        strcpy(result_char, result.c_str());     
//       
//        Serial.println(result_char);  //          
//          
//        StaticJsonDocument<500> doc;          
//        // Deserialize the JSON document
//        DeserializationError error = deserializeJson(doc, result_char);        
//        // Test if parsing succeeds.
//        if (error) {
//          Serial.print(F("deserializeJson() failed: "));
//          Serial.println(error.c_str());
//          return;
//        }  
//          
//        const char* id = doc["id"];
//        const char* name = doc["name"];
//        const char* value = doc["value"];
//        const char* updatetime = doc["updatetime"];
//        delay(100);
//        Serial.println(id); 
//        Serial.println(name); 
//        Serial.println(value); 
//        Serial.println(updatetime);     
      
//      } // if(ESP8266.read() == '[')
      
    } // if (ESP8266.available()) 
    
  
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
    String cmd = "AT+CWMODE=1";  // client로 접속 
    Serial.println(cmd); 
    ESP8266.println(cmd);
    delay(1000);
    
    cmd ="AT+CWJAP=\""+SSID+"\",\""+PASSWORD+"\""; // WIFI 접속
    Serial.println(cmd); 
    ESP8266.println(cmd);
    delay(5000);
    if(ESP8266.find("OK")){
      Serial.println("Wifi connected!!!"); 
       
      cmd = "AT+CIFSR";   // IP 주소 알아내기
      ESP8266.println(cmd);  
      Serial.print("IP ADDRESS :");  
      delay(1000); 
      break;
    }
  }
  
}
