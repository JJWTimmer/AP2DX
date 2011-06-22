package AP2DX.test;

import junit.framework.*;
import org.junit.Test;

import AP2DX.*;
import static org.junit.Assert.*;


public class AP2DXMessageTestCase {

	@Test
	public void testConstuctor() {
		AP2DXMessage msg = 
			new AP2DXMessage(Message.MessageType.AP2DX_COORDINATOR_DRIVE, Module.MOTOR, Module.COORDINATOR);
		assertNotNull("AP2DXMessageTestCase.testConstructor", msg);
	}
	
	@Test
	public void testClone() {
		AP2DXMessage msg = 
			new AP2DXMessage(Message.MessageType.AP2DX_COORDINATOR_DRIVE, Module.MOTOR, Module.COORDINATOR);
		Object clone = msg.clone();
		assertNotNull("AP2DXMessageTestCase.testClone", clone);
	}
	
	@Test
	public void testSetDelay()  {
		AP2DXMessage msg = 
			new AP2DXMessage(Message.MessageType.AP2DX_COORDINATOR_DRIVE, Module.MOTOR, Module.COORDINATOR);
		long currenttime = System.currentTimeMillis();
		long millisec = msg.setDelay(200);
		assertTrue(currenttime < millisec);
		assertTrue(millisec > 0);	
	}
	
}
