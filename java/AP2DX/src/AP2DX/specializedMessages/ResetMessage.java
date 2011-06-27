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
public class ResetMessage extends SpecializedMessage 
{
    
	public ResetMessage(Module sourceId, Module destinationId)
	{
		super(Message.MessageType.RESET, sourceId, destinationId);
	}

    /** Creates a specialized message from a standard AP2DXMessage. */
    public ResetMessage(AP2DXMessage message)
    {
        super(message);
    }

    public void specializedParseMessage()
    {
    		//nothing
    }

    // setters and getters {{{

    // nothing

    // }}}
}
