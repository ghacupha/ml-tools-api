package io.github.ml.app.unit;

/**
 * This is a simple function for applying a theta vector learned in a model to a feature
 * vector.
 */
public class LinearRegressionFunction implements RegressionFunction {

    private final double[] thetaVector;

    public LinearRegressionFunction(final double[] thetaVector) {
        this.thetaVector = thetaVector;
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

        if (featureVector.length != thetaVector.length){
            throw new IllegalArgumentException("The dimension of the feature vector must equal the theta vector");
        }
        for (int i=0; i < featureVector.length; i++) {

            prediction += featureVector[i] * thetaVector[i];
        }
        return prediction;
    }

    /**
     *
     * @return A copy of the theta vector
     */
    @Override
    public double[] getThetas() {
        return this.thetaVector.clone();
    }
}
