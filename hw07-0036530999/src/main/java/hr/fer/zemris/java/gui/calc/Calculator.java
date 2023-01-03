package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.RCPosition;
import hr.fer.zemris.java.gui.calc.components.*;
import hr.fer.zemris.java.gui.calc.components.buttons.*;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {
    private CalcModel model;
    private Display display;
    private boolean inverted;

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
        cp.add(new UnaryOperationButton("1/x", model), new RCPosition(2, 1));
        cp.add(new UnaryOperationButton("log", model), new RCPosition(3, 1));
        cp.add(new UnaryOperationButton("ln", model), new RCPosition(4, 1));
        cp.add(new UnaryOperationButton("sin", model), new RCPosition(2, 2));
        cp.add(new UnaryOperationButton("cos", model), new RCPosition(3, 2));
        cp.add(new UnaryOperationButton("tan", model), new RCPosition(4, 2));
        cp.add(new UnaryOperationButton("ctg", model), new RCPosition(5, 2));
        cp.add(new SwapSignButton(model), new RCPosition(5, 4)); // TODO try entering 5 then swap then 1/x -> fix


    }
}
