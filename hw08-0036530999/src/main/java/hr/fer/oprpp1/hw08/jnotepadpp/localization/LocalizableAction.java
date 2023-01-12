package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LocalizableAction extends AbstractAction {
    public LocalizableAction(String key, ILocalizationProvider lp) {
        super(lp.getString(key));
        lp.addLocalizationListener(() -> putValue(Action.NAME, lp.getString(key)));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
