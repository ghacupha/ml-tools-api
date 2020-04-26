package io.github.ml.app.unit;

import java.util.List;

import static io.github.ml.app.unit.RegressionUtils.sigmoidFunction;

/**
 * This class internally is to use linear regression except the sigmoid is applied to the result
 * <p>
 * That is the hypothesis is identical except that the sigmoid function is applied to the raw model output
 */
public class LogisticRegressionFunction extends AbstractRegressionFunction  implements RegressionFunction {

    public LogisticRegressionFunction(final double[] thetaVector) {
        super(thetaVector);
    }

    @Override
    public Double apply(final Double[] featureVector) {
        final LinearRegressionFunction linearRegressionFunction = new LinearRegressionFunction(getThetas());
        return sigmoidFunction().apply(linearRegressionFunction.apply(featureVector));
    }

    @Override
    public double cost(List<Double[]> dataSet, List<Double> labels) {
        return RegressionUtils.logCost(this, dataSet, labels);
    }
}
