package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.UnaryOperators;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;
import java.util.function.DoubleUnaryOperator;

public class UnaryOperationButton extends JButton {
    private DoubleUnaryOperator operator;
    private CalcModel model;

    public UnaryOperationButton(String text, CalcModel model) {
        super(text);
        operator = UnaryOperators.getOperatorsMap().get(text);
        this.model = model;
        setFont(getFont().deriveFont(20f));

        addActionListener(e -> {
            model.setValue(operator.applyAsDouble(model.getValue()));
        });
    }
}
