'''
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)

GPIO.setup(7,GPIO.OUT,initial=GPIO.LOW)

try:
    while(True):
        GPIO.output(7,GPIO.HIGH)
        time.sleep(0.5)
        GPIO.output(7,GPIO.LOW)
        time.sleep(0.5)
        
except KeyboardInterrupt:
     GPIO.cleanup()
'''




import RPi.GPIO as GPIO
import time

led_pin=7
GPIO.setmode(GPIO.BOARD)
GPIO.setup(led_pin,GPIO.OUT)

pwm_led=GPIO.PWM(led_pin, 500)
pwm_led.start(0)

while True:
    duty_s = input("Enter Brightness(0 to 100):")
    duty = int(duty_s)
    
    if(duty==1000):
        pwm_led.stop()
        GPIO.cleanup()
        break
    else:
        pwm_led.ChangeDutyCycle(duty)



