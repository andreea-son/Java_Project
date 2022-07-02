import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Path2D;

class MyBorder extends AbstractBorder {
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = width - 1;
        int h = height - 1;

        g2.setStroke(new BasicStroke(3));
        Path2D.Float p = new Path2D.Float();
        p.moveTo(x, y - 3);
        p.lineTo(x, y + h);
        p.lineTo(x + w, y + h);
        p.lineTo(x + w, y - 3);
        p.closePath();

        g2.setPaint(c.getBackground());
        g2.fill(p);

        g2.setPaint(c.getForeground());
        g2.draw(p);

        g2.dispose();
    }
    @Override public Insets getBorderInsets(Component c) {
        return new Insets(15, 15, 15, 15);
    }
    @Override public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(15, 15, 15, 15);
        return insets;
    }
}
