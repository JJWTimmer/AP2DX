import socket
import time
import sys

class tunnel:

    tcpIp = "145.50.51.85"
    tcpPort = 666
    bufferSize = 1024
    s = None

    def __init__(self):
        print "Initializing server"
        print self.tcpPort

    
        for i in range(990000):
        
            okay = True
            self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            try:
                self.s.bind((self.tcpIp, self.tcpPort))
            except socket.error:
                okay = False
            if okay:
                print i
        print i
        self.s.listen(1)

        conn, addr = s.accept()
        print 'Connection address:', addr
        while True:
            data = conn.recv(bufferSize)
            if not data: break
            print "received data:", data
            conn.send(data)  # echo
        conn.close()
        
        time.sleep(3)    

def main():
   tunnel() 

if __name__ == "__main__":
    main()
