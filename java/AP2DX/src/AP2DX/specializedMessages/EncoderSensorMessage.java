package AP2DX.specializedMessages;

import AP2DX.*;



/** 
* Specialized message for sensor data
* "The sensor measures a part’s spin angle around the sensor’s axis. The returned
* value is a tick count which is the real angle divided by the sensor’s resolution. How
* we mount the sensor will decide what axis’s spin angle will be measured. The sign of
* the count is also decided by the direction we mount the sensor. For example,
* mounting the sensor on the front or back side of a wheel will give us a different count
* sign."
* 
*
* USERSIM DATA example: SEN {Type Encoder} {Name ECLeft Tick -277} {Name ECRight Tick -141} {Name ECTilt Tick 0} {Name ECPan Tick 0} 
* @author Maarten Inja
*/
public class EncoderSensorMessage extends SpecializedMessage 
{
    
    private double left;
    private double right;
    private double theta;

    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public EncoderSensorMessage(AP2DXMessage message)
    {
        super(message);
    }


    public void specializedParseMessage()
    {   

    } 

}
