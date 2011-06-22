package AP2DX.test;

import java.util.concurrent.TimeUnit;

import junit.framework.*;
import org.junit.Test;

import AP2DX.*;
import static org.junit.Assert.*;


public class AP2DXMessageTestCase {

	@Test
	public void testConstuctor() {
		try {
		AP2DXMessage msg = 
			new AP2DXMessage(Message.MessageType.AP2DX_COORDINATOR_DRIVE, Module.MOTOR, Module.COORDINATOR);
		assertNotNull("AP2DXMessageTestCase.testConstructor", msg);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSetDelay()  {
		try {
		AP2DXMessage msg = 
			new AP2DXMessage(Message.MessageType.AP2DX_COORDINATOR_DRIVE, Module.MOTOR, Module.COORDINATOR);
		
		long currenttime = System.currentTimeMillis();
		long millisec = msg.setDelay(200);
		assertTrue(currenttime < millisec);
		assertTrue(millisec > 0);	
		
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetDelay() {
		try {
		AP2DXMessage msg = 
			new AP2DXMessage(Message.MessageType.AP2DX_COORDINATOR_DRIVE, Module.MOTOR, Module.COORDINATOR);
		
		assertTrue(msg.getDelay(TimeUnit.SECONDS)== 0);
		
		msg.setDelay(200);

		assertTrue(msg.getDelay(TimeUnit.SECONDS) > 0);
		
		} catch (Exception e) {
			fail();
		}		
	}
	
}
