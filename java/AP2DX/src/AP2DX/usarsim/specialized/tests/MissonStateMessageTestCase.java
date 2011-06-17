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
import AP2DX.usarsim.specialized.MissionStateMessage;

/**
 * @author Jasper Timmer
 *
 */
public class MissonStateMessageTestCase {

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
	 * Test method for {@link AP2DX.usarsim.specialized.MissionStateMessage#parseMessage()}.
	 */
	@Test
	public void testParseMessage() {
		MissionStateMessage msg = new MissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
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
	 * Test method for {@link AP2DX.usarsim.specialized.MissionStateMessage#MissionStateMessage(AP2DX.usarsim.UsarSimMessage)}.
	 */
	@Test
	public void testMissionStateMessage() {
		try {
			MissionStateMessage msg = new MissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
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
		MissionStateMessage msg = new MissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
		Deencapsulation.invoke(msg, "compileMessage");
		String s = Deencapsulation.getField(msg, "messageString");
		assertEquals("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}", s);
	}

	/**
	 * Test method for {@link AP2DX.usarsim.UsarSimMessage#getType()}.
	 */
	@Test
	public void testGetType() {
		MissionStateMessage msg = new MissionStateMessage("MISSTA {Time 395.38} {Name CameraPanTilt} {Link 1} {Value 0.0000} {Torque -20.00} {Link 2} {Value 0.0000} {Torque -20.00}");
		Message.MessageType type = msg.getType();
		assertEquals(Message.MessageType.USAR_MISSIONSTATE, type);
	}

}
