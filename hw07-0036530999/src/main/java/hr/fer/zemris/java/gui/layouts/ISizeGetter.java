package hr.fer.zemris.java.gui.layouts;

import java.awt.*;

@FunctionalInterface
public interface ISizeGetter {
    /**
     * Returns the component's size.
     * @param comp Component.
     * @return Component's size.
     */
    public Dimension get(Component comp);
}
