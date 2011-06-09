package AP2DX;

import java.util.concurrent.ArrayBlockingQueue;

public class Listner extends Thread{

	int QueueSize = 256;
	
	private ArrayBlockingQueue<Message> received = new ArrayBlockingQueue<Message>(QueueSize);
	
	/**
	 * Starts the thread.
	 */
	public void run() {
	}
	
	public Message GetMessage() {
		Message temp = received. (0); // take??????????? It wil probaly block the complete program
		temp.SetIsReceived(true);
		return temp;
	}

}
