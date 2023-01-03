package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class ClearButton extends JButton {
    public ClearButton(CalcModel model) {
        super("clr");
        setFont(getFont().deriveFont(20f));
        addActionListener(e -> {
            model.clear();
        });
    }
}
