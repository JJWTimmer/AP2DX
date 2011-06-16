/**
 * 
 */
package AP2DX.usarsim;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AP2DX.Message;
import AP2DX.Module;

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
		String output = "{";
		output += values.get("msgtype");
		Iterator it = values.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
			output += " " + pair.getKey() + " " + pair.getValue();
		}

		this.messageString = output;
	}
}
