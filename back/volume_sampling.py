import numpy as np
import sounddevice as sd

class SoundSurveillance:

    # 定数と変数　・・・　①
    duration = 15 # サンプリングする秒数 duration[s]
    volume = []

    # 音を監視する部分　・・・　②
    def listen_sound(self):
        with sd.InputStream(callback=self.detect_sound):
            sd.sleep(self.duration * 1000)
        
        return np.mean(self.volume)

    def detect_sound(self, indata, frames, time, status):
        self.volume.append(np.linalg.norm(indata) * 30)  # indataからvolumeを計算

ss = SoundSurveillance()
print(ss.listen_sound())