import time
from datetime import datetime
import numpy as np
import sounddevice as sd

class SoundSurveillance:

    # 定数と変数　・・・　①
    VOLUME_THRESHOLD = 40  # 閾値 

    # コンストラクタ　・・・　②
    def __init__(self):
        self.listen_sound()

    # 音を監視する部分　・・・　③
    def listen_sound(self):
        duration = 60  # 音声チェックを一時的に続ける秒数
        try: 
            while True:
                with sd.InputStream(callback=self.detect_sound):
                    sd.sleep(duration * 1000)
        except KeyboardInterrupt:
            print('stop check')   
            

    def detect_sound(self, indata, frames, time, status):
        volume = np.linalg.norm(indata) * 10  # indataからvolumeを計算
        if volume > self.VOLUME_THRESHOLD:  #volumeが閾値を超えていた時の処理
            print(volume)
            
ss = SoundSurveillance()
