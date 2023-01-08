package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;

public class NewButton extends Button {
    private static final String buttonText = "New";
    private static final Icon icon = null;
    public NewButton(MultipleDocumentModel model) {
        super(buttonText, icon, model);
        addActionListener(ButtonActions.NEW.getActionListener(model, this));
    }
}
