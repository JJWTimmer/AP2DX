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
        AP2DX_SENSOR, AP2DX_MOTOR,
        USAR_STATE, USAR_MISSIONSTATE
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

    /**
     * dictionary with key-value pairs of incoming data
     */
    protected Map<String, Object> values = null;
    
    /**
     * constructor without destination, for received messages
     * @param in raw data
     * @param origin sender
     */
    public Message(String in, Module origin)
    {
        this(in, origin, Module.UNDEFINED);
        //parseMessage();
    }
    
    /**
     * constructor with sender and receiver defined
     * @param in raw data
     * @param origin sending module
     * @param destination receiving module
     */
    public Message(String in, Module origin, Module destination)
    {
        this.messageString = in; 
        this.sourceModuleId = origin;
        this.destinationModuleId = destination;

        //parseMessage();
    }

    /**
     * override this to parse specific messagetypes
     * @throws Exception 
     */
    protected void parseMessage() throws Exception {
    	throw new Exception("Can only call this method from specialized messages.");
    }

    /**
    * When the map of this message is filled we can compile a string that can be 
    * send (and parsed once received on the other side).
    */
    protected abstract void compileMessage();


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

    public Map<String, Object> getValues()
    {
        return values;
    }

    public MessageType getType()
    {
        return type;
    }
}


