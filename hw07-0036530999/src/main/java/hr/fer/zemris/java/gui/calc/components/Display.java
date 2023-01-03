package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

import javax.swing.*;

import java.awt.*;

import static javax.swing.SwingUtilities.invokeLater;

public class Display extends JLabel implements CalcValueListener {
    public Display() {
        super("0");
        setFont(getFont().deriveFont(30f));
        setBackground(Color.YELLOW);
        setOpaque(true);
    }

    @Override
    public void valueChanged(CalcModel model) {
        invokeLater(() -> {
            setText(model.toString());
        });
    }
}
