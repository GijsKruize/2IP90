import java.awt.*;
import javax.swing.*;

public class RoundButton extends JButton {

  public RoundButton(String label) {
    super(label);
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width,size.height);
    setPreferredSize(size);

    setContentAreaFilled(false);
  }

  public RoundButton() {
    this("");
  }

  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(Color.lightGray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width-1,getSize().height-1);

    super.paintComponent(g);
  }

  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width-1,     getSize().height-1);
  }
}