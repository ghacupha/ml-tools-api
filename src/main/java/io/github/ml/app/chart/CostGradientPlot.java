package io.github.ml.app.chart;

import java.util.Map;

public interface CostGradientPlot<T> extends Plot<T, Integer, Double> {

    @Override
    T plotGraph(Map<Integer, Double> plotData);
}
