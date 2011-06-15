package AP2DX.reflex;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.ActionType;
import AP2DX.Message;
import AP2DX.Module;

public class Program extends AP2DXBase {
	
	Actions reflex = new Actions();
    /**
	 * Entrypoint of reflex
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
        super(Module.REFLEX); // explicitly calls base constructor
		System.out.println(" Running Reflex... ");

	}
	
	@Override
	protected void doOverride() {

	}


	@Override
	public ArrayList<Message> componentLogic(Message msg) {
		/* Checks from witch module the message comes from */
		if (msg.getType().equals("planner")) {
			/* 
			 * Probably have to be changed in the future. 
			 * Assumes there is an action value. 
			 * Then converts it form string to enum ActionType.
			 */
			ActionType action = ActionType.valueOf(msg.getValues().get("action").toString());
			
			/* 
			 * Switches to decide witch action to take.
			 * Uses the enum ActionType to switch.
			 */
			switch (action) {
			case FORWARD :
				return reflex.forward(0);
			case BACKWARD :
				return reflex.backward(0);
			case LEFT :
				return reflex.left(0);
			case RIGHT :
				return reflex.right(0);
			case TURN :
				return reflex.turn(0);
			case STOP :
				return reflex.stop();
			}
			
			return new ArrayList<Message>();			
		} else if (msg.getType().equals("sensor")) {
			return new ArrayList<Message>();
		} else {
			return new ArrayList<Message>();			
		}
	}
}
