package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.UnaryOperators;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;
import java.util.function.DoubleUnaryOperator;

public class UnaryOperationButton extends JButton {
    private CalcModel model;
    private InvertedToggleButton inv;

    public UnaryOperationButton(String text, CalcModel model, InvertedToggleButton inv) {
        super(text);
        this.model = model;
        this.inv = inv;
        setFont(getFont().deriveFont(20f));
        addActionListener(e -> {
            DoubleUnaryOperator operator = (inv != null && inv.isSelected()) ? UnaryOperators.getInvOperatorsMap().get(text) : UnaryOperators.getOperatorsMap().get(text);
            model.setValue(operator.applyAsDouble(model.getValue()));
        });
    }
}
