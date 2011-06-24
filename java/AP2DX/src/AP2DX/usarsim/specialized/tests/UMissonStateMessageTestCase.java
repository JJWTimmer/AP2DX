/**
 * 
 */
package AP2DX.usarsim.specialized.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mockit.Deencapsulation;
import mockit.Deencapsulation.*;

import AP2DX.Message;
import AP2DX.usarsim.UsarSimMessage;
import AP2DX.usarsim.specialized.MissionStateLink;
import AP2DX.usarsim.specialized.UMissionStateMessage;

/**
 * @author Jasper Timmer
 *
 */
public class UMissonStateMessageTestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link AP2DX.usarsim.specialized.UMissionStateMessage#parseMessage()}.
	 */
	@Test
	public void testParseMessage() {
		UMissionStateMessage msg = new UMissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
		assertEquals("CameraPanTilt", msg.getName());
		assertEquals(395.38, msg.getTime(),1e-4);
		assertEquals(2, msg.getLinks().size());
		List<MissionStateLink> links = msg.getLinks();
		
		MissionStateLink link1 = links.get(0);
		MissionStateLink link2 = links.get(1);
		
		assertEquals(1, link1.getLink());
		assertEquals(0.00, link1.getValue(),1e-4);
		assertEquals(-20.00, link1.getTorque(),1e-4);
		
		assertEquals(2, link2.getLink());
		assertEquals(0.00, link2.getValue(),1e-4);
		assertEquals(-20.00, link2.getTorque(),1e-4);
	}

	/**
	 * Test method for {@link AP2DX.usarsim.specialized.UMissionStateMessage#MissionStateMessage(AP2DX.usarsim.UsarSimMessage)}.
	 */
	@Test
	public void testMissionStateMessage() {
		try {
			UMissionStateMessage msg = new UMissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
			assertNotNull(msg);
		}
		catch (Exception ex) {
			fail("Constructor not working: " + ex.getMessage());
		}
	}

	/**
	 * Test method for {@link AP2DX.usarsim.UsarSimMessage#compileMessage()}.
	 */
	@Test
	public void testCompileMessage() {
		UMissionStateMessage msg = new UMissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
		Deencapsulation.invoke(msg, "compileMessage");
		String s = Deencapsulation.getField(msg, "messageString");
		assertEquals("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}", s);
	}

	/**
	 * Test method for {@link AP2DX.usarsim.UsarSimMessage#getType()}.
	 */
	@Test
	public void testGetType() {
		UMissionStateMessage msg = new UMissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
		Message.MessageType type = msg.getMsgType();
		assertTrue(Message.MessageType.USAR_MISSIONSTATE.equals(type));
	}

}
