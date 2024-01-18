
#
#      라즈베리파이 CCTV 프로젝트 #4 버튼으로 카메라 켜고 끄기
#      웹사이트의 버튼을 누르면 카메라를 켜고 끌 수 있음
#

from flask import Flask, render_template, Response, url_for, redirect
from PIL import ImageFont, ImageDraw, Image
import datetime
import cv2
import numpy as np

app = Flask(__name__)
capture = cv2.VideoCapture(-1)                       # 카메라 영상을 불러와 capture class에 저장
capture.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
capture.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)
font = ImageFont.truetype('fonts/SCDream6.otf', 20)
global push_btn                            # push_btn을 전역변수로 지정
push_btn = False                           # push_btn 변수는 처음엔 거짓(버튼을 누르지 않음)


def gen_frames():  
    global push_btn                        # push_btn을 전역변수로 지정(위의 전역변수를 가져옴)
    while True:                                     # 무한루프
        now = datetime.datetime.now()               # 현재시각 받아옴
        nowDatetime = now.strftime('%Y-%m-%d %H:%M:%S') # 현재시각을 문자열 형태로 저장
        ref, frame = capture.read()                 # 현재 영상을 받아옴
        if not ref:                                 # 영상이 잘 받아지지 않았으면(ref가 거짓)
            break                                   # 무한루프 종료
        else:
            if push_btn:                            # 버튼을 눌렀을때(화면을 끄면)
                frame = np.zeros([480, 640, 3], dtype="uint8")      # 검은화면을 생성
                frame = Image.fromarray(frame)                  
                draw = ImageDraw.Draw(frame)    
                # xy는 텍스트 시작위치, text는 출력할 문자열, font는 글꼴, fill은 글자색(파랑,초록,빨강)  
                draw.text(xy=(10, 15),  text="선생님 웹캠 "+nowDatetime, font=font, fill=(255, 255, 255)) 
                draw.text(xy=(320, 240),  text="off", font=font, fill=(255, 255, 255))
                frame = np.array(frame)
            else:                                   # 버튼을 누르지 않은상태(화면을 켜면)
                frame = Image.fromarray(frame)                  
                draw = ImageDraw.Draw(frame)    
                # xy는 텍스트 시작위치, text는 출력할 문자열, font는 글꼴, fill은 글자색(파랑,초록,빨강)   
                draw.text(xy=(10, 15),  text="선생님 웹캠 "+nowDatetime, font=font, fill=(255, 255, 255))
                frame = np.array(frame)
            ref, buffer = cv2.imencode('.jpg', frame)            
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')  # 그림파일들을 쌓아두고 호출을 기다림

@app.route('/')
def index():
    global push_btn                                 # push_btn을 전역변수로 불러옴
    return render_template('index03.html', push_btn=push_btn)             # index03.html의 형식대로 웹페이지를 보여줌

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/push_switch')
def push_switch():                                  # 버튼을 눌렀을때 실행되는 함수
    global push_btn                                 # push_btn을 전역변수로 불러옴
    push_btn = not push_btn                         # push_btn의 상태를 토글
    return redirect(url_for('index'))

if __name__ == "__main__":  # 웹사이트를 호스팅하여 접속자에게 보여주기 위한 부분
    app.run(host="192.168.0.30", port = "8080")
    # host는 현재 라즈베리파이의 내부 IP, port는 임의로 설정
    # 해당 내부 IP와 port를 포트포워딩 해두면 외부에서도 접속가능