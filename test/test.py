import socket
import time



class test:

    

    def __init__(self):
        TCP_IP = "localhost"
        TCP_PORT = 3000 
        BUFFER_SIZE = 1024
        spawnMessage = "INIT {ClassName USARBot.P2DX} {Location 4.5,1.9,1.8} {Name R1}\r\n"
        forwardDriveMessage = "DRIVE {Left 20} {Right 10} {Normalized true}\r\n"
       
        print "attempting to connect"
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((TCP_IP, TCP_PORT))
        data = s.recv(BUFFER_SIZE)
        print "done, data:\n%s" % data
        print "attempting to send spawn message"
        s.send(spawnMessage)
        data = s.recv(BUFFER_SIZE)
        print "attempting to send forward drive message"
        while True:
            s.send(forwardDriveMessage)
            data = s.recv(BUFFER_SIZE)
            print data
            print time.time()

        s.close()

        time.sleep(10)

def main():
    test()

if __name__ == "__main__":
    main()
