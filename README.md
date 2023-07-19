# Garbage Collector
IoT system programmed with SOC


Specification:
--------------------------------------------------
Equipment:
1. Android app
2. NodeMCU ESP8266
3. Raspberry Pi 4
4. Robotic Arm
5. Servo motors(5x)
6. DC motors(4x)
7. IR sensors(4x)
8. L298N motor driver
9. HC-SR04 ultrasonic sensor
10. Battery

Functionality:

Garbage collector is a mobile robot that has the role of collecting garbage at certain locations. This is an IoT system and it's fully controlled with mobile app.

Consists from two modes of operation:
1. Automatic mode
2. Manual mode

In automatic mode, the robot is programmed to follow a white line and collect trash at a specific location.
To follow the line, robot use four IR sensors. Two sensors in the middle are used to detect the white line,
while the other two are used to detect the black background. Based on the input of the sensor, we formed the logic of the robot's movement.
Also, robot use the HC-SR04 sensor to detect the trash can. Everytime the sensor detects a trash can, stops moving and activate a robotic arm that picks and empties the trash can.
While in manual mode, the robot can be controlled with commands from the mobile app.
Both modes activates from mobile application.

Communication uses WiFi between all devices. NodeMCU Esp8266 is in acces point mode and two other device connect on them.
The mobile application sends an HTTP POST request to the NodeMCU Esp8266, using Retrofit library.
On the NodeMCU side, which acts as a server, we receive a POST request and forward it to the Raspberry Pi.
Raspberry Pi is the main devices and also act like server. He recive requests from NodeMCU Esp8266 and excute all request based on type that comes.
