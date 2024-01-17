import RPi.GPIO as GPIO
import time

led_pin=7
btn_pin=11

GPIO.setmode(GPIO.BOARD)
GPIO.setup(led_pin, GPIO.OUT)
GPIO.setup(btn_pin, GPIO.IN)

cnt=0
pressed=0

try:
    while True:
        a = GPIO.input(btn_pin)
        time.sleep(0.1) #플로팅 현상제거
    
        if a == 1:
            GPIO.output(led_pin, True)
            if pressed == 0:
                cnt = cnt + 1
                print(cnt)
                pressed = 1
        else:
            GPIO.output(led_pin, False)
            pressed = 0
            
        
        
except KeyboardInterrupt:
        GPIO.cleanup()