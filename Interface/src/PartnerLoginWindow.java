import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartnerLoginWindow{
    private static JPanel panel;
    private JLabel partnerLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField emailText;
    private JPasswordField passwordText;
    private JButton button;
    private ImageIcon myIcon;
    private JButton login;
    private JButton back;
    private JLabel tooltip;
    private static String email;
    private static int numOfLogins;
    private GetConnection conn = GetConnection.getInstance();

    public PartnerLoginWindow() {
    }

    public String getEmail() {
        return email;
    }

    public JPanel getPanel(){
        try {
            panel = new MyPanel("Resources/images/background.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);

        partnerLabel = new JLabel("Login as partner ");
        partnerLabel.setForeground(new Color(230, 90, 105));
        partnerLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 50));
        panel.add(partnerLabel);

        emailLabel = new JLabel("Email ");
        emailLabel.setForeground(new Color(230, 90, 105));
        emailLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(emailLabel);

        emailText = new MyJTextField(100);
        panel.add(emailText);

        passwordLabel = new JLabel("Password ");
        passwordLabel.setForeground(new Color(230, 90, 105));
        passwordLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(passwordLabel);

        passwordText = new MyJPassField(100);
        panel.add(passwordText);

        login = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Login", 30);
        panel.add(login);

        back = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "<Back", 30);
        panel.add(back);

        myIcon = new ImageIcon("Resources/images/info_logo.png");

        button = new JButton(myIcon);
        button.setBorder(new EmptyBorder(15, 15, 15, 15));
        button.setBackground(new Color(255, 240, 243));
        button.setContentAreaFilled(false);
        panel.add(button);

        tooltip = new JLabel("For your first login, we generated a default password that consists of all the characters before the @ in your email.");
        tooltip.setOpaque(true);
        tooltip.setBorder(new RoundedCornerBorder(20));
        tooltip.setForeground(new Color(230, 90, 105));
        tooltip.setBackground(Color.WHITE);
        tooltip.setSize(tooltip.getPreferredSize());
        tooltip.setBounds(340, 270 + tooltip.getHeight(), tooltip.getWidth(), tooltip.getHeight());
        panel.add(tooltip);
        tooltip.setVisible(false);

        partnerLabel.setBounds(200, 50, 800, 100);
        emailLabel.setBounds(200, 180, 200, 30);
        emailText.setBounds(400, 180, 300, 30);
        passwordLabel.setBounds(200, 280, 200, 30);
        button.setBounds(340, 280, 31, 31);
        passwordText.setBounds(400, 280, 300, 30);
        back.setBounds(200, 380, 110, 55);
        login.setBounds(350, 380, 110, 55);

        login.addActionListener(ev -> {
            if(ev.getSource() == login) {
                Connection connection = conn.getConnection();
                String password;
                try {
                    try {
                        int exists;
                        email = emailText.getText();

                        if (email.isEmpty())
                            throw new InvalidInputException("Please fill in all the required information!");

                        password = String.valueOf(passwordText.getPassword());

                        if (password.isEmpty())
                            throw new InvalidInputException("Please fill in all the required information!");

                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM PARTNER WHERE PARTNER_EMAIL = '" + email + "'");

                        result.next();
                        exists = result.getInt(1);

                        if (exists == 0) {
                            throw new EmailNotFoundException("Could not find partner with this email!");
                        }
                        statement.close();

                        Statement statement1 = connection.createStatement();
                        Statement statement2 = connection.createStatement();
                        ResultSet result1 = statement1.executeQuery("SELECT * FROM PARTNER WHERE PARTNER_EMAIL = '" + email + "'");
                        if (result1.next()) {
                            String partnerPass = result1.getString(4);
                            numOfLogins = result1.getInt(2);
                            if (!password.equals(partnerPass))
                                throw new IncorrectPasswordException("Incorrect password!");
                            if (numOfLogins == 0) {
                                GUI gui = new GUI();
                                PartnerChangePasswordWindow partnerChangePasswordWindow = new PartnerChangePasswordWindow();
                                gui.getTabbedPane().add(partnerChangePasswordWindow.getPanel());
                                gui.getTabbedPane().setSelectedIndex(1);
                                gui.getTabbedPane().remove(panel);
                                numOfLogins = 1;
                                statement2.executeUpdate("UPDATE PARTNER SET NUM_OF_LOGINS = " + numOfLogins + " WHERE PARTNER_EMAIL = '" + email + "'");
                            }
                            else {
                                numOfLogins++;
                                statement2.executeUpdate("UPDATE PARTNER SET NUM_OF_LOGINS = " + numOfLogins + " WHERE PARTNER_EMAIL = '" + email + "'");
                            }
                            statement1.close();
                            statement2.close();
                        }

                    } catch (IncorrectPasswordException ex) {
                        new MyJOptionPane(ex.getMessage(), "IncorrectPasswordException");
                    } catch (InvalidInputException ex) {
                        new MyJOptionPane(ex.getMessage(), "InvalidInputException");
                    } catch (EmailNotFoundException ex) {
                        new MyJOptionPane(ex.getMessage(), "EmailNotFoundException");
                    }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
            }
                //new UserWindow
        });

        back.addActionListener(ev -> {
            if(ev.getSource() == back){
                GUI gui = new GUI();
                gui.getTabbedPane().add(gui.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon("Resources/images/info_logo_pressed.png"));
                tooltip.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(myIcon);
                tooltip.setVisible(false);
            }
        });

        panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"enter");
        panel.getActionMap().put("enter", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) {
                login.doClick();
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
