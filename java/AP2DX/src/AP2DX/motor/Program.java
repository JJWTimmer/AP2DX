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
public class Program extends AP2DXBase 
{

    /** The amount of meters we want to drive forward or backward. The 
    * x-axis is the axis paralel to the forward and backward moving direction. 
    * See figure 18 "SAE J670 Vehicle Coordinate System" of the USAR sim manual. 
    * Negative simply means moving backward. */
    private double xAxisAim = 0;
    /** The amount of degrees we want to turn left or right. See figure 18
    * "SAE J670 Vehicle Coordiante System" of the USAR sim manual. Negative
    * means 'rotating' (or rather turning) left. */
    private double yawAim = 0;

    /** Used to calculate if we have already moved a distance or not. */
    private double doubleLastOdometryX = 0;
    /** Used to calculate if we have already moved a distance or not. */
    private double doubleLastOdoMetryY = 0;

    /**
	 * Entry point of motor
	 */
	public static void main (String[] args)
    {
		new Program();
	}
	
	/**
	 * constructor
	 */
	public Program() 
    {
        super(Module.ABSTRACTMOTOR); // explicitly calls base constructor
		System.out.println("Running Motor... ");
	}
	
    /** This module receives a "motor action message" from the reflex module
    * and sends "motor messages" to the coordinator. The messages received 
    * will contain information such as "forward 1 meter" or "left 30 degrees". 
    * 
    * This modules task is to send messages to the coordinator to make the robot
    * drive. To send messages with a delay seems like a terrible idea because
    * these messages could be become unwanted (for example when new commands 
    * have been given. 
    * 
    */
	public ArrayList<AP2DXMessage> componentLogic(Message msg) 
    {
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();

        if (!msg.getMsgType().isAp2dxMessage)
        {
            System.out.println("Unexpected message in ap2dx.motor.Program, was not an AP2DX message type.");
            return null;
        }
		
        switch (msg.getMsgType())
        {
            case AP2DX_MOTOR_ACTION: 
                doMotorActionLogic((ActionMotorMessage) msg, messageList);
                break;
            case AP2DX_SENSOR_ODOMETRY:
                doOdometryLogic((OdometrySensorMessage) msg, messageList);
            default:
                System.out.println("Unexpected message type in ap2dx.motor.Program: " + msg.getMsgType());
        }
		return messageList;
	}

    /** Processes an odometry sensor message and adds new messages to the
    * second argument if necessary. {{{ */
    private void doOdometryLogic(OdometrySensorMessage odometrySensorMessage,
        ArrayList<AP2DXMessage> messageList)
    {
        
    } // }}}

    /** Processes a motor action message and adds new messages to the second
    * argument if necessary. {{{*/
    private void doMotorActionLogic(ActionMotorMessage actionMotorMessage, 
        ArrayList<AP2DXMessage> messageList)
    {
	    ActionMotorMessage.ActionType action = actionMotorMessage.getActionType();

	    switch (action) 
        {
	        case FORWARD:
                messageList.add(new MotorMessage(IAM, Module.COORDINATOR, 100, 100));
                break;
	        case BACKWARD:
	        	//return motor.backward(actionMotorMessage.getValue());
                break;
	        case LEFT:
	        	//return motor.left(actionMotorMessage.getValue());
                break;
	        case RIGHT:
	        	//return motor.right(actionMotorMessage.getValue());
                break;
	        case TURN:
	        	//return motor.turn(actionMotorMessage.getValue());
                break;
	        case STOP:
	        	//return motor.stop();
                break;
            default:
                System.out.println("Error in motor.program.componentlogic");
	    }
        //messageList.add(sonarSensorMessage);
    } // }}}
}
