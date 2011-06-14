package AP2DX.planner;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.Message;

public class PlannerMain extends AP2DXBase {
    /**
	 * Entrypoint of planner
	 */
	public static void main (String[] args){
		new PlannerMain();
		
		System.exit(0);
	}

	
	/**
	 * constructor
	 */
	public PlannerMain() 
    {
        super(); // explicitly calls base constructor
		System.out.println(" Running Planner... ");

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
