package io.github.ml.app.unit;

import io.github.ml.app.chart.CostAndGradient;

import java.util.List;
import java.util.function.Function;

import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * This class contains function for computing cost and gradient for a model
 */
public class RegressionUtils {

    /**
     * Takes as input the target function, the list of training records, and their associated labels.
     * The predicted value is computed in a loop, and the error is obtained by subtracting the the real
     * label value.
     * The squared error is computed from which we get the mean error.
     *
     * @param targetFunction This is the function describing the model
     * @param dataSet        this is the list of all numerical features of the data set
     * @param labels         this is the vector containing the actual label for a dataset
     * @return the cost of the model
     */
    public static CostAndGradient<Double> ridgeLogCost(RegressionFunction targetFunction, List<Double[]> dataSet, List<Double> labels, double lambda) {

        int m = dataSet.size();
        double totalCost = 0;
        double totalGradient = 0;

        // calculate L2 regularization
        double regularization = l2(targetFunction, dataSet, lambda);

        // calculate the error for each example and add to the total sum
        totalCost = logisticCost(targetFunction, dataSet, labels, m, totalCost);

        return new CostAndGradient<>(totalCost + regularization, totalGradient);
    }

    /**
     * Takes as input the target function, the list of training records, and their associated labels.
     * The predicted value is computed in a loop, and the error is obtained by subtracting the the real
     * label value.
     * The squared error is computed from which we get the mean error.
     *
     * @param targetFunction This is the function describing the model
     * @param dataSet        this is the list of all numerical features of the data set
     * @param labels         this is the vector containing the actual label for a dataset
     * @return the cost of the model
     */
    public static CostAndGradient<Double> lassoLogCost(RegressionFunction targetFunction, List<Double[]> dataSet, List<Double> labels, double lambda) {

        int m = dataSet.size();
        double totalCost = 0;
        double totalGradient = 0;

        // calculate L1 regularization
        double regularization = l1(targetFunction, dataSet, lambda);

        // calculate the error for each example and add to the total sum
        totalCost = logisticCost(targetFunction, dataSet, labels, m, totalCost);

        return new CostAndGradient<>(totalCost + regularization, totalGradient);
    }

    /**
     * This function calculates the gradient of a target function
     *
     * @param targetFunction
     * @param dataset
     * @param labels
     * @param thetaVector
     * @return
     */
    private static double l2_gradient(RegressionFunction targetFunction, List<Double[]> dataset, List<Double> labels, double[] thetaVector, double lambda) {
        int m = dataset.size();
        // Summarise the error gap * feature
        double sumErrors = 0;
        for (int j = 0; j < thetaVector.length; j++) {
            for (int i = 0; i < m; i++) {
                Double[] featureVector = dataset.get(i);
                double error = targetFunction.apply(featureVector) - labels.get(i);
                sumErrors += error * featureVector[j];
            }
        }
        // TODO regularise l2 GRADIENT
        return (1.0 / m) * sumErrors;
    }

    /**
     * This function calculates the gradient of a target function
     *
     * @param targetFunction
     * @param dataset
     * @param labels
     * @param thetaVector
     * @return
     */
    private static double l1_gradient(RegressionFunction targetFunction, List<Double[]> dataset, List<Double> labels, double[] thetaVector, double lambda) {
        int m = dataset.size();
        // Summarise the error gap * feature
        double sumErrors = 0;
        for (int j = 0; j < thetaVector.length; j++) {
            for (int i = 0; i < m; i++) {
                Double[] featureVector = dataset.get(i);
                double error = targetFunction.apply(featureVector) - labels.get(i);
                sumErrors += error * featureVector[j];
            }
        }
        // TODO regularise l1 GRADIENT
        return (1.0 / m) * sumErrors;
    }

    private static double logisticCost(RegressionFunction targetFunction, List<Double[]> dataSet, List<Double> labels, int m, double totalCost) {
        for (int i = 0; i < m; i++) {
            // feature vector for current example
            Double[] featureVector = dataSet.get(i);
            double h = targetFunction.apply(featureVector);

            double y = labels.get(i);

            double cost = (-y * log(h) + (1 - y) * log(1 - h)) / (double) m;

            totalCost += cost;
        }
        return totalCost;
    }

    private static double l2(RegressionFunction targetFunction, List<Double[]> dataSet, double lambda) {

        int m = dataSet.size();

        double[] thetas = targetFunction.getThetas();

        double regularization = 0;

        for (int j = 0; j < thetas.length; j++) {

            double reg = thetas[j] * thetas[j];

            regularization += reg;
        }

        return regularization * (lambda / (2 * (double) m));
    }

