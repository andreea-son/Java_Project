import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReturnBookPaymentWindow {
    private static JPanel panel;
    private JLabel paymentLabel;
    private JLabel totalPrice;
    private JButton back;
    private JButton next;
    private JLabel pictureLabel;
    private JRadioButton card;
    private JRadioButton cash;
    private JLabel paymentMethod;
    private Float tempPriceFloat;
    private String tempPriceFloatText;
    private long tempNumOfDays;
    private Long tempPrice;
    private int count = 0;
    private String cardOrCash = "card";

    private GetConnection conn = GetConnection.getInstance();
    ReturnBookWindow returnBookWindow = new ReturnBookWindow();

    public ReturnBookPaymentWindow() {
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

        paymentLabel = new JLabel("Payment ");
        paymentLabel.setForeground(new Color(230, 90, 105));
        paymentLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 50));
        panel.add(paymentLabel);

        pictureLabel = new JLabel(new ImageIcon("Resources/images/line_picture.png"));
        panel.add(pictureLabel);

        tempNumOfDays = returnBookWindow.getExceededDays();
        tempPrice = 3 * tempNumOfDays;
        tempPriceFloat = tempPrice.floatValue();
        tempPriceFloatText = String.format("%.02f", tempPriceFloat);
        int cpy = Math.round(tempPriceFloat);
        while(cpy != 0){
            cpy = cpy / 10;
            ++count;
        }
        if(count == 3){
            totalPrice = new JLabel("Total          " + tempPriceFloatText + " ron");
        }
        else if(count == 2){
            totalPrice = new JLabel("Total            " + tempPriceFloatText + " ron");
        }
        else if(count == 1){
            totalPrice = new JLabel("Total              " + tempPriceFloatText + " ron");
        }

        totalPrice.setForeground(new Color(230, 90, 105));
        totalPrice.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(totalPrice);

        paymentMethod = new JLabel("Payment method");
        paymentMethod.setForeground(new Color(230, 90, 105));
        paymentMethod.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 40));
        panel.add(paymentMethod);

        card = new JRadioButton("Card");
        card.setFocusPainted(false);
        card.setForeground(new Color(230, 90, 105));
        card.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        card.setIcon(new ImageIcon("Resources/images/radio_button_pressed.png"));
        card.setContentAreaFilled(false);
        panel.add(card);

        cash = new JRadioButton("Cash");
        cash.setForeground(new Color(230, 90, 105));
        cash.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        cash.setFocusPainted(false);
        cash.setIcon(new ImageIcon("Resources/images/radio_button.png"));
        cash.setContentAreaFilled(false);
        panel.add(cash);

        ButtonGroup group = new ButtonGroup();
        group.add(card);
        group.add(cash);

        back = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "<Back", 30);
        panel.add(back);

        next = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Charge>", 30);
        panel.add(next);

        paymentLabel.setBounds(200, 50, 800, 80);
        paymentMethod.setBounds(200, 150, 500, 50);
        card.setBounds(200, 220, 200, 35);
        cash.setBounds(200, 270, 200, 35);
        pictureLabel.setBounds(200, 290, 300, 200);
        totalPrice.setBounds(200, 360, 400, 35);
        back.setBounds(200, 410, 110, 55);
        next.setBounds(350, 410, 150, 55);

        cash.addActionListener(e -> {
            if (e.getSource() == cash) {
                cash.setIcon(new ImageIcon("Resources/images/radio_button_pressed.png"));
                card.setIcon(new ImageIcon("Resources/images/radio_button.png"));
                cardOrCash = "cash";
            }
        });

        card.addActionListener(e -> {
            if (e.getSource() == card) {
                card.setIcon(new ImageIcon("Resources/images/radio_button_pressed.png"));
                cash.setIcon(new ImageIcon("Resources/images/radio_button.png"));
                cardOrCash = "card";
            }
        });

        back.addActionListener(e -> {
            if (e.getSource() == back) {
                GUI gui = new GUI();
                gui.getTabbedPane().add(returnBookWindow.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
            }
        });

        next.addActionListener(e -> {
            if (e.getSource() == next) {
                next.setEnabled(false);
                Connection connection = conn.getConnection();
                try {
                    int invoiceId = 0;
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT MAX(INVOICE_ID) FROM INVOICE");

                    if(result.next()){
                        invoiceId = result.getInt(1);
                    }

                    ++invoiceId;

                    Statement statement3 = connection.createStatement();
                    statement3.executeUpdate("INSERT INTO INVOICE VALUES(" + invoiceId + ", '" + returnBookWindow.getActualReturnDateText() + "', " + returnBookWindow.getUserIdText() + ", '" + cardOrCash + "', 'FALSE')");
                    statement3.close();

                    Statement statement4 = connection.createStatement();
                    statement4.executeUpdate("UPDATE BOOK SET IS_LENT = 'FALSE' WHERE BOOK_ID = " + returnBookWindow.getBookIdText());
                    statement4.close();

                    MyDate date = new MyDate();
                    Statement statement5 = connection.createStatement();
                    ResultSet result5 = statement5.executeQuery("SELECT ISSUED_DATE FROM LENT_BOOK WHERE IS_LENT = 'TRUE'");
                    if(result5.next()){
                        date.setDate(result5.getString(1));
                    }
                    statement5.close();

                    Statement statement6 = connection.createStatement();
                    statement6.executeUpdate("UPDATE LENT_BOOK SET IS_LENT = 'FALSE', EXCEEDED_DAYS = NULL, EXCEEDED_PRICE = NULL, RETURN_DATE = '" + returnBookWindow.getActualReturnDateText() + "' WHERE BOOK_ID = " + returnBookWindow.getBookIdText() + " AND ISSUED_DATE = '" + date.getDate() + "'");
                    statement6.close();

                    GUI gui = new GUI();
                    JDialog jDialog = new JDialog(gui, "Invoice");
                    jDialog.setLayout(null);

                    jDialog.getContentPane().setBackground(new Color(255, 240, 243));

                    JLabel invoiceNo = new JLabel("Invoice no. " + invoiceId);
                    invoiceNo.setForeground(new Color(230, 90, 105));
                    invoiceNo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(returnBookWindow.getActualReturnDateText());
                    String dayOfTheWeek = new SimpleDateFormat("EEE").format(date1);

                    JLabel date2 = new JLabel(dayOfTheWeek + ", " + returnBookWindow.getActualReturnDateText());
                    date2.setForeground(new Color(230, 90, 105));
                    date2.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel line1 = new JLabel("---------------------------------------");
                    line1.setForeground(new Color(230, 90, 105));
                    line1.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel purchaseInfo = new JLabel(" 1 x " + tempPriceFloatText + " ron");
                    purchaseInfo.setForeground(new Color(230, 90, 105));
                    purchaseInfo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel bookInfo = new JLabel(returnBookWindow.getTitle() + " by " + returnBookWindow.getAuthor() + " x " + returnBookWindow.getExceededDays() + " days");
                    bookInfo.setForeground(new Color(230, 90, 105));
                    bookInfo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel line2 = new JLabel("---------------------------------------");
                    line2.setForeground(new Color(230, 90, 105));
                    line2.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel price = new JLabel("Total " + tempPriceFloatText + " ron");
                    price.setForeground(new Color(230, 90, 105));
                    price.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel line3 = new JLabel("---------------------------------------");
                    line3.setForeground(new Color(230, 90, 105));
                    line3.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel paymentInfo = new JLabel("Paid with " + cardOrCash.toUpperCase());
                    paymentInfo.setForeground(new Color(230, 90, 105));
                    paymentInfo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JButton button2 = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Print", 30);

                    invoiceNo.setBounds(100, 50, 300, 35);
                    date2.setBounds(100, 100, 400, 35);
                    line1.setBounds(100, 150, 600, 35);
                    purchaseInfo.setBounds(100, 200, 200, 35);
                    bookInfo.setBounds(100, 250, 1000, 35);
                    line2.setBounds(100, 300, 600, 35);
                    price.setBounds(100, 350, 300, 35);
                    line3.setBounds(100, 400, 600, 35);
                    paymentInfo.setBounds(100, 450, 400, 35);
                    button2.setBounds(100, 500, 150, 35);

                    jDialog.add(invoiceNo);
                    jDialog.add(date2);
                    jDialog.add(line1);
                    jDialog.add(purchaseInfo);
                    jDialog.add(bookInfo);
                    jDialog.add(line2);
                    jDialog.add(price);
                    jDialog.add(line3);
                    jDialog.add(paymentInfo);
                    jDialog.add(button2);
                    jDialog.setSize(1100, 700);
                    jDialog.setLocationRelativeTo(null);
                    jDialog.setVisible(true);

                    button2.addActionListener(ex -> {
                        if (ex.getSource() == button2) {

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
                                    LibrarianWindow librarianWindow = new LibrarianWindow();
                                    gui.getTabbedPane().add(librarianWindow.getPanel());
                                    gui.getTabbedPane().setSelectedIndex(1);
                                    gui.getTabbedPane().remove(panel);

                                    Window w = SwingUtilities.getWindowAncestor(button3);
                                    w.dispose();
                                }
                            });

                            jDialog.dispose();
                            JOptionPane.showOptionDialog(panel, "The returned book has been recorded in the system successfully!", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);
                        }
                    });

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ParseException ex) {
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