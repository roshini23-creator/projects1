import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinaryTextConverter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Main frame setup
            JFrame frame = new JFrame("Binary ↔ Text Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 600);
            
            // Main panel with gradient background
            JPanel mainPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(100, 149, 237), 
                        getWidth(), getHeight(), new Color(30, 144, 255));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            
            // Header panel
            JPanel headerPanel = new JPanel();
            headerPanel.setOpaque(false);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            
            JLabel title = new JLabel("BINARY ↔ TEXT CONVERTER");
            title.setFont(new Font("Segoe UI", Font.BOLD, 28));
            title.setForeground(Color.WHITE);
            headerPanel.add(title);
            
            // Content panel (translucent)
            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setBackground(new Color(255, 255, 255, 180));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            
            // Binary to Text Section
            JLabel binToTextLabel = new JLabel("Binary to Text");
            binToTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            binToTextLabel.setForeground(new Color(70, 130, 180));
            gbc.gridx = 0;
            gbc.gridy = 0;
            contentPanel.add(binToTextLabel, gbc);
            
            JTextArea binaryInput = new JTextArea(3, 30);
            binaryInput.setFont(new Font("Consolas", Font.PLAIN, 14));
            binaryInput.setLineWrap(true);
            binaryInput.setWrapStyleWord(true);
            binaryInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            JScrollPane binaryScroll = new JScrollPane(binaryInput);
            gbc.gridx = 0;
            gbc.gridy = 1;
            contentPanel.add(binaryScroll, gbc);
            
            JButton binConvertBtn = new JButton("Convert to Text");
            styleButton(binConvertBtn, new Color(46, 204, 113));
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPanel.add(binConvertBtn, gbc);
            
            JTextArea textOutput = new JTextArea(3, 30);
            textOutput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textOutput.setEditable(false);
            textOutput.setBackground(new Color(240, 240, 240));
            textOutput.setLineWrap(true);
            textOutput.setWrapStyleWord(true);
            JScrollPane textScroll = new JScrollPane(textOutput);
            gbc.gridx = 0;
            gbc.gridy = 3;
            contentPanel.add(textScroll, gbc);
            
            // Text to Binary Section
            JLabel textToBinLabel = new JLabel("Text to Binary");
            textToBinLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            textToBinLabel.setForeground(new Color(70, 130, 180));
            gbc.gridx = 0;
            gbc.gridy = 4;
            contentPanel.add(textToBinLabel, gbc);
            
            JTextArea textInput = new JTextArea(3, 30);
            textInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textInput.setLineWrap(true);
            textInput.setWrapStyleWord(true);
            textInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            JScrollPane textInputScroll = new JScrollPane(textInput);
            gbc.gridx = 0;
            gbc.gridy = 5;
            contentPanel.add(textInputScroll, gbc);
            
            JButton textConvertBtn = new JButton("Convert to Binary");
            styleButton(textConvertBtn, new Color(155, 89, 182));
            gbc.gridx = 0;
            gbc.gridy = 6;
            contentPanel.add(textConvertBtn, gbc);
            
            JTextArea binaryOutput = new JTextArea(3, 30);
            binaryOutput.setFont(new Font("Consolas", Font.PLAIN, 14));
            binaryOutput.setEditable(false);
            binaryOutput.setBackground(new Color(240, 240, 240));
            binaryOutput.setLineWrap(true);
            binaryOutput.setWrapStyleWord(true);
            JScrollPane binaryOutputScroll = new JScrollPane(binaryOutput);
            gbc.gridx = 0;
            gbc.gridy = 7;
            contentPanel.add(binaryOutputScroll, gbc);
            
            // Clear Button
            JButton clearBtn = new JButton("Clear All");
            styleButton(clearBtn, new Color(231, 76, 60));
            gbc.gridx = 0;
            gbc.gridy = 8;
            contentPanel.add(clearBtn, gbc);
            
            // Action Listeners
            binConvertBtn.addActionListener(e -> {
                try {
                    String binaryStr = binaryInput.getText().replaceAll("\\s", "");
                    if (!binaryStr.matches("[01]+")) {
                        throw new IllegalArgumentException();
                    }
                    // Split into 8-bit chunks
                    StringBuilder text = new StringBuilder();
                    for (int i = 0; i < binaryStr.length(); i += 8) {
                        String byteStr = binaryStr.substring(i, Math.min(i + 8, binaryStr.length()));
                        char c = (char) Integer.parseInt(byteStr, 2);
                        text.append(c);
                    }
                    textOutput.setText(text.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, 
                        "Invalid binary input!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            textConvertBtn.addActionListener(e -> {
                StringBuilder binary = new StringBuilder();
                String text = textInput.getText();
                for (char c : text.toCharArray()) {
                    binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
                }
                binaryOutput.setText(binary.toString());
            });
            
            clearBtn.addActionListener(e -> {
                binaryInput.setText("");
                textOutput.setText("");
                textInput.setText("");
                binaryOutput.setText("");
            });
            
            // Assemble components
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            frame.add(mainPanel);
            
            // Center and show frame
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    
    private static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}
