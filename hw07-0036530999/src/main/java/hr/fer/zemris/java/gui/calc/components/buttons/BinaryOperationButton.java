package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;
import java.util.function.DoubleBinaryOperator;

public class BinaryOperationButton extends JButton {
    private DoubleBinaryOperator operator;
    private CalcModel model;

    public BinaryOperationButton(String name, CalcModel model) {
        super(name);
        setFont(getFont().deriveFont(20f)); // looks weird with this font increased as well
        this.operator = operator;
        this.model = model;
        addActionListener(e -> {
            model.setActiveOperand(model.getValue());
            model.setPendingBinaryOperation(operator);
            model.clear();
        });
    }
}
