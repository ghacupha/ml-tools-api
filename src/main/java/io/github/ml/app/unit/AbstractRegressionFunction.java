package io.github.ml.app.unit;

import java.util.List;

public abstract class AbstractRegressionFunction implements RegressionFunction {


    private final double[] thetaVector;

    public AbstractRegressionFunction(double[] thetaVector) {
        this.thetaVector = thetaVector;
    }

    /**
     *
     * @return A copy of the theta vector
     */
    @Override
    public double[] getThetas() {
        return this.thetaVector.clone();
    }

    @Override
    public double[] derivative(List<Double[]> dataSet, List<Double> labels) {
        return RegressionUtils.computeDerivativeTerm(this, dataSet, labels);
    }
}
