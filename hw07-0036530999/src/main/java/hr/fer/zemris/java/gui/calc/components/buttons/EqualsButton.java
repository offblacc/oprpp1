package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class EqualsButton extends JButton {
    public EqualsButton(CalcModel model) {
        super("=");
        addActionListener(e -> {
            if (model.getPendingBinaryOperation() != null) {
                System.out.println("Going to apply pending binary operation to operands " + model.getActiveOperand() + " and " + model.getValue());
                model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
                model.clearActiveOperand();
            }
        });
    }

}
