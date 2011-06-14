package AP2DX;

import java.util.Dictionary;

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
	 * the module that has sent this message
	 */
    private Module sourceModuleId;
    
    /**
     * the module that should receive this message
     */
    private Module destinationModuleId;
    
    /**
     * raw message data
     */
    private String messageString;

    /**
     * dictionary with key-value pairs of incoming data
     */
    private Dictionary<String, Object> values = null;
    
    /**
     * constructor without destination, for received messages
     * @param in raw data
     * @param origin sender
     */
    public Message(String in, Module origin)
    {
        this(in, origin, Module.UNDEFINED);
        parseMessage();
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
        this.parseMessage();
        parseMessage();
    }

    /**
     * override this to parse specific messagetypes
     */
    protected abstract void parseMessage();

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
	protected String getMessageString() {
		return messageString;
	}

}


