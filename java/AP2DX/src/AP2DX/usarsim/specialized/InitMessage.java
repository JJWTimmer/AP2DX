/**
 * 
 */
package AP2DX.usarsim.specialized;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AP2DX.Message;
import AP2DX.usarsim.UsarSimMessage;
import AP2DX.usarsim.UsarSimMessage.UsarMessageField;

/**
 * The message that will be used to put a robot in the world of USARSim.
 * @author Maarten de Waard
 */
public final class InitMessage extends UsarSimMessage {

    public InitMessage()
    {
        super(Message.MessageType.USAR_INIT);
    }


    /** The class of the robot we want to spawn. Default is USARBot.P2DX */
    @UsarMessageField(name = "ClassName")
        private String className = "USARBot.P2DX";

    /** The name of the robot in the server. Default: `Awesome P2DX'*/
    @UsarMessageField(name = "Name")
        private String name = "Awesome P2DX";

    /** The spawn location of the robot. Default: {0.76, 2.3, 1.8} 
     *  TODO: Make this not hardcoded
     */
    @UsarMessageField(name = "Location")
        private String location = "0.76,2.3,1.8";

    /** The rotation of the robot. Array with three values, respectively: Roll, Pitch, Yaw*/
    @UsarMessageField(name = "Rotation")
        private String rotation = "0,0,0";


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
    public  void setLocation(String locationIn)
    {
        this.location = locationIn;
    }

    /** @param rotationIn The starting rotation (Roll Pitch Yaw) */
    public  void setRotation(String rotationIn)
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
    public  String getLocation()
    {
        return location;
    }

    /** @return rotation The starting rotation (Roll Pitch Yaw) */
    public  String getRotation()
    {
        return rotation;
    }
}
