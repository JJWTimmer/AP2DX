package AP2DX.mapper;

import java.util.ArrayList;

import AP2DX.*;
import AP2DX.specializedMessages.*;


public class Program extends AP2DXBase 
{

    private Slammer slammer; 
    private PictureViewer pictureViewer;
    private String slamDir;

    /**
	 * Entrypoint of mapper
	 */
	public static void main (String[] args)
    {
		new Program();
	}

    public void doOverride()
    {
        slammer = new Slammer();        
        new Thread(slammer).start();
        pictureViewer = new PictureViewer();
        new Thread(pictureViewer).start();
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
         
		return messageList;
	}

    private void doRangeLogic(RangeScannerSensorMessage message, 
        ArrayList<AP2DXMessage> messageList)
    {
        // just slammin'
        if (slammer == null)
            return;
        slammer.addLaser(message.getDataArray());
    }

    private void doInsLogic(InsSensorMessage message, 
        ArrayList<AP2DXMessage> messageList)
    {
        // TO THE PLANNER! 
        message.setDestinationModuleId(Module.PLANNER);
        message.compileMessage();
        messageList.add(message);
    }
    
    private void doSonarLogic(SonarSensorMessage message,
        ArrayList<AP2DXMessage> messageList)
    {
        // TO THE PLANNER!
        message.setDestinationModuleId(Module.PLANNER);
        message.compileMessage();
        messageList.add(message);
    }

    private void doOdometryLogic(OdometrySensorMessage message,
        ArrayList<AP2DXMessage> messageList)
    {
        // TO THE PLANNER!
        message.setDestinationModuleId(Module.PLANNER);
        message.compileMessage();
        messageList.add(message);

        // just slammin'
        if (slammer == null)
            return;
        slammer.addXYTheta(message.getX(), message.getY(), message.getTheta());
    }

}