    private static double l1(RegressionFunction targetFunction, List<Double[]> dataSet, double lambda) {

        int m = dataSet.size();

        double[] thetas = targetFunction.getThetas();

        double regularization = 0;

        for (int j = 0; j < thetas.length; j++) {

            double reg = Math.abs(thetas[j]);

            regularization += reg;
        }

        return regularization * (lambda / (double) m);
    }

    /**
     * Takes as input the target function, the list of training records, and their associated labels.
     * The predicted value is computed in a loop, and the error is obtained by subtracting the the real
     * label value.
     * The squared error is computed from which we get the mean error.
     *
     * @param targetFunction This is the function describing the model
     * @param dataset        this is the list of all numerical features of the data set
     * @param labels         this is the vector containing the actual label for a dataset
     * @return the cost of the model
     */
    public static Double cost(Function<Double[], Double> targetFunction, List<Double[]> dataset, List<Double> labels) {

        int m = dataset.size();
        double sumSquaredErrors = 0;

        // calculate the error for each example and add to the total sum
        for (int i = 0; i < m; i++) {
            // feature vector for current example
            Double[] featureVector = dataset.get(i);

            // predict the value and compute the error from real label
            double label = labels.get(i);
            double predicted = targetFunction.apply(featureVector);
            double error = predicted - label;
            sumSquaredErrors += pow(error, 2);
        }

        // calculate the mean value of errors
        return (1.0 / (2 * m)) * sumSquaredErrors;
    }

    /**
     * This function trains a linear regression function using the famous gradient descent algorithm.
     * The output is an improved target function using the new theta parameters.
     * Gradient descent minimizes the cost function by finding the combination of theta that produce the
     * lowest cost based on the training data.
     * Within each iteration a new, better value will be computed for each individual theta parameter of the
     * new vector.
     * The learning rate controls the size of the computing step within each iteration, and this computation is
     * repeated until you reach  a theta value combination that fits well.
     * Within each iteration we compute a new value for each theta parameter in parallel. After each iteration a
     * new better fitting instance of the LinearRegressionFunction is created using the new theta vector.
     * <p>
     * This train method will be called again and again and fed the new target function with the new thetas from the
     * previous calculation. These calls will be repeated until the tuned target function's cost reaches a minimum
     * plateau.
     * <p>
     * To validate that the cost is decreasing the cost can be computed after each training step, and with each training
     * step the cost must decrease. If it does not decrease the learning rate is too high, and the algorithm will shoot
     * past the minimum value.
     * <p>
     * If the cost decreases but is still not optimal, it means the model is underfitting, meaning it is unable to capture
     * the underlying trend of the data.
     *
     * @param targetFunction
     * @param dataset
     * @param labels
     * @param alpha          learning rate
     * @return
     */
    public static RegressionFunction train(RegressionFunction targetFunction,
                                           List<Double[]> dataset,
                                           List<Double> labels,
                                           double alpha) {

        int m = dataset.size();
        double[] thetaVector = targetFunction.getThetas();
        double[] newThetaVector = new double[thetaVector.length];

        // compute the new theta
        for (int j = 0; j < thetaVector.length; j++) {
            // Summarise the error gap * feature
            double sumErrors = 0;
            for (int i = 0; i < m; i++) {
                Double[] featureVector = dataset.get(i);
                double error = targetFunction.apply(featureVector) - labels.get(i);
                sumErrors += error * featureVector[j];
            }

            // compute the new theta  value
            double gradient = (1.0 / m) * sumErrors;
            newThetaVector[j] = thetaVector[j] - alpha * gradient;
        }

        return new LinearRegressionFunction(newThetaVector);
    }

    /**
     * This function calculates the gradient of a target function
     *
     * @param targetFunction
     * @param dataSet
     * @param labels
     * @return
     */
    public static double gradient(RegressionFunction targetFunction, List<Double[]> dataSet, List<Double> labels) {
        return gradient(targetFunction, dataSet, labels, targetFunction.getThetas().clone());
    }

    /**
     * This function calculates the gradient of a target function
     *
     * @param targetFunction
     * @param dataset
     * @param labels
     * @param thetaVector
     * @return
     */
    public static double gradient(RegressionFunction targetFunction, List<Double[]> dataset, List<Double> labels, double[] thetaVector) {
        int m = dataset.size();
        // Summarise the error gap * feature
        double sumErrors = 0;
        for (int j = 0; j < thetaVector.length; j++) {
            for (int i = 0; i < m; i++) {
                Double[] featureVector = dataset.get(i);
                double error = targetFunction.apply(featureVector) - labels.get(i);
                sumErrors += error * featureVector[j];
            }
        }
        return (1.0 / m) * sumErrors;
    }


    public static Function<Double, Double> sigmoidFunction() {

        return z -> 1.0 / (1.0 + Math.exp(-z));
    }
}
