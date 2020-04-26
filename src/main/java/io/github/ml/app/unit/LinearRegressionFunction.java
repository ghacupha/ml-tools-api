package io.github.ml.app.unit;

import java.util.List;

/**
 * This is a simple function for applying a theta vector learned in a model to a feature
 * vector.
 */
public class LinearRegressionFunction extends AbstractRegressionFunction implements RegressionFunction {

    public LinearRegressionFunction(final double[] thetaVector) {
        super(thetaVector);
    }

    /**
     * This method applies the theta vector of a model to the feature vector in the argument
     *
     * @param featureVector These vector contains the numerical features of an example
     * @return the predicted label
     */
    @Override
    public Double apply(final Double[] featureVector) {
        // for computational reasons the first element has to be 1.0
        assert featureVector[0] == 1.0;

        double prediction = 0;

        double[] thetaVector = getThetas();

        if (featureVector.length != getThetas().length){
            throw new IllegalArgumentException("The dimension of the feature vector must equal the theta vector");
        }
        for (int i=0; i < featureVector.length; i++) {

            prediction += featureVector[i] * thetaVector[i];
        }
        return prediction;
    }

    @Override
    public double cost(List<Double[]> dataSet, List<Double> labels) {
        return RegressionUtils.cost(this, dataSet, labels);
    }
}
