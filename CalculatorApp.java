import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame {
    private JTextField display;
    private double firstNumber = 0;
    private String operation = "";
    private boolean startNewNumber = true;

    public CalculatorApp() {
        // Frame setup
        setTitle("Colorful Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Display setup
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(220, 230, 240));
        display.setBorder(BorderFactory.createLineBorder(new Color(180, 190, 200), 2));
        mainPanel.add(display, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(new Color(220, 220, 220));

        // Button text and colors
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "√", "x²", "⌫"
        };

        Color[] buttonColors = {
            new Color(100, 150, 220), // Blue
            new Color(100, 150, 220),
            new Color(100, 150, 220),
            new Color(220, 120, 90), // Orange
            new Color(100, 150, 220),
            new Color(100, 150, 220),
            new Color(100, 150, 220),
            new Color(220, 120, 90),
            new Color(100, 150, 220),
            new Color(100, 150, 220),
            new Color(100, 150, 220),
            new Color(220, 120, 90),
            new Color(100, 180, 150), // Green
            new Color(100, 150, 220),
            new Color(90, 180, 100),
            new Color(220, 120, 90),
            new Color(220, 100, 100), // Red
            new Color(180, 100, 200), // Purple
            new Color(180, 100, 200),
            new Color(220, 100, 100)
        };

        // Create and add buttons
        for (int i = 0; i < buttons.length; i++) {
            JButton button = createButton(buttons[i], buttonColors[i]);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createButton(String text, final Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            
            if (startNewNumber) {
                display.setText("");
                startNewNumber = false;
            }
            
            switch (command) {
                case "0": case "1": case "2": case "3": case "4":
                case "5": case "6": case "7": case "8": case "9": case ".":
                    display.setText(display.getText() + command);
                    break;
                    
                case "+": case "-": case "*": case "/":
                    firstNumber = Double.parseDouble(display.getText());
                    operation = command;
                    startNewNumber = true;
                    break;
                case "=":
                    calculateResult();
                    break;
                case "C":
                    display.setText("");
                    firstNumber = 0;
                    operation = "";
                    break;
                case "⌫":
                    String text = display.getText();
                    if (text.length() > 0) {
                        display.setText(text.substring(0, text.length() - 1));
                    }
                    break;
                case "√":
                    double sqrtValue = Math.sqrt(Double.parseDouble(display.getText()));
                    display.setText(Double.toString(sqrtValue));
                    break;
                case "x²":
                    double squareValue = Math.pow(Double.parseDouble(display.getText()), 2);
                    display.setText(Double.toString(squareValue));
                    break;
            }
        }
        
        private void calculateResult() {
            double secondNumber = Double.parseDouble(display.getText());
            double result = 0;
            
            switch (operation) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                default:
                    return;
            }
            
            // Format the result to avoid ".0" for integer results
            if (result == (long) result) {
                display.setText(String.format("%d", (long) result));
            } else {
                display.setText(String.valueOf(result));
            }
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            CalculatorApp calculator = new CalculatorApp();
            calculator.setVisible(true);
        });
    }
}
