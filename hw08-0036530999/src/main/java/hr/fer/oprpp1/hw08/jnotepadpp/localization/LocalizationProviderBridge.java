package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    private ILocalizationProvider parent;
    private boolean connected;

    public LocalizationProviderBridge(ILocalizationProvider parent) {
        super();
        this.parent = parent;
        this.connected = true;
    }


    public void disconnect() {
        if (!connected) return;
        connected = false;
        parent.removeLocalizationListener(this::fire); // TODO what does this do
    }

    public void connect() {
        if (connected) return;
        connected = true;
        addLocalizationListener(this::fire);
    }

    public String getString(String key) {
        if (connected) {
            return parent.getString(key);
        } else {
            return key;
        }
    }

    public String getCurrentLanguage() {
        if (connected) {
            return parent.getCurrentLanguage();
        } else {
            return "en"; // if not connected, return english
        }
    }
}
