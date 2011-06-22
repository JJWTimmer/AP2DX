package AP2DX.usarsim;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;

import AP2DX.IMessageReader;
import AP2DX.Message;

/**
 * Specialized bufferedReader to directly parse messages from a (in our case)
 * sockets.
 * 
 * @author Maarten Inja
 */
public class UsarSimMessageReader extends BufferedReader implements IMessageReader {
	/**
	*
	* @param in Streamreader to read from
	*/
	public UsarSimMessageReader(InputStream in) {
	    super(new InputStreamReader(in));
	}

	/**
	* Reads a message from the stream
	*/
    public Message readMessage() throws IOException {
        String line = null;
        while (line == null)
        {
            System.out.println("Trying to read line");
            line = readLine();
        }
        Message message = new UsarSimMessage(line);

        return message;
    }
}
