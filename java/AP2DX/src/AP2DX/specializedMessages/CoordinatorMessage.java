package AP2DX.specializedMessages;

import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;

public class CoordinatorMessage extends SpecializedMessage {
	
	public enum Command {
		DRIVE, SENSOR
	}
	
	private Command command;
	
	public CoordinatorMessage(AP2DXMessage message) {
		super(message);
	}
	
	public CoordinatorMessage(Message.MessageType type, Module sourceId, Module destinationId, Command command) {
		super(type, sourceId, destinationId);
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public Command setCommand(Command command) {
		this.command = command;
		return command;
	}

}
