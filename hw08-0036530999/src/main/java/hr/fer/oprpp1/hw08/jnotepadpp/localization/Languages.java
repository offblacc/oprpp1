package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Languages {
    // let each language have a tag and a name
    private static HashMap<String, String> languages;

    static {
        languages = new HashMap<>();
        languages.put("en", "English");
        languages.put("hr", "Hrvatski");
        languages.put("de", "Deutsch");
    }

    public static Map<String, String> getLanguages() {
        return Collections.unmodifiableMap(languages);
    }
}
