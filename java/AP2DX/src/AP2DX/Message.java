package AP2DX;

/** In the future we can imagine specialized messages that extend this 
* one, such as "SensordataMessage", or something. 
* @author Maartens */
public class Message
{

    private Module sourceModuleId;
    private Module destinationModuleId;
    private String messageString;

    public Message(String in)
    {
        this.messageString = in; 
        // parseMessage();
    }

    public void parseMessage()
    {
        // TODO: do parsing stuff
    }

    public Module getSourceModuleId()
    {
        return sourceModuleId;
    } 

    public Module getDesitinationModuleId()
    {
        return destinationModuleId;
    }
}


