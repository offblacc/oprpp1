package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class BarChartComponent extends JComponent {
    private final short MARGIN = 20;
    private BarChart chart;

    public BarChartComponent(BarChart chart) {
        this.chart = chart;
        setLayout(null);
        setVisible(true);
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
        int leftMostMargin = MARGIN;
        int rightMostMargin = MARGIN * 2;
        int valuesStartX = leftMostMargin + fm.getHeight();
        String xDescription = chart.getxDescription();

        // draw y axis name
        g2d.rotate(-Math.PI / 2);
        g2d.drawString(chart.getyDescription(), -parentHeight / 2, MARGIN);
        g2d.rotate(Math.PI / 2);
        g2d.drawString(xDescription, parentWidth / 2 - fm.stringWidth(xDescription) / 2, (int) (parentHeight - MARGIN * 1.5));
        int maxYValueStringWidth = fm.stringWidth(Integer.toString(chart.getyMax()));
        // draw grid
        int yGridStep = (parentHeight - MARGIN * 4) / chart.getyMax();
        int xGridStep = (parentWidth - valuesStartX - rightMostMargin) / values.size();

        g2d.setColor(Color.decode("#EEDDBF"));
        // drawing horizontal lines
        for (int i = 0; i <= chart.getyMax(); i += chart.getyStep()) {
            g2d.drawLine(valuesStartX, parentHeight - MARGIN * 3 - i * yGridStep, parentWidth - rightMostMargin, parentHeight - MARGIN * 3 - i * yGridStep);
        }
        // drawing vertical lines
        for (int i = 0; i < values.size(); i++) {
            g2d.drawLine(2 * MARGIN + maxYValueStringWidth + i * xGridStep, parentHeight - MARGIN * 3, 2 * MARGIN + maxYValueStringWidth + i * xGridStep, MARGIN);
        }
        g2d.setColor(Color.BLACK);
        for (int i = 0; i <= chart.getyMax(); i += chart.getyStep()) {
            g2d.drawLine(2 * MARGIN + maxYValueStringWidth - 2, parentHeight - MARGIN * 3 - i * yGridStep, 2 * MARGIN + maxYValueStringWidth+ 2, parentHeight - MARGIN * 3 - i * yGridStep);
        }

        // setting the font for the axis values and drawing them
        Font font = g2d.getFont();
        g2d.setFont(font.deriveFont(Font.BOLD));
        for (int i = 0; i <= chart.getyMax(); i += chart.getyStep()) { // first y values
            String stringToDraw = Integer.toString(i);
            g2d.drawString(stringToDraw, valuesStartX + (maxYValueStringWidth - fm.stringWidth(stringToDraw)), parentHeight - MARGIN * 3 - i * yGridStep + fmAscent / 2);
        }
        for (int i = 0; i < values.size(); i++) { // then x values
            String stringToDraw = Integer.toString(values.get(i).getX());
            g2d.drawString(stringToDraw, 2 * MARGIN + maxYValueStringWidth + i * xGridStep + xGridStep/2 - fm.stringWidth(stringToDraw) / 2,
                    parentHeight - MARGIN * 3 + fmAscent);
        }


        // drawing the axis themselves
        g2d.drawLine(2 * MARGIN + maxYValueStringWidth, MARGIN, 2 * MARGIN + maxYValueStringWidth, parentHeight - MARGIN * 3);
        g2d.drawLine(2 * MARGIN + maxYValueStringWidth, parentHeight - MARGIN * 3, parentWidth - MARGIN, parentHeight - MARGIN * 3);
        // set font to bold

        int i = 0; // index of the current value, keeping track of how far we are in the x axis
        for (var value : values) {
            int x = 2 * MARGIN + maxYValueStringWidth + values.indexOf(value) * xGridStep;
            int y = parentHeight - MARGIN * 3 - value.getY() * yGridStep;
            g2d.setColor(Color.decode("#F37747"));
            g2d.fillRect(2 * MARGIN + maxYValueStringWidth + i++ * xGridStep, parentHeight - MARGIN * 3 - value.getY() * yGridStep, xGridStep - 2, value.getY() * yGridStep);
        }

        g2d.setColor(Color.BLACK);
        Polygon arrow = new Polygon();
        arrow.addPoint(parentWidth - MARGIN + 5, parentHeight - MARGIN * 3);
        arrow.addPoint(parentWidth - MARGIN, parentHeight - MARGIN * 3 - 5);
        arrow.addPoint(parentWidth - MARGIN, parentHeight - MARGIN * 3 + 5);
        g2d.fillPolygon(arrow);
        arrow = new Polygon();
        arrow.addPoint(2 * MARGIN + maxYValueStringWidth, MARGIN - 5);
        arrow.addPoint(2 * MARGIN + maxYValueStringWidth - 5, MARGIN);
        arrow.addPoint(2 * MARGIN + maxYValueStringWidth + 5, MARGIN);
        g2d.fillPolygon(arrow);

    }
}
