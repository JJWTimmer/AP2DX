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
    private final String pathToSlam = "../../../../c/dpslam/slam";

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
            p = r.exec(pathToSlam  + " -p stdinOrWhatever"); 
           
            // creating a new thread to continiously write all kinds of sexeh stuff to the 
            // c program 
            SensorWriter sensorWriter = new SensorWriter(p);
            new Thread(sensorWriter).start();
       
            // a buffered reader to read the output of the program 
            sensorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
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
            while((l = sensorReader.readLine()) != null)
                processOutputString(l);
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
    }

    public void addXYTheta(double x, double y, double theta)
    {
        toSendXYTheta = String.format("Odometry %f %f %f", x, y, theta);
    }

    public void addLaser(double[] rangeArray)
    {
        toSendLaser = "Laser " + rangeArray.length + " ";
        for (double d : rangeArray)
            toSendLaser += d + " ";
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
        }

        public void run() 
        {
            try
            {
                while (true)
                {
                    if (lastSendXYTheta.equals(toSendXYTheta) ||
                        lastSendLaser.equals(toSendLaser))
                        continue;
                    out.write(toSendXYTheta + "\n" + toSendLaser + "\n");
                    out.flush();
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

