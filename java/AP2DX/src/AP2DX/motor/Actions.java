/**
 * 
 */
package AP2DX.motor;

import java.util.ArrayList;

import AP2DX.specializedMessages.*;
import AP2DX.AP2DXMessage;
import AP2DX.InterfaceActions;
import AP2DX.Message;
import AP2DX.Module;

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

	/** 
	 * 
	 */
	@Override
	public ArrayList<AP2DXMessage> forward(int meter) {
		DriveCoordinatorMessage forward = 
			new DriveCoordinatorMessage(Module.ABSTRACTMOTOR, Module.COORDINATOR, CoordinatorMessage.Command.DRIVE, 1, 1);
		DriveCoordinatorMessage stop = 
			new DriveCoordinatorMessage(Module.ABSTRACTMOTOR, Module.COORDINATOR, CoordinatorMessage.Command.DRIVE, 0, 0);
		
		stop.setDelay(1000 * meter);
		
		ArrayList<AP2DXMessage> list = new ArrayList<AP2DXMessage>();
		list.add(forward);
		list.add(stop);
		return list;
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
