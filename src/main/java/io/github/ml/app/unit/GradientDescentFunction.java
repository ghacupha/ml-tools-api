package io.github.ml.app.unit;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * It is hoped that by implementing this function, that it would be clear the cause of premature
 * <p>
 * interruption of the optimization process in the precursor's implementation, whether the cost
 * <p>
 * cost computation resulting in Nan exceptions and errors that seem hard to reproduce without rebuilding
 * <p>
 * the entire model. This is especially so because the dataSet used for test cases seemed robust enough
 * <p>
 * to produce reliable models for classification in the case of logistic regression.Since we can reliably
 * <p>
 * and accurately compute partial derivatives, then why the heck is the gradient descent algorithm failing?
 */
@Slf4j
public class GradientDescentFunction implements Function<LearningInputs, OptimizationArtefact> {

    @Override
    public OptimizationArtefact apply(LearningInputs learningInputs) {
        int iterationsCounter = 0;
        Map<Integer, Double> costMap = new HashMap<>();
        double[] oldTheta = learningInputs.getTargetFunction().getThetas();
        double[] newTheta = new double[oldTheta.length];
        RegressionFunction updatedTargetFunction = null; // boy you better hope you don't forget to update this
        boolean newThetaUpdated = false; // one-time update flag for when newTheta gets updated
        boolean targetFunctionUpdated = false;
        boolean isConverged = false;

        // outer-most loop limited by maximum iterations
        for (int iter = 0; iter < learningInputs.getMaximumIterations(); iter++) {

            double[] derivatives;

            if (targetFunctionUpdated) {
                derivatives = updatedTargetFunction.derivative(learningInputs.getDataset(), learningInputs.getLabels());
            } else {
                derivatives = learningInputs.getTargetFunction().derivative(learningInputs.getDataset(), learningInputs.getLabels());
            }

            // update oldTheta with newTheta in consecutive loops just before changing the newTheta again
            // i.e. if newTheta is not blank
            if (newThetaUpdated) {
                oldTheta = newTheta;
            }

            // update loop for updating newTheta
            for (int j = 0; j < learningInputs.getTargetFunction().getThetas().length; j++) {
                newTheta[j] = oldTheta[j] - learningInputs.getAlpha() * derivatives[j];
            }
            newThetaUpdated = true;

            updatedTargetFunction = new LogisticRegressionFunction(newTheta);
            targetFunctionUpdated = true;

            // update cost-map and counter, @ this point we are relatively certain the function ain't null
            costMap.put(++iterationsCounter, updatedTargetFunction.cost(learningInputs.getDataset(), learningInputs.getLabels()));

            //  exit loop upon convergence rule
            if (convergenceIsAttained(oldTheta, newTheta, learningInputs.getEpsilon())) {
                isConverged = true;
                break;
            }
        }

        log.info("Gradient descent algorithm event, after {} iterations", iterationsCounter);

        return OptimizationArtefact.builder()
            .regressionFunction(updatedTargetFunction)
            .costMap(costMap)
            .isConverged(isConverged)
            .iterations(iterationsCounter)
            .build();
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
