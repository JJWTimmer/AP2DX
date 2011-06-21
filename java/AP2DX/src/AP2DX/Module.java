package AP2DX;

/*
 * Enum type for the different distributed modules of the this software.
 * 
 * @author Wadie Assal
 * @author Maarten Inja
 */
public enum Module 
{
    COORDINATOR(true), MOTOR, REFLEX, SENSOR, 
    PLANNER, MAPPER, USARSIM, TEST, ADMIN, UNDEFINED; // you define constants all captial with underscores between words, thus USAR_SIM, not USARSIM.

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
