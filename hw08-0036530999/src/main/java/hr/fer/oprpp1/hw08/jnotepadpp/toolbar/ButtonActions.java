package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.nio.file.Path;

public class ButtonActions {
    public static final IButtonAction NEW = (MultipleDocumentModel model, Button parent) -> (ActionListener) e -> model.createNewDocument();

    public static final IButtonAction OPEN = (MultipleDocumentModel model, Button parent) -> (ActionListener) e -> {
        var fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");
        if (fileChooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) return;
        Path path = fileChooser.getSelectedFile().toPath();
        model.loadDocument(path);
    };

    public static final IButtonAction SAVE_AS = (MultipleDocumentModel model, Button parent) -> (ActionListener) e -> {
        var currentDocument = model.getCurrentDocument();
        if (currentDocument == null) return;
        var fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file");
        if (fileChooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) return;
        Path path = fileChooser.getSelectedFile().toPath();
        model.saveDocument(currentDocument, path);
    };

    // TODO this is complicated, make sure it works
    public static final IButtonAction SAVE = (MultipleDocumentModel model, Button parent) -> (ActionListener) e -> {
        var currentDocument = model.getCurrentDocument();
        if (currentDocument == null) return;
        if (currentDocument.getFilePath() == null) {
            SAVE_AS.getActionListener(model, parent).actionPerformed(e);
            return;
        }
        model.saveDocument(currentDocument, currentDocument.getFilePath());
    };

    public static final IButtonAction CLOSE = (MultipleDocumentModel model, Button parent) -> (ActionListener) e -> {
        var currentDocument = model.getCurrentDocument();
        if (currentDocument == null) return;
        if (currentDocument.isModified()) {
            var result = JOptionPane.showConfirmDialog(parent, "Do you want to save the file?");
            if (result == JOptionPane.YES_OPTION) {
                SAVE.getActionListener(model, parent).actionPerformed(e);
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        model.closeDocument(currentDocument);
    };
}