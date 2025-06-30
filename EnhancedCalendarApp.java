import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;

public class EnhancedCalendarApp extends JFrame {
    private LocalDate currentDate;
    private JLabel monthYearLabel;
    private JPanel calendarPanel;
    private JTextField searchField;
    private JList<String> todoList;
    private DefaultListModel<String> todoListModel;
    private JTextField todoInputField;

    public EnhancedCalendarApp() {
        setTitle("Enhanced Calendar with To-Do");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 500));

        currentDate = LocalDate.now();

        // Main panels setup
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 250));

        JPanel calendarSection = new JPanel(new BorderLayout());
        JPanel todoSection = new JPanel(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, calendarSection, todoSection);
        splitPane.setDividerLocation(450);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Calendar header with search bar
        JPanel headerPanel = createHeaderPanel();
        calendarSection.add(headerPanel, BorderLayout.NORTH);

        // Day names panel
        JPanel dayNamesPanel = createDayNamesPanel();
        calendarSection.add(dayNamesPanel, BorderLayout.CENTER);

        // Calendar grid
        calendarPanel = new JPanel(new GridLayout(6, 7, 5, 5));
        calendarPanel.setBackground(Color.WHITE);
        JScrollPane calendarScroll = new JScrollPane(calendarPanel);
        calendarScroll.setBorder(BorderFactory.createEmptyBorder());
        calendarSection.add(calendarScroll, BorderLayout.CENTER);

        // To-Do section
        setupTodoSection(todoSection);

        add(mainPanel);
        updateCalendar();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        headerPanel.setBackground(new Color(200, 220, 240));

        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        navPanel.setOpaque(false);

        JButton prevButton = createStyledButton("◀");
        prevButton.addActionListener(e -> navigateMonth(-1));

        JButton nextButton = createStyledButton("▶");
        nextButton.addActionListener(e -> navigateMonth(1));

        JButton todayButton = createStyledButton("Today");
        todayButton.addActionListener(e -> {
            currentDate = LocalDate.now();
            updateCalendar();
        });

        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        monthYearLabel.setForeground(new Color(40, 60, 80));

        navPanel.add(prevButton);
        navPanel.add(todayButton);
        navPanel.add(nextButton);
        headerPanel.add(navPanel, BorderLayout.WEST);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 20));

        JLabel searchLabel = new JLabel("Jump to (MM/YYYY):");
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        searchField = new JTextField(10);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(new LineBorder(new Color(180, 200, 220), 1, true));
        
        JButton searchButton = createStyledButton("Go");
        searchButton.addActionListener(e -> searchMonthYear());

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        headerPanel.add(searchPanel, BorderLayout.EAST);
        headerPanel.add(monthYearLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createDayNamesPanel() {
        JPanel dayNamesPanel = new JPanel(new GridLayout(1, 7));
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            dayLabel.setForeground(new Color(70, 90, 110));
            dayNamesPanel.add(dayLabel);
        }
        return dayNamesPanel;
    }

    private void setupTodoSection(JPanel todoSection) {
        todoSection.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        todoSection.setBackground(new Color(250, 245, 240));

        // Todo list header
        JLabel todoLabel = new JLabel("To-Do List", SwingConstants.CENTER);
        todoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        todoLabel.setForeground(new Color(70, 90, 110));
        todoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        todoSection.add(todoLabel, BorderLayout.NORTH);

        // Todo list
        todoListModel = new DefaultListModel<>();
        todoList = new JList<>(todoListModel);
        todoList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        todoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        todoList.setBackground(new Color(255, 253, 250));
        todoList.setCellRenderer(new TodoListCellRenderer());
        
        JScrollPane todoScroll = new JScrollPane(todoList);
        todoSection.add(todoScroll, BorderLayout.CENTER);

        // Todo input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        todoInputField = new JTextField();
        todoInputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        todoInputField.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        todoInputField.addActionListener(e -> addTodoItem());
        
        JButton addButton = createStyledButton("Add Task");
        addButton.addActionListener(e -> addTodoItem());
        inputPanel.add(todoInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton removeButton = createStyledButton("Remove");
        removeButton.addActionListener(e -> removeSelectedTodo());
        
        JButton clearButton = createStyledButton("Clear All");
        clearButton.addActionListener(e -> clearAllTodos());
        
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);
        
        // Combine panels
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        bottomPanel.setOpaque(false);
        
        todoSection.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(new Color(220, 230, 245));
        button.setForeground(new Color(50, 70, 90));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 200, 220), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(200, 215, 240));
            }
            
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(220, 230, 245));
            }
        });
        
        return button;
    }

    private void updateCalendar() {
        monthYearLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        calendarPanel.removeAll();

        YearMonth yearMonth = YearMonth.from(currentDate);
        LocalDate firstDay = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int dayOfWeek = firstDay.get(ChronoField.DAY_OF_WEEK);

        // Add empty cells for days before first day
        for (int i = 1; i < dayOfWeek; i++) {
            calendarPanel.add(createDayLabel(""));
        }

        LocalDate today = LocalDate.now();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate buttonDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
            calendarPanel.add(createDayButton(day, buttonDate, today));
        }

        // Fill remaining cells
        int remainingCells = 42 - (dayOfWeek - 1 + daysInMonth);
        for (int i = 0; i < remainingCells; i++) {
            calendarPanel.add(createDayLabel(""));
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private JComponent createDayLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(245, 245, 245));
        return label;
    }

    private JButton createDayButton(int day, LocalDate buttonDate, LocalDate today) {
        JButton dayButton = new JButton(String.valueOf(day));
        dayButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dayButton.setFocusPainted(false);
        dayButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        if (buttonDate.equals(today)) {
            dayButton.setBackground(new Color(180, 230, 180));
            dayButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        } else if (buttonDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            dayButton.setBackground(new Color(250, 210, 210));
        } else if (buttonDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            dayButton.setBackground(new Color(210, 210, 250));
        } else {
            dayButton.setBackground(new Color(240, 245, 250));
        }
        
        dayButton.addActionListener(e -> {
            String dateStr = buttonDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
            JOptionPane.showMessageDialog(this, "Selected: " + dateStr, "Date Selected", JOptionPane.INFORMATION_MESSAGE);
        });
        
        return dayButton;
    }

    private void addTodoItem() {
        String task = todoInputField.getText().trim();
        if (!task.isEmpty()) {
            todoListModel.addElement(task);
            todoInputField.setText("");
        }
    }

    private void removeSelectedTodo() {
        int selectedIndex = todoList.getSelectedIndex();
        if (selectedIndex != -1) {
            todoListModel.remove(selectedIndex);
        }
    }

    private void clearAllTodos() {
        if (!todoListModel.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Clear all to-do items?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                todoListModel.clear();
            }
        }
    }

    private void searchMonthYear() {
        try {
            String[] parts = searchField.getText().split("/");
            if (parts.length != 2) throw new IllegalArgumentException();
            
            int month = Integer.parseInt(parts[0].trim());
            int year = Integer.parseInt(parts[1].trim());
            
            if (month < 1 || month > 12) throw new IllegalArgumentException();
            if (year < 1900 || year > 2100) throw new IllegalArgumentException();
            
            currentDate = LocalDate.of(year, month, 1);
            updateCalendar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter date in MM/YYYY format (e.g., 06/2023)", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateMonth(int months) {
        currentDate = currentDate.plusMonths(months);
        updateCalendar();
    }

    static class TodoListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                     boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (isSelected) {
                c.setBackground(new Color(200, 220, 240));
                c.setForeground(Color.BLACK);
            } else {
                c.setBackground(new Color(255, 253, 250));
                if (value.toString().toLowerCase().startsWith("urgent")) {
                    c.setForeground(new Color(200, 0, 0));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setForeground(Color.BLACK);
                }
            }
            
            return c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new EnhancedCalendarApp().setVisible(true);
        });
    }
}
