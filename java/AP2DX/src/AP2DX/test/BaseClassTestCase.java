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

import java.util.Map;

/**
 * Test class for our abstract baselcass
 *
 * @author jjwt
 * @author Maarten Inja
 *
 */
public class BaseClassTestCase extends junit.framework.TestCase
{
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
	public void testAP2DXBase() 
    {
		try {
			ConcreteClass test = new ConcreteClass();
            assertNotNull(test);
		}
		catch (Exception ex) {
		    fail(ex.getMessage());	
		}
	}

	/**
	 * Test method for {@link AP2DX.AP2DXBase#readConfig()}. Make sure
     * we change this method if we change the config file (AP2DX.test.json).
	 */
	@Test
	public void testReadConfig() 
    {
        // readConfig is called in the ctor of the base class
        ConcreteClass test = new ConcreteClass();
        Map config = test.getConfig();

        compareString("logfile", "log/coordinator.log", config);
        compareString("sim_address", "146.50.51.9", config); 
        compareInt("sim_port", 3000, config);
        compareInt("coordinator_listen_port", 9000, config);
	}
  
    /** Help method for testReadConfig. */  
    private void compareString(String jsonValue, String compareToValue, Map config)
    {
        String value = (config.get(jsonValue)).toString();
        if (!value.equals(compareToValue))
            fail(String.format("FAIL: testReadConfig, variable '%s' with value '%s' does not match actual value '%s'", jsonValue, value, compareToValue));
    }

    /** Help method for testReadConfig. */  
    private void compareInt(String jsonValue, int compareToValue, Map config)
    {
        int value = Integer.parseInt(config.get(jsonValue).toString());
        if (value != compareToValue)
            fail(String.format("FAIL: testReadConfig, variable '%s' with value '%s' does not match actual value '%s'", jsonValue, value, compareToValue));
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

        public Map getConfig()
        {
            return config;
        }
		
	}
}
