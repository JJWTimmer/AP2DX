package AP2DX.specializedMessages;

import AP2DX.*;

/**
* Specialized message for sensor data, actually only a constructor to construct
* a specialized message from an AP2DXMessage. It makes sure I don't have to
* type this over and over again for each SpecializedMessage. It's not even
* abstract, but I do not want anyone to create an instance of this bad boy. 
* @author Maarten Inja
*/
public abstract class SpecializedMessage extends AP2DXMessage
{

    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public SpecializedMessage(AP2DXMessage message)
    {
        super(message.getMessageString(), message.getSourceModuleId(), message.getDestinationModuleId());
        sourceModuleId = message.getSourceModuleId();
        destinationModuleId = message.getDestinationModuleId();
        values = message.getValues();
        messageString = message.getMessageString();
        values = message.getValues();
    }
}
