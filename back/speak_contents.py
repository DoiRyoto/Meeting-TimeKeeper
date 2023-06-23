from glob import glob
import random


def speak_contents(mode):
    # path = "/home/pi/build-in-app/"
    path = ""

    filter = path + "*/" + mode + "*.mp3"
    files = glob(filter)
        
    text = files[random.randint(0, len(files) - 1)]

    return text