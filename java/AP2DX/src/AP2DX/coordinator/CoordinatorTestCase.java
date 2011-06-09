/**
 * 
 */
package AP2DX.coordinator;

import static org.junit.Assert.*;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author jjwt
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
	 * Test method for {@link AP2DX.coordinator.Program#setConfig()}.
	 */
	@Test
	public void testSetConfig() {
		try {
			Program coordinator = new Program();
			coordinator.setConfig();
		}
		catch (Exception ex) {
			fail("Error in setConfig");
		}
	}

	/**
	 * Test method for {@link AP2DX.coordinator.Program#main(java.lang.String[])}.
	 */
	@Test
	public void testMain() {
		try {
			String[] args = new String[0];
			Program.main(args);
		}
		catch (Exception ex) {
			fail("Error constructing Coordinator");
		}
	}

	/**
	 * Test method for {@link AP2DX.coordinator.Program#Program()}.
	 */
	@Test
	public void testProgram() {
		try {
			Program test = new Program();
			assertNotNull(test);
		}
		catch (Exception ex) {
			fail("Error constructing Coordinator");
		}
	}

}
