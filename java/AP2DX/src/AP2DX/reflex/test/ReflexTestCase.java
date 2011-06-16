package AP2DX.reflex.test;

import AP2DX.*;
import AP2DX.test.*;
import AP2DX.reflex.*;

import org.junit.*;
import static org.junit.Assert.*;

public class ReflexTestCase extends AbstractTestCase
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
