/**
 * 
 */
package AP2DX.coordinator;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import AP2DX.*;


/**
 * @author Jasper Timmer
 *
 */
public class Program extends AP2DXBase {
    /**
	 * entrypoint of coordinator 
	 */
	public static void main (String[] args){
		new Program();
		
		System.exit(0);
	}

	
	/**
	 * constructor
	 */
	public Program() 
    {
        super(Module.COORDINATOR); // Explicitly calls base constructor
		System.out.println(" Running Coordinator... ");

	}
	
	@Override
	protected void doOverride() {
		System.out.println("Warning, security override in progress");
		
		String address = config.get("sim_address").toString();
		int port = Integer.parseInt(config.get("sim_port").toString());
		PrintWriter out = null;
		UsarSimMessageReader in = null;
		
		try {
			Socket socket = new Socket(address, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new UsarSimMessageReader(socket.getInputStream());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		try {
			out.println("INIT {ClassName USARBot.P2DX} {Location 4.5,1.9,1.8} {Name R1}");
			//out.flush();
			Message msg = in.readMessage();
			System.out.println(msg.getValues().get("type"));
			Thread.sleep(10);
			out.println("DRIVE {Left -1.0} {Right 1.0}");
			Thread.sleep(5000);
			out.println("DRIVE {Left 1.0} {Right -1.0}");
			System.out.println(in.readLine());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public ArrayList<Message> componentLogic(Message msg) {
		// TODO Auto-generated method stub
		return new ArrayList<Message>();
	}
}
