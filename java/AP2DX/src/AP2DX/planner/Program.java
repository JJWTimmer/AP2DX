package AP2DX.planner;

import java.util.ArrayList;
import java.util.Random;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Message.MessageType;
import AP2DX.Module;
import AP2DX.specializedMessages.*;

public class Program extends AP2DXBase {
	private boolean botBlocked = false;

	/**
	 * Entrypoint of planner
	 */
	public static void main(String[] args) {
		new Program();
	}

	/**
	 * constructor
	 */
	public Program() {
		super(Module.PLANNER); // explicitly calls base constructor
		System.out.println(" Running Planner... ");
	}

	@Override
	public ArrayList<AP2DXMessage> componentLogic(Message message) {
		ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();
		System.out.println("Received message " + message.getMessageString());
		System.out.println(String.format("In Queue: %s", this.getReceiveQueue().size()));
		
		switch (message.getMsgType()) {
		case AP2DX_PLANNER_STOP:
			
			setBotBlocked(true);
			
			Random rand = new Random();
			
			// reset
			messageList.add(new ResetMessage(IAM, Module.REFLEX));
			
			//drive backward
			messageList.add(new ActionMotorMessage(IAM, Module.REFLEX,
					ActionMotorMessage.ActionType.BACKWARD, 2.0));
			
			//turn
			double delta = rand.nextDouble() * 5000 * (rand.nextBoolean() ? 1 : -1);
			AP2DXMessage msg1 = new ActionMotorMessage(IAM, Module.REFLEX, ActionMotorMessage.ActionType.TURN, delta);
			//AP2DXMessage msg1 = new ActionMotorMessage(IAM, Module.REFLEX, ActionMotorMessage.ActionType.TURN, -5000.0);
			msg1.setDelay(2000);//let it drive backward 2 seconds
			messageList.add(msg1);
			
			
			//stop
			AP2DXMessage msg4 = new ActionMotorMessage(IAM, Module.REFLEX,
					ActionMotorMessage.ActionType.STOP, 666);
			msg4.setDelay((long)(2000 + Math.abs(delta))); //add delta millisec to last delay: let bot turn for random seconds
			messageList.add(msg4);
			
			//clear
			AP2DXMessage msg3 = new ClearMessage(IAM, Module.REFLEX);
			msg3.setDelay((long)(2000 + Math.abs(delta))); //add delta millisec to last delay: let bot turn for random seconds
			messageList.add(msg3);
			
			//drive forward
			AP2DXMessage msg2 = new ActionMotorMessage(IAM, Module.REFLEX,
					ActionMotorMessage.ActionType.FORWARD, 100.0);
			msg2.setDelay((long)(2000 + Math.abs(delta))); //add delta millisec to last delay: let bot turn for 2 seconds
			messageList.add(msg2);
			
			setBotBlocked(false);
			
			break;
		default:
			if (!isBotBlocked()) {
				// for now, lets just drive forward, OKAY?!
				messageList.add(new ActionMotorMessage(IAM, Module.REFLEX,
						ActionMotorMessage.ActionType.FORWARD, 100.0));
				setBotBlocked(true);
				System.out.println("Sending message " + messageList.get(0));
			}
			AP2DXBase.logger
					.severe("Error in AP2DX.reflex.Program.componentLogic(Message message) Couldn't deal with message: "
							+ message.getMsgType());
		}

		return messageList;
	}

	/**
	 * @param isBotBlocked
	 *            the isBotBlocked to set
	 */
	public void setBotBlocked(boolean isBotBlocked) {
		this.botBlocked = isBotBlocked;
	}

	/**
	 * @return the isBotBlocked
	 */
	public boolean isBotBlocked() {
		return botBlocked;
	}
}
