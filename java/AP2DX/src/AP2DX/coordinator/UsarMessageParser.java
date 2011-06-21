package AP2DX.coordinator;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Map;

import AP2DX.*;
import AP2DX.specializedMessages.*;
import AP2DX.usarsim.*;
import AP2DX.usarsim.specialized.*;

/**
 * This is the class that will take care of receiving messages from UsarSim, and forwarding them to 
 * the Sensor module. 
 * @author Maarten de Waard
 */
public class UsarMessageParser extends Thread
{
    /** An UsarSimMessageReader to read the messages USARSim sends us. */
    private UsarSimMessageReader in;
    /** The module this parser relates to (For message sending purposes) */
    private Module IAM;
    /** The module this parser sends to */
    private Module send;
    /** The connectionHandler that handles the send connection */
    private ConnectionHandler sendConnection;
    /** The base class (supposed to be the AP2DXBase of coordinator */
    private AP2DXBase base;
    /** The config file of the program */
	protected Map config;
    /**
     * This constructor configures the connection with usarsim, 
     * with the data that is specified in the config file.
     * @param base The base (should be coordinator)
     * @param IAM The module from which this parser will send messages
     * @param send The module this parser will send to
     */
    public UsarMessageParser(AP2DXBase base, Module IAM, Module send, Map config)
    {
        this.config = config;
        System.out.println("Config sim port: " + config.get("sim_port"));
        String address = config.get("sim_address").toString();
        int port = Integer.parseInt(config.get("sim_port").toString());
        try 
        {
            Socket socket = new Socket(address, port);
            in = new UsarSimMessageReader(socket.getInputStream());
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }

    public void run()
    {
        while (true) 
        {
            System.out.println("Starting to run while loop in run");
            Message messageIn = null;
            // Get the message from the base
            try 
            {
                System.out.println("Reading message");
                messageIn = in.readMessage();
            } catch (Exception e) {
                e.printStackTrace();
                base.logger
                    .severe("Error in UsarMessageParser.run, attempted to retrieve item out of messageReader");
            }
            //Initialize the message we will return. This shouldn't stay null.
            AP2DXMessage message = null;
            
            switch (messageIn.getMsgType())
            {
                case USAR_SENSOR:
                    System.out.println("USAR SENSOR message detected!");
                    UsarSimSensorMessage sensorMessage = null;
                    try
                    {
                    sensorMessage = new UsarSimSensorMessage((UsarSimMessage) messageIn);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    switch (sensorMessage.getSensorType())
                    {
                        case USAR_SONAR:
                            try
                            {   
                                System.out.println("Trying to send Sonar Message");
                                USonarSensorMessage sonarMessage= new USonarSensorMessage(sensorMessage);
                                //Create a new message to the Sensor module
                                message = new SonarSensorMessage(IAM, Module.SENSOR);
                                // Put the right values in the message
                                message = (SonarSensorMessage) sonarMessage.toAp2dxMessage();
                            }
                            catch (Exception e)
                            {
                                System.err.println("Some exception occured while making a SonarMessage");
                                System.err.println(e.getMessage());
                            }
                            break;
                        default: 
                            System.out.println("Somethings wrong!?");
                    };
                    break;
                default:
                    System.out.println("Unexpected message type in ap2dx.coordonator.UsarMessageParser: " + messageIn.getMsgType());
            };
            System.out.printf("After switches. The message now is %s\n", message);
            //Try to send the message to the right connection.
            if(message != null && message.getMsgType() != Message.MessageType.UNKNOWN)
            {
                try 
                {
                    System.out.println("Trying to send message");
                    sendConnection.sendMessage(message);
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                    base.logger
                        .severe("Error in UsarMessageParser.run, attempted get the connection of message: "
                                + message);
                }
            }
            System.out.printf("End of while loop, message %s not sent.\n", message);
        }
    } 
}


