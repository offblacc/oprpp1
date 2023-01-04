package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class NumberButton extends JButton {
    private int digit;
    private CalcModel model;

    public NumberButton(String text, CalcModel model) {
        super(text);
        setFont(getFont().deriveFont(20f));
        this.model = model;
        try {
            digit = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid digit.");
        }
        addActionListener(e -> {
            if (model.getPendingBinaryOperation() != null && model.toString().isEmpty()) {
                model.clear();
            }
            if (!model.isEditable()) return; // silently ignoring
            model.insertDigit(digit);
        });
    }
}