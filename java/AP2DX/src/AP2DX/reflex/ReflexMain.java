package AP2DX.reflex;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.Message;

public class ReflexMain extends AP2DXBase {
    /**
	 * Entrypoint of reflex
	 */
	public static void main (String[] args){
		new ReflexMain();
		
		System.exit(0);
	}

	
	/**
	 * constructor
	 */
	public ReflexMain() 
    {
        super(); // explicitly calls base constructor
		System.out.println(" Running Reflex... ");

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
