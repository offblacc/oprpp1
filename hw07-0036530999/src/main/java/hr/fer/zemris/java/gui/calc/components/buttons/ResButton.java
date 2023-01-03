package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;
import java.awt.*;

public class ResButton extends JButton {
    public ResButton(CalcModel model) {
        super("res");
        setFont(getFont().deriveFont(20f)); // TODO make a class inbetween called CalcButton, and put this there
        addActionListener(e -> model.clearAll());
    }
}
