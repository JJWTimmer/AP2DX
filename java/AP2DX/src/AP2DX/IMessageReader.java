/**
 * 
 */
package AP2DX;

import java.io.IOException;

/**
 * @author Jasper Timmer
 *
 */
public interface IMessageReader {
	public Message readMessage() throws IOException, InstantiationException, IllegalAccessException;
}
