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
import AP2DX.specializedMessages.ActionMotorMessage.ActionType;

public class Program extends AP2DXBase {
	private boolean botBlocked = false;
	private InsLocationData locData;
	private double travelDistance;
	private double distanceGoal;
	
	/** Tho start the first drive after spawning */
	private boolean firstMessage = false;
	
	private double startAngle;
	
	private double currentAngle;
	
	private double destinationAngle;
	
	private boolean startedTurning = false;

	/** counter for which turn in determining new direction */
	private int turnCount;

	/** permission to save sonardata for direction determining */
	private boolean sonarPermission = false;

	/** will contain sonar scan data for determining direction */
	private double[] sonarData;
	
	private boolean decidedDirection = false;

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
		System.out.println(String.format("In Queue: %s", this.getReceiveQueue()
				.size()));

		switch (message.getMsgType()) {
		case AP2DX_PLANNER_STOP:
			
			startAngle = currentAngle;
			
			sonarPermission =  true;

			messageList.add(new ResetMessage(IAM, Module.REFLEX));

			turnCount = 0;
			break;
		case AP2DX_SENSOR_INS:
			
			
			InsSensorMessage msg = (InsSensorMessage) message;
			double[] loc = msg.getLocation();
			double[] ori = msg.getOrientation();

			if (locData == null) {

				locData = new InsLocationData(loc, ori);
			} else {
				locData.setLocationData(loc, ori);

				setTravelDistance(getTravelDistance()
						+ locData.travelDistance());

				if (getDistanceGoal() > 0) {
					if (getDistanceGoal() >= getTravelDistance()) {
						AP2DXMessage msg6 = new ActionMotorMessage(IAM,
								Module.REFLEX,
								ActionMotorMessage.ActionType.STOP, 666);
						msg6.setDelay(this.getLastDelay());
						messageList.add(msg6);
					}
				}
			}
		case AP2DX_SENSOR_SONAR:
			SonarSensorMessage msgs = (SonarSensorMessage) message;

			if (sonarPermission) {
				switch (turnCount) {
				case 0:
					sonarData = new double[16];
					double[] current = msgs.getRangeArray();
					for (int i = 0; i < current.length; i++) {
						sonarData[i] = current[i];
					}
					
					AP2DXMessage turnMsg1 = new ActionMotorMessage(IAM, Module.REFLEX, ActionType.TURN, 1);
					turnMsg1.compileMessage();
					messageList.add(turnMsg1);
					
					sonarPermission = false;
					startedTurning = true;
					
					turnCount++;
					
					break;
				case 1:
					double[] current2 = msgs.getRangeArray();
					for (int i = 4; i < current2.length; i++) {
						sonarData[i+3] = current2[i];
					}
					
					AP2DXMessage turnMsg2 = new ActionMotorMessage(IAM, Module.REFLEX, ActionType.TURN, 1);
					turnMsg2.compileMessage();
					messageList.add(turnMsg2);
					
					sonarPermission = false;
					
					startAngle = currentAngle;
					startedTurning = true;
					
					turnCount++;
					
					break;
				case 2:
					double[] current3 = msgs.getRangeArray();
					for (int i = 4; i < current3.length; i++) {
						sonarData[i+7] = current3[i];
					}
					
					sonarPermission = false;
					
					int far_i = 0;
					double far_value = 0;
					
					for (int i = 0; i < sonarData.length; i++) {
						if (sonarData[i] > far_value) {
							far_value = sonarData[i];
							far_i = i;
						}
					}
					
					startAngle = currentAngle;
					
					destinationAngle = (2 * Math.PI / 16) * far_i;
					decidedDirection = true;
					
					AP2DXMessage msgt = new ActionMotorMessage(IAM, Module.REFLEX,
							ActionMotorMessage.ActionType.TURN, 5.0);
					messageList.add(msgt);
					
					
					
					break;
				default:
					// nothing
				}
			}
			
			break;
		case AP2DX_SENSOR_ODOMETRY:
			System.out.println("parsing odometry message in planner");
			
			OdometrySensorMessage msgo = (OdometrySensorMessage) message;

			currentAngle = msgo.getTheta();
			
			if (startedTurning) {
				if (Math.abs(startAngle - currentAngle) >= (Math.PI * 0.5)) {
					sonarPermission = true;
					AP2DXMessage stopMsg1 = new ActionMotorMessage(IAM, Module.REFLEX, ActionType.STOP, 666);
					stopMsg1.compileMessage();
					messageList.add(stopMsg1);
					startedTurning = false;
				}
			} else if (decidedDirection) {
				if (Math.abs(startAngle - currentAngle) >= destinationAngle) {
					sonarPermission = true;
					AP2DXMessage stopMsg1 = new ActionMotorMessage(IAM, Module.REFLEX, ActionType.STOP, 666);
					stopMsg1.compileMessage();
					messageList.add(stopMsg1);
					startedTurning = false;
					decidedDirection = false;
					
					AP2DXMessage msg5 = new ClearMessage(IAM, Module.REFLEX);
					messageList.add(msg5);
					
					AP2DXMessage msg6 = new ActionMotorMessage(IAM, Module.REFLEX,
							ActionMotorMessage.ActionType.FORWARD, 10.0);
					messageList.add(msg6);
					
				}
			}
			
			if (!firstMessage) {
				// for now, lets just drive forward, OKAY?!
				AP2DXMessage msg5 = new ActionMotorMessage(IAM, Module.REFLEX,
						ActionMotorMessage.ActionType.FORWARD, 10.0);
				messageList.add(msg5);
				
				setDistanceGoal(1.0);
				
				System.out.println("Sending message " + messageList.get(0));
				
				firstMessage = true;
			}

			
			break;
		default:
			AP2DXBase.logger
					.severe("Error in AP2DX.reflex.Program.componentLogic(Message message) Couldn't deal with message: "
							+ message.getMsgType());
		}

		return messageList;
	}

	/**
	 * @param travelDistance
	 *            the travelDistance to set
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
	 * @param distanceGoal
	 *            the distanceGoal to set
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
