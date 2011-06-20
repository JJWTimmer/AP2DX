/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author jjwt
 *
 */
public class ArrayFloatData {
	private ArrayList<Float> array;
	private String name;
	
	public ArrayFloatData(String name, String data) {
		this.name = name;
		
		String[] nums = data.split(",");
		
		array = new ArrayList<Float>();
		
		for (int i =0; i < nums.length; i++) {
			array.add(Float.parseFloat(nums[i]));
		}
	}
	
	@Override
	public String toString() {
		String nums = join(this.array, ",");
		
		String output = String.format(" {%s %s}", this.name, nums);
		
		return output;
	}
	
	public List<Float> getFloats() {
		return this.array;
	}
	
	public String getName() {
		return this.name;
	}
	
	
    public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

}
