import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;

public class MyJComboBox{
    private int radius;
    private Color foreground;
    private Color background;
    private Color selectionForeground;
    private Color selectionBackground;
    private Color buttonDarkShadow;
    private DefaultComboBoxModel<String> model;
    private String title;

    public MyJComboBox(Color foreground, Color background, Color selectionForeground, Color selectionBackground, Color buttonDarkShadow, DefaultComboBoxModel<String> model, int radius, String title){
        this.foreground = foreground;
        this.background = background;
        this.selectionBackground = selectionBackground;
        this.selectionForeground = selectionForeground;
        this.buttonDarkShadow = buttonDarkShadow;
        this.model = model;
        this.radius = radius;
        this.title = title;
    }

    public Color getSelectionBackground() {
        return selectionBackground;
    }

    public Color getSelectionForeground() {
        return selectionForeground;
    }

    public JComponent makeUI() {
        UIManager.put("ComboBox.foreground", foreground);
        UIManager.put("ComboBox.background", background);
        UIManager.put("ComboBox.selectionForeground", foreground);
        UIManager.put("ComboBox.selectionBackground", background);
        UIManager.put("ComboBox.buttonDarkShadow", buttonDarkShadow);
        UIManager.put("ComboBox.border", new RoundedCornerBorder1(radius));

        JComboBox<String> jComboBox = new JComboBox<>(model);
        jComboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton jButton = super.createArrowButton();
                jButton.setContentAreaFilled(false);
                jButton.setBackground(background);
                jButton.setForeground(foreground);
                jButton.setBorder(new EmptyBorder(15, 15, 15, 15));
                return jButton;
            }
        });
        Object o = jComboBox.getAccessibleContext().getAccessibleChild(0);
        if (o instanceof JComponent) {
            JComponent c = (JComponent) o;
            c.setBorder(new MyBorder());
            c.setForeground(foreground);
            c.setBackground(background);
        }
        Object popup = jComboBox.getUI().getAccessibleChild(jComboBox, 0);
        Component c = ((Container) popup).getComponent(0);

        if (c instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) c;
            JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
            scrollBar.setPreferredSize(new Dimension(0, 0));
        }

        jComboBox.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (index == -1 && value == null) {
                    setText(title);
                }
                else {
                    setText(value.toString());
                }

                background = getBackground();
                foreground = getForeground();

                if (isSelected) {
                    background = getSelectionBackground();
                    foreground = getSelectionForeground();
                }

                setBackground(background);
                setForeground(foreground);
                return this;
            }
        });

        return jComboBox;
    }
}