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
    private boolean bidirection;
    
	public HelloMessage(Module sourceId, Module destinationId, boolean bidirectional)
	{
		super(Message.MessageType.HELLO, sourceId, destinationId);
		setBidirection(bidirectional);
	}

    /** Creates a specialized message from a standard AP2DXMessage. */
    public HelloMessage(AP2DXMessage message)
    {
        super(message);
    }

    public void specializedParseMessage()
    {
    	try
        {
            JSONObject jsonObject = new JSONObject(messageString);
            setBidirection(jsonObject.getBoolean("Bidirection"));
//Boolean.parseBoolean(values.get("Bidirection").toString()));
        }
        catch (Exception e)
        {
            System.out.println("Error in AP2DX.specializedMessages.HelloMessage.specializedParseMessage()... things went south!");
            System.out.println("e.getMessage(): " + e.getMessage());
            e.printStackTrace();
        }
    }

	public boolean getBidirection() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param bidirection the bidirection to set
	 */
	public void setBidirection(boolean bidirection) {
		this.bidirection = bidirection;
		values.put("Bidirection", bidirection);
	}

	/**
	 * @return the bidirection
	 */
	public boolean isBidirection() {
		return bidirection;
	}

    // setters and getters {{{

    // nothing

    // }}}
}
