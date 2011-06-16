/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AP2DX.Message;
import AP2DX.usarsim.UsarSimMessage;

/**
 * @author Jasper Timmer
 * 
 */
public final class MissionStateMessage extends UsarSimMessage {
	@UsarMessageField(name = "Time")
	private float time;
	
	@UsarMessageField(name = "Name")
	private String name;
	
	@UsarMessageIteratorField()
	private List<MissionStateLink> links;
	
	/**
	 * @param msg the UsarSimMessage
	 */
	public MissionStateMessage(UsarSimMessage msg) {
		super(msg.getMessageString());
		this.parseMessage();
	}
	
	/**
	 * @see AP2DX.Message#parseMessage()
	 */
	@Override
	public void parseMessage() {
		String startPatternStr = "\\{Time ([0-9.\\-]+)\\} \\{Name ([a-zA-Z0-9.\\-_]+)\\}";
		Pattern startPattern = Pattern.compile(startPatternStr);
		Matcher startMatcher = startPattern.matcher(this.getMessageString());
		
		if (startMatcher.find()) {
			this.time = Float.parseFloat(startMatcher.group(1));
			this.name = startMatcher.group(2);
		}		
		
		String groupPatternStr = "\\{Link (\\d+)\\} \\{Value ([0-9.\\-]+)\\} \\{Torque ([0-9.\\-]+)\\}";
		Pattern groupPattern = Pattern.compile(groupPatternStr);
		Matcher groupMatcher = groupPattern.matcher(this.getMessageString());

		while (groupMatcher.find()) {
			this.links.add(new MissionStateLink(groupMatcher.group(1), groupMatcher.group(2), groupMatcher.group(3)));
		}
	}

}
