import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinaryDecimalConverter {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("BitWise Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500); // Increased size for better layout
        
        // Main panel with background image
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image background = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Roshini\\Desktop\\maxresdefault.jpg=Binary\nDecimal\nConverter");
                g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180, 150)); // Semi-transparent
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("BITWISE CONVERTER");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        headerPanel.add(titleLabel);
        
        // Create main content panel (semi-transparent)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 240, 240, 200)); // Semi-transparent
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Binary to Decimal Section
        JLabel binToDecLabel = new JLabel("Binary to Decimal");
        binToDecLabel.setFont(new Font("Arial", Font.BOLD, 16));
        binToDecLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(binToDecLabel, gbc);
        
        JTextField binaryInput = new JTextField(20);
        binaryInput.setFont(new Font("Arial", Font.PLAIN, 14));
        binaryInput.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(binaryInput, gbc);
        
        JButton binConvertBtn = new JButton("Convert to Decimal");
        binConvertBtn.setBackground(new Color(100, 149, 237));
        binConvertBtn.setForeground(Color.WHITE);
        binConvertBtn.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(binConvertBtn, gbc);
        
        JLabel decimalOutput = new JLabel("Decimal: ");
        decimalOutput.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(decimalOutput, gbc);
        
        // Decimal to Binary Section
        JLabel decToBinLabel = new JLabel("Decimal to Binary");
        decToBinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        decToBinLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(decToBinLabel, gbc);
        
        JTextField decimalInput = new JTextField(20);
        decimalInput.setFont(new Font("Arial", Font.PLAIN, 14));
        decimalInput.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(decimalInput, gbc);
        
        JButton decConvertBtn = new JButton("Convert to Binary");
        decConvertBtn.setBackground(new Color(100, 149, 237));
        decConvertBtn.setForeground(Color.WHITE);
        decConvertBtn.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(decConvertBtn, gbc);
        
        JLabel binaryOutput = new JLabel("Binary: ");
        binaryOutput.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        contentPanel.add(binaryOutput, gbc);
        
        // Clear Button
        JButton clearBtn = new JButton("Clear All");
        clearBtn.setBackground(new Color(220, 53, 69));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.insets = new Insets(20, 10, 10, 10); // Extra space above
        contentPanel.add(clearBtn, gbc);
        
        // Add action listeners
        binConvertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String binaryStr = binaryInput.getText();
                    if (!binaryStr.matches("[01]+")) {
                        throw new NumberFormatException();
                    }
                    int decimal = Integer.parseInt(binaryStr, 2);
                    decimalOutput.setText("Decimal: " + decimal);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid binary number! Only 0s and 1s allowed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        decConvertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimal = Integer.parseInt(decimalInput.getText());
                    if (decimal < 0) {
                        throw new NumberFormatException();
                    }
                    String binaryStr = Integer.toBinaryString(decimal);
                    binaryOutput.setText("Binary: " + binaryStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid decimal number! Only positive integers allowed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                binaryInput.setText("");
                decimalInput.setText("");
                binaryOutput.setText("Binary: ");
                decimalOutput.setText("Decimal: ");
            }
        });
        
        // Add panels to frame
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        
        // Center the frame and make visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
