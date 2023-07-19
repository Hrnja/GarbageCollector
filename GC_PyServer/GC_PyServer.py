from flask import Flask, request, jsonify
import RPi.GPIO as GPIO
import time
import json

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)

#-------------------------PINS--------------------
#pin declaration for controlling car motors
IN1 = 3# left motor
IN2 = 5 # left motor
IN3 = 7 # right motor
IN4 = 8 # right motor
#pin declaration for controlling motors's speed
EN1 = 10# left motor
EN2 = 11 # right motor
# Servo GPIO pins
servo1_pin = 13                 
servo2_pin = 15
servo3_pin = 16
servo4_pin = 18
servo5_pin = 19
# HC-SR04 GPIO pins
trig_pin = 21
echo_pin = 22
#IR sensor PINS
ir_1 = 23 # prvi s lijeve strane
ir_2 = 24 # drugi s lijeve strane
ir_3 = 26 # drugi s desne strane
ir_4 = 29 # prvi s desne strane


#-----------------------------INITIALIZATION PINS FOR CAR---------------
#-------------------------
#Gpio setup for motors on car
GPIO.setup(IN1, GPIO.OUT, initial=GPIO.HIGH)
GPIO.setup(IN2, GPIO.OUT, initial=GPIO.LOW)
GPIO.setup(IN3, GPIO.OUT, initial=GPIO.LOW)
GPIO.setup(IN4, GPIO.OUT, initial=GPIO.HIGH)
#Gpio setup for car speed
GPIO.setup(EN1, GPIO.OUT)
EN1_pwm = GPIO.PWM(EN1, 50)
EN1_pwm.start(10)
GPIO.setup(EN2, GPIO.OUT)
EN2_pwm = GPIO.PWM(EN2, 50)
EN2_pwm.start(10)

#--------------------------INITIALIZATION PINS FOR SERVO----------
# Servo initialization
GPIO.setup(servo1_pin, GPIO.OUT)
GPIO.setup(servo2_pin, GPIO.OUT)
GPIO.setup(servo3_pin, GPIO.OUT)
GPIO.setup(servo4_pin, GPIO.OUT)
GPIO.setup(servo5_pin, GPIO.OUT)
servo1 = GPIO.PWM(servo1_pin, 50)
servo2 = GPIO.PWM(servo2_pin, 50)
servo3 = GPIO.PWM(servo3_pin, 50)
servo4 = GPIO.PWM(servo4_pin, 50)
servo5 = GPIO.PWM(servo5_pin, 50)
servo1.start(0)
servo2.start(0)
servo3.start(0)
servo4.start(0)
servo5.start(0)


#--------------------------INITIALIZATION PINS FOR HC SENSOR----------
# HC-SR04 initialization
GPIO.setup(trig_pin, GPIO.OUT)
GPIO.setup(echo_pin, GPIO.IN)

#---------------INITIALIZATION PINS FOR IR SENSOR--------
GPIO.setup(ir_1, GPIO.IN)
GPIO.setup(ir_2, GPIO.IN)
GPIO.setup(ir_3, GPIO.IN)
GPIO.setup(ir_4, GPIO.IN)


#----------------------FUNCTION FOR MOVING CAR---------
#function for moving backward
def move_backward():
    GPIO.output(IN1, GPIO.LOW)
    GPIO.output(IN2, GPIO.HIGH)
    GPIO.output(IN3, GPIO.LOW)
    GPIO.output(IN4, GPIO.HIGH)

# function for moving forward
def move_forward():
    GPIO.output(IN1, GPIO.HIGH)
    GPIO.output(IN2, GPIO.LOW)
    GPIO.output(IN3, GPIO.HIGH)
    GPIO.output(IN4, GPIO.LOW)
    
# function for turning right
def turn_right():
    GPIO.output(IN1, GPIO.LOW)
    GPIO.output(IN2, GPIO.HIGH)
    GPIO.output(IN3, GPIO.HIGH)
    GPIO.output(IN4, GPIO.LOW)
    
# function for turning left
def turn_left():
    GPIO.output(IN1, GPIO.HIGH)
    GPIO.output(IN2, GPIO.LOW)
    GPIO.output(IN3, GPIO.LOW)
    GPIO.output(IN4, GPIO.HIGH)
    
# Stop
def stop():
    GPIO.output(IN1, GPIO.LOW)
    GPIO.output(IN2, GPIO.LOW)
    GPIO.output(IN3, GPIO.LOW)
    GPIO.output(IN4, GPIO.LOW)

#----------FUNCTION FOR SERVO POISTION--------------
def set_servo_position(servo, position):
    duty = 2.5 + (position / 18)
    servo.ChangeDutyCycle(duty)
    time.sleep(0.5)
    
#------------FUNCTION FOR SENSOR MEASUREMENT------------
def distance_measurement():
    GPIO.output(trig_pin, True)
    time.sleep(0.00001)
    GPIO.output(trig_pin, False)
    start = time.time()
    while GPIO.input(echo_pin) == 0:
        start = time.time()
    while GPIO.input(echo_pin) == 1:
        stop = time.time()
    elapsed = stop-start
    distance = (elapsed * 34300)/2
    return distance

