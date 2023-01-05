package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BarChartDemo extends JFrame {
    String pathToFile;
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments.");
            return;
        }
        BarChart chart = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(args[0]).toFile()))) {
            String[] parts = new String[6];
            for (int i = 0; i < 6; i++) parts[i] = reader.readLine();
            chart = new BarChart(Arrays.stream(parts[2].split(" "))
                    .map(s -> s.split(","))
                    .map(a -> new XYValue(Integer.parseInt(a[0]), Integer.parseInt(a[1])))
                    .collect(Collectors.toList()),
                    parts[0],
                    parts[1],
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]),
                    Integer.parseInt(parts[5])
            );
        } catch (IOException e) {
            System.out.println("IO Error.");
            e.printStackTrace(); // TODO remove
            return;
        } catch (IllegalArgumentException e) { // covers NumberFormatException
            System.out.println("Invalid file format.");
            e.printStackTrace(); // TODO remove
            return;
        }
        BarChart finalChart = chart;
        SwingUtilities.invokeLater(() -> new BarChartDemo(finalChart, args[0]).setVisible(true));
    }

    public BarChartDemo(BarChart chart, String path) {
        this.pathToFile = path;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI(chart);
    }

    public void initGUI(BarChart chart) {
        setTitle("BarChart");
        setLayout(new BorderLayout());
        setSize(500, 500);
        JLabel label = new JLabel(pathToFile);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);
        add(chart.getComponent(), BorderLayout.CENTER);
    }
}
