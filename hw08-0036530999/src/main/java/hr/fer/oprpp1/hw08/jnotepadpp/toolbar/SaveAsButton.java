package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;

public class SaveAsButton extends Button {
    private static final String buttonText = "Save as";
    private static final Icon icon = null;

    public SaveAsButton(MultipleDocumentModel model) {
        super(buttonText, icon, model);
    }
}
