/**
 * 
 */
package AP2DX.planner.test;

import static org.junit.Assert.*;
import junit.framework.Assert;
import junit.framework.TestCase;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockClass;
import mockit.MockUp;
import mockit.Mockit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import AP2DX.*;
import AP2DX.planner.*;

/**
 * @author Jasper Timmer
 */
public class PlannerTestCase {
	
    /**
	 * Test method for {@link AP2DX.coordinator.Program#Program()}.
	 */
	@Test
	public void testProgram() {
        System.out.println("Before setUpMocks");
		Mockit.setUpMocks(FakeBase.class);
        System.out.println("After SetUpMocks, Before new Program()");
		Program test = new Program();
        System.out.println("After new program");
		assertNotNull(test);
        System.out.println("after assertNotNull()");
	}

	/**
	 * This is the mocked AP2DXBase.
	 * 
	 * @author Jasper
	 * 
	 */
	@MockClass(realClass = AP2DXBase.class)
	public static final class FakeBase {		
		/**
		 * Override the constructor of AP2DXBase to do nothing.
		 */
		@Mock
		public void $init(Module module) {
            System.out.printf("Module: %s\n", module);
			//instead of connecting and configuring, do nothing
		}
	}

}




