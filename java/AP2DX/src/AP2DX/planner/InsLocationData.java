/**
 * 
 */
package AP2DX.planner;

/**
 * @author jjwt
 *
 */
public class InsLocationData {
	/** time between INS measurements as set in the USARsim config file in seconds */
	public final double TIMEDELTA = 0.2;
	
	private double[] lastLocation = new double[3];
	private double[] lastOrientation = new double[3];
	
	private double[] currentLocation = new double[3];
	private double[] currentOrientation = new double[3];
	
	/**
	 * @param time time in Unreal ticks
	 * @param loc Location as double[3] in meters
	 * @param ori Orientation as double[3] in radians
	 */
	public InsLocationData(double[] loc, double[] ori) {
		setCurrentLocation(loc);
		setCurrentOrientation(ori);
	}
	
	public void setLocationData(double[] loc, double[] ori) {
		setLastLocation(getCurrentLocation());
		setLastOrientation(getCurrentOrientation());
		
		setCurrentLocation(loc);
		setCurrentOrientation(ori);
	}
	
	/**
	 * traveldistance in meters, returns 0 if no second dataset is set
	 * @return
	 */
	public double travelDistance() {
		double dx = Math.abs(getLastLocation()[0]  - getCurrentLocation()[0]);
		double dy = Math.abs(getLastLocation()[1]  - getCurrentLocation()[1]);
		double dz = Math.abs(getLastLocation()[2]  - getCurrentLocation()[2]);
		
		double dxs = Math.pow(dx, 2);
		double dys = Math.pow(dy, 2);
		double dzs = Math.pow(dz, 2);
		
		double sum = dxs + dys + dzs;
		
		return Math.sqrt(sum);
	}
	
	/**
	 * @return the travelspeed for this timedelta in meters per second
	 */
	public double speed() {
		return travelDistance() / TIMEDELTA;
	}
	
	/**
	 * @param lastLocation the lastLocation to set
	 */
	private void setLastLocation(double[] lastLocation) {
		this.lastLocation = lastLocation;
	}
	/**
	 * @return the lastLocation
	 */
	private double[] getLastLocation() {
		return lastLocation;
	}
	/**
	 * @param lastOrientation the lastOrientation to set
	 */
	private void setLastOrientation(double[] lastOrientation) {
		this.lastOrientation = lastOrientation;
	}
	/**
	 * @return the lastOrientation
	 */
	private double[] getLastOrientation() {
		return lastOrientation;
	}

	/**
	 * @param currentLocation the currentLocation to set
	 */
	private void setCurrentLocation(double[] currentLocation) {
		setLastLocation(getCurrentLocation());
		this.currentLocation = currentLocation;
	}
	/**
	 * @return the currentLocation
	 */
	public double[] getCurrentLocation() {
		return currentLocation;
	}
	/**
	 * @param currentOrientation the currentOrientation to set
	 */
	private void setCurrentOrientation(double[] currentOrientation) {
		setLastOrientation(getCurrentOrientation());
		this.currentOrientation = currentOrientation;
	}
	/**
	 * @return the currentOrientation
	 */
	public double[] getCurrentOrientation() {
		return currentOrientation;
	}
	
}
