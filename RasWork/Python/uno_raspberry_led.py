import RPi.GPIO as GPIO
import time
import sys
#라이브러리 경로 추가
sys.path.append('/home/pi/pyFirmata')
import pyfirmata

board = pyfirmata.Arduino('/dev/ttyACM0')
pin7 = board.get_pin('d:7:o')

try:
    while True:
        pin7.write(1)
        time.sleep(1)  # 여긴 단위가 초 임 
        pin7.write(0)
        time.sleep(1)   # 따라서 1초임

except KeyboardInterrupt:
    pass