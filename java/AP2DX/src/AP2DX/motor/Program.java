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
	 * Entry point of motor
	 */
	public static void main (String[] args){
		new Program();
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
	public ArrayList<AP2DXMessage> componentLogic(Message msg) {
		
		/* Checks witch type of message it is */
		if (msg.getMsgType().equals(Message.MessageType.AP2DX_MOTOR_ACTION)) {
			ActionMotorMessage actionMotorMessage = (ActionMotorMessage) msg;
			
			
			
			/* 
			 * Probably have to be changed in the future. 
			 * Assumes there is an action value. 
			 * Then converts it form string to enum ActionType.
			 */
			ActionMotorMessage.ActionType action = actionMotorMessage.getActionType();
			
			/* 
			 * Switches to decide witch action to take.
			 * Uses the enum ActionType to switch.
			 */
			switch (action) 
            {
			    case FORWARD:
                    return motor.forward(actionMotorMessage.getValue());
			    case BACKWARD:
			    	return motor.backward(actionMotorMessage.getValue());
			    case LEFT:
			    	return motor.left(actionMotorMessage.getValue());
			    case RIGHT:
			    	return motor.right(actionMotorMessage.getValue());
			    case TURN:
			    	return motor.turn(actionMotorMessage.getValue());
			    case STOP:
			    	return motor.stop();
                default:
                    System.out.println("Error in motor.program.componentlogic");
			}
			
			return new ArrayList<AP2DXMessage>();
		} else {
			return new ArrayList<AP2DXMessage>();
		}
	}
}
