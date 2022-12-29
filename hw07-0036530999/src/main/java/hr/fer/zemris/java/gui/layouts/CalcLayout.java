package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.RCPosition;

import java.awt.*;

// 5 x 7, but the first row has 3 columns, the rest have 7

public class CalcLayout implements LayoutManager2 { // upravljač
    int gap;
    private static final int ROWS = 5;
    private static final int COLUMNS = 7;
    private static final int DEFAULT_GAP = 0;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 500;

    public CalcLayout(int gap) {
        this.gap = gap;
    }

    public CalcLayout() {
        this(DEFAULT_GAP);
    }

    @Override
    public void addLayoutComponent(Component comp, Object RCPositionConstraints) {
        if (RCPositionConstraints == null) {
            throw new NullPointerException("RCPositionConstraints cannot be null.");
        }
        if (!(RCPositionConstraints instanceof RCPosition)) {
            throw new IllegalArgumentException("RCPositionConstraints must be an instance of RCPosition.");
        }
        RCPosition position = (RCPosition) RCPositionConstraints;
        if (position.getRow() < 1 || position.getRow() > ROWS) {
            throw new IllegalArgumentException("Row must be between 1 and 5.");
        }
        if (position.getColumn() < 1 || position.getColumn() > COLUMNS) {
            throw new IllegalArgumentException("Column must be between 1 and 7.");
        }
        if (position.getRow() == 1 && position.getColumn() > 3) {
            throw new IllegalArgumentException("Column must be between 1 and 3 for row 1.");
        }
        comp.setBounds(10 * position.getColumn(), 10 * position.getRow(), 10, 10);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public void layoutContainer(Container parent) {

    }
}
