import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class LibrarianWindow {
    private static JPanel panel;
    private JLabel librarianLabel;
    private JButton lendButton;
    private JButton returnButton;
    private JButton back;

    public LibrarianWindow() {
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

        librarianLabel = new JLabel("Menu ");
        librarianLabel.setForeground(new Color(230, 90, 105));
        librarianLabel.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 50));
        panel.add(librarianLabel);

        lendButton = new LibrarianButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Lend book", 30);
        panel.add(lendButton);

        returnButton = new LibrarianButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "Return book", 30);
        panel.add(returnButton);

        DefaultComboBoxModel<String> addModel = new DefaultComboBoxModel<>();
        addModel.addElement("Section");
        addModel.addElement("Partner");
        addModel.addElement("User");

        JComboBox addBox = (JComboBox) new MyJComboBox(new Color(230, 90, 105), new Color(255, 224, 227), new Color(230, 57, 74), new Color(255, 182, 193), new Color(230, 90, 105), addModel, 50, "Add").makeUI();
        addBox.setBounds(200, 600, 500, 100);
        addBox.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        addBox.setSelectedIndex(-1);
        panel.add(addBox);

        DefaultComboBoxModel<String> viewModel = new DefaultComboBoxModel<>();
        viewModel.addElement("Sections");
        viewModel.addElement("Partners");
        viewModel.addElement("Users");
        viewModel.addElement("Section books");
        viewModel.addElement("Partner books");
        viewModel.addElement("User lent books");
        viewModel.addElement("User returned books");
        viewModel.addElement("User discounts");
        viewModel.addElement("Issued books");
        viewModel.addElement("Available books");

        JComboBox viewBox = (JComboBox) new MyJComboBox(new Color(230, 90, 105), new Color(255, 224, 227), new Color(230, 57, 74), new Color(255, 182, 193), new Color(230, 90, 105), viewModel, 50, "View").makeUI();
        viewBox.setBounds(200, 600, 500, 100);
        viewBox.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        viewBox.setSelectedIndex(-1);
        panel.add(viewBox);

        DefaultComboBoxModel<String> removeModel = new DefaultComboBoxModel<>();
        removeModel.addElement("Book");
        removeModel.addElement("Section");
        removeModel.addElement("Partner");
        removeModel.addElement("User");

        JComboBox removeBox = (JComboBox) new MyJComboBox(new Color(230, 90, 105), new Color(255, 224, 227), new Color(230, 57, 74), new Color(255, 182, 193), new Color(230, 90, 105), removeModel, 50, "Remove").makeUI();
        removeBox.setBounds(200, 600, 500, 100);
        removeBox.setFont(new Font("Book Antiqua", Font.ITALIC|Font.BOLD, 30));
        removeBox.setSelectedIndex(-1);
        panel.add(removeBox);

        back = new MyButton(50, new Color(255, 224, 227), new Color(255, 182, 193), new Color(230, 90, 105), "<Back", 30);
        panel.add(back);

        librarianLabel.setBounds(200, 50, 800, 80);
        lendButton.setBounds(200, 140, 400, 80);
        returnButton.setBounds(200, 280, 400, 80);
        viewBox.setBounds(200, 420, 400, 80);
        addBox.setBounds(200, 560, 400, 80);
        removeBox.setBounds(200, 700, 400, 80);
        back.setBounds(200, 840, 110, 55);

        back.addActionListener(e -> {
            if (e.getSource() == back) {
                GUI gui = new GUI();
                LibrarianLoginWindow librarianLoginWindow = new LibrarianLoginWindow();
                gui.getTabbedPane().add(librarianLoginWindow.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
            }
        });

        lendButton.addActionListener(e -> {
            if (e.getSource() == lendButton) {
                GUI gui = new GUI();
                LendBookWindow lendBookWindow = new LendBookWindow();
                gui.getTabbedPane().add(lendBookWindow.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
            }
        });

        returnButton.addActionListener(e -> {
            if (e.getSource() == returnButton) {
                GUI gui = new GUI();
                ReturnBookWindow returnBookWindow = new ReturnBookWindow();
                gui.getTabbedPane().add(returnBookWindow.getPanel());
                gui.getTabbedPane().setSelectedIndex(1);
                gui.getTabbedPane().remove(panel);
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
