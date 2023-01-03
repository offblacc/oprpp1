package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.RCPosition;
import hr.fer.zemris.java.gui.calc.components.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.components.DecimalPointButton;
import hr.fer.zemris.java.gui.calc.components.Display;
import hr.fer.zemris.java.gui.calc.components.NumberButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import javax.swing.*;
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
        for (var op : BinaryOperator.getOperatorsMap().entrySet()) {
            System.out.println(yPos);
            cp.add(new BinaryOperationButton(op.getKey(), op.getValue(), model), new RCPosition(yPos--, 6));
        }
    }
}
