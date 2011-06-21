package AP2DX.reflex;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import sun.misc.Queue;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.ConnectionHandler;
import AP2DX.Message;
import AP2DX.Module;
import AP2DX.specializedMessages.ActionMotorMessage;

public class Program extends AP2DXBase 
{
	/**
	 * Field to keep all not-send motormessages. They will be queued when there is something wrong.
	 */
    ConcurrentLinkedQueue<AP2DXMessage> motorBacklog = new ConcurrentLinkedQueue<AP2DXMessage>();
	
	
    /**
	 * Entrypoint of reflex
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
        super(Module.REFLEX); // explicitly calls base constructor
		System.out.println(" Running Reflex... ");

	}
	
	
	/**
	 * Does the logic for the Reflex module. That is: <br/>
	 * - check if there is something in front of the vehicle and stop the motor.<br/>
	 * - forward messages from the Planner to the motor if there is nothing.
	 * @param message The AP2DX Message that is received and read from the queue.
	 */
	@Override
	public ArrayList<AP2DXMessage> componentLogic(Message message) 
    {
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage> ();

 
        switch(message.getMsgType())
        {
            case AP2DX_MOTOR_ACTION:
                //TODO: check command and override if nessecary
            	// for now, we will just forward stuff to the motor, okay!?
            	
                message.setDestinationModuleId(Module.MOTOR);
                messageList.add((AP2DXMessage)message);            
                break;
            case AP2DX_SENSOR_SONAR:
            	
            	break;
            default:
                System.out.println("Error in AP2DX.reflex.Program.componentLogic(Message message) Couldn't deal with message: " + message.getMsgType());
        }
        return messageList;
	}
	
	private class MotorPassthrough implements Runnable {
		AP2DXBase base;
		
		public MotorPassthrough(AP2DXBase base) {
			this.base = base;
		}
		
		@Override
		public void run() {
			ConnectionHandler conn = null;
			try {
				conn = this.base.getSendConnection(Module.MOTOR);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while (true) {
				AP2DXMessage msg = motorBacklog.poll();
				
				if (msg != null) {
					AP2DXBase.logger.severe("Motor connection no longer alive");
				}
			}
		}
	}
}
