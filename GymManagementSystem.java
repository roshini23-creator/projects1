import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GymManagementSystem extends JFrame {
    // Colors
    private final Color DARK_BLUE = new Color(0, 51, 102);
    private final Color LIGHT_BLUE = new Color(204, 229, 255);
    private final Color GREEN = new Color(0, 153, 51);
    private final Color RED = new Color(204, 0, 0);
    
    // Components
    private JTextArea displayArea;
    private JTextField nameField, ageField, phoneField, planField;
    private DefaultListModel<String> memberListModel;
    private JList<String> memberList;
    
    // Data
    private HashMap<String, Member> members = new HashMap<>();
    
    public GymManagementSystem() {
        // Frame setup
        setTitle("Gym Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(LIGHT_BLUE);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel titleLabel = new JLabel("GYM MANAGEMENT SYSTEM");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBackground(LIGHT_BLUE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Input fields
        nameField = createInputField("Name:");
        ageField = createInputField("Age:");
        phoneField = createInputField("Phone:");
        planField = createInputField("Plan (Basic/Premium):");
        
        inputPanel.add(createLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(createLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(createLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(createLabel("Plan:"));
        inputPanel.add(planField);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(LIGHT_BLUE);
        
        JButton addButton = createButton("Add Member", GREEN);
        addButton.addActionListener(e -> addMember());
        
        JButton removeButton = createButton("Remove Member", RED);
        removeButton.addActionListener(e -> removeMember());
        
        JButton viewButton = createButton("View Details", DARK_BLUE);
        viewButton.addActionListener(e -> viewMemberDetails());
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);
        
        // Members List
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(LIGHT_BLUE);
        listPanel.setBorder(BorderFactory.createTitledBorder("Members"));
        
        memberListModel = new DefaultListModel<>();
        memberList = new JList<>(memberListModel);
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberList.setBackground(Color.WHITE);
        listPanel.add(new JScrollPane(memberList), BorderLayout.CENTER);
        
        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Member Details"));
        
        // Layout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(LIGHT_BLUE);
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(LIGHT_BLUE);
        rightPanel.add(listPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }
    
    // Helper methods
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(DARK_BLUE);
        return label;
    }
    
    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField();
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(DARK_BLUE, 1));
        return field;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }
    
    // Member operations
    private void addMember() {
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String phone = phoneField.getText().trim();
        String plan = planField.getText().trim();
        
        if (name.isEmpty() || ageStr.isEmpty() || phone.isEmpty() || plan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int age = Integer.parseInt(ageStr);
            String id = "M" + (members.size() + 1);
            members.put(id, new Member(id, name, age, phone, plan));
            memberListModel.addElement(id + " - " + name);
            
            clearFields();
            displayArea.setText("Member added successfully!\nID: " + id + "\nName: " + name);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removeMember() {
        int selectedIndex = memberList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String selected = memberListModel.get(selectedIndex);
        String id = selected.split(" - ")[0];
        
        members.remove(id);
        memberListModel.remove(selectedIndex);
        displayArea.setText("Member removed: " + selected);
    }
    
    private void viewMemberDetails() {
        int selectedIndex = memberList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String selected = memberListModel.get(selectedIndex);
        String id = selected.split(" - ")[0];
        Member member = members.get(id);
        
        displayArea.setText("Member Details:\n" +
                           "ID: " + member.id + "\n" +
                           "Name: " + member.name + "\n" +
                           "Age: " + member.age + "\n" +
                           "Phone: " + member.phone + "\n" +
                           "Plan: " + member.plan + "\n" +
                           "Join Date: " + member.joinDate);
    }
    
    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        planField.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymManagementSystem app = new GymManagementSystem();
            app.setVisible(true);
        });
    }
    
    // Member class
    private class Member {
        String id;
        String name;
        int age;
        String phone;
        String plan;
        String joinDate;
        
        public Member(String id, String name, int age, String phone, String plan) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.phone = phone;
            this.plan = plan;
            this.joinDate = new Date().toString();
        }
    }
}
