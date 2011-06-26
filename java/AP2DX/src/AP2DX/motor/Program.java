package AP2DX.motor;

import java.util.ArrayList;
import java.lang.Math;

import AP2DX.*;
import AP2DX.specializedMessages.*;

/**
 * This module translates commands such as "drive forward 1 metre" to something
 * the coordinator can send (almost directly) to the USAR sim. 
 * 
 * To "know" when the robot has moved X metres is tricky. Currently the odometry
 * sensor data is used. This is the x and y position relative to the start position.
 * When we want to move Z metres we simply interpolate the distance from different
 * odometry data and calculate the distance travelled. 
 *
 * For turns we might be able to use the last argument of the odometry data.
 *
 * @author Wadie Assal
 * @author Maarten Inja
 */
public class Program extends AP2DXBase 
{

    /** The distance (measured in ???) since the last odometry data was received.*/
    private double deltaDistanceSinceLastCommand = 0;
    /** The distance to travel. */
    private double distanceAim = 0;
    
    ///** The amount of meters we want to drive forward or backward. The 
    //* x-axis is the axis paralel to the forward and backward moving direction. 
    //* See figure 18 "SAE J670 Vehicle Coordinate System" of the USAR sim manual. 
    //* Negative simply means moving backward. */
    //private double xAxisAim = 0;
    ///** The amount of degrees we want to turn left or right. See figure 18
    //* "SAE J670 Vehicle Coordiante System" of the USAR sim manual. Negative
    //* means 'rotating' (or rather turning) left. */
    //private double yawAim = 0;

    /** Used to calculate if we have already moved a distance or not. */
    private double lastOdometryX = 0;
    /** Used to calculate if we have already moved a distance or not. */
    private double lastOdometryY = 0;

    /**
	 * Entry point of motor.
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
        super(Module.MOTOR); // explicitly calls base constructor
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
	public ArrayList<AP2DXMessage> componentLogic(Message message) 
    {
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();
        System.out.println("Motor received message: " + message.getMessageString());
        switch (message.getMsgType())
        {
            case AP2DX_MOTOR_ACTION: 
                doMotorActionLogic(new ActionMotorMessage((AP2DXMessage) message), messageList);
                break;
            case AP2DX_SENSOR_INS:
                doOdometryLogic((OdometrySensorMessage) message, messageList);
                break;
            default:
                System.out.println("Unexpected message type in ap2dx.motor.Program: " + message.getMsgType());
        }
		return messageList;
	}

    /** Processes an odometry sensor message and adds new messages to the
    * second argument if necessary. {{{ */
    private void doOdometryLogic(OdometrySensorMessage odometrySensorMessage,
        ArrayList<AP2DXMessage> messageList)
    {
        double odometryX = odometrySensorMessage.getX();
        double odometryY = odometrySensorMessage.getY();

        // linear interpolation to calculate how much the robot has travelled 
        double deltaX = odometryX - lastOdometryX;
        double deltaY = odometryY - lastOdometryY;
        deltaDistanceSinceLastCommand += Math.sqrt(deltaX * deltaX + deltaY * deltaY);
     
        lastOdometryX = odometryX;
        lastOdometryY = odometryY;

        // if we've travelled enough, STOP!
        if (deltaDistanceSinceLastCommand >= distanceAim)
            messageList.add(new MotorMessage(IAM, Module.COORDINATOR, 0, 0));
        
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
                deltaDistanceSinceLastCommand = 0;
                distanceAim = actionMotorMessage.getValue();
                messageList.add(new MotorMessage(IAM, Module.COORDINATOR, 20, 20));
                break;
	        case BACKWARD:
	        	//return motor.backward(actionMotorMessage.getValue());
                deltaDistanceSinceLastCommand = 0;
                distanceAim = actionMotorMessage.getValue();
                messageList.add(new MotorMessage(IAM, Module.COORDINATOR, -10, -10));
                break;
	        case LEFT:
	        	//return motor.left(actionMotorMessage.getValue());
                //TODO: THere should come something here, that keeps track of how far we turned.
                messageList.add(new MotorMessage(IAM, Module.COORDINATOR, -10, 10));
                break;
	        case RIGHT:
	        	//return motor.right(actionMotorMessage.getValue());
                messageList.add(new MotorMessage(IAM, Module.COORDINATOR, 10, -10));
                break;
	        case TURN:
	        	//return motor.turn(actionMotorMessage.getValue());
	        	double turnTime = actionMotorMessage.getValue();
	        	if (turnTime < 0) {
	        		messageList.add(new MotorMessage(IAM, Module.COORDINATOR, -5, 5));
	        	}
	        	else if (turnTime > 0) {
	        		messageList.add(new MotorMessage(IAM, Module.COORDINATOR, 5, -5));
	        	}
	        	AP2DXMessage msg = new MotorMessage(IAM, Module.COORDINATOR, 0, 0);
	        	msg.setDelay(Math.abs((long)turnTime));
	        	messageList.add(msg);
	        	
                break;
	        case STOP:
	        	//return motor.stop();
                messageList.add(new MotorMessage(IAM, Module.COORDINATOR, 0, 0));
                break;
            default:
                System.out.println("Error in motor.program.componentlogic");
	    }
        //messageList.add(sonarSensorMessage);
    } // }}}
}
