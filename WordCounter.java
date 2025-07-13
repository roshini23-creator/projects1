import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordCounter {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Word Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a JTextArea for input
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Add a scroll pane to the text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create a button to count words
        JButton countButton = new JButton("Count Words");
        panel.add(countButton, BorderLayout.SOUTH);

        // Create a label to display the word count
        JLabel wordCountLabel = new JLabel("Word Count: 0", SwingConstants.CENTER);
        panel.add(wordCountLabel, BorderLayout.NORTH);

        // Add action listener to the button
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                if (text.isEmpty()) {
                    wordCountLabel.setText("Word Count: 0");
                } else {
                    String[] words = text.trim().split("\\s+");
                    wordCountLabel.setText("Word Count: " + words.length);
                }
            }
        });

        // Add the panel to the frame
        frame.add(panel);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
