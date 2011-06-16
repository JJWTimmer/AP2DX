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
	
	public Message.MessageType getMessageType() {
		String startPatternStr = "^[A-Z]+";

		Pattern startPattern = Pattern.compile(startPatternStr);
		
		Matcher startMatcher = startPattern.matcher(this.getMessageString());
		
		if (startMatcher.find())
			return this.messageTypeDict.get(startMatcher.group(0));
		else
			return null;
	}	
}
