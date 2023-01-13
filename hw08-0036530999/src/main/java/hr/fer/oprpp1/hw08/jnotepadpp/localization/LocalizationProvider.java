package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
    private String language;
    private ResourceBundle bundle;
    private static final LocalizationProvider instance = new LocalizationProvider(); // singleton

    private LocalizationProvider() {
        super();
        language = "hr";
        bundle = ResourceBundle.getBundle("localization/translations", Locale.forLanguageTag(language));
    }

    public List<ILocalizationListener> listeners() {
        return listeners; // TODO delete this method
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language == null ? "en" : language;
    }

    public void setLanguage(String language) {
        this.language = language;
        System.out.println("Language set to " + language);
        bundle = ResourceBundle.getBundle("localization/translations", Locale.forLanguageTag(language));
        fire();
    }

    public static LocalizationProvider getInstance() {
        return instance;
    }
}
