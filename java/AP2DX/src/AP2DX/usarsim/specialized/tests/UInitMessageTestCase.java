/**
 * 
 */
package AP2DX.usarsim.specialized.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import AP2DX.usarsim.specialized.ArrayFloatData;
import AP2DX.usarsim.specialized.UInitMessage;
import AP2DX.usarsim.specialized.MissionStateLink;
import AP2DX.usarsim.specialized.UMissionStateMessage;

/**
 * @author Jasper Timmer
 *
 */
public class UInitMessageTestCase {

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
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testCompileMessage() throws Exception {
		UInitMessage msg = new UInitMessage();
		msg.setClassName("USARBot.P2DX");
		msg.setName("AP2DX");
		
		List<ArrayFloatData> loc = new ArrayList<ArrayFloatData>();
		List<ArrayFloatData> rot = new ArrayList<ArrayFloatData>();
		
		ArrayFloatData locData = new ArrayFloatData("Location", "1,2,3");
		ArrayFloatData rotData = new ArrayFloatData("Rotation", "4,5,6");
		
		loc.add(locData);
		rot.add(rotData);
				
		msg.setLocation(loc);
		msg.setRotation(rot);
		
		String s = msg.toString();
		System.out.println(s);
		assertEquals("INIT {ClassName USARBot.P2DX} {Location 4.5,1.9,1.8} {Rotation 0,0,0} {Name R1}", s);
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testGetType() throws Exception {
		UInitMessage msg = new UInitMessage();
		Message.MessageType type = msg.getMsgType();
		assertEquals(Message.MessageType.USAR_INIT, type);
	}

}
