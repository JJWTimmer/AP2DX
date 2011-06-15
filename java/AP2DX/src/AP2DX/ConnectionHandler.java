package AP2DX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Thread;
import java.net.Socket;

import AP2DX.*;

//import com.sun.xml.internal.ws.api.message.Message;


/**
 * 
 * This class represents a connection with another module. It continues to listen for
 * incoming messages and puts them in the BlockingQueue of the abstract base. There
 * the message will wait until it can be processed by the Logic thread. <br />
 * <br />
 * Besides listening for message, it can also send messages. 
 * @author Maarten Inja
 * */ 
public class ConnectionHandler extends Thread
{
    
    private Socket socket;
    private AP2DXBase base;
    public final Module moduleID;

    private PrintWriter out;
    private IMessageReader in;

   
    /**
     * The constructor, saves arguments and reads in- and outcoming streams
     * from the socket.
     * 
     * This constructor is for the base-class self-started connections.
     * @param usar If true, handle a USARsim Connection
     * @throws IOException 
     */ 
    public ConnectionHandler(boolean usar, AP2DXBase base, Socket socket, Module origin, Module destination) throws IOException
    {
        this.base = base;
        this.socket = socket;
        this.moduleID = destination;
        out = new PrintWriter(socket.getOutputStream(), true);
        
        if (usar){
        	in = new UsarSimMessageReader(socket.getInputStream());
        }
        else {
        	in = new AP2DXMessageReader(socket.getInputStream(), origin, destination);
        }
    }
    
    /**
     * The constructor, saves arguments and reads in- and outcoming streams
     * from the socket.
     * 
     * This constructor is for the incoming connections.
     * @param usar If true, handle a USARsim Connection
     * @throws IOException 
     */ 
    public ConnectionHandler(AP2DXBase base, Socket socket, Module origin) throws IOException
    {
        this.base = base;
        this.socket = socket;
        
        out = new PrintWriter(socket.getOutputStream(), true);
        
        in = new AP2DXMessageReader(socket.getInputStream(), origin);
        
        Message firstIncomingMessage = in.readMessage();
        this.moduleID = firstIncomingMessage.getSourceModuleId();
        
    }

    /** Thread logic. */
    public void run()
    {
        while(true) // TODO: make interupt variable or something 
        {
            try
            {
            	AP2DXMessage incomingMessage = (AP2DXMessage) in.readMessage();
                base.getReceiveQueue().put(incomingMessage);
            }    
            catch (Exception e)
            {
                base.logger.severe(e.getMessage() + " IN ConnectionHandler.run, attempting to read message.");
            }
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
