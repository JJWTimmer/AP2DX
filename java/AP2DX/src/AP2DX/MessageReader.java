package AP2DX;

import java.io.BufferedReader;
import java.io.Reader;

/** Specialized bufferedReader to directly parse messages from a (in our case) 
* sockets. 
* 
* @author Maarten Inja
*/ 
public class MessageReader extends BufferedReader
{
    public MessageReader(Reader in)
    {
       super(in);
    }

    /** Reads and parses a message. */
    public Message readMessage() throws IOException
    {
        String line = super.readMessage();
        Message message = new Message(line);
        message.parse();
        return message;
    } 

}
