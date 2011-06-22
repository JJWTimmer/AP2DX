import socket
import time



class test:

    

    def __init__(self):
        #tcpIp = "146.50.51.84"
        tcpIp = "localhost"
        tcpPort = 3000
        bufferSize = 1024
        spawnMessage = "INIT {ClassName USARBot.P2DX} {Location 4.5,1.9,1.8} {Name R1}\r\n"
        forwardDriveMessage = "DRIVE {Left 1} {Right 1} {Normalized true}\r\n"
       
        print "attempting to connect"
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((tcpIp, tcpPort))
        data = s.recv(bufferSize)
        print "done, data:\n%s" % data
        print "attempting to send spawn message"
        s.send(spawnMessage)
        data = s.recv(bufferSize)
        print "done, data:\n%s" % data
        print "attempting to send forward drive message"
        while True:
            #s.send(forwardDriveMessage)
            data = s.recv(bufferSize)
            print data

        s.close()

        time.sleep(10)

def main():
    test()

if __name__ == "__main__":
    main()
