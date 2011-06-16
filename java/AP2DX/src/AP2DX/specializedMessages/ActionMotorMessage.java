package AP2DX.specializedMessages;

import AP2DX.*;

/**
* Specialized message send to motor module to control it
* @author Maarten Inja
*/
public class ActionMotorMessage extends SpecializedMessage 
{
	public enum ActionType {
		FORWARD, BACKWARD, LEFT, RIGHT, TURN, STOP
	}
	
	private ActionType action;
	
	/** If the action is TURN than the integer is read as degree else it is read as meter */
	private int value;	
	
    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public ActionMotorMessage(AP2DXMessage message)
    {
        super(message);
    }
    
    public ActionMotorMessage(Module sourceId, Module destinationId, int value)
    {
    	super(sourceId, destinationId);
    	this.value = value;
    }

    public ActionType getActionType() {
    	return action;
    }
    
    public int getValue() {
    	return value;
    }

    public ActionType setActionType(ActionType action) {
    	this.action = action;
    	return action;
    }
    
    public int setMeter(int value) {
    	this.value = value;
    	return value;
    }
}
