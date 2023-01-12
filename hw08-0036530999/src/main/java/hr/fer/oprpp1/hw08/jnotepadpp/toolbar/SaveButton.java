package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;

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
        addActionListener(OldButtonActions.SAVE.getActionListener(model, this));
    }}
