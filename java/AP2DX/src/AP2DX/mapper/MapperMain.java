package AP2DX.mapper;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.Message;

public class MapperMain extends AP2DXBase {
    /**
	 * Entrypoint of mapper
	 */
	public static void main (String[] args){
		new MapperMain();
		
		System.exit(0);
	}

	
	/**
	 * constructor
	 */
	public MapperMain() 
    {
        super(); // explicitly calls base constructor
		System.out.println(" Running Mapper... ");

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
