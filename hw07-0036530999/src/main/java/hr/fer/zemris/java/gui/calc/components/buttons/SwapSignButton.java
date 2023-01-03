package hr.fer.zemris.java.gui.calc.components.buttons;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import javax.swing.*;

public class SwapSignButton extends JButton {
    public SwapSignButton(CalcModel model) {
        super("+/-");
        setFont(getFont().deriveFont(20f));
        addActionListener(e -> {
            model.swapSign();
        });
    }
}
