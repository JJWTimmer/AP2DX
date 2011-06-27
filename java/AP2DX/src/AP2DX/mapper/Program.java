package AP2DX.mapper;

import java.util.ArrayList;

import AP2DX.*;
import AP2DX.specializedMessages.InsSensorMessage;
import AP2DX.specializedMessages.RangeScannerSensorMessage;
import AP2DX.specializedMessages.SonarSensorMessage;

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
        
        switch (msg.getMsgType())
        {
            case AP2DX_SENSOR_INS:
                InsSensorMessage message = (InsSensorMessage) msg;
                message.setDestinationModuleId(Module.PLANNER);
                message.setSourceModuleId(IAM);
                message.compileMessage();
                break;
            default:
//                msg.setDestinationModuleId(Module.PLANNER);
//                msg.setSourceModuleId(IAM);
//                AP2DXMessage msg2 = (AP2DXMessage) msg;
//                msg2.compileMessage();
//                messageList.add(msg2); 
            	
                //System.out.println("Unexpected message type in ap2dx.sensor.Program: " + message.getMsgType());

        }  
        
        messageList.add((AP2DXMessage) msg); 
		return messageList;
	}
}
