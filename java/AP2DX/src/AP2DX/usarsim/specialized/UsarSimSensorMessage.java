/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AP2DX.usarsim.UsarSimMessage;

/**
 * @author jjwt
 *
 */
public class UsarSimSensorMessage extends UsarSimMessage {

	private SensorType sensorType;

	public UsarSimSensorMessage(UsarSimMessage msg) throws Exception {
		super(msg.getMessageString());
	}
	
	public UsarSimSensorMessage(String msg) throws Exception {
		super(msg);
	}

	public SensorType getSensorType() {
		String typePatternStr = "\\{Type ([a-zA-Z0-9,._\\-]+)\\}";
		Pattern typePattern = Pattern.compile(typePatternStr);
		Matcher typeMatcher = typePattern.matcher(this.getMessageString());

		if (typeMatcher.find()) {
			String type = typeMatcher.group(1);
			return SensorType.getEnumByString(type);
		}
		else {
			return SensorType.USAR_UNKNOWN_SENSOR;
		}
		
	}

}
