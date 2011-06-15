/**
 * 
 */
package AP2DX;

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
		System.out.println(this.getMessageString());
		String startPatternStr = "^[A-Z]+";
		String groupPatternStr = "\\{[a-zA-Z0-9 .,_\\-]+\\}";

		Pattern startPattern = 
            Pattern.compile(startPatternStr);
		
		Pattern groupPattern = 
            Pattern.compile(groupPatternStr);

            Matcher startMatcher = startPattern.matcher(this.getMessageString());
            Matcher groupMatcher = groupPattern.matcher(this.getMessageString());

            if (startMatcher.find())
            	System.out.println(startMatcher.group(0));
            
            while (groupMatcher.find()) {
	            System.out.println(groupMatcher.group(0));
            }
	}
}
