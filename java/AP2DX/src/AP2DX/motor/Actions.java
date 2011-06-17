/**
 * 
 */
package AP2DX.motor;

import java.util.ArrayList;

import AP2DX.AP2DXMessage;
import AP2DX.InterfaceActions;
import AP2DX.Message;

/**
 * @author Wadie Assal
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
	public ArrayList<AP2DXMessage> forward(int meter) {
		
		
		return new ArrayList<AP2DXMessage>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#backward(int)
	 */
	@Override
	public ArrayList<AP2DXMessage> backward(int meter) {
		
		
		return new ArrayList<AP2DXMessage>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#left(int)
	 */
	@Override
	public ArrayList<AP2DXMessage> left(int meter) {
		
		
		return new ArrayList<AP2DXMessage>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#right(int)
	 */
	@Override
	public ArrayList<AP2DXMessage> right(int meter) {
		
		
		return new ArrayList<AP2DXMessage>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#turn(int)
	 */
	@Override
	public ArrayList<AP2DXMessage> turn(int degree) {
		
		
		return new ArrayList<AP2DXMessage>();
	}

	/* (non-Javadoc)
	 * @see AP2DX.InterfaceActions#stop()
	 */
	@Override
	public ArrayList<AP2DXMessage> stop() {
		
		
		return new ArrayList<AP2DXMessage>();
	}

}
