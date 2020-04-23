package io.github.ml.app.chart;

import java.util.Map;

/**
 * This interface plots graphs for J and X with an output of type T.
 * The graphs are plotted on a simple 2 dimensional planes where X is
 * on the horizontal dimension and Y is on the vertical dimension.
 *
 * @param <T> Type of output of graph diagram
 */
public interface Plot<T, X, Y> {

    T plotGraph(Map<X, Y> plotData);
}
