import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;

public class MyPanel extends JPanel {
    private Image backgroundImage;

    public MyPanel(String fileName) throws IOException {
        this.backgroundImage = ImageIO.read(new File(fileName));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(backgroundImage, 0, 0, this);
        g2.setColor(new Color(230, 90, 105));
        g2.setStroke(new BasicStroke(5));
        g2.draw(new Line2D.Float(170, 50, 170, 210));//interior y
        g2.draw(new Line2D.Float(170, 50, 470, 50));//interior x
        g2.draw(new Line2D.Float(150, 30, 150, 310));//exterior y
        g2.draw(new Line2D.Float(150, 30, 330, 30));//exterior x
        g2.setStroke(new BasicStroke(1));
    }
}