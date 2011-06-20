/**
 * 
 */
package AP2DX.usarsim.specialized.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import AP2DX.Message;
import AP2DX.usarsim.specialized.StateMessage;

/**
 * @author jjwt
 *
 */
public class StateMessageTestCase {

	/**
	 * Test method for {@link AP2DX.usarsim.specialized.StateMessage#parseMessage()}.
	 */
	@Test
	public void testParseMessage() {
		try {
			StateMessage msg = new StateMessage("STA {Type GroundVehicle} {Time 395.38} {FrontSteer 0.0000} {RearSteer 0.0000} {LightToggle False} {LightIntensity 0} {Battery 1200} {View -1}");
			
			assertEquals("GroundVehicle", msg.getType());
			assertEquals(395.38, msg.getTime(), 1e-4);
			assertEquals(0, msg.getFrontSteer(), 1e-4);
			assertEquals(0, msg.getRearSteer(), 1e-4);
			assertEquals(false, msg.isLightToggle());
			assertEquals(0, msg.getLightIntensity());
			assertEquals(1200, msg.getBattery());
			assertEquals(-1, msg.getView(), 1e-4);
		}
		catch (Exception ex) {
			fail("Constructor not working: " + ex.getMessage());
		}
		
	}

	/**
	 * Test method for {@link AP2DX.usarsim.specialized.StateMessage#StateMessage(AP2DX.usarsim.UsarSimMessage)}.
	 */
	@Test
	public void testStateMessage() {
		try {
			Message msg = new StateMessage("STA {Type GroundVehicle} {Time 395.38} {FrontSteer 0.0000} {RearSteer 0.0000} {LightToggle False} {LightIntensity 0} {Battery 1200} {View -1}");
			assertNotNull(msg);
		}
		catch (Exception ex) {
			fail("Constructor not working: " + ex.getMessage());
		}
	}

}
