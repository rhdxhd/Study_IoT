import RPi.GPIO as GPIO
import time

led_pin=7
btn_pin=11

GPIO.setmode(GPIO.BOARD)
GPIO.setup(led_pin, GPIO.OUT)
GPIO.setup(btn_pin, GPIO.IN)

try:
    while True:
        a = GPIO.input(btn_pin)
        if a == 0:
            GPIO.output(led_pin, False)
        else:
            GPIO.output(led_pin, True)
            
        print(a)
        
except KeyboardInterrupt:
        GPIO.cleanup()