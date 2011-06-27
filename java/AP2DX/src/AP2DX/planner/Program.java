package AP2DX.planner;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Message.MessageType;
import AP2DX.Module;
import AP2DX.specializedMessages.*;

public class Program extends AP2DXBase {
	private boolean botBlocked = false;
	private InsLocationData locData;
	private double travelDistance;
	private double distanceGoal;
	private boolean firstMessage = false;
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
					ActionMotorMessage.ActionType.BACKWARD, 15.0));
			
			//turn
			double delta = rand.nextDouble() * 5000 * (rand.nextBoolean() ? 1 : -1);
			AP2DXMessage msg1 = new ActionMotorMessage(IAM, Module.REFLEX, ActionMotorMessage.ActionType.TURN, delta + 2000);
			//AP2DXMessage msg1 = new ActionMotorMessage(IAM, Module.REFLEX, ActionMotorMessage.ActionType.TURN, -5000.0);
			msg1.setDelay(this.getLastDelay() + 2000);//let it drive backward 2 seconds
			this.setLastDelay(msg1.getDelay(TimeUnit.MILLISECONDS));
			messageList.add(msg1);
			
			
			//stop
			AP2DXMessage msg4 = new ActionMotorMessage(IAM, Module.REFLEX,
					ActionMotorMessage.ActionType.STOP, 666);
			msg4.setDelay(this.getLastDelay() + (long)(2000 + Math.abs(delta))); //add delta millisec to last delay: let bot turn for random seconds
			this.setLastDelay(msg4.getDelay(TimeUnit.MILLISECONDS));
			messageList.add(msg4);
			
			//clear
			AP2DXMessage msg3 = new ClearMessage(IAM, Module.REFLEX);
			msg3.setDelay(this.getLastDelay() + (long)(2000 + Math.abs(delta))); //add delta millisec to last delay: let bot turn for random seconds
			this.setLastDelay(msg3.getDelay(TimeUnit.MILLISECONDS));
			messageList.add(msg3);
			
			setTravelDistance(0);
			setDistanceGoal(0);
			
			//drive forward
			AP2DXMessage msg2 = new ActionMotorMessage(IAM, Module.REFLEX,
					ActionMotorMessage.ActionType.FORWARD, 20.0);
			msg2.setDelay(this.getLastDelay() + (long)(2000 + Math.abs(delta))); //add delta millisec to last delay: let bot turn for 2 seconds
			this.setLastDelay(msg2.getDelay(TimeUnit.MILLISECONDS));
			messageList.add(msg2);
			
			setBotBlocked(false);
			
			break;
		case AP2DX_SENSOR_INS:
			InsSensorMessage msg = (InsSensorMessage) message;
			double[] loc = msg.getLocation();
			double[] ori = msg.getOrientation();
			
			if (locData == null) {
				
				locData = new InsLocationData(loc, ori);
			} else {
				locData.setLocationData(loc, ori);
				
				setTravelDistance(getTravelDistance() + locData.travelDistance());
				
				if (getDistanceGoal() > 0) {
					if (getDistanceGoal() >= getTravelDistance()) {
						AP2DXMessage msg6 = new ActionMotorMessage(IAM, Module.REFLEX,
								ActionMotorMessage.ActionType.STOP, 666);
						msg6.setDelay(this.getLastDelay());
						messageList.add(msg6);
					}
				}
			}
		default:
			if (!firstMessage) {
				// for now, lets just drive forward, OKAY?!
				AP2DXMessage msg5 = new ActionMotorMessage(IAM, Module.REFLEX,
						ActionMotorMessage.ActionType.FORWARD, 20.0);
				msg5.setDelay(this.getLastDelay());
				messageList.add(msg5);
				
				setDistanceGoal(1.0);
				
				setBotBlocked(true);
				System.out.println("Sending message " + messageList.get(0));
				
				firstMessage = true;
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

	/**
	 * @param travelDistance the travelDistance to set
	 */
	public void setTravelDistance(double travelDistance) {
		this.travelDistance = travelDistance;
	}

	/**
	 * @return the travelDistance
	 */
	public double getTravelDistance() {
		return travelDistance;
	}

	/**
	 * @param distanceGoal the distanceGoal to set
	 */
	public void setDistanceGoal(double distanceGoal) {
		this.distanceGoal = distanceGoal;
	}

	/**
	 * @return the distanceGoal
	 */
	public double getDistanceGoal() {
		return distanceGoal;
	}
}
