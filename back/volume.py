import time
from datetime import datetime
import numpy as np
import sounddevice as sd
import sys

class SoundSurveillance:

    # 定数と変数　・・・　①
    VOLUME_THRESHOLD = 100  # 閾値 ToDo: 要調整
    QUIT_COUNT = 0
    duration = 1 # callbackの速さ(1回あたりにサンプリングする秒数 duration[s]ごとに閾値を超えているかを確認)
    QUIT_COUNT_THRESHOLD = 200 # 土居の環境では1[s]あたり40のindataが取得されていた → QCT/40[s]黙ったら強制終了
    cnt = 0 # debug: duration[s]あたりの取得データ数をカウント
    max_roop = 10 # サンプリングを行う回数の上限 → max_roop*duration[s]監視する

    # コンストラクタ　・・・　②
    def __init__(self):
        # presen_time =   # プレゼンテーションにかかる時間(s)
        # time.sleep(presen_time)  # プレゼンテーション・質疑中 
        # print(presen_time, 'seconds passed')
        self.listen_sound()

    # 音を監視する部分　・・・　③
    def listen_sound(self):
        # print(self.cnt) # duration[s]あたりの取得データ数確認
        while True:
            if self.QUIT_COUNT > self.QUIT_COUNT_THRESHOLD: # カウンターいくつで終了するか
                raise sd.CallbackStop("Sensing the stagnation of discussions.")
            with sd.InputStream(callback=self.detect_sound):
                sd.sleep(self.duration * 1000)

            self.max_roop -= 1
            if self.max_roop == 0:
                raise sd.CallbackStop("Allowed waiting time exceeded.")

    def detect_sound(self, indata, frames, time, status):
        volume = np.linalg.norm(indata) * 30  # indataからvolumeを計算
        self.cnt += 1
        if volume > self.VOLUME_THRESHOLD:  #volumeが閾値を超えていた時の処理    
            self.QUIT_COUNT = 0
        else:
            self.QUIT_COUNT += 1

ss = SoundSurveillance()