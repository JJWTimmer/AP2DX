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
public class AP2DXMessage extends Message implements Delayed {


    /**
     * dictionary with key-value pairs of incoming data
     */
    protected Map<String, Object> values;

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

	/**
     * Sets the destination and source module ID. 
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
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
}








