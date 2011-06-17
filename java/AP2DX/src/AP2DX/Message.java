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
     * We can both define enums here and the string we use as value in a JSON string message.
     * @author Maarten Inja
     */
    public enum MessageType 
    {
        AP2DX_SENSOR_ENCODER("sensorEncoder"), APD2X_SENSOR_GPS("sensorGps"), AP2DX_SENSOR_GROUNDTRUTH("sensorGroundTruth"), AP2DX_SENSOR_INS("sensorIns"), 
        AP2DX_SENSOR_ODOMETRY("sensorOdometry"), AP2DX_SENSOR_RANGESCANNER("sensorRangeScanner"), AP2DX_SENSOR_SONAR("sensorSonar"), 
        USAR_STATE("STA"), USAR_MISSIONSTATE("MISTA"), AP2DX_MOTOR_ACTION("motorAction"), AP2DX_COORDINATOR_DRIVE("coordinatorDrive"), 
        AP2DX_COORDINATOR_SENSOR("coordinatorSensor"), UNKNOWN("unknown");

        /** The string that defines the type when a message is being send as JSON/USAR. */
        public final String typeString;

        private MessageType(String typeString)
        {
            this.typeString = typeString;
        }

        /** Finds an enum by a String.
        * TODO: change this to a map for performance increase */
        public static MessageType getEnumByString(String typeString)
        {
            for(MessageType possibleType : MessageType.values())
                if (possibleType.typeString.equals(typeString))
                    return possibleType;
            return MessageType.UNKNOWN;
        }

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


    public Message(MessageType type, Module source, Module destination)
    {
        this(null, source, destination, type);
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
     * @param sets the message type
     */
    public Message(String in, Module source, Module destination, MessageType type)
    {
        this.messageString = in; 
        this.sourceModuleId = source;
        this.destinationModuleId = destination;
        this.type = type;
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
    
    /**
     * setter for sending module
     * @return Module
     */
    public Module setSourceModuleId(Module sourceModuleId)
    {
    	this.sourceModuleId = sourceModuleId;
        return sourceModuleId;
    }
    
    /**
     * setter for receiving side of message
     * @return Module
     */
    public Module setDestinationModuleId(Module destinationModuleId)
    {
    	this.destinationModuleId = destinationModuleId;
        return destinationModuleId;
    }

    /**
     * Sets the messageString if needed. Probably not smart to use it. Hmmm let me think.
     * Yeeeh very stupid to use this. But already wrote it so here it is :-).
     * 
     */
	public String setMessageString(String messageString) {
		this.messageString = messageString;
		return messageString;
	}

	/**
	 * @param type sets the type
	 */
    public MessageType setType(MessageType type)
    {	
    	this.type = type;
        return type;
    }
}


