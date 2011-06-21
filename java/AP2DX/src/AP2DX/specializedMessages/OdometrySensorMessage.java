package AP2DX.specializedMessages;

import AP2DX.*;




/**
* Specialized message for sensor data
*
* USAR SIM page 81 tells us it uses left and right wheel encoders to estimate the robot's pose. 
* It describes the positive x,y-position (and heads theta) relative to the start location. 
*
* Example USAR data: SEN {Type Odometry} {Name Odometry} {Pose 0.4265,0.1643,0.9105}
*
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

    public void specializedParseMessage()
    {   

    } 

   
    public double getX()
    {
        return x; 
    }

    public double getY()
    {
        return y;
    }

    public double getTheta()
    {
        return theta;
    }

}
