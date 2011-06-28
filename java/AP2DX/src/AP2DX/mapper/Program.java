package AP2DX.mapper;

import java.util.ArrayList;

import AP2DX.*;
<<<<<<< HEAD
import AP2DX.specializedMessages.*;
=======
import AP2DX.specializedMessages.InsSensorMessage;
import AP2DX.specializedMessages.RangeScannerSensorMessage;
import AP2DX.specializedMessages.SonarSensorMessage;
>>>>>>> 268bc626146bb927d425e34496b76fd331421099

public class Program extends AP2DXBase 
{

    private String lastReceivedOdometry;
    private String lastReceivedLaser;

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
	
	public ArrayList<AP2DXMessage> componentLogic(Message message) 
    {
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();
        
        switch (message.getMsgType())
        {
            case AP2DX_SENSOR_ODOMETRY:
                doOdometryLogic((OdometrySensorMessage) message, messageList);
                break;
            case AP2DX_SENSOR_INS:
                doInsLogic((InsSensorMessage) message, messageList);
                break;
            case AP2DX_SENSOR_SONAR:
                doSonarLogic((SonarSensorMessage) message, messageList);
                break;
            case AP2DX_SENSOR_RANGESCANNER:
                doRangeLogic((RangeScannerSensorMessage) message, messageList);
                break;
            default:
                System.out.println("Unexpected message type in ap2dx.mapper.Program: " + message.getMsgType());
        }
        // for now just freakin' forward it to the planner okay?!
<<<<<<< HEAD
        //System.out.printf("Forwarding message %s to planner\n", message);
        //message.setDestinationModuleId(Module.PLANNER);
        //message.setSourceModuleId(IAM);
        //messageList.add(((AP2DXMessage) message)); 
=======
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
>>>>>>> 268bc626146bb927d425e34496b76fd331421099
		return messageList;
	}

    
    private void doOdometryLogic(OdometrySensorMessage message, 
        ArrayList<AP2DXMessage> messageList)
    {
        lastReceivedOdometry = String.format("Odometry %f %f %f", 
            message.getX(), message.getY(), message.getTheta());
        System.out.println(lastReceivedOdometry);
    }

    private void doRangeLogic(RangeScannerSensorMessage message,
        ArrayList<AP2DXMessage> messageList)
    {
        lastReceivedLaser = "Laser " + message.getDataArray().length; 
        for (double d : message.getDataArray())
            lastReceivedLaser += " " + d;
        System.out.println(lastReceivedLaser);
    }


    private void doInsLogic(InsSensorMessage insSensorMessage, 
        ArrayList<AP2DXMessage> messageList)
    {
    
    }

    private void doSonarLogic(SonarSensorMessage sonarSensorMessage,
        ArrayList<AP2DXMessage> messageList)
    {
        //if (drawer == null)
        //    System.out.println("ERROR in AP2DX.sensor.Program.ComponentLogic(), drawer == null");
        //else
        //    drawer.paintSonarLines(sonarSensorMessage.getRangeArray());
        //
        //// TO THE REFLEX! 
        //SonarSensorMessage sonarSensorMessage2 = new SonarSensorMessage( (AP2DXMessage) sonarSensorMessage.clone());
        //sonarSensorMessage2.parseMessage();
        //sonarSensorMessage2.setSourceModuleId(Module.SENSOR);
        //sonarSensorMessage2.setDestinationModuleId(Module.REFLEX);  
        //sonarSensorMessage2.compileMessage();
        //messageList.add(sonarSensorMessage2);
        ////messageList.add(sonarSensorMessage.forward(Module.SENSOR, Module.REFLEX));
        //
        //// TO THE MAPPER
        //SonarSensorMessage sonarSensorMessage3 = new SonarSensorMessage( (AP2DXMessage) sonarSensorMessage.clone());
        //sonarSensorMessage3.parseMessage();
        //sonarSensorMessage3.setSourceModuleId(Module.SENSOR);
        //sonarSensorMessage3.setDestinationModuleId(Module.MAPPER);  
        //sonarSensorMessage3.compileMessage();
        //messageList.add(sonarSensorMessage3);
    }


}
