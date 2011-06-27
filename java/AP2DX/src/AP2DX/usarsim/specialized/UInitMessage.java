/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import AP2DX.Message;
import AP2DX.usarsim.UsarSimMessage;

/**
 * The message that will be used to put a robot in the world of USARSim.
 * @author Maarten de Waard
 */
public final class UInitMessage extends UsarSimMessage {

    public UInitMessage()
    {
        super(Message.MessageType.USAR_INIT);
    }

    /** The class of the robot we want to spawn. Default is USARBot.P2DX */
    @UsarMessageField(name = "ClassName")
        private String className = "USARBot.P2DX";

    /** The spawn location of the robot. Default: {0.76, 2.3, 1.8} 
     *  TODO: Make this not hardcoded
     */
    @UsarMessageIteratorField
        private List<ArrayFloatData> location = Arrays.asList(new ArrayFloatData("Location", "4.5,1.9,1.8"));

    /** The rotation of the robot. Array with three values, respectively: Roll, Pitch, Yaw*/
    @UsarMessageIteratorField
        private List<ArrayFloatData> rotation = new ArrayList<ArrayFloatData>();

    /** The name of the robot in the server. Default: `Awesome P2DX'*/
    @UsarMessageField(name = "Name")
        private String name = "R1";
    
    
	/**
	 * @throws Exception 
	 * @see AP2DX.Message#parseMessage()
	 */
	@Override
	protected void parseMessage() throws Exception {
		throw new Exception("NEVER PARSE AN INIT MESSAGE, JUST BUILD IT.");
	}
    

    /** @param classNameIn What robot we want to use */
    public  void setClassName(String classNameIn)
    {
        this.className = classNameIn;
    }

    /** @param nameIn The robot name */
    public  void setName(String nameIn)
    {
        this.name = nameIn;
    }

    /** @param locationIn The start location */
    public  void setLocation(List<ArrayFloatData> locationIn)
    {
        this.location = locationIn;
    }

    /** @param rotationIn The starting rotation (Roll Pitch Yaw) */
    public  void setRotation(List<ArrayFloatData> rotationIn)
    {
        this.rotation = rotationIn;
    }

    /** @return className What robot we want to use */
    public  String getClassName()
    {
        return className;
    }

    /** @return name The robot name */
    public  String getName()
    {
        return name;
    }

    /** @return location The start location */
    public  List<ArrayFloatData> getLocation()
    {
        return location;
    }

    /** @return rotation The starting rotation (Roll Pitch Yaw) */
    public  List<ArrayFloatData> getRotation()
    {
        return rotation;
    }
}
