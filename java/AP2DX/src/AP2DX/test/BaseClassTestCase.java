/**
 * 
 */
package AP2DX.test;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import AP2DX.*;

/**
 * Test class for our abstract baselcass
 *
 * @author Jasper Timmer
 * @author Maarten Inja
 *
 */
public class BaseClassTestCase extends junit.framework.TestCase
{
    public static ServerSocket serverSocket;
    public static Socket socket;
    
	/**
	 * is called before all the tests
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
    {
		try 
        {
            // TODO: check at whatever port this server socket should listen to
			serverSocket = new ServerSocket(3001);
            socket = serverSocket.accept();
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
            System.out.println("ERROR in setting up socket server: " + e.getMessage());
        }
	}
	
	/**
	 * is called after all tests
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
    {
		//pass
	}

	/**
	 * is called before each test
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
    {
        //test = new ConcreteClass();
        
        
        System.out.println("Created ConcreteClass");
	}

	/**
	 * is called after each test
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
    {
		//pass
	}

    private ConcreteClass test;

	/**
	 * Test method for {@link AP2DX.AP2DXBase#AP2DXBase()}.
	 */
	@Test
	public void testAP2DXBase() 
    {
		try 
        {
			test = new ConcreteClass();
            //assertNotNull(test);
		}
		catch (Exception ex) 
        {
            
		    fail("CTOR TEST FAIL: " + ex.getMessage() + ", " + (test == null));	
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
        Map config = test.getConfig();

        compareString("logfile", "log/coordinator.log", config);
        compareString("sim_address", "146.50.51.9", config); 
        compareInt("sim_port", 3000, config);
        compareInt("coordinator_listen_port", 3000, config);
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
	 * Test method for {@link AP2DX.AP2DXBase#getContents(java.io.File)}. 
     * WATCH IT! Make sure the method ConcreteClass.getContentsConcrete calls 
     * the abstract getContents method correctly.
	 */
	@Test
	public void testGetContents() 
    {
        String content = removeWeirdCharacters(test.getContentsConcrete());
        String compareToValue = removeWeirdCharacters(String.format("%s\n%s\n%s\n%s\n%s\n%s\n", 
    "{",
    "\t\"logfile\": \"log/coordinator.log\",",
    "\t\"sim_port\": 3000,",
    "\t\"sim_address\": \"146.50.51.9\",",
    "\t\"coordinator_listen_port\": 9000,",
    "}"));

        if (!content.equals(compareToValue))
		    fail(String.format("FAIL: testGetContents, does not match: \n'%s', \n'%s' %d",
                content, compareToValue, content.trim().compareTo(compareToValue.trim())));
	}

    private String removeWeirdCharacters(String s)
    {   
        String returnString = "";
        for (char c : s.toCharArray())
            if (Character.isLetter(c))
                returnString += c;
        return returnString; 
    }

	/**
	 * Concrete implementation of BaseClass
	 * @author jjwt
	 *
	 */
	public class ConcreteClass extends AP2DXBase 
    {
		
		public Map getConfig() {
			return config;
		}

        public String getContentsConcrete()
        {
            return getContents(new File(this.getClass().getPackage().getName() + ".json"));
        }

		@Override
		public ArrayList<Message> componentLogic(Message msg) 
        {
			// TODO Auto-generated method stub
			return new ArrayList<Message>();
		}
		
	}
}
