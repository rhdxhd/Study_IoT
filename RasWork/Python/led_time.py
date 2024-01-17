import RPi.GPIO as GPIO
import time

led_pin=7
btn_pin=11

GPIO.setmode(GPIO.BOARD)
GPIO.setup(led_pin, GPIO.OUT)
GPIO.setup(btn_pin, GPIO.IN)

led_on = False
pressed = 0
start_time=0
remain_time=0

try:
    while True:
        a = GPIO.input(btn_pin)
    
        if a == 1:       
            if pressed == 0:
                if led_on == False:
                    remain_time = 0
                    start_time = time.time()
                    print(start_time)
                led_on = True
                remain_time = remain_time + 10
                print(remain_time)
            pressed = 1
        else:
            pressed = 0
            
        GPIO.output(led_pin, led_on)
        
        
        if((start_time + remain_time) - time.time()) <= 0: # time.time()은 지금시간
            #start시간이랑 + remain시간이랑 더한거에서 - 지금시간을 뺀게 같으면 끄라는 것
            
            led_on = False
        else:
            led_on = True
        
except KeyboardInterrupt:
        GPIO.cleanup()

