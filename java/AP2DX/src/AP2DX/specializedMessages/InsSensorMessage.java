package AP2DX.specializedMessages;

import AP2DX.*;



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

    public void specializedParseMessage()
    {   

    } 

    /** three doubles describing the location (x, y, z). */
    public double[] getLocation()
    {
        return location;
    }

    /** Three doubles describing the orientation (r, p, y). */
    public double[] getOrientation()
    {
        return orientation;
    }

}

