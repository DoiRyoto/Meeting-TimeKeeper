from gtts import gTTS
import pygame
import sys
from speak_contents import speak_contents


mode = sys.argv[2]  # 入力されたモードをコマンドラインパラメータで取得する
text = speak_contents(mode)
output_file = 'output.mp3'

tts = gTTS(text, lang='ja')
tts.save(output_file)

pygame.mixer.init()
pygame.mixer.music.load(output_file)
pygame.mixer.music.play()
while pygame.mixer.music.get_busy():
    continue