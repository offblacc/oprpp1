package hr.fer.oprpp1.hw08.jnotepadpp.statusbar;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Class that represents a status bar.
 */
public class StatusBar extends JPanel implements CaretListener, WindowListener {
    /**
     * Label that shows the length of the document.
     */
    private JLabel lengthLabel;

    /**
     * Label showing the caret position.
     */
    private JLabel positionLabel;

    /**
     * Clock label.
     */
    private Clock clock;

    /**
     * Constructor that initializes the status bar.
     */
    public StatusBar() {
        super(new GridLayout(1, 0));
        lengthLabel = new JLabel();
        positionLabel = new JLabel();
        clock = new Clock();
        add(lengthLabel);
        add(positionLabel);
        add(clock);
    }

    /**
     * {@inheritDoc}
     * Updates the position label.
     */
    @Override
    public void caretUpdate(CaretEvent e) {
        JTextComponent textArea = (JTextComponent) e.getSource();
        lengthLabel.setText("Length: " + textArea.getText().length());
        Element root = textArea.getDocument().getDefaultRootElement();
        int pos = e.getDot();
        int row = root.getElementIndex(pos);
        int col = pos - root.getElement(row).getStartOffset();
        positionLabel.setText("Ln: " + (row + 1) + ", Col: " + (col + 1));
    }


    /**
     * {@inheritDoc}
     * Stops the clock thread.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        clock.stop();
    }

    /* Empty methods */
    /* ------------------------------ */

    @Override
    public void windowOpened(WindowEvent e) {}


    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    /* ------------------------------ */
}
