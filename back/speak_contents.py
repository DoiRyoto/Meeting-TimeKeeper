import sys

def speak_contents():
    mode = sys.argv[2]

    if mode == "start":
        text = "発表を始めてください" # Todo: ChatGPTを使うなど
    elif mode == "end":
        text = "発表をやめてください" # Todo: ChatGPTを使うなど

    return text