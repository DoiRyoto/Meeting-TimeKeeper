def speak_contents(mode):
    if mode == "start":
        text = "/home/pi/build-in-app/Voice/start_overall_1.mp3"
        print("play: {}".format(text))
    elif mode == "end":
        text = "/home/pi/build-in-app/Voice/end_overall_1.mp3"
        print("play: {}".format(text))

    return text