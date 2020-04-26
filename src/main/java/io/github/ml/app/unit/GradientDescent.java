package io.github.ml.app.unit;

import io.github.ml.app.chart.CostAndGradient;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * This is the class responsible for calling the train function to optimize the LinearRegressionFunction.
 * <p>
 * In a sense the gradient descent algorithm is maintained here. Informal ideas have been applied
 * <p>
 * to cater for instances when the double value degrades into Infinity or NaN in which case the function
 * <p>
 * will simply return the LinearRegressionFunction with the previous theta values which had the
 * <p>
 * least cost. Normal models do not work like that, but normal models are not created in java.
 * <p>
 * With every iteration we take the theta value and subtract the learning rate multiplied by the derivative
 * <p>
 * term. The derivative term for linear regression and the derivative term for logistic regression are
 * <p>
 * different, so in future we need to methods for each.
 */
@Slf4j
public class GradientDescent implements Function<LearningInputs, RegressionFunction> {

    private final Map<Integer, CostAndGradient> costAndGradientPlot = new HashMap<>();
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

        double cost = RegressionUtils.cost(targetFunction, inputs.getDataset(), inputs.getLabels());
        double gradient = RegressionUtils.gradient(targetFunction, inputs.getDataset(), inputs.getLabels());
        costAndGradientPlot.put(iteration, new CostAndGradient<>(cost, gradient));

        for (int i = 0; i <= inputs.getMaximumIterations(); i++) {

            double[] oldTheta = targetFunction.getThetas().clone();

            thetaMap.put(oldTheta, cost);

            // Train model to get new theta
            targetFunction = RegressionUtils.train(targetFunction, inputs.getDataset(), inputs.getLabels(), inputs.getAlpha());

            newTheta = targetFunction.getThetas().clone();

            cost = RegressionUtils.cost(new LinearRegressionFunction(newTheta), inputs.getDataset(), inputs.getLabels());
            gradient = RegressionUtils.gradient(new LinearRegressionFunction(newTheta), inputs.getDataset(), inputs.getLabels());

            if (Double.isNaN(cost) || Double.isInfinite(cost)) {
                // at this point the model has degraded better to use older theta
                Entry<double[], Double> min = Collections.min(thetaMap.entrySet(), new Comparator<Entry<double[], Double>>() {
                    public int compare(Entry<double[], Double> entry1, Entry<double[], Double> entry2) {
                        return entry1.getValue().compareTo(entry2.getValue() * -1);
                    }
                });
                log.info("Function has been interrupted @ cost of {} after {} iterations in {} ms", min.getValue(), iteration, System.currentTimeMillis() - startUp);
                return new LinearRegressionFunction(min.getKey());
            }

            // the chart library cannot chart infinity
            if (!Double.isInfinite(cost) || !Double.isNaN(cost)) {
                costAndGradientPlot.put(iteration++, new CostAndGradient<>(cost, gradient));
            }

            if (convergenceIsAttained(oldTheta, newTheta, inputs.getEpsilon())) {
                log.info("Convergence attained after {} iterations", iteration);
                break;
            }
        }

        log.info("Function has been optimised to a cost of {} after {} iterations in {} ms", cost, iteration, System.currentTimeMillis() - startUp);

        return new LinearRegressionFunction(newTheta);
    }

    /**
     * @return Map containing costs and gradients
     */
    public Map<Integer, CostAndGradient> getCostAndGradientPlot() {
        return costAndGradientPlot;
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
