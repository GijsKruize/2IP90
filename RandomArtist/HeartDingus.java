/**
 * HeartDingus 
 * @author Gijs Nolet
 */

import java.awt.Graphics;

class HeartDingus extends Dingus {
    private double height;
    private double width;

    public HeartDingus(int maxX, int maxY) {
        // initialize Dingus properties
        super(maxX, maxY);

        // initialize HeartDingus properties
        height = random.nextInt(maxY/3);
        width = height;
    }

    @Override
    void draw(Graphics g) {
    int[] triangleX = {
            (int)(width/25 + x - 2*width/20),
            (int)(-(width/25) + x + width + 2*width/20),
            (int)((x - 2*width/20 + x + width + 2*width/20)/2)};
    int[] triangleY = { 
            (int)(y + height - 2*height/3), 
            (int)(y + height - 2*height/3), 
            (int)(y + height)};
    g.fillOval(
            (int)(x - width/12),
            y, 
            (int)(width/2 + width/6), 
            (int)(height/2)); 
    g.fillOval(
            (int)(x + width/2 - width/12),
            (int)(y),
            (int)(width/2 + width/6),
            (int)(height/2));
    g.fillPolygon(triangleX, triangleY, triangleX.length);
    }
}
