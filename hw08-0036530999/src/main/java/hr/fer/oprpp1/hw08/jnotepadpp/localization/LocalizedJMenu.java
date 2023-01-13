package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;

public class LocalizedJMenu extends JMenu implements ILocalizationListener {
    ILocalizationProvider lp;
    String key;
    public LocalizedJMenu(String key, ILocalizationProvider lp) {
        super(lp.getString(key));
        this.lp = lp;
        this.key = key;
        this.lp.addLocalizationListener(this); // TODO why does it seem like it is not the same instance as you know the other similar bit of code
    }

    @Override
    public void localizationChanged() {
        setText(lp.getString(key));
    }
}
