package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class NumberButton extends JButton {
    private int digit;
    private CalcModel model;

    public NumberButton(String text, CalcModel model) {
        super(text);
        setFont(getFont().deriveFont(30f));
        this.model = model;
        try {
            digit = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid digit.");
        }
        addActionListener(e -> {
            model.insertDigit(digit);
        });
    }
}
