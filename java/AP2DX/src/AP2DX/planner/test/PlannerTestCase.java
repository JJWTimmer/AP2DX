package AP2DX.planner.test;

import AP2DX.*;
import AP2DX.test.*;
import AP2DX.planner.*;

import org.junit.*;
import static org.junit.Assert.*;

public class PlannerTestCase extends AbstractTestCase
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

}
