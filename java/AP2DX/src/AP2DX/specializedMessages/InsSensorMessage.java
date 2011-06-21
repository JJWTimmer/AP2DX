package AP2DX.specializedMessages;

import AP2DX.*;

//import org.json.simple.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;



/**
* Specialized message for sensor data, Inertial Navigation System
*
* Location and angle using angular velocities 
*
* Example of USAR message data: SEN {Type INS} {Name INS} {Location 4.50,1.90,1.80} {Orientation 0.00,0.00,0.00}
*
* @author Maarten Inja
*/
public class InsSensorMessage extends SpecializedMessage 
{
    public double[] location;
    public double[] orientation;

    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public InsSensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public InsSensorMessage(Module sourceId, Module destinationId)
    {
        super(Message.MessageType.AP2DX_SENSOR_INS, sourceId, destinationId);
    }

    public void specializedParseMessage()
    {   
        try
        {
            JSONObject jsonObject = new JSONObject(messageString);

            JSONArray jsonArray = jsonObject.getJSONArray("location");
            location = new double[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i ++)
                location[i] = jsonArray.getDouble(i);

            jsonArray = jsonObject.getJSONArray("orientation");
            orientation = new double[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i ++)
                orientation[i] = jsonArray.getDouble(i);
        }
        catch (Exception e)
        {
            System.out.println("Error in AP2DX.specializedMessages.InsSensorMessage.specializedParseMessage()... things went south!");
            e.printStackTrace();
        }
    } 

    // setters and getters {{{

    /** three doubles describing the location (x, y, z). */
    public double[] getLocation()
    {
        return location;
    }

    public void setLocation(double[] value)
    {
        values.put("location", value);
        location = value;
    }

    /** Three doubles describing the orientation (r, p, y). */
    public double[] getOrientation()
    {
        return orientation;
    }

    public void setOrientation(double[] value)
    {
        orientation = value;
        values.put("orientation", value);
    }

    // }}}
}

