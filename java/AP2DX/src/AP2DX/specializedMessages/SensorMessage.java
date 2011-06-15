package AP2DX.specializedMessages;

import AP2DX.*;

/**
* Specialized message for sensor data. 
* @author Maarten Inja
*/
public class SensorMessage extends AP2DXMessage
{
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public SensorMessage(AP2DXMessage message)
    {
        sourceModuleId = message.getSourceModuleId();
        destinationModuleId = message.getDestinationModuleId();
        values = message.getValues();
        messageString = message.getMessageString();
        values = message.getValues();
    }

    /** Retrieves a sensordata array from the values array. I don't know
    * if we can actually use this, but it's more proof of concept. */
    public double[] getSensorDataArray()
    {
        return null;
    }
}
