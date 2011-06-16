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
		
		/* Checks witch type of message it is */
		if (msg.getType().equals("AP2DX_MOTOR_ACTION")) {
			ActionMotorMessage specializedMessage = (ActionMotorMessage) msg;
			
			
			
			/* 
			 * Probably have to be changed in the future. 
			 * Assumes there is an action value. 
			 * Then converts it form string to enum ActionType.
			 */
			ActionMotorMessage.ActionType action = specializedMessage.getActionType();
				ActionMotorMessage.ActionType.valueOf(msg.getValues().get("action").toString());
			
			/* 
			 * Switches to decide witch action to take.
			 * Uses the enum ActionType to switch.
			 */
			switch (action) {
			case FORWARD :
				return motor.forward(Integer.getInteger(msg.getValues().get("value").toString()));
			case BACKWARD :
				return motor.backward(Integer.getInteger(msg.getValues().get("value").toString()));
			case LEFT :
				return motor.left(Integer.getInteger(msg.getValues().get("value").toString()));
			case RIGHT :
				return motor.right(Integer.getInteger(msg.getValues().get("value").toString()));
			case TURN :
				return motor.turn(Integer.getInteger(msg.getValues().get("value").toString()));
			case STOP :
				return motor.stop();
			}
			
			return new ArrayList<Message>();
		} else {
			return new ArrayList<Message>();
		}
	}
}
