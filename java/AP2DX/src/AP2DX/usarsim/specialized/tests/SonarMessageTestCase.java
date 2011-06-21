/**
 * 
 */
package AP2DX.usarsim.specialized.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import AP2DX.usarsim.specialized.SonarMessage;

/**
 * @author Jasper Timmer
 *
 */
public class SonarMessageTestCase {

	/**
	 * Test method for {@link AP2DX.usarsim.specialized.SonarMessage#parseMessage()}.
	 */
	@Test
	public void testParseMessage() {
		try {
			SonarMessage msg = new SonarMessage("SEN {Time 395.6055} {Type Sonar} {Name F1 Range 4.5604} {Name F2 Range 2.4576} {Name F3 Range 1.8846} {Name F4 Range 1.6543} {Name F5 Range 0.6501} {Name F6 Range 0.7861} {Name F7 Range 1.1415} {Name F8 Range 2.0603}");
			msg.parseMessage();
			
			assertEquals(395.6055, msg.getTime(), 1e-4);
			assertEquals("Sonar", msg.getType());
			
			double[] list = msg.getData();
			
			assertEquals(4.5604, list[0], 1e-4);
			assertEquals(2.4576, list[1], 1e-4);
			assertEquals(1.8846, list[2], 1e-4);
			assertEquals(1.6543, list[3], 1e-4);
			assertEquals(0.6501, list[4], 1e-4);
			assertEquals(0.7861, list[5], 1e-4);
			assertEquals(1.1415, list[6], 1e-4);
			assertEquals(2.0603, list[7], 1e-4);
			
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail("Constructor or parseMessage fail: " + ex.getMessage());
		}
	}
}
