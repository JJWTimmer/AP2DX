/**
 * 
 */
package test;

import AP2DX.AP2DXBase;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;;

/**
 * @author Jasper
 *
 */
public class AP2DXBaseTest {
	@Test
	public void testNewInstance() {
		AP2DXBaseConcrete test = new AP2DXBaseConcrete();
		assertNotNull(test);
	}
	
	private class AP2DXBaseConcrete extends AP2DXBase  {
		//empty! :)
	}

}