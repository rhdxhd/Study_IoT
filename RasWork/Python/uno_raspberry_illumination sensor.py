import RPi.GPIO as GPIO
import time
import sys
sys.path.append('/home/pi/pyFirmata')
import pyfirmata

led_pin = 7;

GPIO.setmode(GPIO.BOARD)
GPIO.setup(led_pin, GPIO.OUT, initial=GPIO.LOW)

board = pyfirmata.Arduino('/dev/ttyACM0')
pinA0 = board.get_pin('a:0:i')

it = pyfirmata.util.Iterator(board)
it.start()
pinA0.enable_reporting()

try:
    while True:
        lightValue = pinA0.read()
        print(lightValue)
        if (lightValue != None) and (lightValue < 0.6):
            GPIO.output(led_pin, True)
        else:
            GPIO.output(led_pin, False)

        time.sleep(1)

except KeyboardInterrupt:
    GPIO.cleanup()