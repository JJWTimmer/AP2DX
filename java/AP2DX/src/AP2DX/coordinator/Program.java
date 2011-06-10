/**
 * 
 */
package AP2DX.coordinator;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import AP2DX.AP2DXBase;
import AP2DX.Connection;
import AP2DX.Message;


/**
 * @author jjwt
 *
 */
public class Program extends AP2DXBase {

    /** The port of the simulator. Read from the configuration file. */
    private int simulatorPort;
    /** The IP address of the simulator. Read from the configuration file. */
    private String simulatorAddress;

    private Connection connection;

	/**
	 * entrypoint of coordinator 
	 */
	public static void main (String[] args){
		AP2DXBase instance = new Program();
		
		System.exit(0);
	}

	
	/**
	 * constructor
	 */
	public Program() {
        super(); // explicitly call base ctor for extra 
		System.out.println(" Running... ");

	}

    protected void setConfig() 
    {
        simulatorAddress = (config.get("sim_address")).toString();
        //simulatorPort = config.get("sim_port");
        simulatorPort = 3000;
    }


	@Override
	public ArrayList<Message> componentLogic(Message msg) {
		// TODO Auto-generated method stub
		return null;
	}
}
