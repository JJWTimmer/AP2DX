package AP2DX;

import java.util.ArrayList;

/**
 * 
 * Interface for the actions that can be applied to the robot.
 * This interface is used by the motor, reflex and mapper.
 * Each module will add a higher level of abstraction.
 * 
 * @author Wadie Assal
 * @author Jeroen Rooijmans
 *
 */

public interface InterfaceActions {
	
	public ArrayList<AP2DXMessage> forward(int meter);
	
	public ArrayList<AP2DXMessage> backward(int meter);
	
	public ArrayList<AP2DXMessage> left(int meter);
	
	public ArrayList<AP2DXMessage> right(int meter);
	
	public ArrayList<AP2DXMessage> turn(int degree);
	
	public ArrayList<AP2DXMessage> stop();
}
