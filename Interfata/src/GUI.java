import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GUI extends JFrame{
    private static JTabbedPane tabbedPane = new MyTabbedPane();
    private static JPanel panel;
    private JLabel label;
    private GetConnection conn = GetConnection.getInstance();

    public GUI() {

    }

    public void initializeLentBooks() throws SQLException {
        Connection connection = conn.getConnection();
        MyDate issuedDate = new MyDate();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM LENT_BOOK");

        while (result.next()) {
            int bookId = result.getInt(1);
            issuedDate.setDate(result.getString(5));
            int daysFromIssue = result.getInt(6);
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("UPDATE LENT_BOOK SET PRICE = " + 2 * daysFromIssue + ", EXCEEDED_PRICE = 0, EXCEEDED_DAYS = 0, RETURN_DATE = '" + issuedDate.addDays(daysFromIssue).getDate() + "' WHERE BOOK_ID = " + bookId + " AND ISSUED_DATE = '" + issuedDate.getDate() + "'");
            statement1.close();
        }
        statement.close();
    }

    public void addToFrame(){
        this.add(tabbedPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Library lending system");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public JTabbedPane getTabbedPane(){
        return tabbedPane;
    }

    public JPanel getPanel(){
        try {
            panel = new MyPanel("Resources/images/background.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setBackground(new Color(255, 240, 243));
        panel.setLayout(null);

        label = new JLabel("Login as ");
        label.setBounds(200, 50, 500, 100);
        label.setForeground(new Color(230, 90, 105));
        label.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 50));
        panel.add(label);

        JButton librarianButton = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Librarian", 50);
        librarianButton.setBounds(200, 150, 500, 100);
        panel.add(librarianButton);

        JButton partnerButton = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Partner", 50);
        partnerButton.setBounds(200, 350, 500, 100);
        panel.add(partnerButton);

        JButton userButton = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "User", 50);
        userButton.setBounds(200, 550, 500, 100);
        panel.add(userButton);

        librarianButton.addActionListener(e -> {
            if(e.getSource() == librarianButton){
                LibrarianLoginWindow librarianLoginWindow = new LibrarianLoginWindow();
                tabbedPane.add(librarianLoginWindow.getPanel());
                tabbedPane.setSelectedIndex(1);
                tabbedPane.remove(panel);
            }
        });

        partnerButton.addActionListener(e -> {
            if(e.getSource() == partnerButton){
                PartnerLoginWindow partnerLoginWindow = new PartnerLoginWindow();
                tabbedPane.add(partnerLoginWindow.getPanel());
                tabbedPane.setSelectedIndex(1);
                tabbedPane.remove(panel);
            }
        });

        userButton.addActionListener(e -> {
            if(e.getSource() == userButton){
                UserLoginWindow userLoginWindow = new UserLoginWindow();
                tabbedPane.add(userLoginWindow.getPanel());
                tabbedPane.setSelectedIndex(1);
                tabbedPane.remove(panel);
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.addToFrame();
        try {
            gui.initializeLentBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabbedPane.add(gui.getPanel());
    }
}