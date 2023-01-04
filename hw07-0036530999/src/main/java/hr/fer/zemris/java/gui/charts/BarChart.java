package hr.fer.zemris.java.gui.charts;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BarChart {
    private List<XYValue> values;
    private String xDescription;
    private String yDescription;
    private int yMin;
    private int yMax;
    private int yStep;

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int yStep) {
        if (yMin < 0 || yMax <= yMin || yStep < 0) throw new IllegalArgumentException("Invalid arguments.");
        if ((yMax - yMin) % yStep != 0) yStep = (int) Math.ceil((yMax - yMin) / yStep);
        for (var value : values)
            if (value.getY() < yMin) throw new IllegalArgumentException("Invalid arguments, value is less than yMin.");

        this.values = values;
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.yMin = yMin;
        this.yMax = yMax;
        this.yStep = yStep;
    }

    public BarChartComponent getComponent() {
        return new BarChartComponent(this);
    }
}
