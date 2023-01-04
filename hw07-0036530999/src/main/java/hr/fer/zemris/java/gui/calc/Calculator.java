package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.RCPosition;
import hr.fer.zemris.java.gui.calc.components.*;
import hr.fer.zemris.java.gui.calc.components.buttons.*;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Calculator extends JFrame {
    private CalcModel model;
    private Display display;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }

    public Calculator() {
        super();
        model = new CalcModelImpl();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
        pack();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));
        setTitle("Java Calculator v1.0");
        display = new Display();
        model.addCalcValueListener(display);
        cp.add(display, new RCPosition(1, 1));
        int num = 1;
        for (int i = 4; i > 1; i--) {
            for (int j = 3; j < 6; j++) {
                cp.add(new NumberButton(String.valueOf(num++), model), new RCPosition(i, j));
            }
        }
        cp.add(new NumberButton(String.valueOf(0), model), new RCPosition(5, 3));
        cp.add(new DecimalPointButton(model), new RCPosition(5, 5));
        int yPos = 5;
        for (var opName : BinaryOperators.getOperatorsMap().keySet()) {
            cp.add(new BinaryOperationButton(opName, model), new RCPosition(yPos--, 6));
        }
        cp.add(new EqualsButton(model), new RCPosition(1, 6));
        cp.add(new ClearButton(model), new RCPosition(1, 7));
        cp.add(new ResButton(model), new RCPosition(2, 7));

        cp.add(new UnaryOperationButton("1/x", model, null), new RCPosition(2, 1));
        // -- invertible operations --

        InvertedToggleButton invCheckBox = new InvertedToggleButton();
        ArrayList<UnaryOperationButton> invertibleButtons = new ArrayList<>();
        for (var opName: UnaryOperators.getOperatorsMap().keySet()) {
            if (opName.equals("1/x")) continue; // is unary, but not invertible, so we skip it, added it above manually
            UnaryOperationButton button = new UnaryOperationButton(opName, model, invCheckBox);
            invertibleButtons.add(button);
        }
        int index = 0;
        for (int i = 3; i < 5; i++, index++) cp.add(invertibleButtons.get(index), new RCPosition(i, 1));
        for (int i = 2; i < 6; i++, index++) cp.add(invertibleButtons.get(index), new RCPosition(i, 2));

        cp.add(invCheckBox, new RCPosition(5, 7));
        cp.add(new SwapSignButton(model), new RCPosition(5, 4)); // TODO try entering 5 then swap then 1/x -> fix

    }
}
