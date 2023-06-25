from glob import glob
import random


def speak_contents(mode, no):
    # path = "/home/pi/build-in-app/"
    path = ""

    filter = path + "*/" + mode + "*.mp3"
    files = glob(filter)
        
    text = files[no]

    return text