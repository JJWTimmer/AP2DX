package AP2DX.test;

// The package
import AP2DX.*;

import java.util.Map;
import java.net.*;

// The junit framework stuff, for testing
import junit.framework.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;



import mockit.Expectations;
import mockit.Mock;
import mockit.MockClass;
import mockit.MockUp;
import mockit.Mockit;

// Arraylist, needed for our concrete class
import java.util.ArrayList;

public class LogicTestCase 
{

    private AP2DXBase base;
    private Logic logic;
    
    @Test
    public void testLogic()
    {   
        logic = new Logic(base);
        assertNotNull("LogicTestCase.testLogic, things go south", logic); 
    }

    @Test
    public void testRun()
    {
        boolean running = true;
        try
        {
            logic.run();
        }
        catch(Exception e)
        {
            running = false;
            e.printStackTrace();
        }
        assertTrue("LogicTestCase.testRun, exception caught", running);
    }

    public class ConcreteBase extends AP2DXBase 
    {

        public ConcreteBase(Module module)
        {
            super(module);
        }

        public Map getConfig() {
            return this.config;
        }

        @Override
            public ArrayList<Message> componentLogic(Message msg) {
                // TODO Auto-generated method stub
                return null;
            }

    }

     
    //{{{

	/**
	 * is called before all the tests
	 * 
	 * @throws java.lang.Exception
	 */
	//@BeforeClass
	public static void setUpBeforeClass() throws Exception 
    {
        new MockUp<Socket>() {
            @Mock
                void $init(String host, int port) {
                    System.out.printf("Host: %s, Port: %d\n");
                    //pass
                }
        };

        new MockUp<Thread>() {
            @Mock void join(){}
            @Mock void run(){}
            @Mock void start(){}
        };
	}

	/**
	 * is called after all tests
	 * 
	 * @throws java.lang.Exception
	 */
	//@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// pass
	}

	/**
	 * is called before each test
	 * 
	 * @throws java.lang.Exception
	 */
	//@Before
	public void setUp() throws Exception 
    {
        base = new ConcreteBase(Module.TEST);
        logic = new Logic(base);
	}

	/**
	 * is called after each test
	 * 
	 * @throws java.lang.Exception
	 */
	//@After
	public void tearDown() throws Exception {
		// pass
	}

    //}}}


}

