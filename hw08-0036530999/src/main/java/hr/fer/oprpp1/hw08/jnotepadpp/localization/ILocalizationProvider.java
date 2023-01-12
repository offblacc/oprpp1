package hr.fer.oprpp1.hw08.jnotepadpp.localization;

/**
 * Interface for localization providers, objects that will give us translations. It also
 * is the subject in the observer pattern, so it can notify its listeners when the
 * localization changes, allowing them to update their translations.
 */
public interface ILocalizationProvider {
    /**
     * Adds a new listener to the list of listeners.
     *
     * @param l listener to be added
     */
    void addLocalizationListener(ILocalizationListener l);

    /**
     * Removes a listener from the list of listeners.
     *
     * @param l listener to be removed
     */
    void removeLocalizationListener(ILocalizationListener l);

    /**
     * Returns the string associated with the given key.
     *
     * @param key key of the string
     * @return string associated with the given key
     */
    String getString(String key);

    /**
     * Returns the current language.
     *
     * @return current language
     */
    String getCurrentLanguage();

    // TODO and implement
    // TODO current language cacheing in LocalizationProviderBridge to enable notification dispatching if the
    // TODO bridge is reconnected to the source which had the language changed while the bridge was disconnected.

}
