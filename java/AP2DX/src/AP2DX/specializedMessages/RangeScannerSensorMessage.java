package AP2DX.specializedMessages;

import AP2DX.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


/** Specialized message for sensor data; range scanner. 
*
* In the config file one can set a value "Resolution". The manual (page 81) says the following:
* Resolution = The scan resolution, the step length of rotating from start
* direction to the end direction. The unit is integer. 65535
* means 360 degrees. 
*
* 65535 is the maximum value of an unsigned short integer.
*
* The default version seems to be 800, but .
*
* USERSIM DATA:
* SEN {Time 395.38} {Type RangeScanner} {Name Scanner1} {Resolution 0.0174} {FOV 3.1415} {Range 2.1924,2.1961,2.1961,2.1964,2.2010,2.2031,2.2042,2.2107,2.2165,2.2196,2.2300,2     .2375,2.2456,2.2519,2.2614,2.2730,2.2809,2.2926,2.3073,2.3196,2.3340,2.3498,2.3682,2.3838,2.4021,2.4207,2.4418,2.4625,2.4869,2.5099,2.5354,2.5593,2.5882,2.6187,2.6492,2.677     6,1.4046,1.3873,1.3544,1.3256,1.2973,1.2717,1.2460,1.2234,1.1989,1.1908,1.1702,1.1508,1.1305,1.1130,1.0971,1.0818,1.0656,1.0523,1.0370,1.0244,1.0131,1.0016,0.9890,0.9799,0.     9689,0.9493,0.9408,0.9325,0.9235,0.9167,0.9095,0.9014,0.8948,0.8886,0.8824,0.8774,0.8728,0.8673,0.8622,0.8587,0.8550,0.8512,0.8387,0.8430,0.9502,1.0554,1.1908,1.4281,1.6740     ,1.8226,1.8269,1.8251,1.8257,1.8237,1.8212,1.8244,1.8229,1.8252,1.8260,1.8288,1.8340,1.8350,1.8424,1.8441,1.8506,1.8562,1.8653,1.8715,1.8786,1.8894,1.8983,1.9062,1.9193,1.9     272,1.9407,1.9523,1.9663,1.9828,1.9963,2.0142,2.0307,2.0497,2.0655,2.0853,2.1070,2.1273,2.1546,2.1783,2.2044,2.2274,2.2578,2.2877,2.3175,2.1972,2.2519,2.3132,2.3789 
* ,2.4443,2.5219,2.5809,2.5806,2.6128,2.6474,2.6805,2.7160,2.7522,1.6030,1.5885,1.6500,1.6929,1.7377,1.7815,1.8345,1.8881,1.9420,2.0062,2.0728,2.1427,2.2505,2.3325,2.3928,4.7137,4.9139,4.9524,4.7819,4.6289,4.4862,4.3490,4.2288,4.1090,4.0015,4.3742,4.3846,4.7680,4.7551,4.7384,4.7317,4.7179,4.7090,4.7003,4.6950,4.6979,4.6888,4.6932,4.6792}
*
* @author Maarten Inja
*/
public class RangeScannerSensorMessage extends SpecializedMessage 
{

    /** The scan resolution, the step length of rotating from start
    * direction to the end direction. The unit is integer. 65535
    * means 360 degrees.*/
    private double resolution;
    /** The scan field of view as an integer. 65535 means 360
    * degrees. */
    private double fov;
    private double[] dataArray;

    /** Creates a specialized message from a standard AP2DXMessage.
    * This constructor could be used to clone an AP2DXMessage. */
    public RangeScannerSensorMessage(AP2DXMessage message)
    {
        super(message);
    }

    public RangeScannerSensorMessage(AP2DXMessage message, Module sourceId, Module destinationId)
    {
        super(message, sourceId, destinationId);
    }

	public RangeScannerSensorMessage(Module sourceId, Module destinationId)
	{
		super(Message.MessageType.AP2DX_SENSOR_RANGESCANNER, sourceId, destinationId);
	}

    public void specializedParseMessage()
    {
        //resolution = Double.parseDouble(values.get("resolution").toString());
        //fov = Double.parseDouble(values.get("fov").toString());

		try 
        { 
            // this is, if I'm correct, also in the values map, which 
            // can normally be used to extract the variables, were it not
            // for arrays ...
            JSONObject jsonObject = new JSONObject(messageString);
            JSONArray jsonArray = jsonObject.getJSONArray("dataArray");
            dataArray = new double[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i ++)
                dataArray[i] = jsonArray.getDouble(i);
            //time = jsonObject.getDouble("time");
            resolution = jsonObject.getDouble("resolution");
            fov = jsonObject.getDouble("fov");
        }
        catch (JSONException e)
        {
            System.out.println("Error in AP2DX.specializedMessages.SonarSensorMessage.specializedParseMessage()... things went south!");
            e.printStackTrace();
        }
    }

    // setters and getters {{{
   
    public void setFov(double value)
    {
        this.fov = value;
        values.put("fov", value);
    }     

    public double getFov()
    {
        return fov;
    }

    public void setResolution(double value)
    {
        this.resolution = value;
        values.put("resolution", value);
    } 

    public double getResolution()
    {
        return resolution;
    }

    public void setDataArray(double[] value)
    {
        dataArray = value;
        values.put("dataArray", value);
    }

    public double[] getDataArray()
    {
        return dataArray;
    }

    // }}}


}
