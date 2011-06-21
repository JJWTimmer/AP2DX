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
		super(new InputStreamReader(in), 1024);
	}

	/**
	 * Reads a message from the stream
	 */
	public Message readMessage() throws IOException {
		String line = "";
        for (int i=0;i < 1000; i++)
        {
            int a = read();
            System.out.print(a);
            line += "a";
        }
        
		Message message = new UsarSimMessage(line);

		return message;
	}
}
