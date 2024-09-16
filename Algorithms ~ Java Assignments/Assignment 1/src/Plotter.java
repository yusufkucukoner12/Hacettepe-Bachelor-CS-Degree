import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;

class Plotter {

    public static void plotSortingResults(double[] insertion, double[] merge, double[] counting, String[] labels, String title, double[] inputSizes) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        chart.addSeries(labels[0], inputSizes, insertion);
        chart.addSeries(labels[1], inputSizes, merge);
        chart.addSeries(labels[2], inputSizes, counting);

        new SwingWrapper<>(chart).displayChart();
    }

    public static void plotSearchResults(double[] linearRandom, double[] linearSorted, double[] binarySorted, int[] inputAxis, String title) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Nanoseconds").xAxisTitle("Input Size").build();

        double[] doubleX = Arrays.stream(inputAxis).asDoubleStream().toArray();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        chart.addSeries("Linear Random", doubleX, linearRandom);
        chart.addSeries("Linear Sorted", doubleX, linearSorted);
        chart.addSeries("Binary Sorted", doubleX, binarySorted);

        new SwingWrapper<>(chart).displayChart();
    }
}
