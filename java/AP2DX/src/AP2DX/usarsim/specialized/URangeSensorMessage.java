/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AP2DX.usarsim.UsarSimMessage;

/**
 * Not nessecary to convert this type to a new Usarsim string.
 * @author Jasper Timmer
 * 
 */
public class URangeSensorMessage extends UsarSimMessage {
	
	private String type;
	private String name;
	private double resolution;
	private double fov;
	private double[] range;

	public URangeSensorMessage(UsarSimMessage msg) throws IllegalArgumentException, IllegalAccessException {
		super(msg.getMessageString());
		this.parseMessage();
	}

	public URangeSensorMessage(String string) throws IllegalArgumentException, IllegalAccessException {
		super(string);
		this.parseMessage();
	}

	/**
	 * @see AP2DX.usarsim.specialized.SensorMessage#parseMessage() Parses all
	 *      the fields in the message and sets the values of the fields with the
	 *      same name to the values.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @see AP2DX.Message#parseMessage()
	 */
	@Override
	public void parseMessage() throws IllegalArgumentException,
			IllegalAccessException {
		String typePatternStr = "\\{Type ([a-zA-Z0-9,._\\-]+)\\}";
		Pattern typePattern = Pattern.compile(typePatternStr);
		Matcher typeMatcher = typePattern.matcher(this.getMessageString());

		if (typeMatcher.find()) {
			String type = typeMatcher.group(1);
			this.setType(type);
		}

		String namePatternStr = "\\{Name ([a-zA-Z0-9,._\\-]+)\\}";
		Pattern namePattern = Pattern.compile(namePatternStr);
		Matcher nameMatcher = namePattern.matcher(this.getMessageString());

		if (nameMatcher.find()) {
			String name = nameMatcher.group(1);
			this.setName(name);

		}
		String resPatternStr = "\\{Resolution ([0-9.,\\-_]+)\\}";
		Pattern resPattern = Pattern.compile(resPatternStr);
		Matcher resMatcher = resPattern.matcher(this.getMessageString());

		if (resMatcher.find()) {
			String res = resMatcher.group(1);
			this.setResolution(Double.parseDouble(res));
		}
		String fovPatternStr = "\\{FOV ([0-9.,\\-_]+)\\}";
		Pattern fovPattern = Pattern.compile(fovPatternStr);
		Matcher fovMatcher = fovPattern.matcher(this.getMessageString());

		if (fovMatcher.find()) {
			String fov = fovMatcher.group(1);
			this.setFov(Double.parseDouble(fov));

		}

		String groupPatternStr = "\\{Range ([0-9.,\\-_]+)\\}";
		Pattern groupPattern = Pattern.compile(groupPatternStr);
		Matcher groupMatcher = groupPattern.matcher(this.getMessageString());
		
		double[] data = null;
		if (groupMatcher.find()) {
			String[] ranges = groupMatcher.group(1).split(",");
			data = new double[ranges.length];

			for (int i = 0; i < data.length; i++) {
				data[i] = Double.parseDouble(ranges[i]);
			}
		}
		
		this.setRange(data);
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	/**
	 * @return the resolution
	 */
	public double getResolution() {
		return resolution;
	}

	/**
	 * @param fov the fov to set
	 */
	public void setFov(double fov) {
		this.fov = fov;
	}

	/**
	 * @return the fov
	 */
	public double getFov() {
		return fov;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(double[] range) {
		this.range = range;
	}

	/**
	 * @return the range
	 */
	public double[] getRange() {
		return range;
	}
}
