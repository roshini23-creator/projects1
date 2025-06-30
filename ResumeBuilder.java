import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ResumeBuilder extends JFrame {

    JTextField nameField, emailField, phoneField, skillsField, eduField, expField, projectsField, certField, refField;
    JTextArea previewArea;

    public ResumeBuilder() {
        setTitle("Creative Resume Builder");
        setSize(700, 700);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 250, 240));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel title = new JLabel("Resume Builder");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBounds(240, 10, 300, 30);
        add(title);

        JLabel name = new JLabel("Name:");
        name.setBounds(30, 60, 100, 25);
        name.setForeground(new Color(70, 130, 180)); // Steel Blue
        add(name);

        nameField = new JTextField();
        nameField.setBounds(140, 60, 200, 25);
        add(nameField);

        JLabel email = new JLabel("Email:");
        email.setBounds(30, 100, 100, 25);
        email.setForeground(new Color(70, 130, 180));
        add(email);

        emailField = new JTextField();
        emailField.setBounds(140, 100, 200, 25);
        add(emailField);

        JLabel phone = new JLabel("Phone:");
        phone.setBounds(30, 140, 100, 25);
        phone.setForeground(new Color(70, 130, 180));
        add(phone);

        phoneField = new JTextField();
        phoneField.setBounds(140, 140, 200, 25);
        add(phoneField);

        JLabel skills = new JLabel("Skills:");
        skills.setBounds(30, 180, 100, 25);
        skills.setForeground(new Color(70, 130, 180));
        add(skills);

        skillsField = new JTextField();
        skillsField.setBounds(140, 180, 200, 25);
        add(skillsField);

        JLabel edu = new JLabel("Education:");
        edu.setBounds(30, 220, 100, 25);
        edu.setForeground(new Color(70, 130, 180));
        add(edu);

        eduField = new JTextField();
        eduField.setBounds(140, 220, 200, 25);
        add(eduField);

        JLabel exp = new JLabel("Experience:");
        exp.setBounds(30, 260, 100, 25);
        exp.setForeground(new Color(70, 130, 180));
        add(exp);

        expField = new JTextField();
        expField.setBounds(140, 260, 200, 25);
        add(expField);

        JLabel projects = new JLabel("Projects:");
        projects.setBounds(30, 300, 100, 25);
        projects.setForeground(new Color(70, 130, 180));
        add(projects);

        projectsField = new JTextField();
        projectsField.setBounds(140, 300, 200, 25);
        add(projectsField);

        JLabel cert = new JLabel("Certifications:");
        cert.setBounds(30, 340, 100, 25);
        cert.setForeground(new Color(70, 130, 180));
        add(cert);

        certField = new JTextField();
        certField.setBounds(140, 340, 200, 25);
        add(certField);

        JLabel ref = new JLabel("References:");
        ref.setBounds(30, 380, 100, 25);
        ref.setForeground(new Color(70, 130, 180));
        add(ref);

        refField = new JTextField();
        refField.setBounds(140, 380, 200, 25);
        add(refField);

        JButton generateBtn = new JButton("Generate Resume");
        generateBtn.setBounds(400, 60, 250, 30);
        generateBtn.setBackground(new Color(144, 238, 144));
        add(generateBtn);

        JButton saveBtn = new JButton("Save as Text");
        saveBtn.setBounds(400, 100, 250, 30);
        saveBtn.setBackground(new Color(173, 216, 230));
        add(saveBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBounds(400, 140, 250, 30);
        resetBtn.setBackground(new Color(255, 182, 193));
        add(resetBtn);

        previewArea = new JTextArea();
        previewArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        previewArea.setBorder(BorderFactory.createTitledBorder("Resume Preview"));

        // Add JScrollPane for the JTextArea
        JScrollPane scrollPane = new JScrollPane(previewArea);
        scrollPane.setBounds(30, 420, 620, 220);
        add(scrollPane);

        generateBtn.addActionListener(e -> generateResume());
        saveBtn.addActionListener(e -> saveResume());
        resetBtn.addActionListener(e -> resetFields());

        setVisible(true);
    }

    void generateResume() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String skills = skillsField.getText();
        String education = eduField.getText();
        String experience = expField.getText();
        String projects = projectsField.getText();
        String certifications = certField.getText();
        String references = refField.getText();

        String resume = "==================== RESUME ====================\n";
        resume += "Name: " + name + "\n";
        resume += "Email: " + email + "\n";
        resume += "Phone: " + phone + "\n";
        resume += "Skills: \n" + formatList(skills) + "\n";
        resume += "Education: " + education + "\n";
        resume += "Experience: " + experience + "\n";
        resume += "Projects: \n" + formatList(projects) + "\n";
        resume += "Certifications: \n" + formatList(certifications) + "\n";
        resume += "References: " + references + "\n";
        resume += "===============================================\n";

        previewArea.setText(resume);
    }

    String formatList(String input) {
        String[] items = input.split(",");
        StringBuilder formatted = new StringBuilder();
        for (String item : items) {
            formatted.append(" - ").append(item.trim()).append("\n");
        }
        return formatted.toString();
    }

    void saveResume() {
        try {
            FileWriter writer = new FileWriter("MyResume.txt");
            writer.write(previewArea.getText());
            writer.close();
            JOptionPane.showMessageDialog(this, "Resume saved as MyResume.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving file!");
        }
    }

    void resetFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        skillsField.setText("");
        eduField.setText("");
        expField.setText("");
        projectsField.setText("");
        certField.setText("");
        refField.setText("");
        previewArea.setText("");
    }

    public static void main(String[] args) {
        new ResumeBuilder();
    }
}
