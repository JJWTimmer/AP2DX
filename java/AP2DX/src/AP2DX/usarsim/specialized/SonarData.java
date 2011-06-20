/**
 * 
 */
package AP2DX.usarsim.specialized;

/**
 * @author jjwt
 *
 */
public class SonarData extends Object {
	String name;
	double range;
	
	public SonarData(String name, String range) {
		this.name = name;
		this.range = Double.parseDouble(range);
	}
	
	public SonarData(String name, double range) {
		this.name = name;
		this.range = range;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the range
	 */
	public double getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(double range) {
		this.range = range;
	}
	
	
	@Override
	public String toString() {
		return String.format(" {Name %s Range %s}", this.getName(), this.getRange());
	}
}
