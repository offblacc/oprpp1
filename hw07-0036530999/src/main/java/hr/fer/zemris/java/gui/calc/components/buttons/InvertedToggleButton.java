package hr.fer.zemris.java.gui.calc.components.buttons;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class InvertedToggleButton extends JCheckBox {
    public InvertedToggleButton() {
        super("Inv");
        setFont(getFont().deriveFont(20f)); // TODO make toggling inv change the labels of the buttons
    }

}
