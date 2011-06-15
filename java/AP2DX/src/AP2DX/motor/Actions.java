/**
 * 
 */
package AP2DX.motor;

import java.util.ArrayList;

import AP2DX.InterfaceActions;
import AP2DX.Message;

/**
 * @author Wadie
 * @author Jeroen Rooijmans
 */
public class Actions implements InterfaceActions {

	/**
	 * 
	 */
	public Actions() {
		
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#forward(int)
	 */
	@Override
	public ArrayList<Message> forward(int meter) {
		
		
		return new ArrayList<Message>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#backward(int)
	 */
	@Override
	public ArrayList<Message> backward(int meter) {
		
		
		return new ArrayList<Message>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#left(int)
	 */
	@Override
	public ArrayList<Message> left(int meter) {
		
		
		return new ArrayList<Message>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#right(int)
	 */
	@Override
	public ArrayList<Message> right(int meter) {
		
		
		return new ArrayList<Message>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#turn(int)
	 */
	@Override
	public ArrayList<Message> turn(int degree) {
		
		
		return new ArrayList<Message>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#stop()
	 */
	@Override
	public ArrayList<Message> stop() {
		
		
		return new ArrayList<Message>();
	}

}
