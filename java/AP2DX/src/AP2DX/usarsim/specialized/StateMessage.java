/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AP2DX.Message;
import AP2DX.usarsim.UsarSimMessage;

/**
 * @author Jasper Timmer
 * 
 */
public final class StateMessage extends UsarSimMessage {
	private Message.MessageType type;
	private float time;
	private float frontSteer;
	private float rearSteer;
	private boolean lightToggle;
	private int lightIntensity;
	private int battery;
	private float sternPlaneAngle;
	private float rudderAngle;

	public StateMessage(UsarSimMessage msg) {
		super(msg.getMessageString());
		this.parseMessage();
	}

	/**
	 * @see AP2DX.Message#parseMessage()
	 */
	@Override
	public void parseMessage() {
		String startPatternStr = "^[A-Z]+";
		String groupPatternStr = "\\{([a-zA-Z0-9 .,_\\-]+)\\}";

		Pattern startPattern = Pattern.compile(startPatternStr);

		Pattern groupPattern = Pattern.compile(groupPatternStr);

		Matcher startMatcher = startPattern.matcher(this.getMessageString());
		Matcher groupMatcher = groupPattern.matcher(this.getMessageString());
		
		if (startMatcher.find())
			this.values.put("msgtype", startMatcher.group(0));

		while (groupMatcher.find()) {
			String group = groupMatcher.group(1);
			int space = group.indexOf(' ');
			this.values.put(group.substring(0,space), group.substring(space, group.length()));
		}
	}
	
	@Override
	protected void compileMessage() {
		StringBuilder output = new StringBuilder();
		
		output.append(String.format(" {Type %s}", this.type));
		
		if (this.time != 0)
			output.append(String.format(" {Time %s}", this.time));
		if (this.frontSteer != 0)
			output.append(String.format(" {FrontSteer %s}", this.frontSteer));
		if (this.rearSteer != 0)
			output.append(String.format(" {RearSteer %s}", this.rearSteer));
		if (this.sternPlaneAngle != 0)
			output.append(String.format(" {SternPlaneAngle %s}", this.sternPlaneAngle));
		if (this.rudderAngle != 0)
			output.append(String.format(" {RudderAngle %s}", this.rudderAngle));
		if (this.battery != 0)
			output.append(String.format(" {Battery %s}", this.battery));
		
		output.append(String.format(" {LightToggle %s}", this.lightToggle));
		
		if (this.lightIntensity != 0)
			output.append(String.format(" {LightIntensity %s}", this.lightIntensity));
		
		this.messageString = output.toString();
	}
	
	public String getMessageType() {
		return values.get("msgtype").toString();
	}

	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @return the frontSteer
	 */
	public float getFrontSteer() {
		return frontSteer;
	}

	/**
	 * @return the rearSteer
	 */
	public float getRearSteer() {
		return rearSteer;
	}

	/**
	 * @return the lightToggle
	 */
	public boolean isLightToggle() {
		return lightToggle;
	}

	/**
	 * @return the lightIntensity
	 */
	public int getLightIntensity() {
		return lightIntensity;
	}

	/**
	 * @return the battery
	 */
	public int getBattery() {
		return battery;
	}

	/**
	 * @return the SternPlaneAngle
	 */
	public float SternPlaneAngle() {
		return sternPlaneAngle;
	}

	/**
	 * @return the RudderAngle
	 */
	public float getRudderAngle() {
		return rudderAngle;
	}

	/**
	 * @return the type
	 */
	public Message.MessageType getType() {
		return this.type;
	}

	/**
	 * @return the sternPlaneAngle
	 */
	public float getSternPlaneAngle() {
		return sternPlaneAngle;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Message.MessageType type) {
		this.type = type;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(float time) {
		this.time = time;
	}

	/**
	 * @param frontSteer the frontSteer to set
	 */
	public void setFrontSteer(float frontSteer) {
		this.frontSteer = frontSteer;
	}

	/**
	 * @param rearSteer the rearSteer to set
	 */
	public void setRearSteer(float rearSteer) {
		this.rearSteer = rearSteer;
	}

	/**
	 * @param lightToggle the lightToggle to set
	 */
	public void setLightToggle(boolean lightToggle) {
		this.lightToggle = lightToggle;
	}

	/**
	 * @param lightIntensity the lightIntensity to set
	 */
	public void setLightIntensity(int lightIntensity) {
		this.lightIntensity = lightIntensity;
	}

	/**
	 * @param battery the battery to set
	 */
	public void setBattery(int battery) {
		this.battery = battery;
	}

	/**
	 * @param sternPlaneAngle the sternPlaneAngle to set
	 */
	public void setSternPlaneAngle(float sternPlaneAngle) {
		this.sternPlaneAngle = sternPlaneAngle;
	}

	/**
	 * @param rudderAngle the rudderAngle to set
	 */
	public void setRudderAngle(float rudderAngle) {
		this.rudderAngle = rudderAngle;
	}

}
