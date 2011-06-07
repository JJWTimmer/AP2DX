import socket
import time


class tunnel:

    tcpIp = "145.50.51.85"
    tcpPort = 135 
    bufferSize = 1024
    s = None

    def __init__(self):
        print "Initializing server"


        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.s.bind((self.tcpIp, self.tcpPort))
        self.s.listen(1)
        
        conn, addr = s.accept()
        print 'Connection address:', addr
        while True:
            data = conn.recv(bufferSize)
            if not data: break
            print "received data:", data
            conn.send(data)  # echo
        conn.close()
        
    time.sleep(10)    

def main():
   tunnel() 

if __name__ == "__main__":
    main()
