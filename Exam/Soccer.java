/**
 * @author Gijs Kruize
 * @ID 1658662
 * @Date 1-11-2021
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Soccer extends JPanel implements ActionListener {
    JFrame frame;
    JButton button;
    ArrayList<Ball> balls;

    void buildIt() {
        frame = new JFrame("Soccer");
        frame.add(this);

        button = new JButton("Kick");
        frame.add(button, BorderLayout.NORTH);
        button.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocation(200, 200);
        frame.setVisible(true);

        createBalls();
    }

    void createBalls() {
        balls = new ArrayList<>();
        BlueBall blue1 = new BlueBall(getWidth() / 2, getHeight() / 2);
        BlueBall blue2 = new BlueBall(15, getHeight() - 15);
        PinkBall pink1 = new PinkBall(15, 15);
        DottedBall dotted1 = new DottedBall(getWidth() / 2, getHeight() / 4);
        ChameleonBall chameleon1 = new ChameleonBall(15, getHeight() - 40);
        balls.add(blue1);
        balls.add(blue2);
        balls.add(pink1);
        balls.add(dotted1);
        balls.add(chameleon1);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball b : balls) {
            b.draw(g);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (Ball b : balls) {
            b.kick();
        }
        repaint();
    }

    public static void main(String[] args) {
        new Soccer().buildIt();
    }
}

abstract class Ball {
    long SEED = 37;
    Random rd = new Random(SEED);
    int x, y; // position of center in pixels
    int size; // diameter in pixels
    int DISPLACEMENT = 10;

    public Ball() {
        size = 30;
    }

    public Color RandomColorGenerator() {
        float r = rd.nextFloat();
        float g = rd.nextFloat();
        float b = rd.nextFloat();
        Color color = new Color(r, g, b);
        return color;
    }

    public Ball(int startx, int starty) {
        x = startx;
        y = starty;
        size = 30;
    }

    public abstract void kick();

    public abstract void draw(Graphics g);
}

class PinkBall extends Ball {
    Color color = Color.PINK;

    PinkBall(int startx, int starty) {
        super(startx, starty);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRoundRect(x - size / 2, y - size / 2, size, size, 20, 20);
    }

    @Override
    public void kick() {
        x += DISPLACEMENT;
        y += DISPLACEMENT;
    }
}

class BlueBall extends Ball {
    Color color = Color.BLUE;

    BlueBall(int startx, int starty) {
        super(startx, starty);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - size / 2, y - size / 2, size, size);
    }

    @Override
    public void kick() {
        x += DISPLACEMENT;
    }
}

class DottedBall extends BlueBall {
    Color dotColor = Color.BLACK;

    DottedBall(int startx, int starty) {
        super(startx, starty);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(dotColor);
        g.fillOval(x - size / 10, y - size / 10, size / 5, size / 5);
    }

    @Override
    public void kick() {
        super.kick();
        size += 3;
    }
}

class ChameleonBall extends Ball {
    Color color = RandomColorGenerator();

    ChameleonBall(int startx, int starty) {
        super(startx, starty);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - size / 2, y - size / 2, size, size);
    }

    @Override
    public void kick() {
        color = RandomColorGenerator();
    }
}