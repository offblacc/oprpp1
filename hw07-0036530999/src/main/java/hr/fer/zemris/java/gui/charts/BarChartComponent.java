package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class BarChartComponent extends JComponent {
    private final short MARGIN = 20;
    private BarChart chart;
    private List<XYValue> values;

    public BarChartComponent(BarChart chart) {
        this.chart = chart;
        setLayout(null);
        setVisible(true);
        List<XYValue> values = chart.getValues();
        int valuesCount = values.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        int fmAscent = fm.getAscent();
        var values = chart.getValues();
        int parentWidth = getParent().getWidth();
        int parentHeight = getParent().getHeight();
        int valuesStartX = MARGIN + fm.getHeight();
        String xDescription = chart.getxDescription();
        int maxY = chart.getyMax();

        // draw y axis name
        g2d.rotate(-Math.PI / 2);
        g2d.drawString(chart.getyDescription(), -parentHeight / 2, MARGIN);
        g2d.rotate(Math.PI / 2);
        g2d.drawString(xDescription, parentWidth / 2 - fm.stringWidth(xDescription) / 2, (int) (parentHeight - MARGIN * 1.5));
        int maxYValueStringWidth = fm.stringWidth(Integer.toString(maxY));
        int numOfGaps = maxY / chart.getyStep();
        int xGridStep = (parentWidth - valuesStartX - 2 * MARGIN) / values.size();

        g2d.setColor(Color.decode("#EEDDBF"));
        // set line width
        g2d.setStroke(new BasicStroke(2));
        // drawing horizontal lines
        for (int i = 0; i <= numOfGaps; i++) {
            int y = (int) Math.round(MARGIN + i * (parentHeight - 4 * MARGIN) / (double) numOfGaps);
            g2d.drawLine(2 * MARGIN + maxYValueStringWidth, y, parentWidth - MARGIN + 5, y);
        }

        // drawing vertical lines
        for (int i = 0; i <= values.size(); i++) {
            g2d.drawLine(2 * MARGIN + maxYValueStringWidth + i * xGridStep, parentHeight - MARGIN * 3, 2 * MARGIN + maxYValueStringWidth + i * xGridStep, MARGIN - 5);
        }

        g2d.setColor(Color.BLACK); // for the font
        // set font to bold
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
        for (int i = maxY; i >= 0; i -= chart.getyStep()) { // draw y axis values
            int y = (int) Math.round(MARGIN + i * (parentHeight - 4 * MARGIN) / (double) maxY);
            String stringToRender = Integer.toString(maxY - i);
            g2d.drawString(stringToRender, 2 * MARGIN + maxYValueStringWidth - fm.stringWidth(stringToRender) - 3, y + fmAscent / 2 - 1);
        }
        // now for the x axis values
        for (int i = 0; i < values.size(); i++) {
            String stringToRender = Integer.toString(values.get(i).getX());
            g2d.drawString(stringToRender, 2 * MARGIN + maxYValueStringWidth + i * xGridStep - fm.stringWidth(stringToRender) / 2 + xGridStep / 2, parentHeight - MARGIN * 2 - 5);
        }

        g2d.setColor(Color.decode("#F37747")); // color for the bars

        // now drawing the bars themselves

        for (int i = 0; i < values.size(); i++) {
            int barHeight = (int) Math.round(values.get(i).getY() * (parentHeight - 4 * MARGIN) / (double) maxY);
            g2d.fillRect(2 * MARGIN + maxYValueStringWidth + i * xGridStep + 1, parentHeight - MARGIN * 3 - barHeight, xGridStep - 2, barHeight);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawLine(2 * MARGIN + maxYValueStringWidth, MARGIN - 5, 2 * MARGIN + maxYValueStringWidth, parentHeight - MARGIN * 3); // vertical, y-axis
        g2d.drawLine(2 * MARGIN + maxYValueStringWidth, parentHeight - MARGIN * 3, parentWidth - MARGIN + 5, parentHeight - MARGIN * 3); // horizontal, x-axis

        Polygon arrow = new Polygon(); // x-axis arrow
        arrow.addPoint(parentWidth - MARGIN + 10, parentHeight - MARGIN * 3);
        arrow.addPoint(parentWidth - MARGIN + 5, parentHeight - MARGIN * 3 - 5);
        arrow.addPoint(parentWidth - MARGIN + 5, parentHeight - MARGIN * 3 + 5);
        g2d.fillPolygon(arrow);
        arrow = new Polygon(); // y-axis arrow
        arrow.addPoint(2 * MARGIN + maxYValueStringWidth, MARGIN - 10);
        arrow.addPoint(2 * MARGIN + maxYValueStringWidth - 5, MARGIN - 5);
        arrow.addPoint(2 * MARGIN + maxYValueStringWidth + 5, MARGIN - 5);
        g2d.fillPolygon(arrow);

    }
}
