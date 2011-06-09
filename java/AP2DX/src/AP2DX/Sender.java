package AP2DX;

import java.util.concurrent.ArrayBlockingQueue;


public class Sender extends Thread{
	int QueueSize = 256;
	
	private ArrayBlockingQueue<Message> send = new ArrayBlockingQueue<Message>(QueueSize);
	

	/**
	 * Starts the thread.
	 */
	public void run() {
		while (true) {
			try {
				Message msg = send.take();
				msg.GetConnection().sendMessage(msg.GetMessage());
			} catch (Exception e) {
				System.exit(1);
			}
		}
	}
	
	public Message SendMessage(Message message) {
		try {
			message.SetIsReceived(false);
			send.put(message);
			return message;		
		} catch (Exception e) {
			return null;
		}
	}
}
