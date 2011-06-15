package AP2DX.motor;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;

public class Program extends AP2DXBase {
    /**
	 * Entrypoint of motor
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
        super(Module.ABSTRACTMOTOR); // explicitly calls base constructor
        Actions motor = new Actions();
		System.out.println(" Running Motor... ");
	}
	
	@Override
	protected void doOverride() {
		
	}


	@Override
	public ArrayList<Message> componentLogic(Message msg) {
		
		
		
		
		return new ArrayList<Message>();
	}
}
