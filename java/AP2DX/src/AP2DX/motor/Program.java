package AP2DX.motor;

import java.util.ArrayList;

import AP2DX.*;
import AP2DX.specializedMessages.*;

/**
 * 
 * 
 * @author Wadie Assal
 * 
 */
public class Program extends AP2DXBase {
	
	Actions motor = new Actions();
	
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
		System.out.println(" Running Motor... ");
	}
	
	@Override
	protected void doOverride() {
		
	}


	@Override
	public ArrayList<Message> componentLogic(Message msg) {
		/* Checks from witch module the message comes from */
		if (msg.getType().equals("AP2DX_MOTOR")) {
			/* 
			 * Probably have to be changed in the future. 
			 * Assumes there is an action value. 
			 * Then converts it form string to enum ActionType.
			 */
			MotorMessage.ActionType action = ActionType.valueOf(msg.getValues().get("action").toString());
			
			/* 
			 * Switches to decide witch action to take.
			 * Uses the enum ActionType to switch.
			 */
			switch (action) {
			case FORWARD :
				return motor.forward(0);
			case BACKWARD :
				return motor.backward(0);
			case LEFT :
				return motor.left(0);
			case RIGHT :
				return motor.right(0);
			case TURN :
				return motor.turn(0);
			case STOP :
				return motor.stop();
			}
			
			return new ArrayList<Message>();
		} else {
			return new ArrayList<Message>();
		}
	}
}
