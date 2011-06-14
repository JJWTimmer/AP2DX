package AP2DX.test;

// The package
import AP2DX.*;
// The junit framework stuff, for testing
import junit.framework.*;
// Arraylist, needed for our concrete class
import java.util.ArrayList;
// Mocking classes
import mockit.*;
import mockit.Mockit;

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
        AP2DXBase base = new ConcreteBase();

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
        public ArrayList<Message> componentLogic(Message msg)
        {
            return new ArrayList<Message>();
        }
    }

}

