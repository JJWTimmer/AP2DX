package AP2DX.specializedMessages;

import AP2DX.*;

/**
* Specialized message send to motor module to control it
* @author Maarten Inja
*/
public class MotorMessage extends SpecializedMessage 
{
	private ActionType action;
	private int meter;
	private int degree;
	
	public enum ActionType {
		FORWARD, BACKWARD, LEFT, RIGHT, TURN, STOP
	}	
	
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public MotorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public ActionType getActionType() {
    	return action;
    }
    
    public int getMeter() {
    	return meter;
    }
    
    public int getDegree() {
    	return degree;
    }
    
    public ActionType setActionType(ActionType action) {
    	this.action = action;
    	return action;
    }
    
    public int setMeter(int meter) {
    	this.meter = meter;
    	return meter;
    }
    
    public int setDegree(int degree) {
    	this.degree = degree;
    	return degree;
    }
}
