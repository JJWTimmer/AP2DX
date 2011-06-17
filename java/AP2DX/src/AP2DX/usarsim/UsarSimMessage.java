/**
 * 
 */
package AP2DX.usarsim;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Field;

import AP2DX.Message;
import AP2DX.Module;

/**
 * @author Jasper Timmer
 * 
 */
public class UsarSimMessage extends Message {
	/**
	 * The translation of messagetype in messages to our enum, for example:
	 * "STA" -> Message.MessageType.USAR_STATE
	 */
	public static final Map<String, Message.MessageType> messageTypeDict = createMap();

	/**
	 * Constructor for the messageTypeDict
	 * @return HashMap with the string to MessageType table
	 */
    private static Map<String, Message.MessageType> createMap() {
        Map<String, Message.MessageType> result = new HashMap<String, Message.MessageType>();
        
        result.put("STA", Message.MessageType.USAR_STATE);
        result.put("MISSTA", Message.MessageType.USAR_MISSIONSTATE);
        
        return Collections.unmodifiableMap(result);
    }


    /**
     * make a new UsarSimMessage
     * @param in
     */
	public UsarSimMessage(String in) {
		super(in, Module.UNDEFINED);
	}
	
	@Override
	public Message.MessageType getType() {
		String startPatternStr = "^[A-Z]+";
		Pattern startPattern = Pattern.compile(startPatternStr);
		Matcher startMatcher = startPattern.matcher(this.getMessageString());
	
		if (startMatcher.find()) {
			this.type = UsarSimMessage.messageTypeDict.get(startMatcher.group(0));
		}
		else{
			this.type = null;
		}
		return this.type;
	}


	@Override
	protected void parseMessage() throws Exception {
		throw new Exception("Not possible on this class, try casting to a specialized message type.");
	}
	
	
	/**
	 * This method uses annotated fields to build the output of the message.
	 * field.toString() is the value of the field like so: {name value}.
	 */
	@Override
	protected String compileMessage() {
		StringBuilder output = new StringBuilder();
		
		output.append(this.getType());
		
		for (Field field : this.getClass().getDeclaredFields()) {
		    if (field.isAnnotationPresent(UsarMessageField.class)) {
		    	output.append(String.format(" {%s %s}", field.getAnnotation(UsarMessageField.class).name(), field.toString()));
		    
		    } else if (field.isAnnotationPresent(UsarMessageIteratorField.class)) {
	    		Iterator it = List.class.cast(field).iterator();
	    		
	    		while (it.hasNext()) {
	    			Object entry = it.next();
	    			output.append(entry.toString());
	    		}
	    	}
		}
		
		return output.toString();
	}
	
	/**
	 * override getMessageString to execute compile before getting string.
	 * Best practice is to store this string in a local variable instead of
	 * repeatedly calling this function.
	 */
	@Override
	public String getMessageString() {
		return this.messageString;
	}
	
	/**
	 * Annotation to use on specialized usar sim message fields, that need to be compiled in the outputstring.
	 * 'name' is the Name of the field
	 * @author Jasper Timmer
	 *
	 */
	public @interface UsarMessageField {
		String name();
	};
	
	/**
	 * Declare this on a specialized message's field, to let the compiler know, that type is a list of values.
	 * It should be in the form of List<specialclass> ...; where specialclass.toString() returns the value to
	 * be appended to the output. Example: "{Link 1} {Value 23.3} {Torque -23.4}".
	 * 
	 * @author Jasper Timmer
	 *
	 */
	public @interface UsarMessageIteratorField {};
}
