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
	
	public ArrayList<Message> forward(int meter);
	
	public ArrayList<Message> backward(int meter);
	
	public ArrayList<Message> left(int meter);
	
	public ArrayList<Message> right(int meter);
	
	public ArrayList<Message> turn(int degree);
	
	public ArrayList<Message> stop();
}
