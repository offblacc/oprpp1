package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class InvertedToggleButton extends JCheckBox {
    public InvertedToggleButton(CalcModel model) {
        super("Inv");
        setFont(getFont().deriveFont(20f)); // TODO make toggling inv change the labels of the buttons
    }
}
