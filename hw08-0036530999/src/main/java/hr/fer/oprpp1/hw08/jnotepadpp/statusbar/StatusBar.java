package hr.fer.oprpp1.hw08.jnotepadpp.statusbar;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import java.awt.*;

public class StatusBar extends JPanel implements CaretListener {
    private JLabel lengthLabel;
    private JLabel positionLabel;
    private int fontWidth;

    public StatusBar() {
        super(new GridLayout(1, 0));
        lengthLabel = new JLabel();
        fontWidth = getFontMetrics(this.getFont()).charWidth('0');
        positionLabel = new JLabel();
        add(lengthLabel);
        add(positionLabel);
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        JTextComponent textArea = (JTextComponent) e.getSource();
        lengthLabel.setText("Length: " + textArea.getText().length());
        Element root = textArea.getDocument().getDefaultRootElement();
        int pos = e.getDot();
        int row = root.getElementIndex(pos);
        int col = pos - root.getElement(row).getStartOffset();
        positionLabel.setText("Ln: " + (row + 1) +  ", Col: " + (col + 1));
    }
}
