package AP2DX;

import java.net.InetAddress;

public class Message {
	
	private Connection conn;
	
	private String message;
	
	private boolean isReceived = false;
	
	public Message (Connection conn, String message) {
		this.conn = conn;
		this.message = message;
	}
	
	public Connection SetConnection (Connection conn) {
		this.conn = conn;
		return conn;
	}
	
	public String SetMessage (String message) {
		this.message = message;
		return message;
	}
	
	public boolean SetIsReceived (boolean isReceived) {
		this.isReceived = isReceived;
		return isReceived;
	}
	
	public Connection GetConnection () {
		return conn;
	}
	
	public String GetMessage () {
		return message;
	}
	
	public boolean GetIsReceived () {
		return isReceived;
	}
	
	/**
	 * True if the message is received, false if the message is for sending.
	 */
	public boolean isReceived () {
		return isReceived;
	}

}
