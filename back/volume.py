import time
from datetime import datetime
import numpy as np
import sounddevice as sd
import sys

class SoundSurveillance:

    # 定数と変数　・・・　①
    VOLUME_THRESHOLD = 5  # 閾値 
    QUIT_COUNT = 0

    # コンストラクタ　・・・　②
    def __init__(self):
        # presen_time =   # プレゼンテーションにかかる時間(s)
        # time.sleep(presen_time)  # プレゼンテーション・質疑中 
        # print(presen_time, 'seconds passed')
        self.listen_sound()

    # 音を監視する部分　・・・　③
    def listen_sound(self):
        duration = 60  # 音声チェックを一時的に続ける秒数
        while True:
            with sd.InputStream(callback=self.detect_sound):
                sd.sleep(duration * 1000)
            

    def detect_sound(self, indata, frames, time, status):
        volume = np.linalg.norm(indata) * 10  # indataからvolumeを計算 
        if volume > self.VOLUME_THRESHOLD:  #volumeが閾値を超えていた時の処理    
            self.QUIT_COUNT =0
        else:
            self.QUIT_COUNT += 1
            if self.QUIT_COUNT > 10 :
                raise sd.CallbackStop
                #ここで喋らせる
            
ss = SoundSurveillance()
