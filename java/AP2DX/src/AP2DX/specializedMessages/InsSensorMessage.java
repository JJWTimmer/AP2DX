package AP2DX.specializedMessages;

import AP2DX.*;



/**
* Specialized message for sensor data
* @author Maarten Inja
*/
public class InsSensorMessage extends SpecializedMessage 
{
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public InsSensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public void specializedParseMessage()
    {   

    } 
}

