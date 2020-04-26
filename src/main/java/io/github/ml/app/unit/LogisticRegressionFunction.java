package io.github.ml.app.unit;

import java.util.List;

import static io.github.ml.app.unit.RegressionUtils.sigmoidFunction;

/**
 * This class internally is to use linear regression except the sigmoid is applied to the result
 * <p>
 * That is the hypothesis is identical except that the sigmoid function is applied to the raw model output
 */
public class LogisticRegressionFunction implements RegressionFunction {

    private final LinearRegressionFunction linearRegressionFunction;

    public LogisticRegressionFunction(final double[] thetaVector) {
        this.linearRegressionFunction = new LinearRegressionFunction(thetaVector);
    }

    @Override
    public Double apply(final Double[] featureVector) {
        return sigmoidFunction().apply(linearRegressionFunction.apply(featureVector));
    }

    @Override
    public double[] getThetas() {
        return linearRegressionFunction.getThetas().clone();
    }

    @Override
    public double cost(List<Double[]> dataSet, List<Double> labels) {
        return RegressionUtils.logCost(this, dataSet, labels);
    }

    @Override
    public double[] derivative(List<Double[]> dataSet, List<Double> labels) {
        return RegressionUtils.computeDerivativeTerm(this, dataSet, labels);
    }
}
