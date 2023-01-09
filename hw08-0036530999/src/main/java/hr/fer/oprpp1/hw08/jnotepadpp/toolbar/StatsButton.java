package hr.fer.oprpp1.hw08.jnotepadpp.toolbar;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;

public class StatsButton extends Button {
    private static final String buttonText = "Stats";
    private static final Icon icon = null;

    public StatsButton(MultipleDocumentModel model) {
        super(buttonText, null, model);
        addActionListener(ButtonActions.STATS.getActionListener(model, this));
    }
}

