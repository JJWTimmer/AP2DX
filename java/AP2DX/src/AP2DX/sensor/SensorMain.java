package AP2DX.sensor;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.Message;

public class SensorMain extends AP2DXBase {
    /**
	 * Entrypoint of mapper
	 */
	public static void main (String[] args){
		new SensorMain();
		
		System.exit(0);
	}

	
	/**
	 * constructor
	 */
	public SensorMain() 
    {
        super(); // explicitly calls base constructor
		System.out.println(" Running Sensor... ");

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
