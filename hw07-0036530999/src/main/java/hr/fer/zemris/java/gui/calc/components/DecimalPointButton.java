package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class DecimalPointButton extends JButton {
    private CalcModel model;

    public DecimalPointButton(CalcModel model) {
        super(".");
        setFont(getFont().deriveFont(30f));
        this.model = model;
        addActionListener(e -> {
            model.insertDecimalPoint();
        });
    }
}
