/**
 * 
 */
package AP2DX.test;

import junit.framework.TestCase;
import AP2DX.AP2DXBase;

/**
 * @author Jasper
 *
 */
public class AP2DXBaseTest extends TestCase {

    public void testNothing() {
    	assertEquals(1, 1);
    }
    
    public void testConcreteClass() {
    	System.out.println("#Cur dir: " + System.getProperty("user.dir"));
    	
    	ConcreteClass test = new ConcreteClass();
    	assertNotNull(test);
    }
    
    private class ConcreteClass extends AP2DX.AP2DXBase {
    	//empty

        protected void setConfig() {}    
    }

}
