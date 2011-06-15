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
		String sPattern = "([\\w+]{1})[\\s]{1}([{]{1}[\\w\\s]+[}]{1})+";
		System.out.println(sPattern);
		Pattern pattern = 
            Pattern.compile(sPattern);

            Matcher matcher = pattern.matcher(this.getMessageString());

            int groups = matcher.groupCount();
            System.out.println(matcher.group(0));
            for (int i = 1; i < groups; i++) {
            	System.out.println(matcher.group(0));
            }
	}
}
