package AP2DX.sensor.test;

import java.util.ArrayList;

import AP2DX.*;
import AP2DX.test.*;
import AP2DX.sensor.*;
import AP2DX.specializedMessages.MotorMessage;
import AP2DX.specializedMessages.OdometrySensorMessage;

import org.junit.*;

import static org.junit.Assert.*;

public class SensorTestCase extends AbstractTestCase
{
    @Before
        public void before() throws Exception
        {
            test = new Program();
        }


    /**
	 * default test method for the Program() class in the package.
	 */
    @Override
	@Test public void program() {
		Program myTest = new Program();
        System.out.println("After new program");
		assertNotNull(myTest);
        System.out.println("after assertNotNull()");
    }
    
    @Test public void componentLogic()
    {
    	ArrayList<AP2DXMessage> list = 
    		test.componentLogic(new OdometrySensorMessage(Module.REFLEX, Module.MOTOR));
        assertNotNull(list);
    }

   
    
    /**
     * default test method for the classMain()
     */ 
    @Override
    @Test public void classMain()
    {   
        Program myTest = new Program();
        myTest.main(new String[0]);
    }
}
