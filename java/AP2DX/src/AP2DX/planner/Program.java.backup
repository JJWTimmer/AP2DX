package AP2DX.planner;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;
import AP2DX.specializedMessages.ActionMotorMessage;
import AP2DX.specializedMessages.ActionMotorMessage.ActionType;
import AP2DX.specializedMessages.ClearMessage;
import AP2DX.specializedMessages.InsSensorMessage;
import AP2DX.specializedMessages.OdometrySensorMessage;
import AP2DX.specializedMessages.ResetMessage;
import AP2DX.specializedMessages.SonarSensorMessage;

public class Program extends AP2DXBase {

	/** 
	 * threshold for turning in angle direction, robot should drive in a direction between
	 * destinationAngle - ANGLEUNCERTAIN and destinationAngle + ANGLEUNCERTAIN
	 */
	private static final double ANGLEUNCERTAIN = 15*Math.PI/180;

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
	private double[][] sonarData;

	/** holds angles for sonar data */
	private static double[] SONARANGLES;

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
		//System.out.println("Received message " + message.getMessageString());
		//System.out.println(String.format("In Queue: %s", this.getReceiveQueue().size()));

		switch (message.getMsgType()) {
		case AP2DX_PLANNER_STOP:

			startAngle = currentAngle;

			sonarPermission = true;

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
						msg6.compileMessage();
						messageList.add(msg6);
						System.out
								.println("Sending stop message based on distancegoal");
					}
				}
			}
			break;
		case AP2DX_SENSOR_SONAR:
			SonarSensorMessage msgs = (SonarSensorMessage) message;

			if (sonarPermission) {
				switch (turnCount) {
				case 0:
					sonarData = new double[4][6];
					double[] current = msgs.getRangeArray();
					for (int i = 1; i < 7; i++) { // don't use the outer two
						sonarData[0][i - 1] = current[i];
					}

					AP2DXMessage turnMsg1 = new ActionMotorMessage(IAM,
							Module.REFLEX, ActionType.TURN, 1);
					turnMsg1.compileMessage();
					messageList.add(turnMsg1);

					System.out
							.println("Sending turn message based on turnCount = 0");

					sonarPermission = false;
					startedTurning = true;

					turnCount++;

					break;
				case 1:
				case 2:
					double[] current2 = msgs.getRangeArray();
					for (int i = 1; i < 7; i++) {
						if (turnCount == 1)
							sonarData[1][i - 1] = current2[i];
						else
							sonarData[2][i - 1] = current2[i];
					}

					AP2DXMessage turnMsg2 = new ActionMotorMessage(IAM,
							Module.REFLEX, ActionType.TURN, 1);
					turnMsg2.compileMessage();
					messageList.add(turnMsg2);

					if (turnCount == 1)
						System.out
								.println("Sending turn message based on turnCount = 1");
					else
						System.out
								.println("Sending turn message based on turnCount = 2");

					sonarPermission = false;

					startAngle = currentAngle;
					startedTurning = true;

					turnCount++;

					break;
				case 3:
					double[] current3 = msgs.getRangeArray();
					for (int i = 1; i < 7; i++) {
						sonarData[3][i - 1] = current3[i];
					}

					sonarPermission = false;

					int far_i = 0;
					int far_j = 0;
					double far_value = 0;

					for (int i = 0; i < sonarData.length; i++) {
						for (int j = 0; j < sonarData[i].length; j++) {
							if (sonarData[i][j] > far_value) {
								far_value = sonarData[i][j];
								far_i = i;
								far_j = j;
							}
						}
					}

					destinationAngle = currentAngle + (40 * Math.PI / 180) + (far_i * 0.5 * Math.PI)
																	+ SONARANGLES[far_j];
					if (destinationAngle > Math.PI) destinationAngle = (-1*Math.PI) + (destinationAngle - Math.PI);
					
					startAngle = currentAngle;
					decidedDirection = true;

					System.out.println(String.format("Destination angle: %s",
							destinationAngle));

					AP2DXMessage msgt = new ActionMotorMessage(IAM,
							Module.REFLEX, ActionMotorMessage.ActionType.TURN,
							1);
					msgt.compileMessage();
					messageList.add(msgt);

					System.out
							.println("Sending turn message based on turnCount = 3");

					turnCount++;

					break;
				default:
					System.out.println("Uh uhw, kusje!");
					// nothing
				}
			}

			break;
		case AP2DX_SENSOR_ODOMETRY:
			System.out.println("parsing odometry message in planner");

			OdometrySensorMessage msgo = (OdometrySensorMessage) message;

			currentAngle = msgo.getTheta();

			if (startedTurning) {
				System.out.println("is turning...");
				if (Math.abs(startAngle - currentAngle) >= (Math.PI * 0.5)) {
					sonarPermission = true;
					AP2DXMessage stopMsg1 = new ActionMotorMessage(IAM,
							Module.REFLEX, ActionType.STOP, 666);
					stopMsg1.compileMessage();
					messageList.add(stopMsg1);

					System.out
							.println("Sending stop message based on startedTurning angle");

					startedTurning = false;
				}
			} else if (decidedDirection) {
				System.out.println("going the right direction...");
				System.out.println(String.format("Angle data: current: %s, destination: %s, UNCERTAIN: %s", currentAngle, destinationAngle, ANGLEUNCERTAIN));
				if (currentAngle >= (destinationAngle - ANGLEUNCERTAIN) && currentAngle <= (destinationAngle + ANGLEUNCERTAIN)) {
					
					startedTurning = false;
					decidedDirection = false;
					
					AP2DXMessage msg5 = new ClearMessage(IAM, Module.REFLEX);
					msg5.compileMessage();
					messageList.add(msg5);

					System.out
							.println("Sending clear message based on decided direction");

					AP2DXMessage msg6 = new ActionMotorMessage(IAM,
							Module.REFLEX,
							ActionMotorMessage.ActionType.FORWARD, 10.0);
					msg6.compileMessage();
					messageList.add(msg6);

					System.out
							.println("Sending forward message based on decided direction");
				}
			}

			if (!firstMessage) {
				// for now, lets just drive forward, OKAY?!
				AP2DXMessage msg5 = new ActionMotorMessage(IAM, Module.REFLEX,
						ActionMotorMessage.ActionType.FORWARD, 10.0);
				msg5.compileMessage();
				messageList.add(msg5);

				System.out.println("Sending message first message");

				firstMessage = true;
				
				SONARANGLES = new double[] { 0 * Math.PI / 180,
						20 * Math.PI / 180, 40 * Math.PI / 180, 60 * Math.PI / 180,
						80 * Math.PI / 180, 100 * Math.PI / 180};
				
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
