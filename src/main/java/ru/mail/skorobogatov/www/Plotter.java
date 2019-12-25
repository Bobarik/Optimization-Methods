package ru.mail.skorobogatov.www;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plotter extends JFrame {
    public Plotter(final String title) {
        super(title);
    }
    
    public void plotterStart(List<ArrayList<Double>> dataset, Double[] regression) {
        final JFreeChart chart = setupPlots(dataset, regression);
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1920, 1080));
        setContentPane(chartPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public JFreeChart setupPlots(List<ArrayList<Double>> dataset, Double[] regression) {
        // Create a single plot containing both the scatter and line
        XYPlot plot = new XYPlot();
        
        /* SETUP SCATTER */

        final XYSeries series = new XYSeries("Data from the dataset");
        
        Machine.createDataset(1000);
        for (List<Double> i : dataset) {
            series.add(i.get(0), i.get(1));
        }

        // Create the scatter data, renderer, and axis
        XYDataset collection1 = new XYSeriesCollection(series);
        XYItemRenderer renderer1 = new XYLineAndShapeRenderer(false, true);	// Shapes only
        double size = 1.0;
        double delta = size / 2.0;
        Shape circle = new Ellipse2D.Double(-delta, -delta, size, size);
        renderer1.setSeriesShape(0, circle);
        ValueAxis domain1 = new NumberAxis("X");
        ValueAxis range1 = new NumberAxis("Y");
        
        // Set the scatter data, renderer, and axis into plot
        plot.setDataset(0, collection1);
        plot.setRenderer(0, renderer1);
        plot.setDomainAxis(0, domain1);
        plot.setRangeAxis(0, range1);
        
        // Map the scatter to the first Domain and first Range
        plot.mapDatasetToDomainAxis(0, 0);
        plot.mapDatasetToRangeAxis(0, 0);
        
        /* SETUP LINE */
        
        XYSeries line = new XYSeries("Function itself");
        for (double i = Machine.leftBorder; i < Machine.rightBorder; i+=0.01) {
            line.add(i,Machine.func(i));
        }

        XYSeries lineReg = new XYSeries("Regression");
        for (double i = Machine.leftBorder; i < Machine.rightBorder; i+=0.01) {
            lineReg.add(i, regression[0] + regression[1] * i);
        }

        // Create the line data, renderer, and axis
        XYDataset collection2 = new XYSeriesCollection(line);
        XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, false);	// Lines only
        renderer2.setSeriesPaint(0, Color.BLUE);

        XYDataset collection3 = new XYSeriesCollection(lineReg);
        XYItemRenderer renderer3 = new XYLineAndShapeRenderer(true, false);	// Lines only
        renderer3.setSeriesPaint(0, Color.BLACK);

        // Set the line data, renderer, and axis into plot
        plot.setDataset(1, collection2);
        plot.setRenderer(1, renderer2);

        plot.setDataset(2, collection3);
        plot.setRenderer(2, renderer3);
        // Map the line to the second Domain and second Range
        plot.mapDatasetToDomainAxis(1, 0);
        plot.mapDatasetToRangeAxis(1, 0);
        
        plot.mapDatasetToDomainAxis(2, 0);
        plot.mapDatasetToRangeAxis(2, 0);

        // Create the chart with the plot and a legend
        JFreeChart chart = new JFreeChart("Regression", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        return chart;
    }
}