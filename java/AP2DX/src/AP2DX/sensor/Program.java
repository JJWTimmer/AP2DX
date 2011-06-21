package AP2DX.sensor;

import java.util.ArrayList;

import AP2DX.AP2DXBase;
import AP2DX.AP2DXMessage;
import AP2DX.Message;
import AP2DX.Module;
import AP2DX.specializedMessages.*;

// Imports for the interface
import javax.swing.*;
import java.awt.Graphics;
import java.lang.Thread;
public class Program extends AP2DXBase {
    /** The array with the range data from the Sonar Sensor */
    private double[] rangeArray;
    private Drawer drawer;

    /**
     * Entrypoint of mapper
     */
    public static void main (String[] args){
        new Program();
    }


    /**
     * constructor
     */
    public Program() 
    {
        super(Module.SENSOR); // explicitly calls base constructor
        System.out.println(" Running Sensor... ");

    }

    /**
     * In this override, the program sets up a new JFrame. Then it creates a ``Drawer'', our class that draws lines of the sensor data.
     * It does enough to add the drawer to the frame, sets the size of the frame to 400x400 and draws a standard line
     * This standard line is just to see that it works
     * TODO: If everything works, stop drawing this line.
     */
    @Override
        protected void doOverride() {
            JFrame frame = new JFrame("Sonar");
            drawer = new Drawer();
            frame.getContentPane().add(drawer);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500,500);
            frame.setVisible(true);
            double[] lines = {2.0, 3.0, 4.0, 5.0, 5.0, 4.0, 3.0, 2.0};
            drawer.paintLine(lines); 
        }

    @Override
        public ArrayList<AP2DXMessage> componentLogic(Message msg) 
        {
            ArrayList<AP2DXMessage> messageList = new ArrayList<AP2DXMessage>();

            switch (msg.getMsgType())
            {
                case AP2DX_SENSOR_SONAR:
                    //This is deprecated, I think.
                    //SonarSensorMessage sonarSensorMessage = new SonarSensorMessage((AP2DXMessage)msg, IAM, Module.REFLEX);
                    //messageList.add(sonarSensorMessage);
                    //break;

                    // Do some awesome things with the sonar message we just got!
                    drawer.paintLine(((SonarSensorMessage) msg).getRangeArray());
                default:
                    System.out.println("Unexpected message type in ap2dx.sensor.Program: " + msg.getMsgType());
            }
            return messageList;
        }
    /**
     * The drawer is the class that draws the line of the sonar to some frames. This comes in handy with debugging
     * because we can see the places where our robot detects some objects. We can also use the calculations done in this 
     * class for making the map, later.
     * @author Maarten de Waard
     */
    private class Drawer extends JPanel
    {
        public double[] ranges;
        private int[] thetas = {0, 40, 60, 80, 100, 120, 140, 180};
        private static final double MAX_SONAR_VALUE = 5.0;

// I don't think we need this
//        public Drawer()
//        {
//        }

        /** 
         * Repaints the lines in the frame according to the lines in the argument
         * @param lines An array of 8 doubles, being the distances as measured
         * by the Sonar Sensors
         */
        public void paintLine(double[] lines)
        {
            this.ranges = lines;
            repaint();
        }

        /** Theta is toRadians(corner of the sensor) */
        public void paint(Graphics g) {
            int x1;
            int x2;
            int y1;
            int y2;
            for (int i=0; i < ranges.length; i++)
            {   
                double line = (ranges[i] / MAX_SONAR_VALUE) * getSize().height ;
                double theta = Math.toRadians(thetas[i]);
                System.out.printf("Theta = %f\n", theta);
                x1 = getSize().width / 2;
                y1 = getSize().height - 10;
                y2 = (int) (getSize().height - 10 - (Math.sin(theta) * line));
                System.out.printf("sinus: %f, Cosinus: %f", Math.sin(theta), Math.cos(theta));
                System.out.printf("y2 for line %d is %d\n", i, y2);
                x2 = (int)(.5 * getSize().width + (10 + ( Math.cos(theta) * line)));

                System.out.printf("x2 for line %d is %d\n", i, x2);
                g.drawLine(x1, y1, x2, y2);
            }
            repaint();
        }
    }
}
