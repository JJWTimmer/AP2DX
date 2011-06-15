package AP2DX.specializedMessages;

import AP2DX.*;

/**
* Specialized message for sensor data. 
* @author Maarten Inja
*/
public class MotorMessage extends SpecializedMessage 
{
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public MotorMessage(AP2DXMessage message)
    {
        super(message);
    }

    /** Retrieves a value from values. Not sure if we will end up 
    * using this, but proof concept. */
    public double getRightWheelSpeed()
    {
        return 0;
    }
}
