package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

import javax.swing.*;

public class CloseButton extends Button {
    private static final String buttonText = "Close";
    private static final Icon icon = null;

    public CloseButton(MultipleDocumentModel model) {
        super(buttonText, icon, model);
        addActionListener(ButtonActions.CLOSE.getActionListener(model, this));
    }
}
