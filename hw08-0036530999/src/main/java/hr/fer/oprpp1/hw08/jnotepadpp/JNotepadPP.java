package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.statusbar.StatusBar;
import hr.fer.oprpp1.hw08.jnotepadpp.toolbar.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

public class JNotepadPP extends JFrame implements MultipleDocumentListener {
    private DefaultMultipleDocumentModel multipleDocumentModel;
    private StatusBar statusBar;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

    public JNotepadPP() {
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocation(400, 200);
        setTitle("JNotepad++");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        statusBar = new StatusBar();
        cp.add(statusBar, BorderLayout.SOUTH);


        multipleDocumentModel = new DefaultMultipleDocumentModel();
        addWindowListener(multipleDocumentModel);
        addWindowListener(statusBar); // statusbar is the mediator between its clock and the window
        multipleDocumentModel.addMultipleDocumentListener(this); // so that the parent can change the window title on document change
        cp.add(multipleDocumentModel, BorderLayout.CENTER);

//        JToolBar toolbar = new JToolBar();
//        toolbar.setFloatable(false);
//        toolbar.add(new NewButton(multipleDocumentModel));
//        toolbar.add(new OpenButton(multipleDocumentModel));
//        toolbar.add(new SaveButton(multipleDocumentModel));
//        toolbar.add(new SaveAsButton(multipleDocumentModel));
//        toolbar.add(new CloseButton(multipleDocumentModel));
//        toolbar.add(new StatsButton(multipleDocumentModel));
//        cp.add(toolbar, BorderLayout.NORTH);

        createMenus();
        createActions();
    }

    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(saveAsDocumentAction)); // TODO this missing
        fileMenu.add(new JMenuItem(newDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.addSeparator();

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        editMenu.add(new JMenuItem(deleteSelectedPartAction));
        editMenu.add(new JMenuItem(toggleCaseAction));

        this.setJMenuBar(menuBar);
    }


    @Override
    public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        updateTitle(currentModel);
        //
    }

    @Override
    public void documentAdded(SingleDocumentModel model) {
        updateTitle(model);
        ((DefaultSingleDocumentModel) model).addCaretListener(statusBar); // any time a document is added, add the status bar as it's
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

    private Action openDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open file");
            if (fileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) return;
            Path path = fileChooser.getSelectedFile().toPath();
            multipleDocumentModel.loadDocument(path);
        }
    };

    private Action saveAsDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var currentDocument = multipleDocumentModel.getCurrentDocument();
            if (currentDocument == null) return;
            var fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save file");
            if (fileChooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) return;
            Path path = fileChooser.getSelectedFile().toPath();
            multipleDocumentModel.saveDocument(currentDocument, path);
        }
    };

    private Action saveDocumentAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var currentDocument = multipleDocumentModel.getCurrentDocument();
            if (currentDocument == null) return;
            if (currentDocument.getFilePath() == null) {
                saveAsDocumentAction.actionPerformed(e);
            } else {
                multipleDocumentModel.saveDocument(currentDocument, currentDocument.getFilePath());
            }
        }
    };

    private Action closeAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var currentDocument = multipleDocumentModel.getCurrentDocument();
            if (currentDocument == null) return;
            if (currentDocument.isModified()) {
                var result = JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to save the file?");
                if (result == JOptionPane.YES_OPTION) {
                    saveDocumentAction.actionPerformed(e);
                } else if (result == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            multipleDocumentModel.closeDocument(currentDocument);
        }
    };

    private Action deleteSelectedPartAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();
            Document doc = editor.getDocument();
            int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
            if (len == 0) return;
            int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
            try {
                doc.remove(offset, len);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    };

    private Action toggleCaseAction = new AbstractAction() {

        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea editor = multipleDocumentModel.getCurrentDocument().getTextComponent();

            Document doc = editor.getDocument();
            int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
            int offset = 0;
            if (len != 0) {
                offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
            } else {
                len = doc.getLength();
            }
            try {
                String text = doc.getText(offset, len);
                text = changeCase(text);
                doc.remove(offset, len);
                doc.insertString(offset, text, null);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }

        private String changeCase(String text) {
            char[] znakovi = text.toCharArray();
            for (int i = 0; i < znakovi.length; i++) {
                char c = znakovi[i];
                if (Character.isLowerCase(c)) {
                    znakovi[i] = Character.toUpperCase(c);
                } else if (Character.isUpperCase(c)) {
                    znakovi[i] = Character.toLowerCase(c);
                }
            }
            return new String(znakovi);
        }
    };

    private void createActions() {
        openDocumentAction.putValue(Action.NAME, "Open");
        openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

        saveDocumentAction.putValue(Action.NAME, "Save");
        saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

        deleteSelectedPartAction.putValue(Action.NAME, "Delete selected text");
        deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
        deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        deleteSelectedPartAction.putValue(Action.SHORT_DESCRIPTION, "Used to delete the selected part of text.");

        toggleCaseAction.putValue(Action.NAME, "Toggle case");
        toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
        toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        toggleCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to toggle character case in selected part of text or in entire document.");
    }
}