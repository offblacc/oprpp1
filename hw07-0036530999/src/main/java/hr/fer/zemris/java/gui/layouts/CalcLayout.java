package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.RCPosition;

import java.awt.*;
import java.util.*;

// 5 x 7, but the first row has 3 columns, the rest have 7

public class CalcLayout implements LayoutManager2 { // upravljač
    int gap;
    private static final int ROWS = 5;
    private static final int COLUMNS = 7;
    private static final int DEFAULT_GAP = 0;

    // FIXME - components arr hardcoded here
    private final Component[][] components = new Component[ROWS][COLUMNS];
    // make private static final HashMap<RCPosition, Integer> specialPositions and put rpc 1, 1 with integer value 5
    private static final HashMap<RCPosition, Integer> specialPositions = new HashMap<>();

    static {
        specialPositions.put(new RCPosition(1, 1), 5);
    }

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
        if (!(RCPositionConstraints instanceof RCPosition pos)) {
            throw new CalcLayoutException("RCPositionConstraints must be an instance of RCPosition.");
        }
        int row = pos.getRow();
        int column = pos.getColumn();
        if (row < 1 || row > ROWS) {
            throw new CalcLayoutException("Row must be between 1 and 5.");
        }
        if (column < 1 || column > COLUMNS) {
            throw new CalcLayoutException("Column must be between 1 and 7.");
        }
        if (row == 1 && column > 1 && column < 6) {
            throw new CalcLayoutException("Invalid position.");
        }
        if (components[row - 1][column - 1] != null) {
            throw new CalcLayoutException("Position is already occupied.");
        }
        components[row - 1][column - 1] = comp;
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (components[i][j] != null) {
                    Dimension d = components[i][j].getMaximumSize();
                    if (d.width < x) x = d.width;
                    if (d.height < y) y = d.height;
                }
            }
        }
        return new Dimension(x, y);
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
        throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int x = 0;
        int y = 0;
        RCPosition decidingPosition = null;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (components[i][j] == null) continue;
                int div = 1;
                if (specialPositions.containsKey(new RCPosition(i + 1, j + 1))) {
                    div = specialPositions.get(new RCPosition(i + 1, j + 1));
                }
                Dimension d = components[i][j].getPreferredSize();
                System.out.println(d + " is the preferred size");
                if (d.width / div > x) {
                    x = d.width / div;
                    decidingPosition = new RCPosition(i + 1, j + 1);
                }
                if (d.height > y) {
                    y = d.height;
                    decidingPosition = new RCPosition(i + 1, j + 1);
                }
            }
        }
        int rows = ROWS;
        if (decidingPosition.getRow() == 1) { // FIXME .. you know
            rows = 3;
        }
        return new Dimension(x * COLUMNS + gap * (COLUMNS - 1), y * rows + gap * (rows - 1));
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        int x = 0; // TODO refactor this, delegate to a method which will be used by this, max and pref
        int y = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (components[i][j] != null) {
                    Dimension d = components[i][j].getMinimumSize();
                    if (d.width > x) x = d.width;
                    if (d.height > y) y = d.height;
                }
            }
        }
        return new Dimension(x * COLUMNS + gap * (COLUMNS - 1), y * ROWS + gap * (ROWS - 1));
    }

    // when there is a component that takes multiple rows, it is stored in specialPositions
    // and the number of rows it takes is stored as an Integer value
    // and when we are calculating the size of the component, we need to take that into account, and for the ones following it
    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int width = parent.getWidth() - insets.left - insets.right;
        int height = parent.getHeight() - insets.top - insets.bottom;
        int componentWidth = (width - (COLUMNS - 1) * gap) / COLUMNS;
        int componentHeight = (height - (ROWS - 1) * gap) / ROWS;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0, columnPointer = 0; j < COLUMNS; j++) {
                // TODO idk where it is but you need to be able to parse "1, 1" to RCPosition from somewhere
                if (components[i][j] == null) continue;
                if (columnPointer < j) columnPointer = j;
                int widthMul = specialPositions.getOrDefault(new RCPosition(i + 1, j + 1), 1);
                int x = insets.left + columnPointer * (componentWidth + gap);
                int y = insets.top + i * (componentHeight + gap);
                components[i][j].setBounds(x, y, componentWidth * widthMul + gap * (widthMul - 1), componentHeight);
                columnPointer += widthMul;
            }
        }
    }
}
