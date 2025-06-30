import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class NoteApp extends JFrame {
    private JTextArea noteTextArea;
    private JComboBox<String> categoryComboBox;
    private ArrayList<Note> notes;

    // Note class defined as a static inner class
    static class Note implements Serializable {
        private String title;
        private String content;

        public Note(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public NoteApp() {
        setTitle("JavaNote - Note Taking Application");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        notes = new ArrayList<>();

        noteTextArea = new JTextArea();
        categoryComboBox = new JComboBox<>();
        JButton createButton = new JButton("Create");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        createButton.addActionListener(e -> createNote());
        editButton.addActionListener(e -> editNote());
        deleteButton.addActionListener(e -> deleteNote());

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(createButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(noteTextArea), BorderLayout.CENTER);
        loadNotes();
    }

    @SuppressWarnings("unchecked") // Suppress unchecked cast warning
    private void loadNotes() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("notes.ser"))) {
            notes = (ArrayList<Note>) in.readObject();
            populateCategoryComboBox();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveNotes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("notes.ser"))) {
            out.writeObject(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNote() {
        String title = JOptionPane.showInputDialog(this, "Enter note title:");
        String content = noteTextArea.getText();
        notes.add(new Note(title, content));
        saveNotes();
        populateCategoryComboBox();
        noteTextArea.setText("");
    }

    private void editNote() {
        int index = categoryComboBox.getSelectedIndex();
        if (index >= 0) {
            notes.get(index).setContent(noteTextArea.getText());
            saveNotes();
        }
    }

    private void deleteNote() {
        int index = categoryComboBox.getSelectedIndex();
        if (index >= 0) {
            notes.remove(index);
            saveNotes();
            populateCategoryComboBox();
            noteTextArea.setText("");
        }
    }

    private void populateCategoryComboBox() {
        categoryComboBox.removeAllItems();
        for (Note note : notes) {
            categoryComboBox.addItem(note.getTitle());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NoteApp frame = new NoteApp();
            frame.setVisible(true);
        });
    }
}
