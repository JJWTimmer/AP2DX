package AP2DX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Thread;
import java.net.Socket;
import java.net.SocketTimeoutException;

import AP2DX.*;
import AP2DX.usarsim.UsarSimMessageReader;

//import com.sun.xml.internal.ws.api.message.Message;

/**
 * 
 * This class represents a connection with another module. It continues to
 * listen for incoming messages and puts them in the BlockingQueue of the
 * abstract base. There the message will wait until it can be processed by the
 * Logic thread. <br />
 * <br />
 * Besides listening for message, it can also send messages.
 * 
 * @author Maarten Inja
<<<<<<< HEAD
 * */
public class ConnectionHandler extends Thread {

	private Socket socket;
	private AP2DXBase base;
	public final Module moduleID;

	private PrintWriter out;
	private IMessageReader in;
	private boolean alive = true;

	/**
	 * The constructor, saves arguments and reads in- and outcoming streams from
	 * the socket.
	 * 
	 * This constructor is for the base-class self-started connections.
	 * 
	 * @param usar
	 *            If true, handle a USARsim Connection
	 * @throws IOException
	 */
	public ConnectionHandler(boolean usar, AP2DXBase base, Socket socket,
			Module origin, Module destination) throws IOException {
		this.base = base;
		this.socket = socket;
		this.moduleID = destination;
		out = new PrintWriter(socket.getOutputStream(), true);

		if (usar) {
			in = new UsarSimMessageReader(socket.getInputStream());
		} else {
			in = new AP2DXMessageReader(socket.getInputStream(), origin,
					destination);
		}
	}

	/**
	 * The constructor, saves arguments and reads in- and outcoming streams from
	 * the socket.
	 * 
	 * This constructor is for the incoming connections.
	 * 
	 * @param usar
	 *            If true, handle a USARsim Connection
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public ConnectionHandler(AP2DXBase base, Socket socket, Module origin)
			throws IOException, InstantiationException, IllegalAccessException {
		this.base = base;
		this.socket = socket;

		out = new PrintWriter(socket.getOutputStream(), true);

		in = new AP2DXMessageReader(socket.getInputStream(), origin);

		Message firstIncomingMessage = in.readMessage();
		this.moduleID = firstIncomingMessage.getSourceModuleId();

	}

	/** Thread logic. */
	public void run() {
		while (true) // TODO: make interupt variable or something
		{
			try {
				AP2DXMessage incomingMessage = (AP2DXMessage) in.readMessage();
				base.getReceiveQueue().put(incomingMessage);
			} catch (SocketTimeoutException e) {
				AP2DXBase.logger.warning(String.format("Time out receiving from: %s",
						socket.getRemoteSocketAddress()));
				if (!socket.isConnected())
					setConnAlive(false);
					break;
			} catch (Exception e) {
				AP2DXBase.logger
						.severe(String.format("%s IN ConnectionHandler.run, attempting to read message.",e.getMessage()));
			}
		}
	}

	/**
	 * Sends a message object, this is currently only the message object
	 * toString().
	 */
	public void sendMessage(Message message) {
		System.out.printf("Printing message %s to moduleId %s on socket port %s", message.toString(), moduleID, socket.getPort());
		out.print(message.toString());
	}
	
	public boolean isConnAlive() {
		return alive ;
	}
	
	private void setConnAlive(boolean value) {
		alive = value;
	}

}
