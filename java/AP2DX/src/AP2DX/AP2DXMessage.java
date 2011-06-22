 /**
 * 
 */
package AP2DX;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import AP2DX.specializedMessages.*;

/**
 * @author Jasper Timmer
 * @author Maarten Inja
 */
public class AP2DXMessage extends Message implements Delayed, Cloneable
{
	
	/** 
	 * Delay of the message, handy if you want to send a message in the future.
	 * Time is given in the future in the format used 
	 * by System.currentTimeMillis(). Should stay zero if no delay is required.
	 */
	private long delay = 0;

    public AP2DXMessage(Message.MessageType type, Module source, Module destination)
    {
        super(type, source, destination);
    }
	
	public AP2DXMessage(String in, Module source, Module destination) 
    {
		super(in, source, destination);
        parseMessage();
	}
	
	public AP2DXMessage(String in, Module source) {
		super(in, source);
        parseMessage();
	}

    public AP2DXMessage(String messageString)
    {
        super(messageString);
        parseMessage();
    }

    /** Creates a complete new instance of an AP2DX message. Only this 
    * objects messageString should be compiled correctly. */
    public Object clone()
    {
        AP2DXMessage message = new AP2DXMessage(messageString);
        message.parseMessage();
        message.compileMessage();
        return message;
    }

	/**
     * Parses the message string to fill the values map with stuff and also
     * sets the type and destinationID and source moduleID. 
     * 
	 * @see AP2DX.Message#parseMessage()
	 */
	@Override
	public void parseMessage() 
    {
		JSONParser parser = new JSONParser();
		try 
        {
			values = (Map)parser.parse(messageString);
            destinationModuleId = Module.valueOf(values.get("destinationModuleId").toString());
            sourceModuleId = Module.valueOf(values.get("sourceModuleId").toString());
            type = MessageType.getEnumByString(values.get("type").toString());
        }
        catch (ParseException pe) 
        {   
            System.out.println("Error in AP2DXMessage.parseMessage()");
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
	}

    /** 
    * Creates a (new) string in messageString from whatever is in the values map. 
    */
    public String compileMessage()
    {   
        //try
        //{
        //    // Errors are thrown even though the API says this excists.. SIGH
        //    // WHY do we use 'simple'?
        //    messageString = (new JSONObject(values)).toString();
        //}
        JSONObject jsonObject = new JSONObject();
        for (Entry entry : values.entrySet())
        {
            try
            {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("ERROR in AP2DXMessage.compileMessage(), NULL objects?");
                System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
            }
        }
        messageString = jsonObject.toString();
        return messageString;
    }


    public Map<String, Object> getValues()
    {
        return values;
    }

	@Override
	public int compareTo(Delayed o) throws ClassCastException {
			AP2DXMessage object = (AP2DXMessage) o;
		
		if (this.delay < object.delay) {
			return -1;
		} else if (this.delay > object.delay) {
			
		} else if (this.delay == object.delay) {
			return 0;
		} else {
			System.out.println("Error in AP2DXMessage.compareTo() ");
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		/** Checks if no delay was required */
		if (delay == 0 ) {
			return 0; 
		} else {
			switch (unit) {
				case MICROSECONDS:
					return unit.toMicros(delay);
				case MILLISECONDS:
					return unit.toMillis(delay);
				/* DAMNNN, you mad! This is not physics. */	
				case NANOSECONDS :
					return unit.toNanos(delay);
				/* That is more like it :-) */	
				case SECONDS :
					return unit.toSeconds(delay);
				default:
					System.out.println("Non critical error in AP2DXMessage.getDelay() ");
					return 0;
			}	
		}
	}
	
	/** 
	 * Returns the future delay time with the format 
	 * as used by System.currentTimeMillis().
	 */
	public long setDelay(long millisec) {
		delay = (System.currentTimeMillis() + millisec);
		return delay;
	}

    public String toString()
    {
        compileMessage();
        return messageString;
    }

}








