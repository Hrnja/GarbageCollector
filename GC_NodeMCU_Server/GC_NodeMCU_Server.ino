#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ArduinoJson.h>
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "NodeMCU";  //Name of access point wifi
const char* password = "12345678"; //pasword for ac wifi

ESP8266WebServer server(80); // port 80 for web server

void setup() {
 Serial.begin(9600);

WiFi.mode(WIFI_AP); //set ac wifi
WiFi.softAP(ssid, password);

Serial.println();

IPAddress myIP = WiFi.softAPIP();  //IP address 
Serial.print("IP Address for Access Point Connection: ");
Serial.println(myIP);

server.on("/controlRobot", HTTP_POST, []() {    //{IP: http:://192.168.4.2/control }
String requestBody = server.arg("plain");  
StaticJsonDocument<200> doc;
DeserializationError error = deserializeJson(doc, requestBody);

if(error) {
Serial.println(F("deserializeJson() failed"));
return;  
}

if(doc.containsKey("direction")) {
  String direction = doc["direction"];
  if(direction == "forward") {send_post_req("forward");}
  else if(direction == "forwardRight") {send_post_req("forwardRight");}
  else if(direction == "forwardLeft") {send_post_req("forwardLeft");}
  else if(direction == "right") {send_post_req("right");}
  else if(direction == "left") {send_post_req("left");}
  else if(direction == "down") {send_post_req("down");}
  else if(direction == "downLeft") {send_post_req("downLeft");}
  else if(direction == "downRight") {send_post_req("downRight");}
  else if(direction == "stop") {send_post_req("stop");}
  else if(direction == "startAutomaticMode") {send_post_req("startAutomaticMode");}
}
else if(doc.containsKey("angle") && doc.containsKey("motor")) {
  int angle = doc["angle"];
  String motor = doc["motor"];
 
  if(angle >=544 && angle <= 2400 && motor == "motor1") {send_post_req(angle,motor);}
  else if(angle >=544 && angle <= 2400 && motor == "motor2") {send_post_req(angle,motor);}
  else if(angle >=544 && angle <= 2400 && motor == "motor3") {send_post_req(angle,motor);}
  else if(angle >=544 && angle <= 2400 && motor == "motor4") {send_post_req(angle,motor);}
  else if(angle >=544 && angle <= 2400 && motor == "motor5") {send_post_req(angle,motor);}
}

server.send(200, "text/plain", "Command received");
});
server.begin();
Serial.println("Server started");

}

void loop() {
server.handleClient();
}

void send_post_req(int angle, String motor) {
  WiFiClient client;
  HTTPClient http;

  String url = "http://192.168.4.2/controlRobot";
  http.begin(client,url);
  http.addHeader("Conetent-Type", "application/json");

   // Create a JSON object
  StaticJsonDocument<200> doc;
  doc["angle"] = angle;
  doc["motor"] = motor;

// Serialize the JSON object to a string
  String data;
  serializeJson(doc, data);

  int httpCode = http.POST(data);
 
  if (httpCode > 0) {
    Serial.println(httpCode);
  }
  else {
    Serial.println("Error on request");
  }

  http.end();
}

void send_post_req(String direction) { 
  WiFiClient client;
  HTTPClient http;

  String url = "http://192.168.4.2/controlRobot";
  http.begin(client,url);
  http.addHeader("Content-Type", "application/json");
  //String data = "{\"direction\": \"" + command + "\"}";
  
  StaticJsonDocument<200> doc;
  doc["direction"] = direction;

  String data;
  serializeJson(doc,data);

  int httpCode = http.POST(data);

  if (httpCode > 0) {
    Serial.println(httpCode);
  } else {
    Serial.println("Error on HTTP POST request");
  }
  http.end();
}



