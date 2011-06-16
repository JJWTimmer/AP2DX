package AP2DX.specializedMessages;

import AP2DX.*;

/**
* Specialized message send to motor module to control it
* @author Maarten Inja
*/
public class MotorMessage extends SpecializedMessage 
{
	private ActionType action;
	private int meter = 0;
	private int degree = 0;
	
	public enum ActionType {
		FORWARD, BACKWARD, LEFT, RIGHT, TURN, STOP
	}	
	
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public MotorMessage(AP2DXMessage message)
    {
        super(message);
    }
    
    public MotorMessage(Module sourceId, Module destinationId, int meter)
    {
    	super(sourceId, destinationId);
    	this.meter = meter;
    }
    
    public MotorMessage(Module sourceId, Module destinationId, int degree)
    {
    	super(sourceId, destinationId);
    	this.degree = degree;
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
