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
