package AP2DX.motor;

import java.util.ArrayList;

import AP2DX.*;
import AP2DX.specializedMessages.*;

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
		if (msg.getType().equals("motor"))
		{
			MotorMessage motorMessage = (MotorMessage) msg;
			msg.
		}
		ActionType action = ActionType.valueOf(msg.getValues().get("action").toString());
		
		switch (action) {
		case FORWARD :
			return motor.forward();
			break;
		case BACKWARD :
			return motor.backward();
			break;
		case LEFT :
			return motor.left();
			break;
		case RIGHT :
			return motor.right();
			break;
		case TURN :
			return motor.turn();
		case STOP :
			return motor.stop();
			break;
			
		}
		
		
		
		return new ArrayList<Message>();
	}
}
