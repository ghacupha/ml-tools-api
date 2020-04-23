package io.github.ml.app.chart;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class XYChartCostAndGradientPlot implements Plot<CostGradientChart<XYChart, XYChart>, Integer, CostAndGradient>, CostGradientPlot<CostGradientChart<XYChart, XYChart>> {

    @Override
    public CostGradientChart<XYChart, XYChart> plotGraph(Map<Integer, CostAndGradient> plotData) {

        log.debug("Plotting graph for plot data with : {} entries", plotData.size());

        double[] iterations = new double[plotData.keySet().size()];
        double[] gradients = new double[plotData.keySet().size()];
        double[] costs = new double[plotData.keySet().size()];

        AtomicInteger iteration = new AtomicInteger();

        plotData.forEach((key, value) -> {
            int index = iteration.getAndIncrement();
            iterations[index] = key;
            costs[index] = value.getCost();
            INDArray grads = (INDArray) value.getGradient();
            // quick fix expect to blow up in future
            gradients[index] = grads.sumNumber().doubleValue();
        });

        return new CostGradientChart<XYChart, XYChart>() {
            @Override
            public XYChart costChart() {
                return QuickChart.getChart("Cost Chart", "Iterations", "Cost", "cost(iter)", iterations, costs);
            }

            @Override
            public XYChart gradientChart() {
                return QuickChart.getChart("Gradient Descent Chart", "Iterations", "Gradient", "gradient(iter)", iterations, gradients);
            }
        };
    }
}
