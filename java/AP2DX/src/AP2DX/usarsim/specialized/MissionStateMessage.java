/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.ArrayList;
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
	private List<MissionStateLink> links = new ArrayList<MissionStateLink>();
	
	/**
	 * @param msg the UsarSimMessage
	 */
	public MissionStateMessage(UsarSimMessage msg) {
		super(msg.getMessageString());
		this.parseMessage();
	}
	
	/**
	 * @param msg the UsarSimMessage
	 */
	public MissionStateMessage(String msgstring) {
		super(msgstring);
		this.parseMessage();
	}
	
	/**
	 * @return the links
	 */
	public List<MissionStateLink> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<MissionStateLink> links) {
		this.links = links;
	}

	/**
	 * @see AP2DX.Message#parseMessage()
	 */
	@Override
	protected void parseMessage() {
		String startPatternStr = "\\{Time ([0-9.\\-]+)\\} \\{Name ([a-zA-Z0-9.\\-_]+)\\}";
		Pattern startPattern = Pattern.compile(startPatternStr);
		Matcher startMatcher = startPattern.matcher(this.getMessageString());
		
		if (startMatcher.find()) {
			this.setTime(Float.parseFloat(startMatcher.group(1)));
			this.setName(startMatcher.group(2));
		}		
		
		String groupPatternStr = "\\{Link (\\d+)\\} \\{Value ([0-9.\\-]+)\\} \\{Torque ([0-9.\\-]+)\\}";
		Pattern groupPattern = Pattern.compile(groupPatternStr);
		Matcher groupMatcher = groupPattern.matcher(this.getMessageString());
		
		while (groupMatcher.find()) {
			MissionStateLink link = new MissionStateLink(groupMatcher.group(1), groupMatcher.group(2), groupMatcher.group(3));
			this.links.add(link);
		}
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(float time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
