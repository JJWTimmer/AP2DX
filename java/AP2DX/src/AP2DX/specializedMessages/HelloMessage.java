package AP2DX.specializedMessages;

import AP2DX.*;

//import org.json.simple.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
* A message that is always send first to identify an incomming connection. 
*
* @author Maarten Inja
*/
public class HelloMessage extends SpecializedMessage 
{
    private double[] rangeArray;
    private double time;

	public HelloMessage(Module sourceId, Module destinationId)
	{
		super(Message.MessageType.HELLO, sourceId, destinationId);
	}

    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public SonarSensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public void specializedParseMessage()
    {
        // we don't do anything
    }

    // setters and getters {{{

    // nothing

    // }}}
}
