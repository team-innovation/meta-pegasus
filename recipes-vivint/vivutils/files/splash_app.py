#!/usr/bin/python3
import sys, signal
import time

import os

from PyQt5.QtCore import Qt, QFile, QIODevice, QThread, QSize, QTime, pyqtSignal
from PyQt5.QtGui import QFont, QMovie 
from PyQt5.QtWidgets import QApplication, QSplashScreen, QLabel

class SplashScreen(QThread):
    received_data = pyqtSignal(['QString'])
    PIPE_NAME = '/tmp/splash_pipe'

    def __init__(self, QObject_parent=None, app=None):
        super().__init__(QObject_parent)

        self.app = app
        self.gif_anim = QLabel()

        movie = QMovie("/usr/lib/images/bootsplash.gif")
        movie.setScaledSize(QSize(1024,600))
        self.gif_anim.setMovie(movie)
        movie.start()
        self.gif_anim.setAlignment(Qt.AlignCenter)
        self.gif_anim.setWindowState(Qt.WindowFullScreen)
        self.gif_anim.show()

    def onReceived(self, data):
        items = data.split(';')
        for item in items:
            self.parse_cmds(item)

    def parse_cmds(self, data):
        try:
            try:
                key, val = data.split(':').strip()
            except:
                key = data.strip()

            if key == 'QUIT':
                self.app.quit()
        except Exception as ex:
            print(ex)

    def run(self):
        if os.path.exists(SplashScreen.PIPE_NAME):
            os.unlink(SplashScreen.PIPE_NAME)

        os.mkfifo(SplashScreen.PIPE_NAME)

        while True:
            file = QFile(SplashScreen.PIPE_NAME)

            if not file.open(QIODevice.ReadOnly | QIODevice.Text):
                return

            self.received_data.connect(self.onReceived)

            data = b''

            ret = True
            while ret:
                ret, c = file.getChar()
                data += c

            file.close()

            try:
                d = data[0:-1]
                self.received_data.emit(d.decode())
            except Exception as ex:
                print(ex)

            self.sleep(1)

def main():
    app = QApplication(sys.argv)
    signal.signal(signal.SIGINT, signal.SIG_DFL)

    r = SplashScreen(app=app)
    r.start()

    app.exec_()

if __name__ == "__main__":
    sys.exit(main())

