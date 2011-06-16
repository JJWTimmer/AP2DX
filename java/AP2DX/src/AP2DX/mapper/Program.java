package AP2DX.mapper;

import java.util.ArrayList;

import AP2DX.*;

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
        super(Module.MAPPER); // explicitly calls base constructor
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
