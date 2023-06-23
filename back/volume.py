import time
from datetime import datetime
import numpy as np
import sounddevice as sd
import sys

class SoundSurveillance:

    # 定数と変数　・・・　①
    VOLUME_THRESHOLD = 1  # 閾値 
    QUIT_COUNT = 0

    # コンストラクタ　・・・　②
    def __init__(self):
        # presen_time =   # プレゼンテーションにかかる時間(s)
        # time.sleep(presen_time)  # プレゼンテーション・質疑中 
        # print(presen_time, 'seconds passed')
        self.listen_sound()

    # 音を監視する部分　・・・　③
    def listen_sound(self):
        duration = 100 # callbackの速さ
        while True:
            if self.QUIT_COUNT > 10 : #カウンターいくつで終了するか
                print(self.QUIT_COUNT)
                raise sd.CallbackStop
            with sd.InputStream(callback=self.detect_sound):
                sd.sleep(duration)  
            

    def detect_sound(self, indata, frames, time, status):
        volume = np.linalg.norm(indata) * 30  # indataからvolumeを計算 
        print(self.QUIT_COUNT)
        if volume > self.VOLUME_THRESHOLD:  #volumeが閾値を超えていた時の処理    
            self.QUIT_COUNT =0
        else:
            self.QUIT_COUNT += 1
            
ss = SoundSurveillance()
