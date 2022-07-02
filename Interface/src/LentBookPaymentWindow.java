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

public class LentBookPaymentWindow {
    private static JPanel panel;
    private JLabel paymentLabel;
    private JLabel codeLabel;
    private JTextField code;
    private JLabel totalPrice;
    private JLabel discountValue;
    private JLabel noDiscount;
    private JLabel _10percentDiscount;
    private JToggleButton button1;
    private JLabel apply;
    private JButton back;
    private JButton next;
    private JLabel pictureLabel;
    private JRadioButton card;
    private JRadioButton cash;
    private JLabel paymentMethod;
    private JLabel tooltip1;
    private Float tempPriceFloat;
    private String tempPriceFloatText;
    private String codeText = "";
    private Float tempDiscount;
    private String tempDiscountText;
    private int tempNumOfDays;
    private Integer tempPrice;
    private int count = 0;
    private String cardOrCash = "card";

    private GetConnection conn = GetConnection.getInstance();
    private LendBookWindow lendBookWindow = new LendBookWindow();

    public LentBookPaymentWindow() {
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

        apply = new JLabel("Apply discount");
        apply.setForeground(new Color(230, 90, 105));
        apply.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 40));
        panel.add(apply);

        button1 = new JToggleButton(new ImageIcon("Resources/images/plus_logo.png"));
        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        panel.add(button1);

        codeLabel = new JLabel("Discount code ");
        codeLabel.setForeground(new Color(230, 90, 105));
        codeLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(codeLabel);

        code = new MyJTextField(100);
        panel.add(code);

        tooltip1 = new JLabel("Apply/remove discount (maximum one discount per purchase)");
        tooltip1.setOpaque(true);
        tooltip1.setBorder(new RoundedCornerBorder(20));
        tooltip1.setForeground(new Color(230, 90, 105));
        tooltip1.setBackground(Color.WHITE);
        tooltip1.setSize(tooltip1.getPreferredSize());
        panel.add(tooltip1);
        tooltip1.setVisible(false);

        pictureLabel = new JLabel(new ImageIcon("Resources/images/line_picture.png"));
        panel.add(pictureLabel);

        tempNumOfDays = Integer.parseInt(lendBookWindow.getNumOfDaysText());
        tempPrice = 2 * tempNumOfDays;
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

        noDiscount = new JLabel("NO DISCOUNT");
        noDiscount.setForeground(new Color(230, 90, 105));
        noDiscount.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 15));
        panel.add(noDiscount);

        _10percentDiscount = new JLabel("10% DISCOUNT");
        _10percentDiscount.setForeground(new Color(230, 90, 105));
        _10percentDiscount.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 15));
        _10percentDiscount.setVisible(false);
        panel.add(_10percentDiscount);

        discountValue = new JLabel("Discount ");
        discountValue.setForeground(new Color(230, 90, 105));
        discountValue.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        panel.add(discountValue);

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
        apply.setBounds(200, 320, 400, 45);
        codeLabel.setBounds(200, 390, 400, 35);
        code.setBounds(400, 390, 150, 35);
        button1.setBounds(560, 390, 35, 35);
        tooltip1.setBounds(560, 380 + tooltip1.getHeight(), tooltip1.getWidth(), tooltip1.getHeight());
        pictureLabel.setBounds(200, 400, 300, 200);
        totalPrice.setBounds(200, 470, 400, 35);
        discountValue.setBounds(200, 520, 400, 35);
        noDiscount.setBounds(380, 520, 300, 35);
        _10percentDiscount.setBounds(375, 520, 300, 35);
        back.setBounds(200, 570, 110, 55);
        next.setBounds(350, 570, 150, 55);

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

        button1.addItemListener(itemEvent -> {
            int state = itemEvent.getStateChange();

            if (state == ItemEvent.SELECTED) {
                Connection connection = conn.getConnection();
                try {
                    try {
                        button1.setIcon(new ImageIcon("Resources/images/minus_logo.png"));
                        codeText = code.getText();
                        code.setEnabled(false);
                        code.setDisabledTextColor(Color.BLACK);
                        if (codeText.isEmpty()) {
                            throw new InvalidInputException("Please fill in all the required information!");
                        }

                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery("SELECT EXP_DATE FROM DISCOUNT WHERE USER_ID = " + lendBookWindow.getUserIdText() + " AND IS_USED = 'FALSE' AND CODE = '" + codeText + "'");

                        String expirationDate;

                        if(result.next()){
                            expirationDate = result.getString(1);
                        }
                        else{
                            codeText = "";
                            throw new InvalidCodeException("This code doesn't exist on this user's discounts' list or it has already been used!");
                        }

                        MyDate tempDate = new MyDate();
                        MyDate issuedDate = new MyDate();
                        issuedDate.setDate(lendBookWindow.getIssuedDateText());
                        tempDate.setDate(expirationDate);

                        if(issuedDate.isBiggerThan(tempDate)){
                            codeText = "";
                            throw new InvalidCodeException("This code has already expired!");
                        }

                        tempDiscount = tempPriceFloat / 10;
                        tempPriceFloat = tempPriceFloat - tempDiscount;
                        tempPriceFloatText = String.format("%.02f", tempPriceFloat);
                        tempDiscountText = String.format("%.02f", tempDiscount);
                        noDiscount.setVisible(false);
                        _10percentDiscount.setVisible(true);

                        if (count == 3) {
                            totalPrice.setText("Total          " + tempPriceFloatText + " ron");
                        } else if (count == 2) {
                            totalPrice.setText("Total            " + tempPriceFloatText + " ron");
                        } else if (count == 1) {
                            totalPrice.setText("Total              " + tempPriceFloatText + " ron");
                        }
                    } catch (InvalidInputException ex) {
                        new MyJOptionPane(ex.getMessage(), "InvalidInputException");
                    } catch (InvalidCodeException ex) {
                        new MyJOptionPane(ex.getMessage(), "InvalidCodeException");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else {
                code.setEnabled(true);
                code.setText("");
                codeText = "";
                button1.setIcon(new ImageIcon("Resources/images/plus_logo.png"));
                tempPriceFloat = tempPrice.floatValue();
                tempPriceFloatText = String.format("%.02f", tempPriceFloat);
                noDiscount.setVisible(true);
                _10percentDiscount.setVisible(false);
                if (count == 3) {
                    totalPrice.setText("Total          " + tempPriceFloatText + " ron");
                } else if (count == 2) {
                    totalPrice.setText("Total            " + tempPriceFloatText + " ron");
                } else if (count == 1) {
                    totalPrice.setText("Total              " + tempPriceFloatText + " ron");
                }
            }
        });

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tooltip1.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tooltip1.setVisible(false);
            }
        });

        back.addActionListener(e -> {
            if (e.getSource() == back) {
                GUI gui = new GUI();
                gui.getTabbedPane().add(lendBookWindow.getPanel());
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

                    if(!codeText.isEmpty()) {
                        Statement statement1 = connection.createStatement();
                        statement1.executeUpdate("UPDATE DISCOUNT SET IS_USED = 'TRUE' WHERE CODE = '" + codeText + "'");
                        statement1.close();

                        Statement statement2 = connection.createStatement();
                        statement2.executeUpdate("INSERT INTO INVOICE VALUES(" + invoiceId + ", '" + lendBookWindow.getIssuedDateText() + "', " + lendBookWindow.getUserIdText() + ", '" + cardOrCash + "', 'TRUE')");
                        statement2.close();
                    }
                    else{
                        Statement statement3 = connection.createStatement();
                        statement3.executeUpdate("INSERT INTO INVOICE VALUES(" + invoiceId + ", '" + lendBookWindow.getIssuedDateText() + "', " + lendBookWindow.getUserIdText() + ", '" + cardOrCash + "', 'FALSE')");
                        statement3.close();
                    }

                    Statement statement4 = connection.createStatement();
                    statement4.executeUpdate("UPDATE BOOK SET IS_LENT = 'TRUE' WHERE BOOK_ID = " + lendBookWindow.getBookIdText());
                    statement4.close();

                    MyDate tempDate = new MyDate();
                    tempDate.setDate(lendBookWindow.getIssuedDateText());
                    Long numOfDays = Long.parseLong(lendBookWindow.getNumOfDaysText());
                    Statement statement5 = connection.createStatement();
                    statement5.executeUpdate("INSERT INTO LENT_BOOK VALUES(" + lendBookWindow.getBookIdText() + ", '" + lendBookWindow.getAuthor() + "', '" + lendBookWindow.getTitle() + "', '" + lendBookWindow.getDescription() + "', '" + lendBookWindow.getIssuedDateText() + "', " + lendBookWindow.getNumOfDaysText() + ", '" + tempDate.addDays(numOfDays).getDate() + "', 'TRUE', " + lendBookWindow.getSectionId() + ", " + lendBookWindow.getPartnerId() + ", " + lendBookWindow.getUserIdText() + ", " + tempPriceFloatText + ", 0, 0 )");
                    statement5.close();

                    GUI gui = new GUI();
                    JDialog jDialog = new JDialog(gui, "Invoice");
                    jDialog.setLayout(null);

                    jDialog.getContentPane().setBackground(new Color(255, 240, 243));

                    JLabel invoiceNo = new JLabel("Invoice no. " + invoiceId);
                    invoiceNo.setForeground(new Color(230, 90, 105));
                    invoiceNo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(lendBookWindow.getIssuedDateText());
                    String dayOfTheWeek = new SimpleDateFormat("EEE").format(date1);

                    JLabel date = new JLabel(dayOfTheWeek + ", " + lendBookWindow.getIssuedDateText());
                    date.setForeground(new Color(230, 90, 105));
                    date.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel line1 = new JLabel("---------------------------------------");
                    line1.setForeground(new Color(230, 90, 105));
                    line1.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel purchaseInfo = new JLabel(" 1 x " + tempPriceFloatText + " ron");
                    purchaseInfo.setForeground(new Color(230, 90, 105));
                    purchaseInfo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel bookInfo = new JLabel(lendBookWindow.getTitle() + " by " + lendBookWindow.getAuthor() + " x " + lendBookWindow.getNumOfDaysText() + " days");
                    bookInfo.setForeground(new Color(230, 90, 105));
                    bookInfo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel line2 = new JLabel("---------------------------------------");
                    line2.setForeground(new Color(230, 90, 105));
                    line2.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel price = new JLabel("Total " + tempPriceFloatText + " ron");
                    price.setForeground(new Color(230, 90, 105));
                    price.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel discount1 = new JLabel("Discount ");
                    discount1.setForeground(new Color(230, 90, 105));
                    discount1.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel noDiscount1 = new JLabel("NO DISCOUNT");
                    noDiscount1.setForeground(new Color(230, 90, 105));
                    noDiscount1.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
                    noDiscount1.setVisible(false);

                    JLabel _10percentDiscount1 = new JLabel("10% DISCOUNT");
                    _10percentDiscount1.setForeground(new Color(230, 90, 105));
                    _10percentDiscount1.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
                    _10percentDiscount1.setVisible(false);

                    JLabel line3 = new JLabel("---------------------------------------");
                    line3.setForeground(new Color(230, 90, 105));
                    line3.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel paymentInfo = new JLabel("Paid with " + cardOrCash.toUpperCase());
                    paymentInfo.setForeground(new Color(230, 90, 105));
                    paymentInfo.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));

                    JLabel info = new JLabel("Note: For every day that exceeds the return date you have to pay an additional 3 ron");
                    info.setForeground(new Color(230, 90, 105));
                    info.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 20));

                    JButton button2 = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Print", 30);

                    invoiceNo.setBounds(100, 50, 300, 35);
                    date.setBounds(100, 100, 400, 35);
                    line1.setBounds(100, 150, 600, 35);
                    purchaseInfo.setBounds(100, 200, 200, 35);
                    bookInfo.setBounds(100, 250, 1000, 35);
                    line2.setBounds(100, 300, 600, 35);
                    price.setBounds(100, 350, 300, 35);
                    discount1.setBounds(100, 400, 200, 35);
                    noDiscount1.setBounds(230, 400, 300, 35);
                    _10percentDiscount1.setBounds(230, 400, 300, 35);
                    line3.setBounds(100, 450, 600, 35);
                    paymentInfo.setBounds(100, 500, 400, 35);
                    info.setBounds(100, 550, 1000, 35);
                    button2.setBounds(100, 600, 150, 35);

                    jDialog.add(invoiceNo);
                    jDialog.add(date);
                    jDialog.add(line1);
                    jDialog.add(purchaseInfo);
                    jDialog.add(bookInfo);
                    jDialog.add(line2);
                    jDialog.add(price);
                    jDialog.add(discount1);
                    jDialog.add(noDiscount1);
                    jDialog.add(_10percentDiscount1);
                    jDialog.add(line3);
                    jDialog.add(paymentInfo);
                    jDialog.add(info);
                    jDialog.add(button2);
                    jDialog.setSize(1100, 800);
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
                            JOptionPane.showOptionDialog(panel, "The lent book has been recorded in the system successfully!", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);
                        }
                    });

                    if(codeText.isEmpty()){
                        noDiscount1.setVisible(true);
                        _10percentDiscount1.setVisible(false);
                    }
                    else {
                        noDiscount1.setVisible(false);
                        _10percentDiscount1.setVisible(true);
                    }

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

