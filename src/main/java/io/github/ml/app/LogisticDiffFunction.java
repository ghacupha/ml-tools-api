package io.github.ml.app;

import edu.stanford.nlp.optimization.DiffFunction;
import io.github.ml.app.unit.LogisticRegressionFunction;
import io.github.ml.app.unit.RegressionUtils;

import java.util.List;

/**
 * This is the objective logistic function containing the dataSet and labels for which the
 * <p>
 * derivative and values can be computed at different values of theta during line-search
 */
public class LogisticDiffFunction implements DiffFunction {

    private final List<Double[]> dataSet;
    private final List<Double> labels;

    public LogisticDiffFunction(final List<Double[]> dataSet, final List<Double> labels) {
        this.dataSet = dataSet;
        this.labels = labels;
    }

    @Override
    public double[] derivativeAt(final double[] doubles) {
        LogisticRegressionFunction logisticRegressionFunction = new LogisticRegressionFunction(doubles);
        return RegressionUtils.computeDerivativeTerm(logisticRegressionFunction, dataSet, labels);
    }

    @Override
    public double valueAt(final double[] doubles) {
        LogisticRegressionFunction logisticRegressionFunction = new LogisticRegressionFunction(doubles);
        return RegressionUtils.logCost(logisticRegressionFunction, dataSet, labels);
    }

    @Override
    public int domainDimension() {
        return dataSet.get(0).length;
    }
}
