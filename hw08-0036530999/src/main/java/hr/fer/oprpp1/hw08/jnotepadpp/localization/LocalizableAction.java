package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LocalizableAction extends AbstractAction implements ILocalizationListener {
    ILocalizationProvider lp; // TODO (deprecated comment) why this, this is not right, should be the one passed in the constructor
    String key;

    public LocalizableAction(String key, ILocalizationProvider lp) {
        super(lp.getString(key));
        this.key = key;
        this.lp = lp;
        this.lp.addLocalizationListener(this); // TODO as you need the FLP's listeners, LP instances' listeners are not needed, bad code?
    }

    @Override
    public void localizationChanged() {
        putValue(Action.NAME, lp.getString(key));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
