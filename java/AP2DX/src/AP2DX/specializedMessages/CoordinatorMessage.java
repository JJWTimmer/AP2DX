package AP2DX.specializedMessages;

import AP2DX.AP2DXMessage;

public class CoordinatorMessage extends SpecializedMessage {
	
	public enum Command {
		DRIVE, SENSOR
	}
	
	private Command command;
	
	public CoordinatorMessage(AP2DXMessage message, Command command) {
		super(message);
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
