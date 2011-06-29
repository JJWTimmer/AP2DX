import java.io.*;
import java.util.*;

public class SystemCallExample 
{

    public static void main(String[] args) 
    {    
        new SystemCallExample();
    }

    public SystemCallExample()
    {
        Runtime r = Runtime.getRuntime();

        try
        {
            Process p = r.exec("../slam -p stdinOrWhatever");
            String file = "../testLog.log";
            //String file = "SystemCallExample.java"; 
            SensorWriter sensorWriter = new SensorWriter(p, file);
            
            Thread writerThread = new Thread(sensorWriter);
            writerThread.start(); 
        

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // readin'
            String l = "a";
            while((l = reader.readLine()) != null)
                System.out.println(l);
        }
        catch (Exception e)
        {
            System.out.println("Something went terrible wrong, message: " + 
                e.getMessage());
            e.printStackTrace();
        }
    }

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

    private class SensorWriter implements Runnable
    {

        private ArrayList<String> lines = readLines("../testLog.log");
        private BufferedWriter out;

        public SensorWriter(Process p, String file) throws Exception
        {
            lines = readLines(file);
            out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );
        }

        public void run() 
        {
            System.out.println("Running!");
            try
            {
                for (String line : lines)
                {
                    out.write(line + "\n");
                    out.flush();
                }
            }
            catch (Exception e)
            {
                System.out.println("Error in snesorwriter.run " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}

