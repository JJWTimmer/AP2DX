package AP2DX.specializedMessages;

import AP2DX.*;




/**
* Specialized message for sensor data
*
* USAR SIM page 81 tells us it uses left and right wheel encoders to estimate the robot's pose. 
* It describes the positive x,y-position (and heads theta) relative to the start location. 
*
* Example USAR data: SEN {Type Odometry} {Name Odometry} {Pose 0.4265,0.1643,0.9105}
* @deprecated We do not use odometry sensor message, see the INS sensor messages. Also, bugs in code exists. 
* @author Maarten Inja
*/
@Deprecated 
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
            x = Double.parseDouble(values.get("x").toString());
            y = Double.parseDouble(values.get("y").toString());
            theta = Double.parseDouble(values.get("theta").toString());
        }
        catch (Exception e)
        {
            System.out.println("Error in AP2DX.specializedMessages.OdometrySensorMessage.specializedParseMessage()... things went south!");
            e.printStackTrace();
        }
    } 

    // setters and getters {{{

    public void setX(double value)
    {
        x = value;  
        values.put("x", x);
    }

    public double getX()
    {
        return x; 
    }

    public void setY(double value)
    {
        y = value;
        values.put("y", y);
    }

    public double getY()
    {
        return y;
    }

    public void setTheta(double value)
    {
        theta = value;
        values.put("theta", theta);   
    }

    public double getTheta()
    {
        return theta;
    }

    // }}}
}
