package AP2DX;

/*
 * Enum type for the different distributed modules of the this software.
 * 
 * @author Wadie Assal
 * @author Maarten Inja
 */
public enum Module 
{
    COORDINATOR(true), ABSTRACTMOTOR, REFLEX, SENSOR, 
    PLANNER, MAPPER, USARSIM, TEST, ADMIN, UNDEFINED;

    /** Checks if this module can accept JSON message. Default is false. */
    public final boolean canAcceptJsonMessages;

    private Module()
    {
        this(false);
    }

    private Module(boolean canAcceptJsonMessages)
    {
        this.canAcceptJsonMessages = canAcceptJsonMessages;
    }
}
