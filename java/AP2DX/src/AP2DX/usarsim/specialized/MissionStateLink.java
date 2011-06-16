/**
 * 
 */
package AP2DX.usarsim.specialized;

/**
 * @author Jasper Timmer
 *
 */
public class MissionStateLink {
	private int link;
	private float value;
	private float torque;
	
	public MissionStateLink(int link, float value, float torque) {
		this.setLink(link);
		this.setValue(value);
		this.setTorque(torque);
		
	}
	
	public MissionStateLink(String link, String value, String torque) {
		this.setLink(Integer.parseInt(link));
		this.setValue(Float.parseFloat(value));
		this.setTorque(Float.parseFloat(torque));
	}
	
	@Override
	public String toString() {
		return String.format(" {Link %s} {Value %s} {Torque %s}", link, value, torque);
	}

	/**
	 * @param torque the torque to set
	 */
	public void setTorque(float torque) {
		this.torque = torque;
	}

	/**
	 * @return the torque
	 */
	public float getTorque() {
		return torque;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	public void setLink(int link) {
		this.link = link;
	}

	public int getLink() {
		return link;
	}

}
