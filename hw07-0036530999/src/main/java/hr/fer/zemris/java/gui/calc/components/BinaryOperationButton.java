package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;
import java.util.function.DoubleBinaryOperator;

public class BinaryOperationButton extends JButton {
    private DoubleBinaryOperator operator;
    private CalcModel model;

    public BinaryOperationButton(String name, DoubleBinaryOperator operator, CalcModel model) {
        super(name);
//        setFont(getFont().deriveFont(30f)); // looks weird with this font increased as well
        this.operator = operator;
        this.model = model;
        addActionListener(e -> {
            // if (model.isActiveOperandSet()) // TODo stopped at freezing the value after setting first operand and resetting on writing in the new operand
            model.setPendingBinaryOperation(operator);
            model.setActiveOperand(model.getValue());
        });
    }
}
