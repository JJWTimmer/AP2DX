package AP2DX.mapper;

import java.io.*;
import java.util.*;

/** This class interfaces with the c program "DP-SLAM". See: http://www.cs.duke.edu/~parr/dpslam/ .
* We've changed the source of DP-SLAM to not read the sensor data from file but rather from stdin. 
* We read the output stream of the c process for the robots location and orientation and print our 
* sensor data to the input stream of the c process.
*
* What we want to do in the future is pair up odometry and laser sensordata.
* We assume right now that these always arrive together, but if they do not 
* we still send the last received xytheta data and the last received laser data.
* So if we miss one of these data once, the data we send won't be syncd any longer
*
* The x, y, theta and precision are parsed from the output of the program. Before
* using the getters for these variables, you might want to be sure they are
* initialized?
*
* DP-SLAM will write hmapXY.png and lmapZQ.png to some directory (which ?) and will
* overwrite previous data without warning. 
*
* @author Maarten Inja
* */
public class Slammer implements Runnable
{

    private Process p; 
    private SensorWriter sensorWriter;
    private BufferedReader sensorReader;
    private String pathToSlam = "/home/mpdw/uva/ap2dx/c/dpslam/slam"; // TODO: put in config file or something
    protected BufferedWriter outToFile;

    /** Location and orientation last read from file. */
    private double x, y, theta;
       
    /** A number DP-SLAM output we assume to be how sure we are of the parsed 
    * position. */ 
    private double precision;

    /** The strings to write to the c program. */
    private String toSendXYTheta, toSendLaser;


    public Slammer()
    {
        Runtime r = Runtime.getRuntime();

        try
        {
            // startin' DP SLAM
            System.out.println("Starting: " + pathToSlam + " -p stdinOrWhatever");
            p = r.exec(pathToSlam + " -p stdinOrWhatever"); 
           
            // creating a new thread to continiously write all kinds of sexeh stuff to the 
            // c program 
            SensorWriter sensorWriter = new SensorWriter(p);
            new Thread(sensorWriter).start();
       
            // a buffered reader to read the output of the program 
            sensorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            FileWriter fstream = new FileWriter("mapper_out.txt");
            outToFile = new BufferedWriter(fstream);
        }
        catch (Exception e)
        {
            System.out.println("Something went terribly wrong in AP2DX.mapper.Slammer!");
            System.out.println("Error message: " + e.getMessage());
            System.out.println("Stacktrace: ");
            e.printStackTrace();
            System.out.println("Slammer stopped slammin'");
        }
    }

