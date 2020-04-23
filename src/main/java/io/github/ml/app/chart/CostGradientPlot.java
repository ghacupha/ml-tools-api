package io.github.ml.app.chart;

import java.util.Map;

public interface CostGradientPlot<T> extends Plot<T, Integer, CostAndGradient> {

    @Override
    T plotGraph(Map<Integer, CostAndGradient> plotData);
}
