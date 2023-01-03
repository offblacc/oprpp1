package hr.fer.zemris.java.gui.calc.components.buttons;

import javax.swing.*;

public class InvertedToggleButton extends JCheckBox {
    public InvertedToggleButton(String text) {
        super(text);
        setFont(getFont().deriveFont(20f));
    }
}
