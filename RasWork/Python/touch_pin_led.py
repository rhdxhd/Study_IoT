import RPi.GPIO as GPIO
import time


touch_pin = 15
led_pin = 7


GPIO.setmode(GPIO.BOARD)
GPIO.setup(touch_pin, GPIO.IN)
GPIO.setup(led_pin, GPIO.OUT, initial=GPIO.LOW)



try:
    while True:
       a = GPIO.input(touch_pin)
       print(a)
       
       if a ==1:
           GPIO.output(led_pin, True)
       else:
           GPIO.output(led_pin, False)
       
        
except KeyboardInterrupt:
    GPIO.cleanup()



