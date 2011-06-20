/**
 * 
 */
package AP2DX.usarsim.specialized.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import AP2DX.usarsim.specialized.SonarData;
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
			
			List<SonarData> list = msg.getData();
			
			SonarData d1 = (SonarData) list.get(0);
			SonarData d2 = (SonarData) list.get(1);
			SonarData d3 = (SonarData) list.get(2);
			SonarData d4 = (SonarData) list.get(3);	
			SonarData d5 = (SonarData) list.get(4);
			SonarData d6 = (SonarData) list.get(5);
			SonarData d7 = (SonarData) list.get(6);
			SonarData d8 = (SonarData) list.get(7);	
			
			assertEquals("F1", d1.getName());
			assertEquals(4.5604, d1.getRange(), 1e-4);
			assertEquals("F2", d2.getName());
			assertEquals(2.4576, d2.getRange(), 1e-4);
			assertEquals("F3", d3.getName());
			assertEquals(1.8846, d3.getRange(), 1e-4);
			assertEquals("F4", d4.getName());
			assertEquals(1.6543, d4.getRange(), 1e-4);
			assertEquals("F5", d5.getName());
			assertEquals(0.6501, d5.getRange(), 1e-4);
			assertEquals("F6", d6.getName());
			assertEquals(0.7861, d6.getRange(), 1e-4);
			assertEquals("F7", d7.getName());
			assertEquals(1.1415, d7.getRange(), 1e-4);
			assertEquals("F8", d8.getName());
			assertEquals(2.0603, d8.getRange(), 1e-4);
			
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail("Constructor or parseMessage fail: " + ex.getMessage());
		}
	}

	/**
	 * Test method for {@link AP2DX.usarsim.specialized.SonarMessage#SonarMessage(java.lang.String)}.
	 */
	@Test
	public void testSonarMessageString() {
		try {
			SonarMessage msg = new SonarMessage("SEN {Time 395.6055} {Type Sonar} {Name F1 Range 4.5604} {Name F2 Range 2.4576} {Name F3 Range 1.8846} {Name F4 Range 1.6543} {Name F5 Range 0.6501} {Name F6 Range 0.7861} {Name F7 Range 1.1415} {Name F8 Range 2.0603}");
			
			assertEquals("SEN {Time 395.6055} {Type Sonar} {Name F1 Range 4.5604} {Name F2 Range 2.4576} {Name F3 Range 1.8846} {Name F4 Range 1.6543} {Name F5 Range 0.6501} {Name F6 Range 0.7861} {Name F7 Range 1.1415} {Name F8 Range 2.0603}", msg.toString());
		}
		catch (Exception ex) {
			fail("Constructor or toString fail: " + ex.getMessage());
		}
	}

	/**
	 * Test method for {@link AP2DX.usarsim.specialized.SonarMessage#SonarMessage(AP2DX.usarsim.UsarSimMessage)}.
	 */
	@Test
	public void testSonarMessageUsarSimMessage() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link AP2DX.usarsim.UsarSimMessage#compileMessage()}.
	 */
	@Test
	public void testCompileMessage() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link AP2DX.usarsim.UsarSimMessage#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
