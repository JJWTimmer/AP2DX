package AP2DX.reflex;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import AP2DX.*;
import AP2DX.specializedMessages.*;
import AP2DX.usarsim.specialized.USonarSensorMessage;

public class Program extends AP2DXBase {
	private double[] currentDistances;
	private double[] previousDistances;
	private double[] approaching;
	private boolean isBotBlocked = false;
	private boolean clear = true;

	/**
	 * Entrypoint of reflex
	 */
	public static void main(String[] args) {
		new Program();
	}

	/**
	 * constructor
	 */
	public Program() {
		super(Module.REFLEX); // explicitly calls base constructor
		System.out.println(" Running Reflex... ");

	}

	/**
	 * Does the logic for the Reflex module. That is: <br/>
	 * - check if there is something in front of the vehicle and stop the motor.<br/>
	 * - forward messages from the Planner to the motor if there is nothing.
	 * 
	 * @param message
	 *            The AP2DX Message that is received and read from the queue.
	 */
	@Override
	public ArrayList<AP2DXMessage> componentLogic(Message message) {
		ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();
		System.out.print("Message received: " + message.getMessageString());

		switch (message.getMsgType()) {
		case RESET:
			System.out.println("RESET Detected");
			setBotBlocked(false);
			break;
		case CLEAR:
			System.out.println("CLEAR Detected");
			this.clear = true;
			break;
		case AP2DX_MOTOR_ACTION:
			System.out.println("AP2DX_MOTOR_ACTION Detected");
			if (!isBotBlocked()) {
				ActionMotorMessage msg = new ActionMotorMessage(
						(AP2DXMessage) message);
				msg.setDestinationModuleId(Module.MOTOR);

				System.out.printf("Sending message %s to MOTOR module\n",
						msg.getMessageString());

				messageList.add((AP2DXMessage) msg);
			}
			break;
		case AP2DX_SENSOR_SONAR:
			SonarSensorMessage msg2 = new SonarSensorMessage(
					(AP2DXMessage) message);

			setPreviousDistances(getCurrentDistances());
			setCurrentDistances(msg2.getRangeArray());

			double[] approaching = new double[getCurrentDistances().length];
			for (int i = 0; i < getCurrentDistances().length; i++) {
				if (getCurrentDistances()[i] < getPreviousDistances()[i]) {
					approaching[i] = 1;
				} else if (getCurrentDistances()[i] == getPreviousDistances()[i]) {
					approaching[i] = 0;
				} else {
					approaching[i] = -1;
				}
			}

			if (!isBotBlocked) {
				for (int i = 0; i < getCurrentDistances().length; i++) {
					if (getCurrentDistances()[i] < 0.75 && approaching[i] == 1) {
						setBotBlocked(true);
						this.clear = false;

						System.out.println("Reflex says: STOP!");

						messageList.add(new ActionMotorMessage(IAM,
								Module.MOTOR,
								ActionMotorMessage.ActionType.STOP, 666));
						
						messageList.add(new StopPlannerMessage(IAM,Module.PLANNER));

						System.out.println("Sending stop messages to PLANNER and MOTOR");
						
						break;
					}
				}
			}
			break;
		default:
			System.out
					.println("Error in AP2DX.reflex.Program.componentLogic(Message message) Couldn't deal with message: "
							+ message.getMsgType());
		}
		return messageList;
	}

	/**
	 * 
	 */
	@Override
	public void doOverride() {

	}

	/**
	 * @param currentDistances
	 *            the currentDistances to set
	 */
	public void setCurrentDistances(double[] currentDistances) {
		this.currentDistances = currentDistances;
	}

	/**
	 * @return the currentDistances
	 */
	public double[] getCurrentDistances() {
		return currentDistances;
	}

	/**
	 * @param previousDistances
	 *            the previousDistances to set
	 */
	public void setPreviousDistances(double[] previousDistances) {
		this.previousDistances = previousDistances;
	}

	/**
	 * @return the previousDistances
	 */
	public double[] getPreviousDistances() {
		return previousDistances;
	}

	/**
	 * @param approaching
	 *            the approaching to set
	 */
	public void setApproaching(double[] approaching) {
		this.approaching = approaching;
	}

	/**
	 * @return the approaching
	 */
	public double[] getApproaching() {
		return approaching;
	}

	/**
	 * @param isBotBlocked
	 *            the isBotBlocked to set
	 */
	public void setBotBlocked(boolean isBotBlocked) {
		this.isBotBlocked = isBotBlocked;
	}

	/**
	 * @return the isBotBlocked
	 */
	public boolean isBotBlocked() {
		return isBotBlocked;
	}
}
