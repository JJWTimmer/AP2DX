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
public class AP2DXMessage extends Message {
	
	public AP2DXMessage(String in, Module origin, Module destination) {
		super(in, origin, destination);
	}
	
	public AP2DXMessage(String in, Module origin) {
		super(in, origin);
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
