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

public class LendBookWindow {
    private static JPanel panel;
    private JLabel lendLabel;
    private JLabel bookLabel;
    private JTextField bookId;
    private JLabel userLabel;
    private JTextField userId;
    private JLabel dateLabel;
    private JTextField issuedDate;
    private JLabel daysLabel;
    private JTextField numOfDays;
    private JButton button1;
    private JButton button2;
    private ImageIcon myIcon;
    private JLabel tooltip1;
    private JLabel tooltip2;
    private JButton back;
    private JButton next;
    private MyDate lastDate;
    private MyDate tempDate;
    private static String bookIdText;
    private static String userIdText;
    private static String issuedDateText;
    private static String numOfDaysText;
    private static String author;
    private static String title;
    private static String description;
    private static int partnerId;
    private static int sectionId;
    private ArrayList <String> date = new ArrayList <>();
    private MyLastDate myLastDate = new MyLastDate();
    private GetConnection conn = GetConnection.getInstance();

    public LendBookWindow() {
    }

    public String getBookIdText() {
        return bookIdText;
    }

    public String getUserIdText() {
        return userIdText;
    }

    public String getIssuedDateText() {
        return issuedDateText;
    }

    public String getNumOfDaysText() {
        return numOfDaysText;
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

    public static void setBookIdText(String bookIdText) {
        LendBookWindow.bookIdText = bookIdText;
    }

    public static void setUserIdText(String userIdText) {
        LendBookWindow.userIdText = userIdText;
    }

    public static void setIssuedDateText(String issuedDateText) {
        LendBookWindow.issuedDateText = issuedDateText;
    }

    public static void setNumOfDaysText(String numOfDaysText) {
        LendBookWindow.numOfDaysText = numOfDaysText;
    }

    public static void setAuthor(String author) {
        LendBookWindow.author = author;
    }

    public static void setTitle(String title) {
        LendBookWindow.title = title;
    }

    public static void setDescription(String description) {
        LendBookWindow.description = description;
    }

    public static void setPartnerId(int partnerId) {
        LendBookWindow.partnerId = partnerId;
    }

    public static void setSectionId(int sectionId) {
        LendBookWindow.sectionId = sectionId;
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

        lendLabel = new JLabel("Lend Book ");
        lendLabel.setForeground(new Color(230, 90, 105));
        lendLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 50));
        panel.add(lendLabel);

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

        dateLabel = new JLabel("Issue date ");
        dateLabel.setForeground(new Color(230, 90, 105));
        dateLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(dateLabel);

        issuedDate = new MyJTextField(100);
        panel.add(issuedDate);

        daysLabel = new JLabel("Number of days to keep the book ");
        panel.add(daysLabel);
        daysLabel.setFocusTraversalKeysEnabled(false);
        daysLabel.setForeground(new Color(230, 90, 105));
        daysLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

        numOfDays = new MyJTextField(100);
        panel.add(numOfDays);

        tooltip1 = new JLabel("Format: yyyy-MM-dd");
        tooltip1.setOpaque(true);
        tooltip1.setBorder(new RoundedCornerBorder(20));
        tooltip1.setForeground(new Color(230, 90, 105));
        tooltip1.setBackground(Color.WHITE);
        tooltip1.setSize(tooltip1.getPreferredSize());
        tooltip1.setBounds(340, 380 + tooltip1.getHeight(), tooltip1.getWidth(), tooltip1.getHeight());
        panel.add(tooltip1);
        tooltip1.setVisible(false);

        tooltip2 = new JLabel("Maximum: 60 days");
        tooltip2.setOpaque(true);
        tooltip2.setBorder(new RoundedCornerBorder(20));
        tooltip2.setForeground(new Color(230, 90, 105));
        tooltip2.setBackground(Color.WHITE);
        tooltip2.setSize(tooltip2.getPreferredSize());
        tooltip2.setBounds(650, 480 + tooltip2.getHeight(), tooltip2.getWidth(), tooltip2.getHeight());
        panel.add(tooltip2);
        tooltip2.setVisible(false);

        myIcon = new ImageIcon("Resources/images/info_logo.png");

        button1 = new JButton(myIcon);
        button1.setBorder(new EmptyBorder(15, 15, 15, 15));
        button1.setBackground(new Color(255, 240, 243));
        button1.setContentAreaFilled(false);
        panel.add(button1);
        button1.setFocusPainted(false);

        button2 = new JButton(myIcon);
        button2.setBorder(new EmptyBorder(15, 15, 15, 15));
        button2.setBackground(new Color(255, 240, 243));
        button2.setContentAreaFilled(false);
        panel.add(button2);
        button2.setFocusPainted(false);

