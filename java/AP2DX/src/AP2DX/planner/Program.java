package AP2DX.planner;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;
import AP2DX.specializedMessages.*;

public class Program extends AP2DXBase 
    {

    /**
	 * Entrypoint of planner
	 */
	public static void main(String[] args)
    {
		new Program();
	}

	
	/**
	 * constructor
	 */
	public Program() 
    {
        super(Module.PLANNER); // explicitly calls base constructor
		System.out.println(" Running Planner... ");
	}
	
	@Override
	public ArrayList<AP2DXMessage> componentLogic(Message message) 
    {
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage> ();

        // for now, lets just drive forward, OKAY?!
        messageList.add(new ActionMotorMessage(IAM, Module.REFLEX, ActionMotorMessage.ActionType.FORWARD, 1));

        switch(message.getMsgType())
        {
            //case AP2DX_MOTOR_ACTION:
            //    message.setDestinationModuleId(Module.MOTOR);
            //    messageList.add(message);            
            //    break;
            default:
                System.out.println("Error in AP2DX.reflex.Program.componentLogic(Message message) Couldn't deal with message: " + message.getMsgType());
        }

 
		return messageList;
	}
}









