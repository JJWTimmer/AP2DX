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
	public Program() 
    {
        super(); // explicitly call base ctor for extra 
		System.out.println(" Running... ");

	}
	
	@Override
	protected void doOverride() {
		System.out.println("Warning, security override in progress");
	}


	@Override
	public ArrayList<Message> componentLogic(Message msg) {
		// TODO Auto-generated method stub
		return null;
	}
}
