import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class EnhancedCalendarApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EnhancedCalendarApp().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        // Create main window
        JFrame mainFrame = new JFrame("Enhanced Calendar App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(300, 200));
        
        // Styled main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridLayout(0, 1, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Title label
        JLabel titleLabel = new JLabel("Calendar Application", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 100, 200));
        mainPanel.add(titleLabel);

        // Year input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(240, 240, 240));
        
        JLabel yearLabel = new JLabel("Enter Year:");
        JTextField yearField = new JTextField(10);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        mainPanel.add(inputPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton showButton = createStyledButton("Show Calendar", new Color(0, 150, 0));
        JButton exitButton = createStyledButton("Exit", new Color(200, 0, 0));
        
        // Add action listeners
        showButton.addActionListener(e -> showCalendar(yearField.getText()));
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(showButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel);

        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    private void showCalendar(String yearInput) {
        try {
            int year = Integer.parseInt(yearInput.trim());
            if (year <= 0) {
                throw new NumberFormatException();
            }

            // Create calendar dialog
            JDialog calendarDialog = new JDialog();
            calendarDialog.setTitle("Calendar for " + year);
            calendarDialog.setModal(true);
            calendarDialog.setPreferredSize(new Dimension(600, 600));
            calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            // Create calendar content
            JTextArea textArea = new JTextArea(40, 40);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setText(generateCalendar(year));
            textArea.setEditable(false);

            // Add scrolling
            JScrollPane scrollPane = new JScrollPane(textArea);
            calendarDialog.add(scrollPane);

            calendarDialog.pack();
            calendarDialog.setLocationRelativeTo(null);
            calendarDialog.setVisible(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Please enter a valid year (positive number)!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateCalendar(int year) {
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        
        for (int month = 0; month < 12; month++) {
            calendar.set(year, month, 1);
            
            String monthName = getMonthName(month);
            sb.append(String.format("%" + (20 + monthName.length()/2) + "s\n\n", monthName + " " + year));
            sb.append(" Su Mo Tu We Th Fr Sa\n");
            
            // Get first day of week (1=Sunday)
            int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            
            // Print leading spaces
            for (int i = 0; i < firstDayOfWeek - 1; i++) {
                sb.append("   ");
            }
            
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int day = 1; day <= daysInMonth; day++) {
                sb.append(String.format("%3d", day));
                if ((day + firstDayOfWeek - 1) % 7 == 0 || day == daysInMonth) {
                    sb.append("\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getMonthName(int month) {
        return new java.text.DateFormatSymbols().getMonths()[month];
    }
}
