package AP2DX.mapper;

import java.util.ArrayList;

import AP2DX.*;
import AP2DX.specializedMessages.InsSensorMessage;
import AP2DX.specializedMessages.OdometrySensorMessage;
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
                message.compileMessage();
                messageList.add(message);
                break;
            case AP2DX_SENSOR_ODOMETRY:
            	System.out.println("parsing odometry message in the mapper");
                OdometrySensorMessage message2 = (OdometrySensorMessage) msg;
                message2.setDestinationModuleId(Module.PLANNER);
                message2.compileMessage();
                messageList.add(message2);
                break;
            case AP2DX_SENSOR_SONAR:
                SonarSensorMessage message3 = (SonarSensorMessage) msg;
                message3.setDestinationModuleId(Module.PLANNER);
                message3.compileMessage();
                messageList.add(message3);
                break;
            default:
//                msg.setDestinationModuleId(Module.PLANNER);
//                msg.setSourceModuleId(IAM);
//                AP2DXMessage msg2 = (AP2DXMessage) msg;
//                msg2.compileMessage();
//                messageList.add(msg2); 
            	
                //System.out.println("Unexpected message type in ap2dx.sensor.Program: " + message.getMsgType());

        }  
         
		return messageList;
	}
}
