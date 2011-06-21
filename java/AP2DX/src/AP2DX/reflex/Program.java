package AP2DX.reflex;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;
import AP2DX.specializedMessages.ActionMotorMessage;

public class Program extends AP2DXBase 
{
	
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
	

	@Override
	public ArrayList<AP2DXMessage> componentLogic(Message message) 
    {
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage> ();

        switch(message.getMsgType())
        {
            case AP2DX_MOTOR_ACTION:
                // for now, well just forward stuff to the motor, okay!?
                message.setDestinationModuleId(Module.MOTOR);
                messageList.add((AP2DXMessage)message);            
                break;
            default:
                System.out.println("Error in AP2DX.reflex.Program.componentLogic(Message message) Couldn't deal with message: " + message.getMsgType());
        }
        return messageList;
	}
}
