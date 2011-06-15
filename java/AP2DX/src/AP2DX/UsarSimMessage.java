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
		Pattern pattern = 
            Pattern.compile("(\\w+)\\s({[\\w\\s]+})+");

            Matcher matcher = pattern.matcher(this.getMessageString());

            int groups = matcher.groupCount();
            System.out.println(matcher.group(0));
            for (int i = 1; i < groups; i++) {
            	System.out.println(matcher.group(0));
            }
	}
}
