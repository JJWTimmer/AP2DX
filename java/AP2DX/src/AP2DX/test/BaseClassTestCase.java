package AP2DX.test;

// The package
import AP2DX.*;
// The junit framework stuff, for testing
import junit.framework.*;
// Arraylist, needed for our concrete class
import java.util.ArrayList;
// Mocking classes
import mockit.*;

public class BaseClassTestCase extends TestCase
{
    public void testConstructor()
    {
        System.out.println("Hello world?");
    /*    new MockUp<Logic>()
        {
            @Mock
            void $init(AP2DXBase base){
                System.out.println("Starting mocked logic");
                }
            @Mock
            public void start()
            {
                System.out.println("logic start");
            }
            @Mock
            public final void join()
            {
                System.out.println("Logic join");
            }
        };*/
        //Mockit.setUpMock(Logic.class, MockedLogic.class);
        Mockit.setUpMock(Thread.class, MockedThread.class);
        AP2DXBase base = new ConcreteBase(Module.TEST);

    }
    
    private static class MockedThread
    {
        /*public MockedThread(AP2DXBase base)
        {
            System.out.println("Starting mocked logic");
        }*/

        public void run()
        { 
            System.out.println("Thread run");
        }
        public void start()
        {
            System.out.println("logic start");
        }
        public final void join()
        {
            System.out.println("Logic join");
        }
    }

    private class ConcreteBase extends AP2DXBase
    {
        public ConcreteBase(Module myModule) {
			super(myModule);
			// TODO Auto-generated constructor stub
		}

		public ArrayList<Message> componentLogic(Message msg)
        {
            return new ArrayList<Message>();
        }
    }
///*<<<<<<< HEAD
//
///**
// * Test class for our abstract baselcass
// * 
// * @author Jasper Timmer
// * @author Maarten Inja
// * 
// */
//public class BaseClassTestCase extends junit.framework.TestCase {
//
//
//    public void testietest()
//    {
//        new ConcreteBase();
//    }
//
//	/**
//	 * is called before all the tests
//	 * 
//	 * @throws java.lang.Exception
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		// pass
//	}
//
//	/**
//	 * is called after all tests
//	 * 
//	 * @throws java.lang.Exception
//	 */
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		// pass
//	}
//
//	/**
//	 * is called before each test
//	 * 
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		// pass
//	}
//
//	/**
//	 * is called after each test
//	 * 
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//		// pass
//	}
//
//	/**
//	 * Test method for {@link AP2DX.AP2DXBase#AP2DXBase()}.
//	 */
//	@Test
//	@Ignore("freezes test")
//	public void testAP2DXBase() {
//		/**
//		 * empty socket mockup
//		 */
//		new MockUp<Socket>() {
//			@Mock
//			void $init(String host, int port) {
//				//pass
//			}
//		};
//		
//		/**
//		 * empty thread mockup
//		 */
//		new MockUp<Thread>() {
//			@Mock
//			void join() {
//				//pass
//			}
//		};
//		AP2DXBase test;
//		try {
//			test = new ConcreteBase();
//			assertNotNull(test);
//		} catch (Exception ex) {
//			fail("CTOR TEST FAIL: " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * Test method for {@link AP2DX.AP2DXBase#readConfig()}. Make sure we change
//	 * this method if we change the config file (AP2DX.test.json).
//	 */
//	@Test
//	@Ignore("not finished")
//	public void testReadConfig() {
//		new MockUp<Thread>() {
//			@Mock
//			void join() {
//				//pass
//			}
//		};
//		
//		// readConfig is called in the ctor of the base class
//		ConcreteBase test = new ConcreteBase();
//		Map config = test.getConfig();
//
//		compareString("logfile", "log/coordinator.log", config);
//		compareString("sim_address", "146.50.51.9", config);
//		compareInt("sim_port", 3000, config);
//		compareInt("coordinator_listen_port", 3000, config);
//	}
//
//	/** Help method for testReadConfig. */
//	private void compareString(String jsonValue, String compareToValue,
//			Map config) {
//		String value = (config.get(jsonValue)).toString();
//		if (!value.equals(compareToValue))
//			fail(String
//					.format("FAIL: testReadConfig, variable '%s' with value '%s' does not match actual value '%s'",
//							jsonValue, value, compareToValue));
//	}
//
//	/** Help method for testReadConfig. */
//	private void compareInt(String jsonValue, int compareToValue, Map config) {
//		int value = Integer.parseInt(config.get(jsonValue).toString());
//		if (value != compareToValue)
//			fail(String
//					.format("FAIL: testReadConfig, variable '%s' with value '%s' does not match actual value '%s'",
//							jsonValue, value, compareToValue));
//	}
//	
//	public class ConcreteBase extends AP2DXBase {
//
//		
//		public Map getConfig() {
//			return this.config;
//		}
//		
//		@Override
//		public ArrayList<Message> componentLogic(Message msg) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
//=======
//>>>>>>> 718a05a1d584cf5e86062f5e8cc3b70ba867c714
}

