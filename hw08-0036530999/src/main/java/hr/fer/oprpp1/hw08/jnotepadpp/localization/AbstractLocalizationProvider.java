package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
    List<ILocalizationListener> listeners;
    public AbstractLocalizationProvider() {
        listeners = new ArrayList<>();
    }

    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);

    }

    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    @Override
    public abstract String getString(String key);

    @Override
    public abstract String getCurrentLanguage();

    /**
     * Notifies all listeners that the localization has changed.
     */
    public void fire() {
        listeners.forEach(ILocalizationListener::localizationChanged);
    }
}
