import RPi.GPIO as GPIO
import time

piezo_pin =13
GPIO.setmode(GPIO.BOARD)
GPIO.setup(piezo_pin, GPIO.OUT, initial=GPIO.LOW)

pi = GPIO.PWM(piezo_pin, 500)
pi.start(5)

try:
    while True:
        for i in range(100, 1000, 5):
            pi.ChangeFrequency(i)
            time.sleep(0.1)
            
        for i in range(1000, 100, -5):
            pi.ChangeFrequency(i)
            time.sleep(0.1)            
       
        
except KeyboardInterrupt:
    pi.stop()
    GPIO.cleanup()


