/**
 * 
 */
package AP2DX.coordinator;

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

/**
 * @author Jasper Timmer
 * 
 */
public class CoordinatorTestCase extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link AP2DX.coordinator.Program#Program()}.
	 */
	@Test
	public void testProgram() {
		Mockit.setUpMocks(FakeBase.class);

		Program test = new Program();
		assertNotNull(test);

		Message msg = new UsarSimMessage("SEN {Time 395.3833} {Type GroundTruth} {Name GroundTruth} {Location 4.50,1.90,1.85} {Orientation 0.00,6.28,6.28}", Module.UNDEFINED);
		test.componentLogic(msg);
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
		public void $init() {
			//instead of connecting and configuring, do nothing
		}
	}

}
