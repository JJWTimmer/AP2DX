/**
 * 
 */
package test;

import AP2DX.AP2DXBase;

import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * @author Jasper
 *
 */
public class AP2DXBaseTest {
	
	@Test
	public void testVeryBasicTest() {
		assertEquals(0, 0);
		
	}
	
	@Test
	public void testNewInstance() {
		//AP2DXBaseConcrete test = new AP2DXBaseConcrete();
		//assertNotNull(test);
		assertEquals(0, 0);
	}
	
	private class AP2DXBaseConcrete extends AP2DXBase  {
		//empty! :)
	}

}