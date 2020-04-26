package io.github.ml.app.unit;

import io.github.ml.app.chart.Plottable;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This is gradient descent applied for the case of logistic regression. Unlike the typical gradient descent it uses
 * <p>
 * the logIterationUpdate which returns a LogisticRegressionFunction
 */
@Slf4j
public class LogGradientDescent implements Function<LearningInputs, RegressionFunction>, Plottable<Map<Integer, Double>> {


    private final Map<Integer, Double> costPlot = new HashMap<>();
    private final Map<double[], Double> thetaMap = new HashMap<>();

    /**
     * This method will call multiple iterations to the train method inorder to train a target function and
     * <p>
     * at the same time map the cost to each iteration in order to see if cost is reducing with
     * <p>
     * each iteration
     */
    @Override
    public RegressionFunction apply(final LearningInputs inputs) {

        long startUp = System.currentTimeMillis();

        int iteration = 0;
        double[] newTheta = new double[inputs.getTargetFunction().getThetas().length];

        RegressionFunction targetFunction = inputs.getTargetFunction();

        double cost = targetFunction.cost(inputs.getDataset(), inputs.getLabels());

        costPlot.put(iteration, cost);

        for (int i = 0; i <= inputs.getMaximumIterations(); i++) {

            ++iteration;

            double[] oldTheta = targetFunction.getThetas().clone();

            thetaMap.put(oldTheta, cost);

            // Train model to get new theta
            targetFunction = RegressionUtils.logIterationUpdate(targetFunction, inputs.getDataset(), inputs.getLabels(), inputs.getAlpha());

            newTheta = targetFunction.getThetas().clone();

            // cost of the newTheta Update
            cost = targetFunction.cost(inputs.getDataset(), inputs.getLabels());

            if (Double.isNaN(cost) || Double.isInfinite(cost)) {
                // at this point the model has degraded better to use older theta
                Map.Entry<double[], Double> min = Collections.min(thetaMap.entrySet(), (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue() * -1));
                log.info("Function has been interrupted @ cost of {} after {} iterations in {} ms", min.getValue(), iteration, System.currentTimeMillis() - startUp);
                return new LogisticRegressionFunction(min.getKey());
            }
//
            // the chart library cannot chart infinity
            if (!Double.isInfinite(cost) || !Double.isNaN(cost)) {
                costPlot.put(iteration, cost);
            }
//
            if (convergenceIsAttained(oldTheta, newTheta, inputs.getEpsilon())) {
                log.info("Convergence attained after {} iterations", iteration);
                break;
            }
        }

        log.info("Function has been optimised to a cost of {} after {} iterations in {} ms", cost, iteration, System.currentTimeMillis() - startUp);

        return new LogisticRegressionFunction(newTheta);
    }

    /**
     * @return Map containing costs and gradients
     */
    @Override
    public Map<Integer, Double> getPlot() {
        return costPlot;
    }

    /**
     * This function tests whether convergence has been attained by checking if the difference between
     * <p>
     * the old and new values of theta is smaller than this criteria. If this is the case I stop the
     * <p>
     * loop since theta will not change in a meaningful way.
     */
    private boolean convergenceIsAttained(final double[] oldTheta, final double[] newTheta, final double epsilon) {
        double diffSum = 0.0;
        for (int i = 0; i < oldTheta.length; i++) {
            diffSum = +Math.abs(oldTheta[i] - newTheta[i]);
        }
        return diffSum / oldTheta.length < epsilon;
    }
}
