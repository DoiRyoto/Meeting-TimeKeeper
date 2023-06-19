from gtts import gTTS
import pygame
import sys

text = sys.argv[1]  # 入力されたテキストをコマンドラインパラメータで取得する
output_file = 'output.mp3'

tts = gTTS(text, lang='ja')
tts.save(output_file)

pygame.mixer.init()
pygame.mixer.music.load(output_file)
pygame.mixer.music.play()
while pygame.mixer.music.get_busy():
    continue

