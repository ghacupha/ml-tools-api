package io.github.ml.app.chart;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class XYChartCostAndGradientPlot implements Plot<CostGradientChart<XYChart, XYChart>, Integer, Double>, CostGradientPlot<CostGradientChart<XYChart, XYChart>> {

    @Override
    public CostGradientChart<XYChart, XYChart> plotGraph(Map<Integer, Double> plotData) {

        log.debug("Plotting graph for plot data with : {} entries", plotData.size());

        double[] iterations = new double[plotData.keySet().size()];
        double[] costs = new double[plotData.keySet().size()];

        AtomicInteger iteration = new AtomicInteger();

        plotData.forEach((key, value) -> {
            int index = iteration.getAndIncrement();
            iterations[index] = key;
            costs[index] = value;
        });

        return () -> QuickChart.getChart("Cost Chart", "Iterations", "Cost", "cost(iter)", iterations, costs);
    }
}
