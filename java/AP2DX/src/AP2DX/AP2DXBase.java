/**
 * 
 */
package AP2DX;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.*;
import java.util.Map;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * @author Jasper Timmer
 * @author Wadie Assal
 * 
 * Baseclass for AP2DX components
 * 
 * Can read config file
 * Can write logfile
 * Can listen at socket
 * Can send to socket[s]
 */

public abstract class AP2DXBase {
	
	public static Logger logger;
    /** The configuration read from file and set by readConfig.*/
    protected Map config;
    private Logic BaseLogic;
    
    private ArrayList<ConnectionHandler> inConnections = new ArrayList<ConnectionHandler>();
    private ArrayList<ConnectionHandler> outConnections = new ArrayList<ConnectionHandler>();

    public ArrayBlockingQueue<Message> receiveQueue = new ArrayBlockingQueue<Message>(128);
    
	/**
	 * constructor
	 */
	public AP2DXBase(Module module) {
		config = readConfig();
        
		setConfig();
	    
        initLogger();
	    
	    logger.info("Package: " + this.getClass().getPackage().getName());
	    logger.info("Directory: " + System.getProperty("user.dir"));
	    
	    BaseLogic = new Logic(this);
	    
	    BaseLogic.run();
	    
	    
	    /**
	     * Establish all the connections.
	     */
	    //Outgoing connections
	    	    
	    
	    //Incoming connections
	}
	
	/**
	 * starts a logger, defines a format
	 */
	private void initLogger() {
		try {
		      boolean append = true;
		      FileHandler fh = new FileHandler(config.get("logfile").toString(), append);
		      
		      //set logfile format:
		      //date millis methodname level message
		      fh.setFormatter(new Formatter() {
		          public String format(LogRecord rec) {
		             StringBuffer buf = new StringBuffer(1000);
		             buf.append(new java.util.Date());
		             buf.append(' ');
		             buf.append(rec.getMillis());
		             buf.append(' ');
		             buf.append(rec.getSourceMethodName());
		             buf.append(' ');
		             buf.append(rec.getLevel());
		             buf.append(' ');
		             buf.append(formatMessage(rec));
		             buf.append('\n');
		             return buf.toString();
		             }
		           });

		      logger = Logger.getLogger(this.getClass().getPackage().getName());
		      logger.addHandler(fh);
		    }
		    catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	
	/**
	 * read configfile. The config file is the package name with ".json" appended. 
	 */
	protected Map readConfig() {
		  //get text contents of file config.json (hard-coded filename)
		  String jsonText = getContents(new File(this.getClass().getPackage().getName() + ".json"));
		  Object jsonObj = null;
		  Map jsonMap = null;
		  JSONParser parser = new JSONParser();
		                
		  try {
			  jsonObj = parser.parse(jsonText);
			  jsonMap = (Map)jsonObj;
		  }
		  catch(ParseException pe){
		    System.out.println("position: " + pe.getPosition());
		    System.out.println(pe);
		  }
		  
		  return jsonMap;
	}

    /** A method that reads the configurations and sets variables to the correct 
    value.*/
    protected abstract void setConfig();

	/**
	  * Fetch the entire contents of a text file, and return it in a String.
	  * This style of implementation does not throw Exceptions to the caller.
	  *
	  * @param aFile is a file which already exists and can be read.
	  */
	static protected String getContents(File aFile) {
	    //...checks on aFile are elided
	    StringBuilder contents = new StringBuilder();
	    
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    return contents.toString();
	  }
	
	/**
	 * Compares old connection's module with new connection's module.
	 * Replaces connection object for the same module.
	 * @author Wadie Assal
	 * @param connection
	 * @return
	 */
	public boolean UpdateIncomingConnection  (ConnectionHandler connection) {
		ConnectionHandler conn;
		for (int i = 0; i < inConnections.size(); i++) {
			conn = inConnections.get(i);
			if (conn.moduleID.compareTo(connection.moduleID) == 0) {
				inConnections.set(i, connection);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Compares old connection's module with new connection's module.
	 * Replaces connection object for the same module.
	 * @author Wadie Assal
	 * @param connection
	 * @return
	 */
	public boolean UpdateOutgoingConnection (ConnectionHandler connection) {
		ConnectionHandler conn;
		for (int i = 0; i < outConnections.size(); i++) {
			conn = outConnections.get(i);
			if (conn.moduleID.compareTo(connection.moduleID) == 0) {
				outConnections.set(i, connection);
				return true;
			}
		}
		return false;
	}
	
	
	public ConnectionHandler getSendConnection (Module module) throws Exception{
		ConnectionHandler conn;
		for (int i = 0; i < outConnections.size(); i++) {
			conn = outConnections.get(i);
			if (conn.moduleID.compareTo(module) == 0) {
				return conn;
			}
		}
		return null;
	}
	
	private boolean AddSendConnection (String ipaddress, int port, Module module){
		try {
			Socket sock = new Socket(ipaddress, port);
			ConnectionHandler conn = new ConnectionHandler(this, sock, module);
			outConnections.add(conn);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public abstract ArrayList<Message> ComponentLogic(Message msg);
}
