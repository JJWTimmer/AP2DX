/**
 * 
 */
package AP2DX.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jjwt
 *
 */
public class baseClassTestCase {
    /** The port of the simulator. Reaf from the configuration file. */
    private int simulatorPort;
    /** The IP address of the simulator. Read from the configuration file. */
    private String simulatorAddress;
    
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			//pass
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//pass
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//pass
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		//pass
	}

	/**
	 * Test method for {@link AP2DX.AP2DXBase#AP2DXBase()}.
	 */
	@Test
	public void testAP2DXBase() {
		try {
			ConcreteClass test = new ConcreteClass();
		}
		catch (Exception ex) {
			
		}
	}

	/**
	 * Test method for {@link AP2DX.AP2DXBase#readConfig()}.
	 */
	@Test
	public void testReadConfig() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link AP2DX.AP2DXBase#setConfig()}.
	 */
	@Test
	public void testSetConfig() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link AP2DX.AP2DXBase#getContents(java.io.File)}.
	 */
	@Test
	public void testGetContents() {
		fail("Not yet implemented");
	}

	/**
	 * Concrete implementation of BaseClass
	 * @author jjwt
	 *
	 */
	private class ConcreteClass extends AP2DX.AP2DXBase {
		/**
		 * Concrete implementation of setConfig
		 */
		@Override
		protected void setConfig() {
			simulatorAddress = (config.get("sim_address")).toString();
	        simulatorPort = Integer.parseInt(config.get("sim_port").toString());
			
		}
		
		//now it is concrete!
	}
}
