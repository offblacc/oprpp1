package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
    private String language;
    private ResourceBundle bundle;
    private static final LocalizationProvider instance = new LocalizationProvider(); // singleton

    private LocalizationProvider() {
        super();
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public String getCurrentLanguage() {
        return null;
    }

    public void setLanguage(String language) {
        this.language = language;
        bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi", Locale.forLanguageTag(language));
        fire();
    }

    public static LocalizationProvider getInstance() {
        return instance;
    }
}
