 package AP2DX;

import java.util.Map;

/**
 * In the future we can imagine specialized messages that extend this 
 * one, such as "SensordataMessage", or something. 
 * @author Maarten Inja
 * @author Maarten de Waard
 * @author Jasper Timmer
 */
public abstract class Message
{

    /**
     * Enum type for the different types of messages between modules.
     */
    public enum MessageType 
    {
        AP2DX_SENSOR_ENCODER, APD2X_SENSOR_GPS, AP2DX_SENSOR_GROUNDTRUTH, AP2DX_SENSOR_INS, 
        AP2DX_SENSOR_ODOMETRY, AP2DX_SENSOR_RANGESCANNER, AP2DX_SENSOR_SONAR, 
        USAR_STATE, USAR_MISSIONSTATE, AP2DX_MOTOR_ACTION
    }

    /** A type that identifies a message. We might want to change this 
    * to an enum in the future, but for now String will do. */
    protected MessageType type;
	/**
	 * the module that has sent this message
	 */
    protected Module sourceModuleId;
    
    /**
     * the module that should receive this message
     */
    protected Module destinationModuleId;
    
    /**
     * raw message data
     */
    protected String messageString;


    public Message(Module source, Module destination)
    {
        this(null, source, destination);
    }
    
    /**
     * constructor without destination, for received messages
     * @param in raw data
     * @param source sender
     */
    public Message(String in, Module source)
    {
        this(in, source, Module.UNDEFINED);
    }
    
    /**
     * constructor with sender and receiver defined
     * @param in raw data
     * @param source sending module
     * @param destination receiving module
     */
    public Message(String in, Module source, Module destination)
    {
        this.messageString = in; 
        this.sourceModuleId = source;
        this.destinationModuleId = destination;
    }

    /**
     * override this to parse specific messagetypes
     * TODO: find a better solution to fix this.
     * @throws Exception 
     */
    protected abstract void parseMessage() throws Exception;
   
    ///** Parses the <String, Object> values to compile a String messageString 
    //* which makes this object ready to send. */
    //protected abstract void parseMessage(); // I do not know why one would throw an exception here... 

    /**
    * When the map of this message is filled we can compile a string that can be 
    * send (and parsed once received on the other side).
     * @return 
    */
    protected String compileMessage()  throws Exception {
    	throw new Exception("Can only call this method from specialized messages.");
    }


    /**
     * getter for sending module
     * @return Module
     */
    public Module getSourceModuleId()
    {
        return sourceModuleId;
    }
    
    /**
     * getter for receiving side of message
     * @return Module
     */
    public Module getDestinationModuleId()
    {
        return destinationModuleId;
    }

    /**
     * 
     * @return raw data
     */
	public String getMessageString() {
		return messageString;
	}


    public MessageType getType()
    {
        return type;
    }
}


