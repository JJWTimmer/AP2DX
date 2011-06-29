package AP2DX.specializedMessages;

import java.util.HashMap;

import AP2DX.*;

//import org.json.simple.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;



/**
* Specialized message for sensor data, Internal Navigation System
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

    /** Creates a specialized message from a standard AP2DXMessage.*/
    public InsSensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public InsSensorMessage(Module sourceId, Module destinationId)
    {
        super(Message.MessageType.AP2DX_SENSOR_INS, sourceId, destinationId);
        if (values == null)
            values = new HashMap();
        setLocation(location);
        setOrientation(orientation);
    }

    public void specializedParseMessage()
    {   

        //System.out.println("InsSensorMessage.specializedParseMessage: messageSTring" + messageString);

        //System.out.printf("In specializedParseMessage of INS with string %s\n", messageString);
        try
        {
            JSONObject jsonObject = new JSONObject(messageString);

            JSONArray jsonArray = jsonObject.getJSONArray("location");
            location = new double[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i ++)
                location[i] = jsonArray.getDouble(i);
            setLocation(location);

            jsonArray = jsonObject.getJSONArray("orientation");
            orientation = new double[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i ++)
                orientation[i] = jsonArray.getDouble(i);
            setOrientation(orientation);
        }
        catch (Exception e)
        {
            System.out.println("Error in AP2DX.specializedMessages.InsSensorMessage.specializedParseMessage()... things went south!");
            System.out.println("e.getMessage(): " + e.getMessage());
            e.printStackTrace();
        }
    } 
    
    /** 
     * Creates a (new) string in messageString from whatever is in the values map. 
     */
    @Override
     public String compileMessage()
     {   
    	if (messageString != null)
    		specializedParseMessage();
    	
         try
         {
             if (values == null)
                 values = new HashMap();
             values.put("destinationModuleId", destinationModuleId.toString());
             values.put("sourceModuleId", sourceModuleId.toString());
             values.put("type", type.typeString.toString());
             messageString = (new JSONObject(values)).toString();
         }
         catch (Exception e)
         {
             System.out.println("Error in AP2DX.SpecializedMessages.OdometrySensormessage.compileMessage: " + e.getMessage());
             e.printStackTrace();
         }
         return messageString;
     }

    // setters and getters {{{

    /** three doubles describing the location (x, y, z). */
    public double[] getLocation()
    {
        return location;
    }

    public void setLocation(double[] value)
    {
        location = value;
        values.put("location", value);
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

