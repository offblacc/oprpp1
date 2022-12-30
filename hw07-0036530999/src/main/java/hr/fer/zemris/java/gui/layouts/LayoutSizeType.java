package hr.fer.zemris.java.gui.layouts;

import java.awt.*;

public class LayoutSizeType {
    public static final ISizeGetter PREF = Component::getPreferredSize;
    public static final ISizeGetter MIN = Component::getMinimumSize;
    public static final ISizeGetter MAX = Component::getMaximumSize;
}
