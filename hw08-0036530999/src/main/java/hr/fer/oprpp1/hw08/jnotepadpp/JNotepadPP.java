package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;

public class JNotepadPP extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

    public JNotepadPP() {
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setTitle("JNotepad++");
        add(new DefaultMultipleDocumentModel());
    }
}
