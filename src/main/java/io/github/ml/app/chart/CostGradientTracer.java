package io.github.ml.app.chart;

import java.util.Map;

/**
 * Used as call back to maintain a map of cost and gradient at every iteration
 *
 */
public interface CostGradientTracer {

    Map<Integer, CostAndGradient> trace(Integer iteration, CostAndGradient costGradient);
}
