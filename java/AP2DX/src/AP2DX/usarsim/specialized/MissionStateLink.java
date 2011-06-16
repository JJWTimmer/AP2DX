/**
 * 
 */
package AP2DX.usarsim.specialized;

/**
 * @author Jasper Timmer
 *
 */
public class MissionStateLink {
	private float value;
	private float torque;
	
	public MissionStateLink(float value, float torque) {
		this.setValue(value);
		this.setTorque(torque);
		
	}
	
	public MissionStateLink(String value, String torque) {
		this.setValue(Float.parseFloat(value));
		this.setTorque(Float.parseFloat(torque));
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

}
