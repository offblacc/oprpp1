package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;

public class OpenButton extends Button {
    private static final String buttonText = "Open";
    private static final Icon icon = null;
    public OpenButton(MultipleDocumentModel model) {
        super(buttonText, icon, model);
    }
}