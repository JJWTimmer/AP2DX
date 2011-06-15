package AP2DX;

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
	
	public boolean forward(int meter);
	
	public boolean backward(int meter);
	
	public boolean left(int meter);
	
	public boolean right(int meter);
	
	public boolean turn(int degree);
	
	public boolean stop();
}
