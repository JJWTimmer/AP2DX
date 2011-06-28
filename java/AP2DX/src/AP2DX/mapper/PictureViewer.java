//package AP2DX.mapper;


// Imports for the interface
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.lang.Thread;
import java.io.File;
import java.io.IOException;

/**
 * @author Maarten de Waard
 */
public class PictureViewer extends JPanel
{
    private Image backgroundImage;
    public int imageCounter = 0;
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Map");
        PictureViewer viewer = new PictureViewer();
        frame.setBackground(java.awt.Color.white);

        frame.getContentPane().add(viewer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        String pathToTheImage = "../../../../c/dpslam/hmap";
        String image;
        Image bgImage;
        while(true)
        {
            try 
            {
                image = String.format("%s, %2d, %s", pathToTheImage + viewer.imageCounter + ".png");
                System.out.println("String image = " + image);
                bgImage = ImageIO.read(new File(image));
                viewer.imageCounter++;
                viewer.updateImage(bgImage);
            } catch (IOException ex) 
            {
                System.out.println("No image found, will sleep for some time");
                try{Thread.sleep(1000);}catch(Exception e){}
            }
        }
    }

    public PictureViewer()
    {
        super();
    }

    public PictureViewer(Image backgroundImage) {
        super();
        this.backgroundImage = backgroundImage;
    }

    public void updateImage(Image backgroundImage)
    {
        this.backgroundImage = backgroundImage;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);        
        if(backgroundImage != null)
            g.drawImage(this.backgroundImage, 0, 0, null);
    }

}
