/**
 * 
 */
package AP2DX;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jasper Timmer
 * 
 */
public class UsarSimMessage extends Message {

	public UsarSimMessage(String in) {
		super(in, Module.UNDEFINED);
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

		Map<String, String> dict = this.getValues();
		
		if (startMatcher.find())
			dict.put("type", startMatcher.group(0));

		while (groupMatcher.find()) {
			String group = groupMatcher.group(1);
			int space = group.indexOf(' ');
			dict.put(group.substring(0,space), group.substring(space, group.length()));
		}
		this.setValues(dict);
	}
}
