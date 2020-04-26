package io.github.ml.app.unit;

import java.util.List;
import java.util.function.Function;

public interface RegressionFunction extends Function<Double[], Double> {
    /**
     * This method applies the theta vector of a model to the feature vector in the argument
     *
     * @param featureVector These vector contains the numerical features of an example
     * @return the predicted label
     */
    @Override
    Double apply(Double[] featureVector);

    /**
     *
     * @return A copy of the theta vector
     */
    double[] getThetas();

    /**
     * Returns the cost given the feature matrix x and labels y
     *
     * TODO add l2 and l1 regularised cost functions
     *
     * @param dataSet feature matrix (assumed to to contain the bias column)
     * @param labels y vector
     * @return cost of the function
     */
    double cost(List<Double[]> dataSet, List<Double> labels);

    /**
     * Returns derivative vector given the theta of this class
     *
     * TODO add gradient with l1 and l2 Pair penalty
     *
     * @param dataSet feature matrix x
     * @param labels y vector
     * @return geadient vector of the theta values
     */
    double[] derivative(List<Double[]> dataSet, List<Double> labels);
}
