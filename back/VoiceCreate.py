import pygame
import sys
from speak_contents import speak_contents


mode = sys.argv[1]  # 入力されたモードをコマンドラインパラメータで取得する
output_file = speak_contents(mode) # speak_contentsでしゃべる内容の音声フォルダのパスを取得する

pygame.mixer.init()
pygame.mixer.music.load(output_file)
pygame.mixer.music.play()
while pygame.mixer.music.get_busy():
    continue