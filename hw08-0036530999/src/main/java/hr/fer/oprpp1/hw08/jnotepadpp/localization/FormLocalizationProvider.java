package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormLocalizationProvider extends LocalizationProviderBridge {
    public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
        super(parent);
        frame.addWindowListener(new WindowAdapter() {
            @Override // not sure that I need this one
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
