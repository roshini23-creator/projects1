import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;

public class TypingTest extends JFrame {
    private JTextArea textArea;
    private JTextArea inputArea;
    private JLabel timerLabel;
    private JLabel wordCountLabel;
    private JLabel correctWordsLabel;
    private JLabel mistakesLabel;
    private JLabel speedLabel;
    private JButton startButton;
    private JButton stopButton;
    private JButton clearButton;

    private final String textToType =
            "This is a longer text for you to practice your typing skills. " +
            "The quick brown fox jumps over the lazy dog. " +
            "Typing practice helps improve your speed and accuracy. " +
            "Consistent practice and focus can lead to significant improvements over time. " +
            "Remember to keep your posture correct and hands relaxed while typing.";

    private boolean timerRunning = false;
    private Instant startTime;
    private int wordCount = 0;
    private int correctWordCount = 0;
    private int mistakesCount = 0;

    public TypingTest() {
        setTitle("Typing Test");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createWidgets();
    }

    private void createWidgets() {
        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea(10, 50);
        textArea.setText(textToType);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setForeground(Color.BLUE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.NORTH);

        inputArea = new JTextArea(5, 50);
        inputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        inputArea.setWrapStyleWord(true);
        inputArea.setLineWrap(true);
        inputArea.setEditable(false);
        inputArea.setBackground(Color.WHITE);
        inputArea.setForeground(Color.BLACK);
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
        });
        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        panel.add(inputScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.DARK_GRAY);
        startButton = new JButton("Start");
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(e -> startTest());
        controlPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.setBackground(Color.RED);
        stopButton.setForeground(Color.WHITE);
        stopButton.addActionListener(e -> stopTest());
        stopButton.setEnabled(false);
        controlPanel.add(stopButton);

        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.YELLOW);
        clearButton.setForeground(Color.BLACK);
        clearButton.addActionListener(e -> clearTest());
        clearButton.setEnabled(false);
        controlPanel.add(clearButton);

        timerLabel = new JLabel("Time: 0.0s");
        timerLabel.setForeground(Color.WHITE);
        controlPanel.add(timerLabel);

        wordCountLabel = new JLabel("Words: 0");
        wordCountLabel.setForeground(Color.WHITE);
        controlPanel.add(wordCountLabel);

        correctWordsLabel = new JLabel("Correct: 0");
        correctWordsLabel.setForeground(Color.WHITE);
        controlPanel.add(correctWordsLabel);

        mistakesLabel = new JLabel("Mistakes: 0");
        mistakesLabel.setForeground(Color.WHITE);
        controlPanel.add(mistakesLabel);

        speedLabel = new JLabel("Speed: 0 WPM");
        speedLabel.setForeground(Color.WHITE);
        controlPanel.add(speedLabel);

        panel.add(controlPanel, BorderLayout.SOUTH);
        add(panel);
    }

    private void startTest() {
        inputArea.setEditable(true);
        inputArea.requestFocus();
        inputArea.setText("");
        timerRunning = true;
        startTime = Instant.now();
        new Timer(100, e -> updateTimer()).start();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        clearButton.setEnabled(true);
    }

    private void stopTest() {
        timerRunning = false;
        calculateSpeed();
        inputArea.setEditable(false);
        stopButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Your time: " + timerLabel.getText());
    }

    private void clearTest() {
        inputArea.setEditable(false);
        inputArea.setText("");
        timerRunning = false;
        startTime = null;
        wordCount = 0;
        correctWordCount = 0;
        mistakesCount = 0;
        timerLabel.setText("Time: 0.0s");
        wordCountLabel.setText("Words: 0");
        correctWordsLabel.setText("Correct: 0");
        mistakesLabel.setText("Mistakes: 0");
        speedLabel.setText("Speed: 0 WPM");
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        clearButton.setEnabled(false);
    }

    private void updateTimer() {
        if (timerRunning) {
            Duration elapsedTime = Duration.between(startTime, Instant.now());
            timerLabel.setText(String.format("Time: %.1fs", elapsedTime.toMillis() / 1000.0));
        }
    }

    private void checkInput() {
        String inputText = inputArea.getText().trim();
        String[] textWords = textToType.split("\\s+");
        String[] inputWords = inputText.split("\\s+");

        wordCount = inputWords.length;
        correctWordCount = 0;
        mistakesCount = 0;

        for (int i = 0; i < inputWords.length; i++) {
            if (i < textWords.length && inputWords[i].equals(textWords[i])) {
                correctWordCount++;
            } else {
                mistakesCount++;
            }
        }

        wordCountLabel.setText("Words: " + wordCount);
        correctWordsLabel.setText("Correct: " + correctWordCount);
        mistakesLabel.setText("Mistakes: " + mistakesCount);

        if (inputText.equals(textToType)) {
            stopTest();
        }
    }

    private void calculateSpeed() {
        Duration elapsedTime = Duration.between(startTime, Instant.now());
        double minutes = elapsedTime.toMillis() / 60000.0;
        double wpm = wordCount / minutes;
        speedLabel.setText("Speed: " + (int) wpm + " WPM");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TypingTest app = new TypingTest();
            app.setVisible(true);
        });
    }
}

