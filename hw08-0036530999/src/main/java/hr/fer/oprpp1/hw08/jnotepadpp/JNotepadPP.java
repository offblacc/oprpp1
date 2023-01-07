package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.toolbar.*;

import javax.swing.*;
import java.awt.*;

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
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        DefaultMultipleDocumentModel model = new DefaultMultipleDocumentModel();
        cp.add(new DefaultMultipleDocumentModel(), BorderLayout.CENTER);
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(new NewButton(model));
        toolbar.add(new OpenButton(model));
        toolbar.add(new SaveButton(model));
        toolbar.add(new SaveAsButton(model));
        cp.add(toolbar, BorderLayout.NORTH);
    }
}
