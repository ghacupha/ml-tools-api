package io.github.ml.app.unit;

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
}
