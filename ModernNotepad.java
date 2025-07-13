import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ModernNotepad extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile;

    public ModernNotepad() {
        initializeUI();
        createMenuBar();
        createToolBar();
        setupEventListeners();
    }

    private void initializeUI() {
        setTitle("Modern Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create text area with modern styling
        textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create scroll pane with dark border
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        // Setup file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 240, 240));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        // File Menu
        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, "New", KeyEvent.VK_N, "Create new file", "ctrl N");
        addMenuItem(fileMenu, "Open...", KeyEvent.VK_O, "Open existing file", "ctrl O");
        addMenuItem(fileMenu, "Save", KeyEvent.VK_S, "Save current file", "ctrl S");
        addMenuItem(fileMenu, "Save As...", KeyEvent.VK_S, "Save to new location", "ctrl shift S");
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", KeyEvent.VK_X, "Exit application", "alt F4");
        menuBar.add(fileMenu);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        addMenuItem(editMenu, "Cut", KeyEvent.VK_X, "Cut selected text", "ctrl X");
        addMenuItem(editMenu, "Copy", KeyEvent.VK_C, "Copy selected text", "ctrl C");
        addMenuItem(editMenu, "Paste", KeyEvent.VK_V, "Paste from clipboard", "ctrl V");
        editMenu.addSeparator();
        addMenuItem(editMenu, "Select All", KeyEvent.VK_A, "Select entire text", "ctrl A");
        menuBar.add(editMenu);

        // Format Menu
        JMenu formatMenu = new JMenu("Format");
        JCheckBoxMenuItem wordWrapItem = new JCheckBoxMenuItem("Word Wrap");
        wordWrapItem.setSelected(true);
        wordWrapItem.addActionListener(e -> textArea.setLineWrap(wordWrapItem.isSelected()));
        formatMenu.add(wordWrapItem);
        menuBar.add(formatMenu);

        setJMenuBar(menuBar);
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        toolBar.setBackground(new Color(248, 248, 248));

        addToolBarButton(toolBar, "New", "Create new file", "icons/new.png", e -> newFile());
        addToolBarButton(toolBar, "Open", "Open file", "icons/open.png", e -> openFile());
        addToolBarButton(toolBar, "Save", "Save file", "icons/save.png", e -> saveFile());
        toolBar.addSeparator();
        addToolBarButton(toolBar, "Cut", "Cut selected text", "icons/cut.png", e -> textArea.cut());
        addToolBarButton(toolBar, "Copy", "Copy selected text", "icons/copy.png", e -> textArea.copy());
        addToolBarButton(toolBar, "Paste", "Paste from clipboard", "icons/paste.png", e -> textArea.paste());

        getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    private void addMenuItem(JMenu menu, String text, int keyCode, String tooltip, String accelerator) {
        JMenuItem menuItem = new JMenuItem(text, keyCode);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator));
        menuItem.setToolTipText(tooltip);
        
        // Set action based on menu text
        switch (text) {
            case "New" -> menuItem.addActionListener(e -> newFile());
            case "Open..." -> menuItem.addActionListener(e -> openFile());
            case "Save" -> menuItem.addActionListener(e -> saveFile());
            case "Save As..." -> menuItem.addActionListener(e -> saveAsFile());
            case "Exit" -> menuItem.addActionListener(e -> System.exit(0));
            case "Cut" -> menuItem.addActionListener(e -> textArea.cut());
            case "Copy" -> menuItem.addActionListener(e -> textArea.copy());
            case "Paste" -> menuItem.addActionListener(e -> textArea.paste());
            case "Select All" -> menuItem.addActionListener(e -> textArea.selectAll());
        }
        
        menu.add(menuItem);
    }

    private void addToolBarButton(JToolBar toolBar, String text, String tooltip, String iconPath, ActionListener action) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.addActionListener(action);
        button.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        button.setFocusPainted(false);
        
        // Load icon - placeholder for actual implementation
        button.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
        
        toolBar.add(button);
    }

    private void setupEventListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (confirmUnsavedChanges()) {
                    dispose();
                }
            }
        });
    }

    private void newFile() {
        if (confirmUnsavedChanges()) {
            textArea.setText("");
            currentFile = null;
            setTitle("Modern Notepad");
        }
    }

    private void openFile() {
        if (confirmUnsavedChanges()) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                currentFile = fileChooser.getSelectedFile();
                setTitle("Modern Notepad - " + currentFile.getName());
                
                try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    textArea.setText(content.toString());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveAsFile();
        } else {
            saveToFile(currentFile);
        }
    }

    private void saveAsFile() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            saveToFile(file);
        }
    }

    private void saveToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(textArea.getText());
            currentFile = file;
            setTitle("Modern Notepad - " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean confirmUnsavedChanges() {
        if (textArea.getText().trim().isEmpty()) {
            return true;
        }
        
        int option = JOptionPane.showConfirmDialog(
            this, 
            "Do you want to save changes?", 
            "Unsaved Changes", 
            JOptionPane.YES_NO_CANCEL_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            saveFile();
            return true;
        } else if (option == JOptionPane.NO_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            ModernNotepad notepad = new ModernNotepad();
            notepad.setVisible(true);
        });
    }
}
