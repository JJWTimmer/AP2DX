package AP2DX;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;

/** Specialized bufferedReader to directly parse messages from a (in our case) 
* sockets. 
* 
* @author Maarten Inja
*/ 
public class MessageReader extends BufferedReader
{
	/**
	 * 
	 * @param in
	 */
    public MessageReader(Reader in)
    {
       super(in);
    }

    /** Reads and parses a message. */
    public Message readMessage() throws IOException
    {
        String line = readLine();
        Message message = new Message(line);
        
        message.parseMessage();
        return message;
    } 

}
