/*
 * part of HA Random artist
 * TO BE COMPLETED
 *
 * @author huub
 * @author kees
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.io.*;

public class Painting extends JPanel implements ActionListener {

    /*---- Randomness ----*/
    /**
     * you can change the variable SEED if you like the same program with the same
     * SEED will generate exactly the same sequence of pictures
     */
    final static long SEED = 37; // seed for the random number generator; any number works

    /** do not change the following two lines **/
    final static Random random = new Random(SEED); // random generator to be used by all classes
    int numberOfRegenerates = 0;

    /*---- Screenshots ----
    * do not change
    */
    char current = '0';
    String filename = "randomshot_"; // prefix

    /*---- Dinguses ----*/
    ArrayList<Dingus> shapes = new ArrayList<Dingus>();
    // ..

    public Painting() {
        setPreferredSize(new Dimension(800, 450)); // make panel 800 by 450 pixels.
        // ...
    }

    @Override
    protected void paintComponent(Graphics g) { // draw all your shapes
        super.paintComponent(g); // clears the panel

        // draw all shapes
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).draw(g);
        }

    }

    /**
     * reaction to button press
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Regenerate".equals(e.getActionCommand())) {
            regenerate();
            repaint();
        } else { // screenshot
            saveScreenshot(this, filename + current++); // ++ to show of compact code :-)
        }
    }

    void regenerate() {
        numberOfRegenerates++; // do not change

        // clear the shapes array;
        shapes.clear();

        //Number of shapes to generate
        int numberOfShapes = random.nextInt(20 - 10) + 10;
        // Boolean used to check if four unique shapes have been added to the array shapes
        boolean fourUniqueShapes = false;
        while (!fourUniqueShapes) {
            for (int i = 0; i < numberOfShapes; i++) {

                int shapeToAdd = random.nextInt(6);
                switch (shapeToAdd) {
                    case 0:
                        shapes.add(new CircleDingus(800, 450));
                        break;
                    case 1:
                        shapes.add(new TreeDingus(800, 450));
                        break;
                    case 2:
                        shapes.add(new SquareDingus(800, 450));
                        break;
                    case 3:
                        shapes.add(new HeartDingus(800, 450));
                        break;
                    case 4:
                        shapes.add(new TriangleDingus(800, 450));
                        break;
                    case 5:
                        shapes.add(new RectangleDingus(800, 450));
                        break;
                    case 6:
                        shapes.add(new OvalDingus(800, 450));
                        break;
            }

            if (shapes.stream().distinct().count() >= 4) {
                fourUniqueShapes = true;
            }
        }
    }

    /**
     * saves a screenshot of a Component on disk overides existing files
     *
     * @param component - Component to be saved
     * @param name      - filename of the screenshot, followed by a sequence number
     * 
     *                  do not change
     */
    void saveScreenshot(Component component, String name) {
        String randomInfo = "" + SEED + "+" + (numberOfRegenerates - 1); // minus 1 because the initial picture should
                                                                         // not count
        System.out.println(SwingUtilities.isEventDispatchThread());
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        // call the Component's paint method, using
        // the Graphics object of the image.
        Graphics graphics = image.getGraphics();
        component.paint(graphics); // alternately use .printAll(..)
        graphics.drawString(randomInfo, 0, component.getHeight());

        try {
            ImageIO.write(image, "PNG", new File(name + ".png"));
            System.out.println("Saved screenshot as " + name);
        } catch (IOException e) {
            System.out.println("Saving screenshot failed: " + e);
        }
    }

}