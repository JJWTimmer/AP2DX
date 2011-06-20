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
 * @author Jasper Timmer
 * 
 */
public class SonarMessage extends UsarSimMessage {
	
	@UsarMessageField(name = "Type")
	private String type;

	@UsarMessageField(name = "Time")
	private double time;
	
	@UsarMessageIteratorField
	private List<SonarData> data = new ArrayList<SonarData>();

	public SonarMessage(UsarSimMessage msg) throws IllegalArgumentException, IllegalAccessException {
		super(msg.getMessageString());
		this.parseMessage();
	}

	public SonarMessage(String string) throws IllegalArgumentException, IllegalAccessException {
		super(string);
		this.parseMessage();
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
	 * @param time the time to set
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<SonarData> data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public List<SonarData> getData() {
		return data;
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

		String timePatternStr = "\\{Time ([a-zA-Z0-9,._\\-]+)\\}";
		Pattern timePattern = Pattern.compile(timePatternStr);
		Matcher timeMatcher = timePattern.matcher(this.getMessageString());

		if (timeMatcher.find()) {
			String time = timeMatcher.group(1);
			this.setTime(Double.parseDouble(time));

		}

		String groupPatternStr = "\\{Name ([\\w\\d]+) Range ([0-9.,\\-_]+)\\}";
		Pattern groupPattern = Pattern.compile(groupPatternStr);
		Matcher groupMatcher = groupPattern.matcher(this.getMessageString());

		List<SonarData> dataList = new ArrayList<SonarData>();
		
		while (groupMatcher.find()) {
			String name = groupMatcher.group(1);
			String value = groupMatcher.group(2);
			SonarData data = new SonarData(name, value);
			
			dataList.add(data);
		}
		
		this.setData(dataList);
	}
}
