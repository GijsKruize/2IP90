
/**
 * CircleDingus -- part of HA RandomArtist
 * example of a very simple Dingus
 * @author huub
 */

import java.awt.Graphics;

class TriangleDingus extends Dingus {
    protected int width;
    protected int height;
    protected boolean filled; // true: filled, false: outline

    public TriangleDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        // initialize randomly the CircleDingus properties, i.e., radius and filledness
        width = random.nextInt(maxX / 4);
        height = width;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        int[] triangleX = { x - 2 * width / 18, x + width + 2 * width / 18,
                (x - 2 * width / 18 + x + width + 2 * width / 18) / 2 };
        int[] triangleY = { y + height - 2 * height / 3, y + height - 2 * height / 3, y + height };

        if (filled) {
            g.fillPolygon(triangleX, triangleY, triangleX.length);
        } else {
            g.drawPolygon(triangleX, triangleY, triangleX.length);
        }
    }
}
