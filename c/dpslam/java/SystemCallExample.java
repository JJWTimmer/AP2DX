import java.io.*;

public class SystemCallExample 
{

    public static void main(String[] args) 
    {    
        Runtime r = Runtime.getRuntime();

        try
        {
            Process p = r.exec("../slam -p loop5short.log");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String l = "a";
            while((l = reader.readLine()) != null)
                System.out.println(l);
        }
        catch (Exception e)
        {
            System.out.println("Something went terrible wrong.");
        }

    }
}

