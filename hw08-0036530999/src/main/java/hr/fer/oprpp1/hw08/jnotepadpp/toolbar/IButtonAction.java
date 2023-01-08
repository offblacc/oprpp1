package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import java.awt.event.ActionListener;

@FunctionalInterface
public interface IButtonAction {
    public ActionListener getActionListener(MultipleDocumentModel model, Button parent);
}
