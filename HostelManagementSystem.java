//Username=admin
//password=1234
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HostelManagementSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField usernameField, nameField, roomField;
    private JPasswordField passwordField;
    private JTextArea studentArea;
    private JLabel welcomeLabel;
    private ArrayList<String> studentList = new ArrayList<>();

    public HostelManagementSystem() {
        setTitle("Hostel Management System");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(240, 248, 255));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 241, 210));
        addLabel(panel, "Hostel Login", 180, 30, 24);
        usernameField = addTextField(panel, "Username:", 100);
        passwordField = addPasswordField(panel, "Password:", 140);
        addButton(panel, "OK", 200, e -> login());
        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(220, 248, 198));
        welcomeLabel = addLabel(panel, "Welcome!", 180, 20, 20);
        addButton(panel, "Register Student", 80, e -> registerStudent());
        addButton(panel, "View Students", 130, e -> viewStudents());
        addButton(panel, "Allocate Room", 180, e -> allocateRoom());
        addButton(panel, "Logout", 240, e -> logout());
        return panel;
    }

    private JLabel addLabel(JPanel panel, String text, int x, int y, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setBounds(x, y, 240, 30);
        panel.add(label);
        return label;
    }

    private JTextField addTextField(JPanel panel, String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(120, y, 100, 25);
        panel.add(label);
        JTextField textField = new JTextField();
        textField.setBounds(220, y, 200, 25);
        panel.add(textField);
        return textField;
    }

    private JPasswordField addPasswordField(JPanel panel, String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(120, y, 100, 25);
        panel.add(label);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(220, y, 200, 25);
        panel.add(passwordField);
        return passwordField;
    }

    private void addButton(JPanel panel, String text, int y, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(220, y, 100, 30);
        button.setBackground(new Color(255, 153, 51));
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        panel.add(button);
    }

    private void login() {
        if ("admin".equals(usernameField.getText()) && "1234".equals(new String(passwordField.getPassword()))) {
            welcomeLabel.setText("Welcome, " + usernameField.getText() + "!");
            cardLayout.show(mainPanel, "Dashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password");
        }
    }

    private void registerStudent() {
        JPanel regPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        nameField = new JTextField();
        roomField = new JTextField();
        regPanel.add(new JLabel("Student Name:"));
        regPanel.add(nameField);
        regPanel.add(new JLabel("Room Number:"));
        regPanel.add(roomField);

        if (JOptionPane.showConfirmDialog(this, regPanel, "Register Student", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String room = roomField.getText();
            if (!name.isEmpty() && !room.isEmpty()) {
                studentList.add(name + " - Room " + room);
                JOptionPane.showMessageDialog(this, "Student Registered Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!");
            }
        }
    }

    private void viewStudents() {
        studentArea = new JTextArea();
        if (studentList.isEmpty()) {
            studentArea.setText("No students registered yet.");
        } else {
            for (String s : studentList) {
                studentArea.append(s + "\n");
            }
        }
        studentArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(studentArea), "Registered Students", JOptionPane.INFORMATION_MESSAGE);
    }

    private void allocateRoom() {
        if (studentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students available for room allocation.");
        } else {
            JOptionPane.showMessageDialog(this, "Rooms allocated successfully to all registered students.");
        }
    }

    private void logout() {
        usernameField.setText("");
        passwordField.setText("");
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HostelManagementSystem::new);
    }
}
