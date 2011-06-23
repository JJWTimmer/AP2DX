package AP2DX.mapper;

import java.util.ArrayList;

import AP2DX.*;

public class Program extends AP2DXBase 
{
    /**
	 * Entrypoint of mapper
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
        super(Module.MAPPER); // explicitly calls base constructor
		System.out.println(" Running Mapper... ");
	}
	
	public ArrayList<AP2DXMessage> componentLogic(Message msg) 
    {
        // for now just freakin' forward it to the planner okay?!
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();  
        System.out.printf("Forwarding message %s to planner\n", msg);
        msg.setDestinationModuleId(Module.PLANNER);
        msg.setSourceModuleId(IAM);
        messageList.add(((AP2DXMessage) msg)); 
		return messageList;
	}
}