    public void run()
    {
        try
        {
            // readin' output of the program
            String l = "whatever";
            System.out.println("Started reading");
            for (int i =0;i<10;i++)
            {
            while((l = sensorReader.readLine()) != null)
                processOutputString(l);
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\nSOMETHING WENT TERRIBLY WRONG\n\n\n\n\n\n\n\n\n\n");
        }
        catch (Exception e)
        {
            System.out.println("Something went terribly wrong in AP2DX.mapper.Slammer.run()!");
            System.out.println("Error message: " + e.getMessage());
            System.out.println("Stacktrace: ");
            e.printStackTrace();
            System.out.println("Slammer stopped slammin'");
        }
    }

    /** Processes a random output string to update the x, y and theta of the
    * robots position. */
    private void processOutputString(String line)
    {
        // TODO: set x, y, theta and precision from output
        System.out.println(line);
    }

    public void addXYTheta(double x, double y, double theta)
    {
        toSendXYTheta = String.format("Odometry %f %f %f", x, y, theta);
    }

    public void addLaser(double[] rangeArray)
    {
        System.out.println("RangeArray: " + rangeArray.length);
        if(rangeArray.length == 181)
        {
            String tempToSendLaser = "Laser " + rangeArray.length + " ";
            for (double d : rangeArray)
                tempToSendLaser += d + " ";
            toSendLaser = tempToSendLaser;
        }
    }

    // getters {{{
   
    /** The x position as parsed from DP-SLAM output. */ 
    public double getX()
    {
        return x;
    }

    /** The y position as parsed from DP-SLAM output. */ 
    public double getY()
    {
        return y;
    }

    /** The orientation as parsed from DP-SLAM output. */ 
    public double getTheta()
    {
        return theta;
    }

    /** The precision as parsed from DP-SLAM output. */ 
    public double getPrecision()
    {
        return precision;
    }

    // }}} 

    /** A class to be able to continiously send our sensor data. */ // {{{
    private class SensorWriter implements Runnable
    {
        private BufferedWriter out;

        /** Whatever was send last shouldn't be send again. */
        private String lastSendXYTheta, lastSendLaser; 

        public SensorWriter(Process p) throws Exception
        {
            out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );
            //Just some random data which hopefully will only be used once!
            lastSendXYTheta = toSendXYTheta = "Odometry -2.424026 4.800517 2.590948";
            lastSendLaser = toSendLaser = "Laser 181 0.642000 0.644000 0.645000 0.644000 0.643000 0.651000 0.650000 0.649000 0.648000 0.653000 0.653000 0.652000 0.651000 0.659000 0.659000 0.658000 0.658000 0.667000 0.666000 0.666000 0.674000 0.674000 0.683000 0.683000 0.692000 0.692000 0.702000 0.709000 0.717000 0.717000 0.726000 0.734000 0.742000 0.751000 0.751000 0.771000 0.770000 0.779000 0.797000 0.806000 0.815000 0.823000 0.832000 0.840000 0.858000 0.871000 0.880000 0.896000 0.917000 0.932000 0.949000 0.967000 0.991000 1.008000 1.034000 1.050000 1.077000 1.103000 1.130000 1.155000 1.190000 1.217000 1.252000 1.293000 1.327000 1.379000 1.421000 1.466000 1.484000 1.469000 1.454000 1.447000 1.438000 1.430000 1.441000 1.522000 1.598000 1.682000 1.788000 1.922000 2.077000 3.150000 3.133000 3.380000 3.755000 4.216000 4.803001 5.581001 6.778000 7.438000 8.183001 8.183001 8.183001 8.183001 8.183001 8.183001 8.183001 8.183000 8.183000 8.183001 8.183001 6.864000 6.396001 6.401001 6.418001 6.111000 6.091000 5.286001 4.937000 4.945000 4.670000 4.259000 3.899000 3.712000 3.543000 3.391000 3.250000 3.129000 3.005000 2.903000 2.807000 2.711000 2.631000 2.549000 2.472000 2.402000 2.335000 2.276000 2.216000 2.162000 2.109000 2.064000 2.018000 1.972000 1.937000 1.903000 1.859000 1.824000 1.799000 1.764000 1.741000 1.713000 1.688000 1.660000 1.634000 1.617000 1.591000 1.574000 1.549000 1.531000 1.515000 1.498000 1.481000 1.464000 1.448000 1.439000 1.431000 1.414000 1.406000 1.388000 1.380000 1.372000 1.354000 1.346000 1.338000 1.329000 1.330000 1.321000 1.313000 1.313000 1.305000 1.297000 1.288000 1.289000 1.289000 1.282000 1.283000 1.276000 1.268000 1.271000 1.264000";
        }

        public void run() 
        {
            try
            {
                while (true)
                {
                    if ((lastSendXYTheta.equals(toSendXYTheta) &&
                        lastSendLaser.equals(toSendLaser)) ||
                        out == null)
                    {
                        //System.out.println("WRONG INFOOOO");
                        continue;
                    }
                    out.write(toSendXYTheta + "x"+ "\n" );
                    outToFile.write(toSendXYTheta + "\n");
                    out.flush();
                    out.write(toSendLaser + "x" + "\n");
                    outToFile.write(toSendLaser + "\n");
                    out.flush();
                    System.out.println("Printin' some lines!");
                    lastSendXYTheta = toSendXYTheta;
                    lastSendLaser = toSendLaser;
                }
            }
            catch (Exception e)
            {
                System.out.println("Something went terribly wrong in AP2DX.mapper.Slammer.Sensorwriter.run()!");
                System.out.println("Error message: " + e.getMessage());
                System.out.println("Stacktrace: ");
                e.printStackTrace();
                try{outToFile.close();}catch(Exception ex){System.out.println("COULDNT OUTPUT THE FILE");}
            }
        }
    } // }}}

    /** reads a file as an array list of strings NOT USED ANYMORE {{{
    public ArrayList<String> readLines(String file) throws Exception
    {
        ArrayList<String> lines = new ArrayList<String> ();

        // Open the file that is the first 
        // command line parameter
        FileInputStream fstream = new FileInputStream(file);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   
            lines.add(strLine);
        in.close();
        return lines;
    }
    // }}} */

}

