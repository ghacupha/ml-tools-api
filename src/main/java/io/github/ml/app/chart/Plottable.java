package io.github.ml.app.chart;

import java.util.Map;

/**
 * The name is not very intuitive, but it represents an object which has data in the form T
 * <p>
 * For instance if T is {@code Map<Integer, Double>}, where the integer represents a unit count, and the double
 * <p>
 * represents a measurement. For instance when monitoring the decline of cost in a gradient
 * <p>
 * descent algorithm, we are monitoring the cost per iteration and the input will therefore be
 * <p>
 * represented by the integer for the iteration and the double value for the model cost
 *
 * @param <T> Type of Plot data
 */
public interface Plottable<T> {

    /**
     * @return T plot data for charting
     */
    T getPlot();
}
