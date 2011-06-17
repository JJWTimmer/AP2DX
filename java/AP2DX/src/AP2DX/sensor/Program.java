package AP2DX.sensor;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;

public class Program extends AP2DXBase {
    /**
	 * Entrypoint of mapper
	 */
	public static void main (String[] args){
		new Program();
	}

	
	/**
	 * constructor
	 */
	public Program() 
    {
        super(Module.SENSOR); // explicitly calls base constructor
		System.out.println(" Running Sensor... ");

	}
	
	@Override
	protected void doOverride() {

	}


	@Override
	public ArrayList<AP2DXMessage> componentLogic(Message msg) {
		// TODO Auto-generated method stub
		return new ArrayList<AP2DXMessage>();
	}
}
