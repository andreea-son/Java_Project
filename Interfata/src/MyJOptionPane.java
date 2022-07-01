import javax.swing.*;
import java.awt.*;

public class MyJOptionPane{
    private String message;
    private String title;
    public MyJOptionPane(String message, String title){
        JPanel jPanel = new JPanel();
        UIManager.put("OptionPane.background", new Color(255, 240, 243));
        UIManager.put("Panel.background", new Color(255, 240, 243));
        UIManager.put("Button.select", new Color(248, 211, 211));
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.border", new NormalBorder());
        JOptionPane.showMessageDialog(jPanel, message, title, JOptionPane.ERROR_MESSAGE, new ImageIcon("Resources/images/error_logo.png"));
    }
}
