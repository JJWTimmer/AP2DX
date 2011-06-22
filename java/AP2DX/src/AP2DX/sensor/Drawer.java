package AP2DX.sensor;


// Imports for the interface
import javax.swing.*;
import java.awt.Graphics;
import java.lang.Thread;

/**
 * The drawer is the class that draws the line of the sonar to some frames. This comes in handy with debugging
 * because we can see the places where our robot detects some objects. We can also use the calculations done in this 
 * class for making the map, later.
 * 
 * TODO: the sonar now all originate from the same spot, in "reality" however,
 * the origin is slightly different for each different measurement of the data.
 * We should fix that!!
 *
 * @author Maarten de Waard
 * @author Maarten Inja
 */
public class Drawer extends JPanel
{
    public double[] sonarRanges = new double[0];
    public double[] rangeScannerRanges = new double[0];
    private int[] sonarThetas = {0, 40, 60, 80, 100, 120, 140, 180};
    private static final double MAX_SONAR_VALUE = 5.0;
    // VV might be the same as MAX_SONAR_VALUE, always?!
    private static final double MAX_RANGE_SCANNER_VALUE = 5.0; 

    // TODO create a getter and setter for this so range scanner messages
    // can set the value itself 
    private double rangeScannerFov = 3.1415; 
    //private double rangeScannerResolution = 0.01745;
    private double rangeScannerResolution = 0.0237992424;

    /** 
     * Repaints the lines in the frame according to the lines in the argument
     * @param lines An array of 8 doubles, being the distances as measured
     * by the Sonar Sensors
     */
    public void paintSonarLines(double[] sonarData)
    {
        this.sonarRanges = sonarData;
        repaint();
    }

    public void paintSonarLines(Graphics g)
    {
        java.awt.Color previousColor = g.getColor();
        g.setColor(java.awt.Color.red);
    
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i=0; i < sonarRanges.length; i++)
        {   
            double line = (sonarRanges[i] / MAX_SONAR_VALUE) * getSize().height ;
            double theta = Math.toRadians(sonarThetas[i]);
            //System.out.printf("Theta = %f\n", theta);
            x1 = getSize().width / 2;
            y1 = getSize().height - 10;
            y2 = (int) (getSize().height - 10 - (Math.sin(theta) * line));
            //System.out.printf("sinus: %f, Cosinus: %f", Math.sin(theta), Math.cos(theta));
            //System.out.printf("y2 for line %d is %d\n", i, y2);
            x2 = (int)(.5 * getSize().width + (10 + ( Math.cos(theta) * line)));

            //System.out.printf("x2 for line %d is %d\n", i, x2);
            g.drawLine(x1, y1, x2, y2);
        }
        g.setColor(previousColor);
    }

    public void paintRangeScannerLines(double[] rangeScannerData)
    {
        this.rangeScannerRanges = rangeScannerData; 
        repaint();
    }

    /** Paints the actual lines of whatever is in the array of doubles: 
    * rangeScannerRanges. This isn't tested at all.... :S but it looks pretty?*/
    public void paintRangeScannerLines(Graphics g)
    {
        java.awt.Color previousColor = g.getColor();    
        g.setColor(java.awt.Color.blue); 
    
        int x1;
        int x2;
        int y1;
        int y2;

        for (int i = 0; i < rangeScannerRanges.length; i ++)
        {
            double line = (rangeScannerRanges[i] / MAX_RANGE_SCANNER_VALUE) * getSize().height;
            double theta = i * rangeScannerResolution;
            //System.out.printf("Theta = %f, for line %d\n", theta, i);
             
            x1 = getSize().width / 2;
            y1 = getSize().height - 10;
            y2 = (int) (getSize().height - 10 - (Math.sin(theta) * line));
            //System.out.printf("sinus: %f, Cosinus: %f", Math.sin(theta), Math.cos(theta));
            //System.out.printf("y2 for line %d is %d\n", i, y2);
            x2 = (int)(.5 * getSize().width + (10 + ( Math.cos(theta) * line)));

            //System.out.printf("x2 for line %d is %d\n", i, x2);
            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(previousColor);
    }

    /** Theta is toRadians(corner of the sensor) */
    public void paint(Graphics g) 
    {
        g.fillRect (0, 0, getWidth(), getHeight());
        paintSonarLines(g);
        paintRangeScannerLines(g);
        repaint();
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Sonar");
        frame.setBackground(java.awt.Color.white);
        Drawer drawer = new Drawer();
        frame.getContentPane().add(drawer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        double[] sonarDataSample = {2.0, 3.0, 4.0, 5.0, 5.0, 4.0, 3.0, 2.0};
        // test data to draw range scanner data
        double[] rangeScannerDataSample = {2.1924,2.1961,2.1961,2.1964,2.2010,2.2031,2.2042,2.2107,2.2165,2.2196,2.2300,2.2375,2.2456,2.2519,2.2614,2.2730,2.2809,2.2926,2.3073,2.3196,2.3340,2.3498,2.3682,2.3838,2.4021,2.4207,2.4418,2.4625,2.4869,2.5099,2.5354,2.5593,2.5882,2.6187,2.6492,2.6776,1.4046,1.3873,1.3544,1.3256,1.2973,1.2717,1.2460,1.2234,1.1989,1.1908,1.1702,1.1508,1.1305,1.1130,1.0971,1.0818,1.0656,1.0523,1.0370,1.0244,1.0131,1.0016,0.9890,0.9799,0.9689,0.9493,0.9408,0.9325,0.9235,0.9167,0.9095,0.9014,0.8948,0.8886,0.8824,0.8774,0.8728,0.8673,0.8622,0.8587,0.8550,0.8512,0.8387,0.8430,0.9502,1.0554,1.1908,1.4281,1.6740,1.8226,1.8269,1.8251,1.8257,1.8237,1.8212,1.8244,1.8229,1.8252,1.8260,1.8288,1.8340,1.8350,1.8424,1.8441,1.8506,1.8562,1.8653,1.8715,1.8786,1.8894,1.8983,1.9062,1.9193,1.9272,1.9407,1.9523,1.9663,1.9828,1.9963,2.0142,2.0307,2.0497,2.0655,2.0853,2.1070,2.1273,2.1546,2.1783,2.2044,2.2274,2.2578,2.2877,2.3175,2.1972,2.2519,2.3132,2.3789};

        drawer.paintSonarLines(sonarDataSample); 
        drawer.paintRangeScannerLines(rangeScannerDataSample);
    }

    // setters and getters {{{

    public double getRangeScannerFov()
    {
        return rangeScannerFov;
    }

    public void setRangeScannerFov(double value)
    {
        rangeScannerFov = value;
    } 

    public double getRangeScannerResolution()
    {
        return rangeScannerResolution;
    }

    public void setRangeScannerResolution(double value)
    {
        rangeScannerResolution = value;
    }

    // }}}
}
