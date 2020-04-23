package io.github.ml.app.unit;

/**
 * This class internally is to use linear regression except the sigmoid is applied to the result
 */
public class LogisticRegressionFunction implements RegressionFunction {

    private final LinearRegressionFunction linearRegressionFunction;

    public LogisticRegressionFunction(final double[] thetaVector) {
        this.linearRegressionFunction = new LinearRegressionFunction(thetaVector);
    }

    @Override
    public Double apply(final Double[] featureVector) {
        SigmoidFunction sigmoidFunction = new SigmoidFunction();
        return sigmoidFunction.apply(linearRegressionFunction.apply(featureVector));
    }

    @Override
    public double[] getThetas() {
        return linearRegressionFunction.getThetas().clone();
    }
}
