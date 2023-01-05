package hr.fer.zemris.java.gui.layouts;

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
        if (RCPositionConstraints == null || comp == null) {
            throw new NullPointerException("Component or RCPositionConstraints is null.");
        }
        RCPosition pos;
        if (!(RCPositionConstraints instanceof RCPosition)) {
            if (!(RCPositionConstraints instanceof String strPos)) {
                throw new CalcLayoutException("RCPositionConstraints must be an instance of RCPosition, or a string in the format \"row,column\".");
            }
            pos = RCPosition.parse(strPos);
        } else pos = (RCPosition) RCPositionConstraints;

        int row = pos.getRow();
        int column = pos.getColumn();
        if (row < 1 || row > ROWS) {
            throw new CalcLayoutException("Row must be between 1 and 5, got " + row);
        }
        if (column < 1 || column > COLUMNS) {
            throw new CalcLayoutException("Column must be between 1 and 7, got " + column);
        }
        if (row == 1 && column > 1 && column < 6) {
            throw new CalcLayoutException("Invalid position.");
        }
        if (components[row - 1][column - 1] != null) {
            throw new CalcLayoutException("Position (" + row + ", " + column + ") is already occupied.");
        }
        components[row - 1][column - 1] = comp;
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
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (components[i][j] == comp) {
                    components[i][j] = null;
                    return;
                }
            }
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getLayoutSize(parent, LayoutSizeType.MIN);
    }

    @Override
    public Dimension maximumLayoutSize(Container parent) {
        return getLayoutSize(parent, LayoutSizeType.MAX);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getLayoutSize(parent, LayoutSizeType.PREF);
    }

    /**
     * Lays out the specific container.
     *
     * @param parent the container to be laid out
     */
    @Override
    public void layoutContainer(Container parent) { // TODO make sure you alternate 38 39 38 39 blahblah u know
        Insets insets = parent.getInsets();
        int width = parent.getWidth() - insets.left - insets.right;
        int height = parent.getHeight() - insets.top - insets.bottom;
        int componentWidth = (width - (COLUMNS - 1) * gap) / COLUMNS;
        int componentHeight = (height - (ROWS - 1) * gap) / ROWS;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0, columnPointer = 0; j < COLUMNS; j++) {
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

    public Dimension getLayoutSize(Container parent, ISizeGetter sizeType) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (components[i][j] == null) continue;
                Dimension d = sizeType.get(components[i][j]);
                RCPosition currRCPos = new RCPosition(i + 1, j + 1);
                int width = d.width;
                if (specialPositions.containsKey(currRCPos)) {
                    int specialWidthValue = specialPositions.get(currRCPos);
                    width = (d.width - (specialWidthValue - 1) * gap) / specialWidthValue;

                }
                if (x < width) x = width;
                if (d.height > y) y = d.height;
            }
        }
        return new Dimension(x * COLUMNS + gap * (COLUMNS - 1), y * ROWS + gap * (ROWS - 1));
    }
}
