/**
 * 
 */
package AP2DX;

import java.io.*;
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

public abstract class AP2DXBase extends Thread{
	
	public static Logger logger;
    /** The configuration read from file and set by readConfig.*/
    protected Map config;
    
    static private ArrayList<Connection> connections = new ArrayList<Connection>();

	/**
	 * constructor
	 */
	public AP2DXBase() {
		config = readConfig();
        setConfig(); 
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
	    
	    logger.info("Package: " + this.getClass().getPackage().getName());
	    logger.info("Directory: " + System.getProperty("user.dir"));
	    
	    /**
	     * Establish all the connections.
	     */
	    //CODE
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
	
	
	public boolean UpdateConnection (Connection connection) {
		Connection conn;
		for (int i = 0; i < connections.size(); i++) {
			conn = connections.get(i);
			if (conn.GetModule().compareTo(connection.GetModule()) == 0) {
				connections.set(i, connection);
				return true;
			}
		}
		return false;
	}
	
	public Connection GetConnection (Module module) throws Exception{
		Connection conn;
		for (int i = 0; i < connections.size(); i++) {
			conn = connections.get(i);
			if (conn.GetModule().compareTo(module) == 0) {
				return conn;
			}
		}
		return null;
	}
	
	private boolean AddConnection (String ipaddress, int port, Module module){
		try {
			Connection conn = new Connection(ipaddress, port, module);
			connections.add(conn);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
