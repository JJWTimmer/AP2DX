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

		/**
		 * Inline mockup class
		 */
		new MockUp<Message>() {
			
			/**
			 * override constructor
			 * @param msg
			 */
			@Mock
			void $init(String msg) {
				assertEquals("test", msg);
			}
		};

		Message msg = new Message("test");
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
		 * Automagic field must be called 'it'.
		 * 'it' represents the class and can be used to call and check methods and fields.
		 */
		//AP2DXBase it;
		
		/**
		 * Override the constructor of AP2DXBase to do nothing.
		 */
		@Mock
		public void $init() {
			//instead of connecting and configuring, do nothing
		}
	}

}
