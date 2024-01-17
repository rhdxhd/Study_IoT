import RPi.GPIO as GPIO
import time

led_pin=7
btn_pin=11

GPIO.setmode(GPIO.BOARD)
GPIO.setup(led_pin, GPIO.OUT)
GPIO.setup(btn_pin, GPIO.IN)

led_on = False
pressed = 0

try:
    while True:
        a = GPIO.input(btn_pin)
        time.sleep(0.1) #플로팅 현상제거
    
        if a == 1:
            
            if pressed == 0:
                led_on = not led_on #led_on 값과 반대
                print(led_on)
                pressed = 1
        else:
            pressed = 0
            
        GPIO.output(led_pin, led_on)
        
        
except KeyboardInterrupt:
        GPIO.cleanup()
