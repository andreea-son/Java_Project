import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class LibrarianLoginWindow {
    private static JPanel panel;
    private JLabel librarianLabel;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton login;
    private JButton back;

    public LibrarianLoginWindow() {

    }

    public JPanel getPanel() {
        try {
            panel = new MyPanel("Resources/images/background.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(null);

        librarianLabel = new JLabel("Login as librarian ");
        librarianLabel.setBounds(200, 50, 800, 100);
        librarianLabel.setForeground(new Color(230, 90, 105));
        librarianLabel.setFont(new Font("Book Antiqua", Font.ITALIC | Font.BOLD, 50));
        panel.add(librarianLabel);

        userLabel = new JLabel("Username ");
        userLabel.setBounds(200, 180, 200, 30);
        userLabel.setForeground(new Color(230, 90, 105));
        userLabel.setFont(new Font("Book Antiqua", Font.ITALIC | Font.BOLD, 30));
        panel.add(userLabel);

        userText = new MyJTextField(100);
        userText.setBounds(350, 180, 300, 30);
        panel.add(userText);

        passwordLabel = new JLabel("Password ");
        passwordLabel.setBounds(200, 280, 200, 30);
        passwordLabel.setForeground(new Color(230, 90, 105));
        passwordLabel.setFont(new Font("Book Antiqua", Font.ITALIC | Font.BOLD, 30));
        panel.add(passwordLabel);

        passwordText = new MyJPassField(100);
        passwordText.setBounds(350, 280, 300, 30);
        panel.add(passwordText);

        login = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Login", 30);
        login.setBounds(350, 380, 110, 55);
        panel.add(login);

        back = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "<Back", 30);
        back.setBounds(200, 380, 110, 55);
        panel.add(back);

        login.addActionListener(e -> {
            if (e.getSource() == login) {
                try {
                    String username = userText.getText();
                    if (username.isEmpty()) {
                        throw new InvalidInputException("Please fill in all the required information!");
                    }

                    String password = String.valueOf(passwordText.getPassword());
                    if (password.isEmpty()) {
                        throw new InvalidInputException("Please fill in all the required information!");
                    }

                    if (!username.equals(("admin"))) {
                        throw new UsernameNotFoundException("Incorrect username!");
                    }

                    if (!password.equals(("admin"))) {
                        throw new IncorrectPasswordException("Incorrect password!");
                    }
                    GUI gui = new GUI();
                    LibrarianWindow librarianWindow = new LibrarianWindow();
                    gui.getTabbedPane().add(librarianWindow.getPanel());
                    gui.getTabbedPane().setSelectedIndex(1);
                    gui.getTabbedPane().remove(panel);
                } catch (UsernameNotFoundException ex) {
                    new MyJOptionPane(ex.getMessage(), "UsernameNotFoundException");
                } catch (InvalidInputException ex) {
                    new MyJOptionPane(ex.getMessage(), "InvalidInputException");
                } catch (IncorrectPasswordException ex) {
                    new MyJOptionPane(ex.getMessage(), "IncorrectPasswordException");
                }
            }
        });

        back.addActionListener(e -> {
            if (e.getSource() == back) {
                GUI gui = new GUI();
                gui.getTabbedPane().add(gui.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
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
