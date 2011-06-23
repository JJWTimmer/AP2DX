package AP2DX.planner;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Message.MessageType;
import AP2DX.Module;
import AP2DX.specializedMessages.*;

public class Program extends AP2DXBase 
    {
	private boolean botBlocked = false;

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
        System.out.println("Received message " + message.getMessageString());

        switch(message.getMsgType())
        {
        case AP2DX_PLANNER_STOP:
        	setBotBlocked(true);
        	break;
        default:
        	if (!isBotBlocked()) {
        		// for now, lets just drive forward, OKAY?!
        		messageList.add(new ActionMotorMessage(IAM, Module.REFLEX, ActionMotorMessage.ActionType.FORWARD, 100.0));  
                System.out.println("Sending message "+ messageList.get(0));
        	}
            AP2DXBase.logger.severe("Error in AP2DX.reflex.Program.componentLogic(Message message) Couldn't deal with message: " + message.getMsgType());
        }

 
		return messageList;
	}


	/**
	 * @param isBotBlocked the isBotBlocked to set
	 */
	public void setBotBlocked(boolean isBotBlocked) {
		this.botBlocked = isBotBlocked;
	}


	/**
	 * @return the isBotBlocked
	 */
	public boolean isBotBlocked() {
		return botBlocked;
	}
}









