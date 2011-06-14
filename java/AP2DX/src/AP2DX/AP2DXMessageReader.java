package AP2DX;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;

/**
 * Specialized bufferedReader to directly parse messages from a (in our case)
 * sockets.
 * 
 * @author Maarten Inja
 */
public class AP2DXMessageReader extends BufferedReader implements IMessageReader {
	/**
	 * origin of messages of the stream
	 */
	private Module origin = null;
	
	/**
	 * destinatnion of messages of the stream, should be component self.
	 */
	private Module destination = null;

	/**
	 * 
	 * @param in
	 */
	public AP2DXMessageReader(Reader in, Module origin) {
		super(in);
		this.origin = origin;
	}
	
	/**
	 * 
	 * @param in
	 */
	public AP2DXMessageReader(Reader in, Module origin, Module destination) {
		super(in);
		this.origin = origin;
		this.destination = destination;
	}

	/**
	 * Reads oen message and parses it.
	 */
	public Message readMessage() throws IOException {
		String line = readLine();
		Message message = new AP2DXMessage(line , origin);

		return message;
	}
}
