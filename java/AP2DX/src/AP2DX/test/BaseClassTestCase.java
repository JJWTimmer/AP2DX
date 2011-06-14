/**
 * 
 */
package AP2DX.test;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import mockit.Mock;
import mockit.MockUp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import AP2DX.*;

/**
 * Test class for our abstract baselcass
 * 
 * @author Jasper Timmer
 * @author Maarten Inja
 * 
 */
public class BaseClassTestCase extends junit.framework.TestCase {


    public void testietest()
    {
        new ConcreteBase();
    }

	/**
	 * is called before all the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// pass
	}

	/**
	 * is called after all tests
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// pass
	}

	/**
	 * is called before each test
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// pass
	}

	/**
	 * is called after each test
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// pass
	}

	/**
	 * Test method for {@link AP2DX.AP2DXBase#AP2DXBase()}.
	 */
	@Test
	@Ignore("freezes test")
	public void testAP2DXBase() {
		new MockUp<Socket>() {
			@Mock
			void $init(String host, int port) {
				//pass
			}
		};
		
		new MockUp<Thread>() {
			@Mock
			void join() {
				//pass
			}
		};
		AP2DXBase test;
		try {
			test = new ConcreteBase();
			assertNotNull(test);
		} catch (Exception ex) {
			fail("CTOR TEST FAIL: " + ex.getMessage());
		}
	}

	/**
	 * Test method for {@link AP2DX.AP2DXBase#readConfig()}. Make sure we change
	 * this method if we change the config file (AP2DX.test.json).
	 */
	@Test
	@Ignore("not finished")
	public void testReadConfig() {
		new MockUp<Thread>() {
			@Mock
			void join() {
				//pass
			}
		};
		
		// readConfig is called in the ctor of the base class
		ConcreteBase test = new ConcreteBase();
		Map config = test.getConfig();

		compareString("logfile", "log/coordinator.log", config);
		compareString("sim_address", "146.50.51.9", config);
		compareInt("sim_port", 3000, config);
		compareInt("coordinator_listen_port", 3000, config);
	}

	/** Help method for testReadConfig. */
	private void compareString(String jsonValue, String compareToValue,
			Map config) {
		String value = (config.get(jsonValue)).toString();
		if (!value.equals(compareToValue))
			fail(String
					.format("FAIL: testReadConfig, variable '%s' with value '%s' does not match actual value '%s'",
							jsonValue, value, compareToValue));
	}

	/** Help method for testReadConfig. */
	private void compareInt(String jsonValue, int compareToValue, Map config) {
		int value = Integer.parseInt(config.get(jsonValue).toString());
		if (value != compareToValue)
			fail(String
					.format("FAIL: testReadConfig, variable '%s' with value '%s' does not match actual value '%s'",
							jsonValue, value, compareToValue));
	}
	
	public class ConcreteBase extends AP2DXBase {

		
		public Map getConfig() {
			return this.config;
		}
		
		@Override
		public ArrayList<Message> componentLogic(Message msg) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
