package AP2DX.motor;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.Message;

public class MotorMain extends AP2DXBase {
    /**
	 * Entrypoint of motor
	 */
	public static void main (String[] args){
		new MotorMain();
		
		System.exit(0);
	}

	
	/**
	 * constructor
	 */
	public MotorMain() 
    {
        super(); // explicitly calls base constructor
		System.out.println(" Running Motor... ");

	}
	
	@Override
	protected void doOverride() {

	}


	@Override
	public ArrayList<Message> componentLogic(Message msg) {
		// TODO Auto-generated method stub
		return new ArrayList<Message>();
	}
}
