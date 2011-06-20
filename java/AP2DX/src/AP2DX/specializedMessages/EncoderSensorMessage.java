package AP2DX.specializedMessages;

import AP2DX.*;



/** 
* Specialized message for sensor data
*
* USERSIM DATA example: SEN {Type Encoder} {Name ECLeft Tick -277} {Name ECRight Tick -141} {Name ECTilt Tick 0} {Name ECPan Tick 0} 
* @author Maarten Inja
*/
public class EncoderSensorMessage extends SpecializedMessage 
{
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public EncoderSensorMessage(AP2DXMessage message)
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
