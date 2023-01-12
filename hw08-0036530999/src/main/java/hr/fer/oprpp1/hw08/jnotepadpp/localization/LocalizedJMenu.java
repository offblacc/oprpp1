package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;

public class LocalizedJMenu extends JMenu {
    public LocalizedJMenu(String key, ILocalizationProvider lp) {
        super(lp.getString(key));
        lp.addLocalizationListener(() -> setText(lp.getString(key)));
    }
}
