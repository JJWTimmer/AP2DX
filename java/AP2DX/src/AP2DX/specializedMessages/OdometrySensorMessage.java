package AP2DX.specializedMessages;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;




/**
* Specialized message for sensor data
*
* USAR SIM page 81 tells us it uses left and right wheel encoders to estimate the robot's pose. 
* It describes the positive x,y-position (and heads theta) relative to the start location. 
*
* Example USAR data: SEN {Type Odometry} {Name Odometry} {Pose 0.4265,0.1643,0.9105} 
* @author Maarten Inja
*/

public class OdometrySensorMessage extends SpecializedMessage 
{

    private double x, y, theta;

    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public OdometrySensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public OdometrySensorMessage(Module sourceId, Module destinationId)
    {
        super(Message.MessageType.AP2DX_SENSOR_ODOMETRY, sourceId, destinationId);
    }
 
    public void specializedParseMessage()
    {   
        try
        {
        	JSONObject jsonObject = new JSONObject(messageString);

            setX(jsonObject.getDouble("x"));
            setY(jsonObject.getDouble("y"));
            setTheta(jsonObject.getDouble("theta"));
        }
        catch (Exception e)
        {
            System.out.println("Error in AP2DX.specializedMessages.OdometrySensorMessage.specializedParseMessage()... things went south!");
            e.printStackTrace();
        }
    } 
    
    /** 
     * Creates a (new) string in messageString from whatever is in the values map. 
     */
    @Override
     public String compileMessage()
     {   
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

    public void setX(double value)
    {
        x = value;  
        NumberFormat formatter = new DecimalFormat("###.#####");  
           
        values.put("x", x);
    }

    public double getX()
    {
        return x; 
    }

    public void setY(double value)
    {
        y = value;
        NumberFormat formatter = new DecimalFormat("###.#####");
        values.put("y", y);
    }

    public double getY()
    {
        return y;
    }

    public void setTheta(double value)
    {
        theta = value;
        NumberFormat formatter = new DecimalFormat("###.#####");
        values.put("theta", theta);   
    }

    public double getTheta()
    {
        return theta;
    }

    // }}}
}
