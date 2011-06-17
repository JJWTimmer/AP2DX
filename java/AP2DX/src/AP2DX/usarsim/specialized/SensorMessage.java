/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AP2DX.Message;
import AP2DX.usarsim.UsarSimMessage;
import AP2DX.usarsim.UsarSimMessage.UsarMessageField;

/**
 * @author Jasper Timmer
 * 
 */
public final class SensorMessage extends UsarSimMessage {

	@UsarMessageField(name = "Type")
	private String type;

	
	@UsarMessageField(name = "Time")
	private float time;
	
	@UsarMessageIteratorField
	private List<ISensorData> data = new ArrayList<ISensorData>();

	public SensorMessage(UsarSimMessage msg) throws IllegalArgumentException, IllegalAccessException {
		super(msg.getMessageString());
		this.parseMessage();
	}

	public SensorMessage(String string) throws IllegalArgumentException, IllegalAccessException {
		super(string);
		this.parseMessage();
	}

	/**
	 * Parses all the fields in the message and sets the values of the fields
	 * with the same name to the values.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @see AP2DX.Message#parseMessage()
	 */
	@Override
	public void parseMessage() throws IllegalArgumentException, IllegalAccessException {
		//TODO: Fix this method
		String groupPatternStr = "\\{(\\w+) ([a-zA-Z0-9,._\\-]+)\\}";
		Pattern groupPattern = Pattern.compile(groupPatternStr);
		Matcher groupMatcher = groupPattern.matcher(this.getMessageString());

		if (groupMatcher.find()) {
			String name = groupMatcher.group(1);
			Object value = groupMatcher.group(2);
			for (Field field : this.getClass().getDeclaredFields()) {
				if (field.getAnnotation(UsarMessageField.class).name().equals(name)) {
					field.set(this, field.getType().cast(value));
				}
			}
		}
	}
}
