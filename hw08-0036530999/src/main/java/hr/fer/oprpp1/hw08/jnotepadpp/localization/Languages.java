package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Languages {
    // let each language have a tag and a name
    private static HashMap<String, String> languages; // TODO here this is bad

    private Languages() {
        languages.put("en", "English");
        languages.put("hr", "Croatian");
        languages.put("de", "German");
    }

    public static Map<String, String> getLanguages() {
        return Collections.unmodifiableMap(languages);
    }
}
