package AP2DX.specializedMessages;

import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;

public class DriveCoordinatorMessage extends CoordinatorMessage {
		
	private int left;
	private int right;
	
	public DriveCoordinatorMessage(AP2DXMessage message) {
		super(message);
	}

	public DriveCoordinatorMessage(Module sourceId, Module destinationId, Command command, int left, int right) {
		super(Message.MessageType.AP2DX_COORDINATOR_DRIVE, sourceId, destinationId, command);
		this.left = left;
		this.right = right;
	}
	
	public int GetLeft() {
		return left;
	}

	public int GetRight() {
		return right;
	}
	
	public int SetLeft(int left) {
		this.left = left;
		return left;
	}
	
	public int SetRight(int right) {
		this.right = right;
		return right;
	}
}
