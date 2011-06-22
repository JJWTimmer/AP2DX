/**
 * 
 */
package AP2DX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import AP2DX.specializedMessages.HelloMessage;

/**
 * Baseclass for AP2DX components<br>
 * <br>
 * Can read config file<br>
 * Can write logfile<br>
 * Can listen at socket<br>
 * Can send to socket[s]<br>
 *
 * @author Jasper Timmer
 * @author Wadie Assal
 * 
 */

public abstract class AP2DXBase {

	/** Log object */
	public static Logger logger;
	
	/** The configuration read from file and set by readConfig */
	protected Map config;
	
	/** The read message queue logic that calls the concrete logic */
	private Logic baseLogic;

	/** Threadcounter for outgoing connections */
	private AtomicInteger threadCounter = new AtomicInteger();

	/** This is module... */
	protected final Module IAM;
	
	// TODO check if connection closes
	/** list containing all incoming connections that are active */
	private ArrayList<ConnectionHandler> inConnections = new ArrayList<ConnectionHandler>();
	/** list containing all outgoing connections that are active */
	private ArrayList<ConnectionHandler> outConnections = new ArrayList<ConnectionHandler>();

	/** all received AP2DX.Messages will be stored here */
	private DelayQueue<AP2DXMessage> receiveQueue = new DelayQueue<AP2DXMessage>();
    

