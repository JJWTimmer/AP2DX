package AP2DX.usarsim.specialized;

public enum SensorType {
	USAR_ODOMETRY("Odometry"),
	USAR_INS("INS"),
	USAR_RANGE("RangeScanner"),
	USAR_SONAR("Sonar"),
	USAR_UNKNOWN_SENSOR("UNKNOWN");
	
	
	public String typeString;
	
	SensorType (String type) {
		this.typeString = type;
	}
	
    /**
     * Finds an enum by a String.
     * TODO: change this to a map for performance increase
     */
     public static SensorType getEnumByString(String typeString)
     {
         for(SensorType possibleType : SensorType.values())
             if (possibleType.typeString.equals(typeString))
                 return possibleType;
         return SensorType.USAR_UNKNOWN_SENSOR;
     }
}
