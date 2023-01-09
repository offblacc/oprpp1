package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.toolbar.*;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class JNotepadPP extends JFrame implements MultipleDocumentListener {
    private DefaultMultipleDocumentModel multipleDocumentModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

    public JNotepadPP() {
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setTitle("JNotepad++");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        multipleDocumentModel = new DefaultMultipleDocumentModel();
        addWindowListener(multipleDocumentModel);
        multipleDocumentModel.addMultipleDocumentListener(this); // so that the parent can change the window title on document change
        cp.add(multipleDocumentModel, BorderLayout.CENTER);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(new NewButton(multipleDocumentModel));
        toolbar.add(new OpenButton(multipleDocumentModel));
        toolbar.add(new SaveButton(multipleDocumentModel));
        toolbar.add(new SaveAsButton(multipleDocumentModel));
        toolbar.add(new CloseButton(multipleDocumentModel));
        toolbar.add(new StatsButton(multipleDocumentModel));
        cp.add(toolbar, BorderLayout.NORTH); // neka ovo bude listener na tabovima, odnosno na tabchanged, i onda samo brate lupaj setTitle(model.getCurrentDocument().getFilePath().getFileName().toString());
    }

    @Override
    public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        updateTitle(currentModel);
    }

    @Override
    public void documentAdded(SingleDocumentModel model) {
        updateTitle(model);
    }

    @Override
    public void documentRemoved(SingleDocumentModel model) {
        updateTitle(multipleDocumentModel.getCurrentDocument());
    }

    private void updateTitle(SingleDocumentModel model) {
        if (model == null) {
            setTitle("JNotepad++");
        } else {
            Path path = model.getFilePath();
            if (path == null) setTitle("(untitled) - JNotepad++");
            else setTitle(path.getFileName().toString() + " - JNotepad++");
        }
    }
}
