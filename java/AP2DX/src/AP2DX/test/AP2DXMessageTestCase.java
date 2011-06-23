package AP2DX.test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import junit.framework.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;

import AP2DX.*;
import static org.junit.Assert.*;


/*
 * 
 * @author Wadie Assal
 */
public class AP2DXMessageTestCase {

	/*
	 * Construct AP2DXMessage with the different constructors.
	 * Checks if they don't have NULL values
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConstuctor() {
		
		/* Constructing a JSON string. */
		Map obj = new LinkedHashMap();
		obj.put("name","foo");
		obj.put("num",new Integer(100));
		obj.put("balance",new Double(1000.21));
		obj.put("is_vip",new Boolean(true));
		obj.put("nickname",null);
		String jsonText = JSONValue.toJSONString(obj);		
		
		/* First constructor. */
		try {
		AP2DXMessage msg = 
			new AP2DXMessage(Message.MessageType.AP2DX_COORDINATOR_DRIVE, Module.MOTOR, Module.COORDINATOR);
			
			assertNotNull("AP2DXMessageTestCase.testConstructor", msg);
		} catch (Exception e) {
			fail();
		}

		/* Second constructor. */	  
		try {
		AP2DXMessage msg2 = 
			new AP2DXMessage(jsonText, Module.MOTOR, Module.COORDINATOR);
			assertNotNull("AP2DXMessageTestCase.testConstructor", msg2);
		} catch(Exception e) {
			fail();
		}
		
		/* Third constructor. */
		try {
			AP2DXMessage msg3 = 
				new AP2DXMessage(jsonText, Module.MOTOR);
				assertNotNull("AP2DXMessageTestCase.testConstructor", msg3);
			} catch(Exception e) {
				fail();
		}
		
		/* Fourth constructor. */
		try {
			AP2DXMessage msg4 = 
				new AP2DXMessage(jsonText);
				assertNotNull("AP2DXMessageTestCase.testConstructor", msg4);
			} catch(Exception e) {
				fail();
		}			
	}
	
	/*
	 * Checks if the delay is not less then the current time and if there is
	 * a value.
	 */
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
	
	/*
	 * Tsets the function toClone and parse, compileMessage along the way.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testClone() {
		/* Constructing a JSON string. */
		Map obj = new LinkedHashMap();
		obj.put("name","foo");
		obj.put("num",new Integer(100));
		obj.put("balance",new Double(1000.21));
		obj.put("is_vip",new Boolean(true));
		obj.put("nickname",null);
		String jsonText = JSONValue.toJSONString(obj);	
		
		try {
			AP2DXMessage msg = new AP2DXMessage(jsonText);
			assertNotNull("AP2DXMessageTestCase.testConstructor", msg.clone());
		} catch(Exception e) {
				fail();
		}		
	}
	
	/*
	 * TODO Writing this damn thing.
	 */
	@Test	
	public void testCompareTo() {
		assertTrue(false);
	}
	
	/*
	 * Checks the return values before and after the return values are set
	 */
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
