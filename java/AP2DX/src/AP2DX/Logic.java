package AP2DX;


import java.lang.Thread;
import java.util.concurrent.ArrayBlockingQueue;

/** 
 * This class defines the Logic engine of each module. It calls the ``processLogic'' module of a class, to think of a reaction.
 */
public Class Logic extends Thread
{
    /** The base class, in which we can run the componentLogic */
    private AP2DXBase base;

    /**
     *  The constructor. For now it only takes moduleID, but i'm sure it will need some more things later
     */
    public Logic(AP2DXBase base)
    {
        this.base = base;
    }
    
    /**
     * Where the magic happens. This will be on all the time, calling componentLogic to find a reaction
     */
    public void run()
    {   
        //Get the message from the base
        Message message = base.receiveQueue.take();
        // run componentLogic with the message
        ArrayList<Message> actions = componentLogic(message);
        if (!actions.isEmpty())
        {
            for (action:actions)
            {
                ConnectionHandler connection = base.getSendConnection(action.destinationModuleId);
                connection.sendMessage(action)
            }

        }
    }
}
