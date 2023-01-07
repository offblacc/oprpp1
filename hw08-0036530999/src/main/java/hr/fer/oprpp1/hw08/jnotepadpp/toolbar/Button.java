package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;

public class Button extends JButton {
    /**
     * The document model this button is going to interact with.
     */
    MultipleDocumentModel model;

    public Button(String text, Icon icon, MultipleDocumentModel model) {
        super(text, icon);
        this.model = model;
    }
}