	/**
	 * Where everything happens:
	 * <ul>
	 * 	<li>Read the config file</li>
	 *  <li>Init the logger</li>
	 *  <li>Start the logic thread</li>
	 *  <li>Read the config file</li>
	 *  <li>Establish incoming connections</li>
	 *  <li>Start outgoing connections</li>
	 *  <li>And if defined, execute extra logic</li>
	 * </ul>
	 * 
	 * @author Jasper Timmer
	 *
	 */
	public AP2DXBase(Module myModule) {
		IAM = myModule;

        System.out.println("Starting AP2DXBase");

		config = readConfig();

		initLogger();

		logger.info("Package: " + this.getClass().getPackage().getName());
		logger.info("Directory: " + System.getProperty("user.dir"));

		baseLogic = new Logic(this);

		baseLogic.start();

		
		// Establish all the connections.
		
		// Incoming connections
		ServerSocket svr = null;
		try {
			svr = new ServerSocket(Integer.parseInt(config.get("listen_port").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.exit(1);
		}
        System.out.println("After ServerSocket");
		// start new thread that accepts all connections and creates connectionhandlers
		Listener conn_c = new Listener(svr, this);
		Thread t = new Thread(conn_c);
		t.start();

		
		// Outgoing connections
		// Get the list of outgoing connections from the configfile
		List out = (List) config.get("outgoing");

		// for every connection, start a connector thread
		for (int i = 0; i < out.size(); i++) {
			int port = Integer.parseInt(((JSONObject) out.get(i)).get("port")
					.toString());
			String address = ((JSONObject) out.get(i)).get("address")
					.toString();
			Module module = Module.valueOf(((JSONObject) out.get(i)).get(
					"component").toString());

			Connector connector = new Connector(this, address, port, module);
			Thread t1 = new Thread(connector);
			
			try {
				t1.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//System.exit(1);
			}
			threadCounter.incrementAndGet();
		}

		// wait for all the connectors to connect
		while (this.threadCounter.get() != 0) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Start connectionchecker to remove dead connections
		// and restart outgoing connections
		ConnectionChecker CC = new ConnectionChecker(this);
		Thread t2 = new Thread(CC);
		try {
			t2.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.exit(1);
		}

        System.out.println("Before override");
		// run the extra logic
		this.doOverride();
		
		try {
			baseLogic.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("End of construc tor!");
	}

	/**
	 * If needed, extra logic for the constructor of the concrete class can be added here.
	 * This is nessecary because the component constructor calls super() but will
	 * never return. It waits for the BaseLogic to return.
	 */
	protected void doOverride() {
		// override this for extra behaviour in concrete class.
	}

	/**
	 * Starts a logger and defines the log format. Gets the logfile location
	 * from the configfile variable "logfile".
	 */
	private void initLogger() {
		try {
			boolean append = true;
			FileHandler fh = new FileHandler(config.get("logfile").toString(), append);

			// set logfile format:
			// date millis methodname level message
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read configfile. The config file is the package name with ".json"
	 * appended.
	 * 
	 * @return jsonMap A raw key/value map with the values from the jsonfile.
	 */
	protected Map readConfig() {
		String jsonText = getContents(new File(
				this.getClass()
					.getPackage()
					.getName() + ".json"
			));
		
		Object jsonObj = null;
		Map jsonMap = null;
		
		JSONParser parser = new JSONParser();

		try {
			jsonObj = parser.parse(jsonText);

			jsonMap = (Map) jsonObj;

		} catch (ParseException pe) {
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}

		return jsonMap;
	}

	/**
	 * Fetch the entire contents of a text file, and return it in a String. This
	 * style of implementation does not throw Exceptions to the caller.
	 * 
	 * @param aFile
	 *            is a file which already exists and can be read.
	 */
	static protected String getContents(File aFile) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}

	/**
	 * Compares old connection's module with new connection's module. Replaces
	 * connection object for the same module.
	 * 
	 * @author Wadie Assal
	 * @param connection
	 * @return
	 */
	public boolean UpdateIncomingConnection(ConnectionHandler connection) {
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
	 * Compares old connection's module with new connection's module. Replaces
	 * connection object for the same module.
	 * 
	 * @author Wadie Assal
	 * @param connection
	 * @return bool update succeeded
	 */
	public boolean UpdateOutgoingConnection(ConnectionHandler connection) {
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
	
	/**
	 * return the ConnectionHandler for the requested module.
	 * 
	 * @author Wadie Assal
	 * @param module The module to get connection for
	 * @return conn The requested connection object or null
	 */
	public ConnectionHandler getSendConnection(Module module) throws Exception {
		ConnectionHandler conn;
		for (int i = 0; i < outConnections.size(); i++) {
			conn = outConnections.get(i);
			if (conn.moduleID.compareTo(module) == 0) {
				return conn;
			}
		}
		return null;
	}

	/**
	 * Establishes a new connection and adds it to the outConnections list
	 * 
	 * @param ipaddress
	 * @param port
	 * @param destination Module it connects to
	 * @return true if operation succeeded false otherwise
	 */
	private boolean addSendConnection(String ipaddress, int port, Module destination) {
		try {
			Socket sock = new Socket(ipaddress, port);
			
			// not a usar sim connection, so first value is false
			ConnectionHandler conn = new ConnectionHandler(false, this, sock, IAM, destination);
			outConnections.add(conn);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


    public ArrayList<AP2DXMessage> componentLogicCheck(Message msg)
    {
        System.out.println("In componentLogicCheck");
        if (!msg.getMsgType().isAp2dxMessage && !IAM.canAcceptJsonMessages)
        {
            System.err.println("Unexpected message in " + IAM + ". Received a non-AP2DX message type and cannot accept JSON messages.");
            return null;
        }
        return componentLogic(msg);
    }

	/**
	 *  List with received messages
	 *  @param msg
	 *  @return
	 */
	public abstract ArrayList<AP2DXMessage> componentLogic(Message msg);

	public DelayQueue<AP2DXMessage> getReceiveQueue() {
		return receiveQueue;
	}

	/**
	 * Class to create outgoing connection
	 * 
	 * @author Jasper Timmer
	 * 
	 */
	private class Connector implements Runnable {
		
		/** Ipaddress of the connection */
		private String address;
		
		/** Port of the connection */
		private int port;
		
		/** The module to connect to */
		private Module module;
		
		/** Reference to the baseclass */
		private AP2DXBase base;

		/**
		 * Constructor
		 * Only sets variables
		 **/
		public Connector(AP2DXBase base, String address, int port, Module module) {
			this.address = address;
			this.port = port;
			this.module = module;
			this.base = base;
		}

		/**
		 * the body of the thread,
		 * tries until forever to connect to the given computer
		 */
		@Override
		public void run() {
			Socket conn = null;
			boolean success = false;

			while (!success) {
				try {
					conn = new Socket(this.address, this.port);
					success = true;
				}
				catch (ConnectException e2) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// keep trying!
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//System.exit(1);
				}
			}

			ConnectionHandler connHandler = null;
			
			try {
				connHandler = new ConnectionHandler(false, base, conn, IAM, this.module);
				
				HelloMessage message = new HelloMessage(IAM, this.module);
				connHandler.sendMessage(message);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// add the connectionhandler to the list of connections
			outConnections.add(connHandler);
			
			// decrement the runningthread counter
			this.base.threadCounter.decrementAndGet();
		}
	}

	/**
	 * This class is used to check the lists of incoming and outgoing connections,
	 * for dead links. If nessecary, try to reestablish connections.
	 * @author Jasper Timmer
	 *
	 */
	private class ConnectionChecker implements Runnable {

		private AP2DXBase base;
		
		public ConnectionChecker(AP2DXBase base) {
			this.base = base;
		}
		
		@Override
		public void run() {
			while (true) {
				//check if incoming connection has closed and remove from list
				for (ConnectionHandler conn : base.inConnections) {
					if (!conn.isAlive()) {
						base.inConnections.remove(conn);
					}
				}
				
				// check if outgoing connection has closed and attempt to reconnect
				for (ConnectionHandler conn : base.outConnections) {
					if (!conn.isAlive()) {
						int port = conn.getPort();
						String address = conn.getAddress();
						
						Module module = conn.getModule();

						Connector connector = new Connector(this.base, address, port, module);
						Thread t1 = new Thread(connector);
						
						try {
							base.threadCounter.incrementAndGet();
							t1.start();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//System.exit(1);
							
						}
					}
				}
			}
		}
	}
	
	/**
	 * Class for listening at specified port and accepting connections.
	 * 
	 * @author Jasper Timmer
	 * 
	 */
	private class Listener implements Runnable {
		/** Socket to listen on */
		private ServerSocket server;
		/** Reference to the base */
		private AP2DXBase base;

		/**
		 * Sets variables
		 * @param svr
		 * @param base
		 */
		public Listener(ServerSocket svr, AP2DXBase base) {
			this.server = svr;
			this.base = base;
		}

		/**
		 * Listen and accept connections forever. <br/>
		 * <br/>
		 * Timeout on new sockets is 250 millis
		 */
		@Override
		public void run() {
			while (true) {
                System.out.println("Started Listener");
				ConnectionHandler connHandler = null;
				Socket conn = null;
				try {
                    System.out.println("Waiting for connection");
					conn = this.server.accept();
                    System.out.println("Connection starting");
					
					// important: timeout on all connections is set here
					conn.setSoTimeout(250);
					
					AP2DXBase.logger.info(String.format("New connection with %s", conn.getRemoteSocketAddress()));
					
					connHandler = new ConnectionHandler(base, conn, IAM);

					connHandler.start();

                    System.out.printf("Adding connection %s to connections\n", connHandler.moduleID);
                    base.inConnections.add(connHandler);

				} catch (Exception e) {
					// something wend terribly wrong, terminate module.
					AP2DXBase.logger.severe(String.format("Error in connectionhandler: %s\n%s", conn.getRemoteSocketAddress(), e.getMessage()));
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
}
