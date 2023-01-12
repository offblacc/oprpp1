package hr.fer.oprpp1.hw08.jnotepadpp.localization;

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
        bundle = ResourceBundle.getBundle("localization/translations", Locale.forLanguageTag(language));
        fire();
    }

    public static LocalizationProvider getInstance() {
        return instance;
    }
}
