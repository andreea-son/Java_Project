import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PartnerChangePasswordWindow {
    private static JPanel panel;
    private JLabel userLabel;
    private JLabel newPasswordLabel;
    private JPasswordField newPasswordText;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordText;
    private JButton button1;
    private JButton button2;
    private ImageIcon myIcon1;
    private ImageIcon myIcon2;
    private JButton save;
    private JButton back;
    private JButton skip;
    private JLabel tooltip1;
    private JLabel tooltip2;
    private GetConnection conn = GetConnection.getInstance();

    public PartnerChangePasswordWindow() {

    }
    public JPanel getPanel(){

        try {
            panel = new MyPanel("Resources/images/background.png");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);

        userLabel = new JLabel("Change password ");
        userLabel.setForeground(new Color(230, 90, 105));
        userLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 50));
        panel.add(userLabel);

        newPasswordLabel = new JLabel("New password ");
        newPasswordLabel.setForeground(new Color(230, 90, 105));
        newPasswordLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(newPasswordLabel);

        newPasswordText = new MyJPassField(100);
        panel.add(newPasswordText);

        confirmPasswordLabel = new JLabel("Confirm password ");
        confirmPasswordLabel.setForeground(new Color(230, 90, 105));
        confirmPasswordLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(confirmPasswordLabel);

        confirmPasswordText = new MyJPassField(100);
        panel.add(confirmPasswordText);

        save = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Save", 30);
        panel.add(save);

        back = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "<Back", 30);
        panel.add(back);

        skip = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Skip>", 30);
        panel.add(skip);

        myIcon1 = new ImageIcon("Resources/images/question_logo.png");

        button1 = new JButton(myIcon1);

        button1.setBorder(new EmptyBorder(15, 15, 15, 15));
        button1.setBackground(new Color(255, 240, 243));
        button1.setContentAreaFilled(false);
        panel.add(button1);

        tooltip1 = new JLabel("For security reasons, we advise changing your default password");
        tooltip1.setOpaque(true);
        tooltip1.setBorder(new RoundedCornerBorder(20));
        tooltip1.setForeground(new Color(230, 90, 105));
        tooltip1.setBackground(Color.WHITE);
        tooltip1.setSize(tooltip1.getPreferredSize());
        tooltip1.setBounds(610, 80 + tooltip1.getHeight(), tooltip1.getWidth(), tooltip1.getHeight());
        panel.add(tooltip1);
        tooltip1.setVisible(false);

        myIcon2 = new ImageIcon("Resources/images/info_logo.png");

        button2 = new JButton(myIcon2);
        button2.setBorder(new EmptyBorder(15, 15, 15, 15));
        button2.setBackground(new Color(255, 240, 243));
        button2.setContentAreaFilled(false);
        panel.add(button2);

        tooltip2 = new JLabel("Format: At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");
        tooltip2.setOpaque(true);
        tooltip2.setBorder(new RoundedCornerBorder(20));
        tooltip2.setForeground(new Color(230, 90, 105));
        tooltip2.setBackground(Color.WHITE);
        tooltip2.setSize(tooltip2.getPreferredSize());
        tooltip2.setBounds(410, 170 + tooltip2.getHeight(), tooltip2.getWidth(), tooltip2.getHeight());
        panel.add(tooltip2);
        tooltip2.setVisible(false);

        userLabel.setBounds(200, 50, 800, 100);
        button1.setBounds(610, 85, 32, 37);
        newPasswordLabel.setBounds(200, 180, 300, 30);
        button2.setBounds(410, 180, 32, 31);
        newPasswordText.setBounds(500, 180, 300, 30);
        confirmPasswordLabel.setBounds(200, 280, 300, 30);
        confirmPasswordText.setBounds(500, 280, 300, 30);
        back.setBounds(200, 380, 110, 55);
        save.setBounds(350, 380, 110, 55);
        skip.setBounds(500, 380, 110, 55);

        back.addActionListener(ev -> {
            if(ev.getSource() == back){
                GUI gui = new GUI();
                PartnerLoginWindow partnerLoginWindow = new PartnerLoginWindow();
                gui.getTabbedPane().add(partnerLoginWindow.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
            }
        });

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setIcon(new ImageIcon("Resources/images/question_logo_pressed.png"));
                tooltip1.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setIcon(myIcon1);
                tooltip1.setVisible(false);
            }
        });

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setIcon(new ImageIcon("Resources/images/info_logo_pressed.png"));
                tooltip2.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setIcon(myIcon2);
                tooltip2.setVisible(false);
            }
        });

        save.addActionListener(e -> {
            if(e.getSource() == save) {
                Connection connection = conn.getConnection();
                String password;
                String confirmPassword;
                try{
                    try {
                        password = String.valueOf(newPasswordText.getPassword());
                        if (password.isEmpty())
                            throw new InvalidInputException("Please enter password!");

                        confirmPassword = String.valueOf(confirmPasswordText.getPassword());
                        if (confirmPassword.isEmpty())
                            throw new InvalidInputException("Please confirm password!");

                        if (!password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}"))
                            throw new IncorrectPassFormatException("Password doesn't have required format!");

                        if (!confirmPassword.equals(password)) {
                            throw new IncorrectPasswordException("Password doesn't match!");
                        }
                        PartnerLoginWindow partnerLoginWindow = new PartnerLoginWindow();
                        Statement statement = connection.createStatement();
                        statement.executeUpdate("UPDATE PARTNER SET PARTNER_PASS = '" + password + "' WHERE PARTNER_EMAIL = '" + partnerLoginWindow.getEmail() + "'");
                        statement.close();

                    } catch (InvalidInputException ex) {
                        new MyJOptionPane(ex.getMessage(), "IncorrectPasswordException");
                    } catch (IncorrectPasswordException ex) {
                        new MyJOptionPane(ex.getMessage(), "IncorrectPasswordException");
                    } catch (IncorrectPassFormatException ex) {
                        new MyJOptionPane(ex.getMessage(), "IncorrectPassFormatException");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"enter");
        panel.getActionMap().put("enter", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) {
                save.doClick();
            }
        });

        panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),"escape");
        panel.getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) {
                back.doClick();
            }
        });

        return panel;
    }
}
