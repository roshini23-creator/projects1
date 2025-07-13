import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class CrimeRecord {
    String id, crimeType, location, accusedName, officer;

    CrimeRecord(String id, String crimeType, String location, String accusedName, String officer) {
        this.id = id;
        this.crimeType = crimeType;
        this.location = location;
        this.accusedName = accusedName;
        this.officer = officer;
    }
}

public class CrimeRecordSystem extends JFrame {
    private JTextField tfId, tfLocation, tfAccused, tfOfficer, tfSearch;
    private JComboBox<String> cbCrimeType;
    private DefaultTableModel tableModel;
    private ArrayList<CrimeRecord> crimeList = new ArrayList<>();

    public CrimeRecordSystem() {
        setTitle("Crime Record Management System");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Crime Record Management System", JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 22));
        title.setForeground(Color.RED);
        add(title, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        tfId = new JTextField(); cbCrimeType = new JComboBox<>(new String[]{"Theft", "Murder", "Fraud", "Assault", "Cybercrime"});
        tfLocation = new JTextField(); tfAccused = new JTextField(); tfOfficer = new JTextField(); tfSearch = new JTextField();
        formPanel.add(new JLabel("Crime ID:")); formPanel.add(tfId);
        formPanel.add(new JLabel("Crime Type:")); formPanel.add(cbCrimeType);
        formPanel.add(new JLabel("Location:")); formPanel.add(tfLocation);
        formPanel.add(new JLabel("Accused Name:")); formPanel.add(tfAccused);
        formPanel.add(new JLabel("Officer In Charge:")); formPanel.add(tfOfficer);
        JButton btnAdd = new JButton("Add Record"), btnSearch = new JButton("Search Crime Type");
        formPanel.add(btnAdd); formPanel.add(btnSearch);
        add(formPanel, BorderLayout.WEST);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Crime Type", "Location", "Accused", "Officer"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(new JLabel("Search Crime Type:")); bottomPanel.add(tfSearch);
        JButton btnShowAll = new JButton("Show All"), btnDelete = new JButton("Delete Selected");
        bottomPanel.add(btnShowAll); bottomPanel.add(btnDelete);
        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        btnAdd.addActionListener(e -> addRecord());
        btnSearch.addActionListener(e -> searchCrimeType());
        btnDelete.addActionListener(e -> deleteRecord());
        btnShowAll.addActionListener(e -> showAllRecords());

        // Sample Record
        crimeList.add(new CrimeRecord("C101", "Theft", "NYC", "John Doe", "Officer Mark"));
        showAllRecords();
    }

    private void addRecord() {
        String id = tfId.getText().trim(), crimeType = cbCrimeType.getSelectedItem().toString(),
                location = tfLocation.getText().trim(), accused = tfAccused.getText().trim(), officer = tfOfficer.getText().trim();
        if (id.isEmpty() || location.isEmpty() || accused.isEmpty() || officer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }
        crimeList.add(new CrimeRecord(id, crimeType, location, accused, officer));
        showAllRecords();
        clearFields();
    }

    private void showAllRecords() {
        tableModel.setRowCount(0);
        for (CrimeRecord cr : crimeList) {
            tableModel.addRow(new Object[]{cr.id, cr.crimeType, cr.location, cr.accusedName, cr.officer});
        }
    }

    private void searchCrimeType() {
        String search = tfSearch.getText().trim();
        tableModel.setRowCount(0);
        for (CrimeRecord cr : crimeList) {
            if (cr.crimeType.equalsIgnoreCase(search)) {
                tableModel.addRow(new Object[]{cr.id, cr.crimeType, cr.location, cr.accusedName, cr.officer});
            }
        }
    }

    private void deleteRecord() {
        int row = tableModel.getRowCount();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record to delete.");
            return;
        }
        crimeList.removeIf(cr -> cr.id.equals(tableModel.getValueAt(row, 0).toString()));
        showAllRecords();
    }

    private void clearFields() {
        tfId.setText(""); tfLocation.setText(""); tfAccused.setText(""); tfOfficer.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CrimeRecordSystem().setVisible(true));
    }
}
