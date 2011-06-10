/**
 * 
 */
package AP2DX;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Baseclass for AP2DX components
 * 
 * Can read config file Can write logfile Can listen at socket Can send to
 * socket[s]
 * 
 * @author Jasper Timmer
 * @author Wadie Assal
 * 
 */

public abstract class AP2DXBase {

    public static Logger logger;
    /** The configuration read from file and set by readConfig. */
    protected Map config;
    private Logic BaseLogic;

    //threadcounter for outgoing connections
    private AtomicInteger threadCounter = new AtomicInteger();

    private ArrayList<ConnectionHandler> inConnections = new ArrayList<ConnectionHandler>();
    private ArrayList<ConnectionHandler> outConnections = new ArrayList<ConnectionHandler>();

    private ArrayBlockingQueue<Message> receiveQueue = new ArrayBlockingQueue<Message>(
            128);

    /**
     * constructor
     */
    public AP2DXBase() {
        config = readConfig();

        initLogger();

        logger.info("Package: " + this.getClass().getPackage().getName());
        logger.info("Directory: " + System.getProperty("user.dir"));

        BaseLogic = new Logic(this);

        BaseLogic.start();

        /**
         * Establish all the connections.
         */
        // Incoming connections
        ServerSocket svr = null;
        try {
            svr = new ServerSocket(Integer.parseInt(config.get("listen_port")
                    .toString()));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Listener conn_c = new Listener(svr, this);
        Thread t = new Thread(conn_c);
        t.start();

        // Outgoing connections
        List out = (List) config.get("outgoing");

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
                System.exit(1);
            }
            threadCounter.incrementAndGet();
        }

        while (this.threadCounter.get() != 0) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        this.doOverride();
        
        try {
            BaseLogic.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void doOverride() {
        // override this for extra behaviour in concrete class.
    }

    /**
     * starts a logger, defines a format
     */
    private void initLogger() {
        try {
            boolean append = true;
            FileHandler fh = new FileHandler(config.get("logfile").toString(),
                    append);

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
     */
    protected Map readConfig() {
        // get text contents of file config.json (hard-coded filename)
        String jsonText = getContents(new File(this.getClass().getPackage()
                .getName()
                + ".json"));
        Object jsonObj = null;
        Map jsonMap = null;
        JSONParser parser = new JSONParser();

        ContainerFactory containerFactory = new ContainerFactory() {
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };

        try {
            jsonObj = parser.parse(jsonText);

            jsonMap = (Map) jsonObj;

            Iterator iter = jsonMap.entrySet().iterator();

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
     * @return
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

    private boolean addSendConnection(String ipaddress, int port, Module module) {
        try {
            Socket sock = new Socket(ipaddress, port);
            ConnectionHandler conn = new ConnectionHandler(this, sock, module);
            outConnections.add(conn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public abstract ArrayList<Message> componentLogic(Message msg);

    public ArrayBlockingQueue<Message> getReceiveQueue() {
        return receiveQueue;
    }

    /**
     * Class to create outgoing connection
     * 
     * @author jjwt
     * 
     */
    private class Connector implements Runnable {
        private String address;
        private int port;
        private Module module;
        private AP2DXBase base;

        public Connector(AP2DXBase base, String address, int port, Module module) {
            this.address = address;
            this.port = port;
            this.module = module;
            this.base = base;
        }

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
                    // keep trying!
                }
                catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    System.exit(1);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            ConnectionHandler connHandler = null;
            try {
                connHandler = new ConnectionHandler(base, conn, this.module);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            outConnections.add(connHandler);
            
            this.base.threadCounter.decrementAndGet();
        }
    }

    /**
     * Class for listening at specified port and accepting connections.
     * 
     * @author jjwt
     * 
     */
    private class Listener implements Runnable {
        private ServerSocket server;
        private AP2DXBase base;

        public Listener(ServerSocket svr, AP2DXBase base) {
            this.server = svr;
            this.base = base;
        }

        @Override
        public void run() {
            while (true) {
                ConnectionHandler connHandler = null;
                Socket conn = null;
                try {
                    conn = this.server.accept();
                    connHandler = new ConnectionHandler(base, conn);

                    connHandler.start();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
