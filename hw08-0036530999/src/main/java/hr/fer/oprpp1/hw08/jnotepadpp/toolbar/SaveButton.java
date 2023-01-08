package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

public class SaveButton extends Button {
    private static final String buttonText = "Save";
    private static final Icon icon = null;

    /**
     * Creates a new SaveButton.
     *
     * @param model
     */
    public SaveButton(MultipleDocumentModel model) {
        super(buttonText, icon, model);
        addActionListener(ButtonActions.SAVE.getActionListener(model, this));
    }}
