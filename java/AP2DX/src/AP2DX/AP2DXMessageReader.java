package AP2DX;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;

import AP2DX.specializedMessages.*;


/**
 * Specialized bufferedReader to directly parse messages from a (in our case)
 * sockets.
 * 
 * @author Maarten Inja
 */
public class AP2DXMessageReader extends BufferedReader implements IMessageReader {
	/**
	 * source of messages of the stream
	 */
	private Module source = null;
	
	/**
	 * destinatnion of messages of the stream, should be component self.
	 */
	private Module destination = null;


	/**
	 * 
	 * @param in
	 */
	public AP2DXMessageReader(InputStream in, Module source) {
		super(new InputStreamReader(in));
		this.source = source;
	}

	
	/**
	 * 
	 * @param in
	 */
	public AP2DXMessageReader(InputStream in, Module source, Module destination) {
		super(new InputStreamReader(in));
		this.source = source;
		this.destination = destination;
	}

	/**
	 * Reads a message and parses it. Messag
	 */
	public Message readMessage() throws IOException 
    {
		String line = readLine();
		AP2DXMessage message = new AP2DXMessage(line, source);
        if (message.getType() == "sensor")
            return new SensorMessage(message);
        if (message.getType() == "motor")
            return new MotorMessage(message);
        System.out.println("No specialized message could be parsed!"); 
		return message;
	}
}























