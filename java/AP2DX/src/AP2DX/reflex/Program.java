package AP2DX.reflex;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;
import AP2DX.specializedMessages.ActionMotorMessage;

public class Program extends AP2DXBase {
	
    /**
	 * Entrypoint of reflex
	 */
	public static void main (String[] args){
		new Program();
	}

	
	/**
	 * constructor
	 */
	public Program() 
    {
        super(Module.REFLEX); // explicitly calls base constructor
		System.out.println(" Running Reflex... ");

	}
	
	@Override
	protected void doOverride() {

	}


	@Override
	public ArrayList<AP2DXMessage> componentLogic(Message msg) {
		/* Checks from witch module the message comes from */
		if (msg.getType().equals("")) {
			
			return new ArrayList<AP2DXMessage>();			
		} else if (msg.getType().equals("sensor")) {
			return new ArrayList<AP2DXMessage>();
		} else {
			return new ArrayList<AP2DXMessage>();			
		}
	}
}
