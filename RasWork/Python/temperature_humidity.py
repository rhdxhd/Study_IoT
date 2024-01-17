import RPi.GPIO as GPIO
import time

# #파이썬 3 이상부터 다운로드 받은 라이브러리 경로를 추가해야함

import sys
sys.path.append('/home/pi/Adafruit_Python_DHT')
import Adafruit_DHT

sensor = Adafruit_DHT.DHT11  # DTH22 면 22를 불러옴
pin = 26  # 반드시 핀넘버가 아닌 GPIO 넘버로 해야함 여기서는 핀넘버 37 (GPIO26)


try:
    while True:
        humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)

        if humidity is not None and temperature is not None:
            print('Temp={0:0.1f},  Humidity={1:0.1f}%'.format(temperature, humidity))
        else:
            print('Failed to get reading. Try again!')

        time.sleep(3)

except KeyboardInterrupt:
    GPIO.cleanup()