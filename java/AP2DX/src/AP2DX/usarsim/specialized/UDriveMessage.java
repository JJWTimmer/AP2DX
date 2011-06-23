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
 * The message that will be sent to the robot to make it move.
 * @author Maarten de Waard
 */
public final class UDriveMessage extends UsarSimMessage {

    public UDriveMessage()
    {
        super(Message.MessageType.USAR_DRIVE);
    }

    /** Spin speed of the left side wheel. Has a value between -100 and 100, 
     * corresponds to AP2DX' minimum and maximum spin speed. If `Normalized' is False, 
     * this will be the spin speed in Radians per Second
     */
	@UsarMessageField(name = "Left")
	private double left;

    /** Same as left, but for the right wheel */
    @UsarMessageField(name = "Right")
    private double right;
    
    /** If this is set to true (standard is false) the value of left and right should be between -100 and 100.
     * If it is set to False, left and right controll the wheel spin speed in radians per second.
     */
    @UsarMessageField(name = "Normalized")
    private boolean normalized;

    /** True to turn the lights on, false to turn them off */
    @UsarMessageField(name = "Light")
    private boolean light;

    /** Will flip the robot to stand wheels-down on the floor */
    @UsarMessageField(name = "Flip")
    private boolean flip;
    
    /** Setter for the left wheel speed */
    public  void setLeft(double leftIn)
    {
        this.left = leftIn;
    }

    /** Setter for the right wheel speed */
    public  void setRight(double rightIn)
    {
        this.right = rightIn;
    }

    /** Setter for normalized */
    public  void setNormalized(boolean norm)
    {
        this.normalized = norm;
    }

    /** Setter for light */
    public  void setLight(boolean lightIn)
    {
        this.light = lightIn;
    }

    /** Setter for flip */
    public  void setFlip(boolean flipIn)
    {
        // This flip is flippin'!
        this.flip = flipIn;
    }

    /** Getter for right wheel speed */
    public  double getRight()
    {
        return this.right;
    }

    /** Getter for left wheel speed */
    public  double getLeft()
    {
        return this.left;
    }

    /** Getter for normalized*/
    public  boolean getNormalized()
    {
        return this.normalized;
    }

    /** Getter for light*/
    public  boolean getLight()
    {
        return this.light;
    }

    /** Getter for normalized*/
    public  boolean getFlip()
    {
        return this.flip;
    }
}
