/**
 * CircleDingus -- part of HA RandomArtist
 * example of a very simple Dingus
 * @author huub
 */

import java.awt.Graphics;

class SquareDingus extends Dingus {
    protected int size;
    protected boolean filled; //true: filled, false: outline

    public SquareDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the CircleDingus properties, i.e., radius and filledness
        size = random.nextInt(maxX/4);
        filled = random.nextBoolean();
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        if (filled)
            g.fillRect(x, y, size, size);
        else
            g.drawRect(x, y, size, size);
    }
}
