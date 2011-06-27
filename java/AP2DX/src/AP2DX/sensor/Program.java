package AP2DX.sensor;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;
import AP2DX.specializedMessages.*;
import AP2DX.sensor.*;

// Imports for the interface
import javax.swing.*;
import java.awt.Graphics;
import java.lang.Thread;

public class Program extends AP2DXBase 
{

    /** The array with the range data from the Sonar Sensor */
    //private double[] rangeArray;

    private Drawer drawer;

    /**
     * Entrypoint of mapper
     */
    public static void main (String[] args){
        new Program();
    }


    /**
     * constructor
     */
    public Program() 
    {
        super(Module.SENSOR); // explicitly calls base constructor
        System.out.println(" Running Sensor... ");
    }

    /**
     * In this override, the program sets up a new JFrame. Then it creates a ``Drawer'', our class that draws lines of the sensor data.
     * It does enough to add the drawer to the frame, sets the size of the frame to 400x400 and draws a standard line
     * This standard line is just to see that it works
     * TODO: If everything works, stop drawing this line.
     */
    @Override
        protected void doOverride() 
        {
            JFrame frame = new JFrame("Sonar");
            drawer = new Drawer();
            frame.getContentPane().add(drawer);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            frame.setVisible(true);
            System.out.println("Ended override");

            // probably for testin' purposes? Removed either way :P
            // (In hindsight I discovered it wasn't for testing purposes but values could not be 
            // null... )
            //double[] sonarDataSample = {2.0, 3.0, 4.0, 5.0, 5.0, 4.0, 3.0, 2.0};
            //// test data to draw range scanner data

            //drawer.paintSonarLines(sonarDataSample); 
            //drawer.paintRangeScannerLines(rangeScannerDataSample);
        }

    @Override
    public ArrayList<AP2DXMessage> componentLogic(Message message) 
    {
        //System.out.println("\tReceived a message in the component logic of the sensor module. The message was: " + message.getMessageString());
        ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();

        
        try 
        {
            switch (message.getMsgType())
            {
                case AP2DX_SENSOR_ODOMETRY:
                	System.out.println("parsing odometry message in sensor");
                    
                	OdometrySensorMessage odometrySensorMessage = (OdometrySensorMessage) message;
                	
                    odometrySensorMessage.setDestinationModuleId(Module.MAPPER);
                    odometrySensorMessage.setSourceModuleId(IAM);
                    
                    odometrySensorMessage.compileMessage();
                    
                    messageList.add(odometrySensorMessage);

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
                    //System.out.println("Unexpected message type in ap2dx.sensor.Program: " + message.getMsgType());

            }  
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong in the parsing of a message in sensor.Program.compontentLogic");
            System.out.println("message.getMessageString() " + message.getMessageString());
            System.out.println("message.getMsgType() " + message.getMsgType());
            System.out.println("e.getMessage() " + e.getMessage());
            System.out.println("Printing stacktrace:");
            e.printStackTrace(); 
        } 
    

        //for (int i = 0; i < messageList.size(); i ++)
        //    System.out.println("!!!! " + i + " message: " + messageList.get(i).getMessageString());
    
        return messageList;
    }

    private void doInsLogic(InsSensorMessage insSensorMessage, 
        ArrayList<AP2DXMessage> messageList)
    {
        // TO THE MAPPER! Said Batman
        insSensorMessage.parseMessage();
        insSensorMessage.setSourceModuleId(IAM);
        insSensorMessage.setDestinationModuleId(Module.MAPPER);
        insSensorMessage.compileMessage();
        messageList.add(insSensorMessage);

        // TO THE REFLEX! 
        //insSensorMessage.compileMessage();
        InsSensorMessage message2 = new InsSensorMessage(
        //insSensorMessage);
               (AP2DXMessage) insSensorMessage.clone());
        message2.setDestinationModuleId(Module.REFLEX);
        message2.compileMessage();
        messageList.add(message2);
    }

    private void doSonarLogic(SonarSensorMessage sonarSensorMessage,
        ArrayList<AP2DXMessage> messageList)
    {
        if (drawer == null)
            System.out.println("ERROR in AP2DX.sensor.Program.ComponentLogic(), drawer == null");
        else
            drawer.paintSonarLines(sonarSensorMessage.getRangeArray());
        
        // TO THE REFLEX! 
        SonarSensorMessage sonarSensorMessage2 = new SonarSensorMessage( (AP2DXMessage) sonarSensorMessage.clone());
        sonarSensorMessage2.parseMessage();
        sonarSensorMessage2.setSourceModuleId(Module.SENSOR);
        sonarSensorMessage2.setDestinationModuleId(Module.REFLEX);  
        sonarSensorMessage2.compileMessage();
        messageList.add(sonarSensorMessage2);
        //messageList.add(sonarSensorMessage.forward(Module.SENSOR, Module.REFLEX));
        
        // TO THE MAPPER
        SonarSensorMessage sonarSensorMessage3 = new SonarSensorMessage( (AP2DXMessage) sonarSensorMessage.clone());
        sonarSensorMessage3.parseMessage();
        sonarSensorMessage3.setSourceModuleId(Module.SENSOR);
        sonarSensorMessage3.setDestinationModuleId(Module.MAPPER);  
        sonarSensorMessage3.compileMessage();
        messageList.add(sonarSensorMessage3);
    }

    private void doRangeLogic(RangeScannerSensorMessage rangeScannerSensorMessage,
        ArrayList<AP2DXMessage> messageList)
    {
        //System.out.println("RangeScanner message detected");

        if (drawer == null)
            System.out.println("ERROR in AP2DX.sensor.Program.ComponentLogic(), drawer == null");
        else
        {
            drawer.setRangeScannerFov(rangeScannerSensorMessage.
                getFov());
            drawer.setRangeScannerResolution(
                rangeScannerSensorMessage.getResolution());
            drawer.paintRangeScannerLines(rangeScannerSensorMessage.
                getDataArray());
        }


        // TO THE REFLEX! 
        RangeScannerSensorMessage m2 = new RangeScannerSensorMessage( (AP2DXMessage) rangeScannerSensorMessage.clone());
        m2.parseMessage();
        m2.setSourceModuleId(Module.SENSOR);
        m2.setDestinationModuleId(Module.REFLEX);  
        m2.compileMessage();
        messageList.add(m2);
        
        // TO THE MAPPER
        RangeScannerSensorMessage m3 = new RangeScannerSensorMessage( (AP2DXMessage) rangeScannerSensorMessage.clone());
        m3.parseMessage();
        m3.setSourceModuleId(Module.SENSOR);
        m3.setDestinationModuleId(Module.REFLEX);  
        m3.compileMessage();
        messageList.add(m3);
    }
}




















