import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReturnBookWindow {
    private static JPanel panel;
    private JLabel returnLabel;
    private JLabel bookLabel;
    private JTextField bookId;
    private JLabel userLabel;
    private JTextField userId;
    private JLabel dateLabel;
    private JTextField returnDate;
    private JButton button1;
    private ImageIcon myIcon;
    private JLabel tooltip1;
    private JButton back;
    private JButton next;
    private MyDate lastDate;
    private MyDate tempDate;
    private static String bookIdText;
    private static String userIdText;
    private static String returnDateText;
    private static String actualReturnDateText;
    private static String author;
    private static String title;
    private static String description;
    private static int partnerId;
    private static int sectionId;
    private static long exceededDays;
    private static float exceededPrice;
    private ArrayList <String> date = new ArrayList <>();
    private MyLastDate myLastDate = new MyLastDate();
    private GetConnection conn = GetConnection.getInstance();

    public ReturnBookWindow() {
    }

    public String getBookIdText() {
        return bookIdText;
    }

    public String getUserIdText() {
        return userIdText;
    }

    public String getReturnDateText() {
        return returnDateText;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public int getSectionId(){
        return sectionId;
    }

    public static long getExceededDays() {
        return exceededDays;
    }

    public static float getExceededPrice() {
        return exceededPrice;
    }

    public static String getActualReturnDateText() {
        return actualReturnDateText;
    }

    public static void setBookIdText(String bookIdText) {
        ReturnBookWindow.bookIdText = bookIdText;
    }

    public static void setUserIdText(String userIdText) {
        ReturnBookWindow.userIdText = userIdText;
    }

    public static void setReturnDateText(String returnDateText) {
        ReturnBookWindow.returnDateText = returnDateText;
    }

    public static void setAuthor(String author) {
        ReturnBookWindow.author = author;
    }

    public static void setTitle(String title) {
        ReturnBookWindow.title = title;
    }

    public static void setDescription(String description) {
        ReturnBookWindow.description = description;
    }

    public static void setPartnerId(int partnerId) {
        ReturnBookWindow.partnerId = partnerId;
    }

    public static void setSectionId(int sectionId) {
        ReturnBookWindow.sectionId = sectionId;
    }

    public static void setExceededDays(long exceededDays) {
        ReturnBookWindow.exceededDays = exceededDays;
    }

    public static void setExceededPrice(float exceededPrice) {
        ReturnBookWindow.exceededPrice = exceededPrice;
    }

    public static void setActualReturnDateText(String actualReturnDateText) {
        ReturnBookWindow.actualReturnDateText = actualReturnDateText;
    }

    static String getAlphaNumericString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
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

        returnLabel = new JLabel("Return Book ");
        returnLabel.setForeground(new Color(230, 90, 105));
        returnLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 50));
        panel.add(returnLabel);

        returnDate = new JTextField(100);
        panel.add(returnDate);

        bookLabel = new JLabel("Book ID ");
        bookLabel.setForeground(new Color(230, 90, 105));
        bookLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(bookLabel);

        bookId = new MyJTextField(100);
        panel.add(bookId);

        userLabel = new JLabel("User ID ");
        userLabel.setForeground(new Color(230, 90, 105));
        userLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(userLabel);

        userId = new MyJTextField(100);
        panel.add(userId);

        dateLabel = new JLabel("Return date ");
        dateLabel.setForeground(new Color(230, 90, 105));
        dateLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(dateLabel);

        returnDate = new MyJTextField(100);
        panel.add(returnDate);

        tooltip1 = new JLabel("Today's date following this format: yyyy-MM-dd");
        tooltip1.setOpaque(true);
        tooltip1.setBorder(new RoundedCornerBorder(20));
        tooltip1.setForeground(new Color(230, 90, 105));
        tooltip1.setBackground(Color.WHITE);
        tooltip1.setSize(tooltip1.getPreferredSize());
        panel.add(tooltip1);
        tooltip1.setVisible(false);

        myIcon = new ImageIcon("Resources/images/info_logo.png");

        button1 = new JButton(myIcon);
        button1.setBorder(new EmptyBorder(15, 15, 15, 15));
        button1.setBackground(new Color(255, 240, 243));
        button1.setContentAreaFilled(false);
        panel.add(button1);
        button1.setFocusPainted(false);

        back = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "<Back", 30);
        panel.add(back);

        next = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Return>", 30);
        panel.add(next);

        returnLabel.setBounds(200, 50, 800, 80);
        bookLabel.setBounds(200, 180, 400, 35);
        bookId.setBounds(500, 180, 400, 35);
        userLabel.setBounds(200, 280, 400, 35);
        userId.setBounds(500, 280, 400, 35);
        dateLabel.setBounds(200, 380, 400, 35);
        button1.setBounds(370, 380, 32, 35);
        tooltip1.setBounds(370, 370 - tooltip1.getHeight(), tooltip1.getWidth(), tooltip1.getHeight());
        returnDate.setBounds(500, 380, 400, 35);
        back.setBounds(200, 440, 110, 55);
        next.setBounds(350, 440, 150, 55);

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setIcon(new ImageIcon("Resources/images/info_logo_pressed.png"));
                tooltip1.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setIcon(myIcon);
                tooltip1.setVisible(false);
            }
        });

        back.addActionListener(e -> {
            if (e.getSource() == back) {
                GUI gui = new GUI();
                LibrarianWindow librarianWindow = new LibrarianWindow();
                gui.getTabbedPane().add(librarianWindow.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
            }
        });

        next.addActionListener(e -> {
            if (e.getSource() == next) {
                Connection connection = conn.getConnection();
                try {
                    try {
                        setBookIdText(bookId.getText());
                        if (bookIdText.isEmpty()) {
                            throw new InvalidInputException("Please fill in all the required information!");
                        }
                        setUserIdText(userId.getText());
                        if (userIdText.isEmpty()) {
                            throw new InvalidInputException("Please fill in all the required information!");
                        }
                        setActualReturnDateText(returnDate.getText());
                        if (actualReturnDateText.isEmpty()) {
                            throw new InvalidInputException("Please fill in all the required information!");
                        }

                        Statement statement1 = connection.createStatement();
                        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM BOOK WHERE BOOK_ID = " + bookIdText);

                        int bookExists = 0;

                        if(result1.next()) {
                            bookExists = result1.getInt(1);
                        }

                        if (bookExists == 0) {
                            throw new BookNotFoundException("Could not find book with this ID!");
                        }
                        statement1.close();

                        Statement statement2 = connection.createStatement();
                        ResultSet result2 = statement2.executeQuery("SELECT COUNT(*) FROM LENT_BOOK WHERE BOOK_ID = " + bookIdText + " AND IS_LENT = 'TRUE'");

                        int isLent = 0;
                        if(result2.next()){
                            isLent = result2.getInt(1);
                        }

                        if(isLent == 0){
                            throw new BookNotLentException("This book is currently not lent! You can't return it!");
                        }
                        statement2.close();

                        Statement statement3 = connection.createStatement();
                        ResultSet result3 = statement3.executeQuery("SELECT COUNT(*) FROM \"USER\" WHERE USER_ID = " + userIdText);

                        int userExists = 0;

                        if(result3.next()) {
                            userExists = result3.getInt(1);
                        }

                        if (userExists == 0) {
                            throw new UserNotFoundException("Could not find user with this ID!");
                        }
                        statement3.close();

                        Statement statement4 = connection.createStatement();
                        ResultSet result4 = statement4.executeQuery("SELECT COUNT(*) FROM LENT_BOOK WHERE BOOK_ID = " + bookIdText + " AND USER_ID = " + userIdText + " AND IS_LENT = 'TRUE'");

                        int isLentByUser = 0;
                        if(result4.next()){
                            isLentByUser = result4.getInt(1);
                        }

                        if(isLentByUser == 0){
                            throw new BookNotLentException("This book is currently not lent by this user! You can't return it!");
                        }
                        statement4.close();

                        if (!actualReturnDateText.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                            throw new IncorrectDateFormatException("This date doesn't match the required date format!");
                        }
                        lastDate = new MyDate();
                        tempDate = new MyDate();

                        lastDate.setDate(myLastDate.getLastDate().getDate());
                        tempDate.setDate(getActualReturnDateText());

                        if(lastDate.isBiggerThan(tempDate)){
                            throw new DateNotValidException("Please enter a date equal to or grater than " + lastDate.getDate());
                        }

                        Statement statement5 = connection.createStatement();
                        ResultSet result5 = statement5.executeQuery("SELECT * FROM LENT_BOOK WHERE BOOK_ID = " + bookIdText + " AND IS_LENT = 'TRUE'");

                        if(result5.next()){
                            setAuthor(result5.getString(2));
                            setTitle(result5.getString(3));
                            setDescription(result5.getString(4));
                            setReturnDateText(result5.getString(7));
                            setSectionId(result5.getInt(9));
                            setPartnerId(result5.getInt(10));
                        }
                        statement5.close();

                        MyDate tempDate1 = new MyDate();
                        tempDate1.setDate(getReturnDateText());
                        MyDate tempDate2 = new MyDate();
                        tempDate2.setDate(getActualReturnDateText());

                        setExceededDays(tempDate1.differenceInDays(tempDate2));
                        setExceededPrice(3 * getExceededDays());

                        if(exceededDays > 0){
                            UIManager.put("OptionPane.background", new Color(255, 240, 243));
                            UIManager.put("Panel.background", new Color(255, 240, 243));
                            UIManager.put("Button.select", new Color(248, 211, 211));
                            UIManager.put("Button.focus", new Color(0, 0, 0, 0));
                            UIManager.put("Button.background", Color.WHITE);
                            UIManager.put("Button.border", new NormalBorder());

                            JButton button2 = new JButton("Payment>");

                            Object[] option = {button2};

                            button2.addActionListener(ex2 -> {
                                if (ex2.getSource() == button2){
                                    GUI gui = new GUI();
                                    ReturnBookPaymentWindow returnBookPaymentWindow = new ReturnBookPaymentWindow();
                                    gui.getTabbedPane().add(returnBookPaymentWindow.getPanel());
                                    gui.getTabbedPane().setSelectedIndex(1);
                                    gui.getTabbedPane().remove(panel);

                                    Window w = SwingUtilities.getWindowAncestor(button2);
                                    w.dispose();
                                }
                            });

                            JOptionPane.showOptionDialog(panel, "This user has exceeded the return date. Click the button below and you will be redirected to the payment page!", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);
                        }

                        else {
                            String code = getAlphaNumericString();
                            MyDate expirationDate = new MyDate();
                            expirationDate.setDate(getActualReturnDateText());

                            Statement statement6 = connection.createStatement();
                            statement6.executeUpdate("INSERT INTO DISCOUNT VALUES('" + code + "', '" + expirationDate.addMonths(2).getDate() + "', " + getUserIdText() + ", 'FALSE')");
                            statement6.close();

                            Statement statement7 = connection.createStatement();
                            statement7.executeUpdate("UPDATE BOOK SET IS_LENT = 'FALSE' WHERE BOOK_ID = " + getBookIdText());
                            statement7.close();

                            MyDate date = new MyDate();
                            Statement statement8 = connection.createStatement();
                            ResultSet result8 = statement8.executeQuery("SELECT ISSUED_DATE FROM LENT_BOOK WHERE IS_LENT = 'TRUE'");
                            if(result8.next()){
                                date.setDate(result8.getString(1));
                            }
                            statement8.close();

                            Statement statement9 = connection.createStatement();
                            statement9.executeUpdate("UPDATE LENT_BOOK SET IS_LENT = 'FALSE', EXCEEDED_DAYS = NULL, EXCEEDED_PRICE = NULL, RETURN_DATE = '" + getActualReturnDateText() + "' WHERE BOOK_ID = " + getBookIdText() + " AND ISSUED_DATE = '" + date.getDate() + "'");
                            statement9.close();

                            UIManager.put("OptionPane.background", new Color(255, 240, 243));
                            UIManager.put("Panel.background", new Color(255, 240, 243));
                            UIManager.put("Button.select", new Color(248, 211, 211));
                            UIManager.put("Button.focus", new Color(0, 0, 0, 0));
                            UIManager.put("Button.background", Color.WHITE);
                            UIManager.put("Button.border", new NormalBorder());

                            JButton button3 = new JButton("Back to menu");

                            Object[] option = {button3};

                            button3.addActionListener(ex2 -> {
                                if (ex2.getSource() == button3){
                                    back.doClick();

                                    Window w = SwingUtilities.getWindowAncestor(button3);
                                    w.dispose();
                                }
                            });

                            JOptionPane.showOptionDialog(panel, "What a fast reader! We generated a 10% discount code for future purchases! Code: " + code + ". Expiration date: " + expirationDate.addMonths(2).getDate(), "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);
                        }

                    } catch (InvalidInputException ex) {
                        new MyJOptionPane(ex.getMessage(), "InvalidInputException");
                    } catch (BookNotFoundException ex) {
                        new MyJOptionPane(ex.getMessage(), "BookNotFoundException");
                    } catch (UserNotFoundException ex) {
                        new MyJOptionPane(ex.getMessage(), "UserNotFoundException");
                    } catch (BookNotLentException ex) {
                        new MyJOptionPane(ex.getMessage(), "BookNotLentException");
                    } catch (IncorrectDateFormatException ex) {
                        new MyJOptionPane(ex.getMessage(), "IncorrectDateFormatException");
                    } catch (DateNotValidException ex) {
                        new MyJOptionPane(ex.getMessage(), "DateNotValidException");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"enter");
        panel.getActionMap().put("enter", new AbstractAction(){
            public void actionPerformed(ActionEvent ae) {
                next.doClick();
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
