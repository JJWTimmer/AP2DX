package AP2DX.specializedMessages;

import AP2DX.*;



/** USERSIM DATA:
SEN {Time 395.7716} {Type Sonar} {Name F1 Range 4.5798} {Name F2 Range 2.1461} {Name F3 Range 1.7450} {Name F4 Range 1.5893} {Name F5 Range 0.6239} {Name F6 Range 0.7805} {Name F7 Range 1.2004} {Name F8 Range 2.0657}
*/

/**
* Specialized message for sensor data
* An example of a USAR sonar message:
* SEN {Time 395.7716} {Type Sonar} {Name F1 Range 4.5798} {Name F2 Range 2.1461} {Name F3 Range 1.7450} {Name F4 Range 1.5893} {Name F5 Range 0.6239} {Name F6 Range 0.7805} {Name F7 Range 1.2004} {Name F8 Range 2.0657}
* @author Maarten Inja
*/
public class SonarSensorMessage extends SpecializedMessage 
{
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public SonarSensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public boolean getTime()
    {
        return Double.parseDouble(values.get("time").toString);
    }

    /** The array of Sonar data.*/
    public double[] getRangeArray()
    {
        return null;
    }
}
