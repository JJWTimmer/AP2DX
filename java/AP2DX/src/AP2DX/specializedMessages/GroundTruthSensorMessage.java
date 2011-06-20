package AP2DX.specializedMessages;

import AP2DX.*;



/** USERSIM DATA:
*/

/**
* Specialized message for sensor data
* @author Maarten Inja
*/
public class GroundTruthSensorMessage extends SpecializedMessage 
{
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public GroundTruthSensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    /** Retrieves a sensordata array from the values array. I don't know
    * if we can actually use this, but it's more proof of concept. */
    public double[] getSensorDataArray()
    {
        return null;
    }
    public void specializedParseMessage()
    {   

    } 

}
