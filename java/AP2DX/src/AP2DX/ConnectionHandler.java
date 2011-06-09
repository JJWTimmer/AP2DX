package AP2DX;

import java.lang.Thread;
import java.net.Socket;

/**
 * @author Maarten Inja
 * 
 * This class represents a connection with another module. It continues to listen for
 * incoming messages and puts them in the BlockingQueue of the abstract base. There
 * the message will wait until it can be processed by the Logic thread. <br />
 * <br />
 * Besides listening for message, it can also send messages. 
*/ 
public class ConnectionHandler extends Thread
{
    
    private Socket socket;
    private AP2DXBase base;
    public final Module moduleID;

    private PrintWriter out;
    private BufferedReader in;

   
    /** The constructor, safes arguments and reads in- and outcoming streams
    * from the socket. */ 
    public ConnectionHandler(AP2DXBase base, Socket socket, Module moduleID)
    {
        this.base = base;
        this.socket = socket;
        this.moduleID = moduleID;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /** Thread logic. */
    public void run()
    {
        try
        {
            String plainIncomingMessage = in.readLine();
            base.getBlockingQueue().put(plainIncomingMessage);
        }    
        catch (Exception e)
        {
            base.Logger.severe(e.getMessage() + " IN ConnectionHandler.run, attempting to read message.");
        }
    } 

    /** Sends a message object, this is currently only the message object 
    * toString().
    */
    public void sendMessage(Message message)
    {
        out.print(message.toString());
    }


}