        back = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "<Back", 30);
        panel.add(back);

        next = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Payment>", 30);
        panel.add(next);

        lendLabel.setBounds(200, 50, 800, 80);
        bookLabel.setBounds(200, 180, 400, 35);
        bookId.setBounds(700, 180, 400, 35);
        userLabel.setBounds(200, 280, 400, 35);
        userId.setBounds(700, 280, 400, 35);
        dateLabel.setBounds(200, 380, 400, 35);
        button1.setBounds(340, 380, 32, 35);
        issuedDate.setBounds(700, 380, 400, 35);
        daysLabel.setBounds(200, 480, 600, 35);
        button2.setBounds(650, 480, 32, 35);
        numOfDays.setBounds(700, 480, 400, 35);
        back.setBounds(200, 540, 110, 55);
        next.setBounds(350, 540, 150, 55);

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

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setIcon(new ImageIcon("Resources/images/info_logo_pressed.png"));
                tooltip2.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setIcon(myIcon);
                tooltip2.setVisible(false);
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
                        setIssuedDateText(issuedDate.getText());
                        if (issuedDateText.isEmpty()) {
                            throw new InvalidInputException("Please fill in all the required information!");
                        }
                        setNumOfDaysText(numOfDays.getText());
                        if (numOfDaysText.isEmpty()) {
                            throw new InvalidInputException("Please fill in all the required information!");
                        }

                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM BOOK WHERE BOOK_ID = " + bookIdText);

                        int bookExists = 0;

                        if(result.next()) {
                            bookExists = result.getInt(1);
                        }

                        if (bookExists == 0) {
                            throw new BookNotFoundException("Could not find book with this ID!");
                        }
                        statement.close();

                        Statement statement1 = connection.createStatement();
                        ResultSet result1 = statement1.executeQuery("SELECT * FROM BOOK WHERE BOOK_ID = " + bookIdText);

                        if(result1.next()){
                            boolean isLent = Boolean.parseBoolean(result1.getString(5));
                            if(isLent){
                                throw new BookAlreadyLentException("This book is not available for lending right now!");
                            }
                        }
                        statement1.close();

                        Statement statement2 = connection.createStatement();
                        ResultSet result2 = statement2.executeQuery("SELECT COUNT(*) FROM \"USER\" WHERE USER_ID = " + userIdText);

                        int userExists = 0;

                        if(result2.next()) {
                            userExists = result2.getInt(1);
                        }

                        if (userExists == 0) {
                            throw new UserNotFoundException("Could not find user with this ID!");
                        }
                        statement2.close();

                        if (!issuedDateText.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                            throw new IncorrectDateFormatException("This date doesn't match the required date format!");
                        }
                        lastDate = new MyDate();
                        tempDate = new MyDate();

                        lastDate.setDate(myLastDate.getLastDate().getDate());
                        tempDate.setDate(issuedDateText);

                        if(lastDate.isBiggerThan(tempDate)){
                            throw new DateNotValidException("Please enter a date equal to or grater than " + lastDate.getDate());
                        }

                        int days = Integer.parseInt(numOfDaysText);
                        if (days > 60) {
                            throw new MaxNumOfDaysException("Too many days!");
                        }

                        Statement statement3 = connection.createStatement();
                        ResultSet result3 = statement3.executeQuery("SELECT * FROM BOOK WHERE BOOK_ID = " + bookIdText);

                        if(result3.next()){
                            setAuthor(result3.getString(2));
                            setTitle(result3.getString(3));
                            setDescription(result3.getString(4));
                            setSectionId(result3.getInt(6));
                            setPartnerId(result3.getInt(7));
                        }

                        statement3.close();

                        GUI gui = new GUI();
                        LentBookPaymentWindow lentBookPaymentWindow = new LentBookPaymentWindow();
                        gui.getTabbedPane().add(lentBookPaymentWindow.getPanel());
                        gui.getTabbedPane().setSelectedIndex(1);
                        gui.getTabbedPane().remove(panel);

                    } catch (InvalidInputException ex) {
                        new MyJOptionPane(ex.getMessage(), "InvalidInputException");
                    } catch (BookNotFoundException ex) {
                        new MyJOptionPane(ex.getMessage(), "BookNotFoundException");
                    } catch (BookAlreadyLentException ex) {
                        new MyJOptionPane(ex.getMessage(), "BookAlreadyLentException");
                    } catch (UserNotFoundException ex) {
                        new MyJOptionPane(ex.getMessage(), "UserNotFoundException");
                    } catch (IncorrectDateFormatException ex) {
                        new MyJOptionPane(ex.getMessage(), "IncorrectDateFormatException");
                    } catch (DateNotValidException ex) {
                        new MyJOptionPane(ex.getMessage(), "DateNotValidException");
                    } catch (MaxNumOfDaysException ex) {
                        new MyJOptionPane(ex.getMessage(), "MaxNumOfDaysException");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.exit(1);
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