def robotArmControl():
    set_servo_position(servo1,23)
    time.sleep(1)
    set_servo_position(servo3,0)
    time.sleep(1)
    set_servo_position(servo2,45)
    time.sleep(1)
    set_servo_position(servo5,60)
    time.sleep(2)
    set_servo_position(servo2,25)
    time.sleep(1)
    set_servo_position(servo5,90)
    time.sleep(1)
    set_servo_position(servo2,90)
    time.sleep(1)
    set_servo_position(servo1,120)
    time.sleep(1)
    set_servo_position(servo2,60)
    time.sleep(1)
    set_servo_position(servo4,120)
    time.sleep(3)
    set_servo_position(servo4,0)
    time.sleep(1)
    set_servo_position(servo2,90)
    time.sleep(1)
    set_servo_position(servo1,40)
    time.sleep(1)
    set_servo_position(servo2,40)
    time.sleep(1)
    set_servo_position(servo5,60)
    time.sleep(1)
    set_servo_position(servo2,90)
    time.sleep(1)
    set_servo_position(servo1,0)
    time.sleep(1)
    set_servo_position(servo3,40)
    time.sleep(1)
    

def automaticMode():
    while True:
        val_ir_1 = GPIO.input(ir_1)
        val_ir_2 = GPIO.input(ir_2)
        val_ir_3 = GPIO.input(ir_3)
        val_ir_4 = GPIO.input(ir_4)
        
        distance = distance_measurement()
        
        if(distance<15):
            stop()
            robotArmControl()
        else:
            #forward
            if val_ir_1 == 1 and val_ir_2 == 0 and val_ir_3 == 0 and val_ir_4 == 1:
                move_forward()
                EN1_pwm.ChangeDutyCycle(50)
                EN2_pwm.ChangeDutyCycle(50)
            #forwardRight
            elif val_ir_1 == 0 and val_ir_2 == 0 and val_ir_3 == 0 and val_ir_4 == 1:
                turn_right()
                EN1_pwm.ChangeDutyCycle(70)
                EN2_pwm.ChangeDutyCycle(70)
            #forwardRight
            elif val_ir_1 == 0 and val_ir_2 == 0 and val_ir_3 == 1 and val_ir_4 == 1:
                turn_right()
                EN1_pwm.ChangeDutyCycle(70)
                EN2_pwm.ChangeDutyCycle(75)
            #forwardRight
            elif val_ir_1 == 0 and val_ir_2 == 1 and val_ir_3 == 1 and val_ir_4 == 1:
                turn_right()
                EN1_pwm.ChangeDutyCycle(70)
                EN2_pwm.ChangeDutyCycle(80)
            #forwardLeft
            elif val_ir_1 == 1 and val_ir_2 == 0 and val_ir_3 == 0 and val_ir_4 == 0:
                turn_left()
                EN1_pwm.ChangeDutyCycle(70)
                EN2_pwm.ChangeDutyCycle(70)
            #forwardLeft
            elif val_ir_1 == 1 and val_ir_2 == 1 and val_ir_3 == 0 and val_ir_4 == 0:
                turn_left()
                EN1_pwm.ChangeDutyCycle(75)
                EN2_pwm.ChangeDutyCycle(70)
            #forwardLeft
            elif val_ir_1 == 1 and val_ir_2 == 1 and val_ir_3 == 1 and val_ir_4 == 0:
                turn_left()
                EN1_pwm.ChangeDutyCycle(80)
                EN2_pwm.ChangeDutyCycle(70)
            elif val_ir_1 == 1 and val_ir_2 == 1 and val_ir_3 == 1 and val_ir_4 == 1:
                stop()
                EN1_pwm.ChangeDutyCycle(0)
                EN2_pwm.ChangeDutyCycle(70)
            else:
                stop()

#SetRobotArmInStartingPosition
set_servo_position(servo1,0)
set_servo_position(servo2,90)
set_servo_position(servo3,40)
set_servo_position(servo4,0)
set_servo_position(servo5,120)

#---------------Execute POST request-----------
app = Flask(__name__)

@app.route('/controlRobot', methods=['POST'])
def control_robot():
    request_data = request.data.decode('utf-8')
    #data = request.get_json()
    data = json.loads(request_data)
    
    if 'direction' in data:
        #handle direction for move car
        direction = data['direction']
        
        if direction == 'forward':
            move_forward()
            EN1_pwm.ChangeDutyCycle(50)
            EN2_pwm.ChangeDutyCycle(50)
            pass
        elif direction == 'left':
            turn_left()
            EN1_pwm.ChangeDutyCycle(70)
            EN2_pwm.ChangeDutyCycle(70)
            pass
        elif direction == 'right':
            turn_right()
            EN1_pwm.ChangeDutyCycle(70)
            EN2_pwm.ChangeDutyCycle(70)
            pass
        elif direction == 'down':
            move_backward()
            EN1_pwm.ChangeDutyCycle(50)
            EN2_pwm.ChangeDutyCycle(50)
            pass
        elif direction == 'stop':
            stop()
            EN1_pwm.ChangeDutyCycle(30)
            EN2_pwm.ChangeDutyCycle(30)
            pass
        elif direction == 'startAutomaticMode':
            automaticMode()
            pass
        else:
            return 'Inavalid direction', 400
    
    elif 'angle' in data and 'motor' in data: 
        angle = data['angle']
        motor = data['motor']
        
        position = (angle-544)/10.32
        
        if motor=='motor1':
            set_servo_position(servo1,position)
        elif motor == 'motor2':
            set_servo_position(servo2,position)
        elif motor == 'motor3':
            set_servo_position(servo3,position)
        elif motor == 'motor4':
            set_servo_position(servo4,position)
        elif motor == 'motor5':
            set_servo_position(servo5,position)
        
    return 'Command execute successfully',200

if __name__ == '__main__':
    app.run(host='192.168.4.2', port=80)