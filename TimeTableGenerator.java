import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TimeTableGenerator extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbDays, cbPeriod, cbSubject, cbTeacher;

    public TimeTableGenerator() {
        setTitle("Time Table Generator");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255));

        // Heading
        JLabel heading = new JLabel("Weekly Time Table Generator", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(0, 102, 204));
        add(heading, BorderLayout.NORTH);

        // Combo Box Setup
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] periods = {"1", "2", "3", "4", "5", "6", "7"};
        String[] subjects = {"Math", "Science", "History", "English", "Computer"};
        String[] teachers = {"Mr. A", "Ms. B", "Mr. C", "Ms. D"};

        cbDays = new JComboBox<>(days);
        cbPeriod = new JComboBox<>(periods);
        cbSubject = new JComboBox<>(subjects);
        cbTeacher = new JComboBox<>(teachers);
        JButton btnAssign = new JButton("Assign");

        // Combo Panel
        JPanel comboPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        comboPanel.setBackground(new Color(240, 248, 255));
        for (JComponent component : new JComponent[]{new JLabel("Select Day:"), cbDays,
                new JLabel("Select Period:"), cbPeriod,
                new JLabel("Select Subject:"), cbSubject,
                new JLabel("Select Teacher:"), cbTeacher}) {
            comboPanel.add(component);
        }
        add(comboPanel, BorderLayout.SOUTH);

        // Table Setup
        String[] columnNames = {"Day", "Period 1", "Period 2", "Period 3", "Period 4", "Period 5", "Period 6", "Period 7"};
        model = new DefaultTableModel(new Object[][]{
                {"Monday", "", "", "", "", "", "", ""},
                {"Tuesday", "", "", "", "", "", "", ""},
                {"Wednesday", "", "", "", "", "", "", ""},
                {"Thursday", "", "", "", "", "", "", ""},
                {"Friday", "", "", "", "", "", "", ""}
        }, columnNames);
        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button Action
        btnAssign.addActionListener(e -> assignSubject());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(240, 248, 255));
        btnPanel.add(btnAssign);
        add(btnPanel, BorderLayout.NORTH);
    }

    private void assignSubject() {
        int dayIndex = cbDays.getSelectedIndex();
        int periodIndex = cbPeriod.getSelectedIndex() + 1;
        String subject = cbSubject.getSelectedItem().toString();
        String teacher = cbTeacher.getSelectedItem().toString();
        model.setValueAt(subject + " (" + teacher + ")", dayIndex, periodIndex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimeTableGenerator().setVisible(true));
    }
}
