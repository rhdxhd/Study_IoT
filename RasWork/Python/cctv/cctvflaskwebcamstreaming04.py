#
#      라즈베리파이 CCTV 프로젝트 #5 버튼으로 영상캡쳐
#      웹페이지의 버튼을 눌러 현재화면 캡쳐
#

from flask import Flask, render_template, Response, url_for, redirect
from PIL import ImageFont, ImageDraw, Image
import datetime
import cv2
import numpy as np


app = Flask(__name__)
global is_capture            # is_capture를 전역변수로 지정
capture = cv2.VideoCapture(-1)                       # 카메라 영상을 불러와 capture class에 저장
capture.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
capture.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)
font = ImageFont.truetype('fonts/SCDream6.otf', 20)
is_capture = False                         # 각 변수들은 처음엔 거짓(버튼을 누르지 않음)

def gen_frames():  
    global is_capture      # is_capture를 전역변수로 지정(위의 전역변수를 가져옴)
    while True:                                     # 무한루프
        now = datetime.datetime.now()               # 현재시각 받아옴        
        nowDatetime = now.strftime('%Y-%m-%d %H:%M:%S') # 현재시각을 문자열 형태로 저장
        nowDatetime_path = now.strftime('%Y-%m-%d %H_%M_%S') 
        ref, frame = capture.read()     # 현재 영상을 받아옴
        if not ref:                     # 영상이 잘 받아지지 않았으면(ref가 거짓)
            break                       # 무한루프 종료
        else:
            frame = Image.fromarray(frame)    
            draw = ImageDraw.Draw(frame)    
            # xy는 텍스트 시작위치, text는 출력할 문자열, font는 글꼴, fill은 글자색(파랑,초록,빨강)   
            draw.text(xy=(10, 15),  text="선생님 웹캠 "+nowDatetime, font=font, fill=(255, 255, 255))
            frame = np.array(frame)
            ref, buffer = cv2.imencode('.jpg', frame)            
            frame1 = frame          # 현재화면을 frame1에 복사해둠
            frame = buffer.tobytes()
            if is_capture:          # is_capture가 참이면
                is_capture = False  # is_capture를 거짓으로 만들고
                cv2.imwrite("capture/capture " + nowDatetime_path + ".png", frame1)  # 파일이름(한글안됨), 이미지로 영상을 캡쳐하여 그림파일로 저장                 
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')  # 그림파일들을 쌓아두고 호출을 기다림

@app.route('/')
def index():
    return render_template('index04.html')             # index04.html의 형식대로 웹페이지를 보여줌

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/push_capture')
def push_capture():                 # 캡쳐버튼을 눌렀을때 실행되는 함수
    global is_capture               # is_capture를 전역변수로 불러옴
    is_capture = True               # is_capture를 참으로 만들어줌
    return redirect(url_for('index'))

if __name__ == "__main__":  # 웹사이트를 호스팅하여 접속자에게 보여주기 위한 부분
    app.run(host="192.168.0.30", port = "8080")
    # host는 현재 라즈베리파이의 내부 IP, port는 임의로 설정
    # 해당 내부 IP와 port를 포트포워딩 해두면 외부에서도 접속가능