package io.github.ml.app.chart;

/**
 * Interface was created after carefully considering the fact that the return value for the
 * charting library, should one want to chart cost and gradient simultaneously may need to contain
 * both charts, so that both may be charted at the same time
 *
 * @param <C>
 * @param <G>
 */
public interface CostGradientChart<C,G> {

    /**
     *
     * @return The cost chart
     */
    C costChart();

    /**
     *
     * @return The gradient chart
     */
    G gradientChart();
}
