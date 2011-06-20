package AP2DX.specializedMessages;

import AP2DX.*;

/**
* Specialized message send from the motor to the coordinator.  
* 
* Damnit, I now see the there was already a class for this :( .. But I had
* to complete it anyway, and now it has the Java conventions.
*
* @author Maarten Inja
*/
public class MotorMessage extends SpecializedMessage 
{

    /** Value for the left wheel. Still unsure if doubles can be send. */
	private double leftValue;	
    /** Value for the right wheel. */
	private double rightValue;	

    /** Normalized means we can assign values between the -100 and the 100
    * for the left and right values for the wheels. I've hardcoded this to 
    * be always true. */
    private boolean normalized;
	
    /** Creates a specialized message from a standard AP2DXMessage. */
    public MotorMessage(AP2DXMessage message)
    {
        super(message);
    }
    
    public MotorMessage(Module sourceId, Module destinationId, double leftValue, double rightValue)
    {
    	super(Message.MessageType.AP2DX_MOTOR_ACTION, sourceId, destinationId);
        setLeftValue(leftValue);
        setRightValue(rightValue);
        setNormalized(true);
    }

    public void specializedParseMessage()
    {
        try
        {
            leftValue = Double.parseDouble(values.get("leftValue").toString());
            rightValue = Double.parseDouble(values.get("rightValue").toString());
        }
        catch (Exception e)
        {
            System.out.println("Error in AP2DX.specializedMessages.MotorMessage.specializedParseMessage()... shit hitteted the van!");
            e.printStackTrace();
        }
    } 

    // setters and getters {{{

    public double getLeftValue()
    {
        return leftValue;
    }

    public void setLeftValue(double value)
    {
        values.put("leftValue", value);
        leftValue = value;
    }

    public double getRightValue()
    {
        return rightValue;
    }

    public void setRightValue(double value)
    {
        values.put("rightValue", value);
        rightValue = value;
    }

    public boolean getNormalized()
    {
        return normalized;
    }

    public void setNormalized(boolean value)
    {
        values.put("normalized", value);
        normalized = value;
    }
    
    // }}}

}
