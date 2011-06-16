/**
 * 
 */
package AP2DX.usarsim;

import java.util.Collections;
import java.util.HashMap;
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
	/**
	 * The translation of messagetype in messages to our enum, for example:
	 * "STA" -> Message.MessageType.USAR_STATE
	 */
	public static final Map<String, Message.MessageType> messageTypeDict = createMap();

	/**
	 * Constructor for the messageTypeDict
	 * @return HashMap with the string to MessageType table
	 */
    private static Map<String, Message.MessageType> createMap() {
        Map<String, Message.MessageType> result = new HashMap<String, Message.MessageType>();
        
        result.put("STA", Message.MessageType.USAR_STATE);
        result.put("MISSTA", Message.MessageType.USAR_MISSIONSTATE);
        
        return Collections.unmodifiableMap(result);
    }


    /**
     * make a new UsarSimMessage
     * @param in
     */
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

}
