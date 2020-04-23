package io.github.ml.app.unit;

import io.github.ml.app.chart.CostAndGradient;
import io.github.ml.app.chart.CostGradientChart;
import io.github.ml.app.chart.Plot;
import io.github.ml.app.chart.XYChartCostAndGradientPlot;
import io.github.ml.app.excel.ExcelFileDeserializer;
import io.github.ml.app.models.Credit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.ml.app.excel.ExcelTestUtil.creditExcelFileDeserializer;
import static io.github.ml.app.excel.ExcelTestUtil.readFile;
import static io.github.ml.app.excel.ExcelTestUtil.toBytes;
import static io.github.ml.app.unit.RegressionUtils.cost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.knowm.xchart.BitmapEncoder.saveBitmap;

// @formatter: off

/**
 * This data set was borrowed from UCI from the link : https://archive.ics.uci.edu/ml/machine-learning-databases/00350/ It contains a data credit card data for 30,000 customers. The idea is to try to
 * predict if they will default next month.
 * <p>
 * This data was used in the paper : Yeh, I. C., & Lien, C. H. (2009). The comparisons of data mining techniques for the predictive accuracy of probability of default of credit card clients. Expert
 * Systems with Applications, 36(2), 2473-2480
 * <p>
 * This research aimed at the case of customersâ€™ default payments in Taiwan and compares the predictive accuracy of probability of default among six data mining methods. From the perspective of risk
 * management, the result of predictive accuracy of the estimated probability of default will be more valuable than the binary result of classification - credible or not credible clients. Because the
 * real probability of default is unknown, this study presented the novel â€œSorting Smoothing Methodâ€ to estimate the real probability of default. With the real probability of default as the
 * response variable (Y), and the predictive probability of default as the independent variable (X), the simple linear regression result (Y = A + BX) shows that the forecasting model produced by
 * artificial neural network has the highest coefficient of determination; its regression intercept (A) is close to zero, and regression coefficient (B) to one. Therefore, among the six data mining
 * techniques, artificial neural network is the only one that can accurately estimate the real probability of default.
 * <p>
 * Attributes:
 * <p>
 * This research employed a binary variable, default payment (Yes = 1, No = 0), as the response variable. This study reviewed the literature and used the following 23 variables as explanatory
 * variables: X1: Amount of the given credit (NT dollar): it includes both the individual consumer credit and his/her family (supplementary) credit. X2: Gender (1 = male; 2 = female). X3: Education (1
 * = graduate school; 2 = university; 3 = high school; 4 = others). X4: Marital status (1 = married; 2 = single; 3 = others). X5: Age (year). X6 - X11: History of past payment. We tracked the past
 * monthly payment records (from April to September, 2005) as follows: X6 = the repayment status in September, 2005; X7 = the repayment status in August, 2005; . . .;X11 = the repayment status in
 * April, 2005. The measurement scale for the repayment status is: -1 = pay duly; 1 = payment delay for one month; 2 = payment delay for two months; . . .; 8 = payment delay for eight months; 9 =
 * payment delay for nine months and above. X12-X17: Amount of bill statement (NT dollar). X12 = amount of bill statement in September, 2005; X13 = amount of bill statement in August, 2005; . . .; X17
 * = amount of bill statement in April, 2005. X18-X23: Amount of previous payment (NT dollar). X18 = amount paid in September, 2005; X19 = amount paid in August, 2005; . . .;X23 = amount paid in
 * April, 2005.
 */
@Slf4j
public class CreditDefaultTest {
    // @formatter: on


    private List<Credit> creditCards;
    private static Plot<CostGradientChart<XYChart, XYChart>, Integer, CostAndGradient> costGradientPlot = new XYChartCostAndGradientPlot();

    @BeforeEach
    void setUp() throws IOException {
        //        File irisFile = new File("/data/iris.xlsx");
        ExcelFileDeserializer<Credit> deserializer = creditExcelFileDeserializer();
        creditCards = deserializer.deserialize(toBytes(readFile("default-of-credit-card-clients.xlsx")));
    }

    @Test
    public void apply() {

        List<Double[]> dataset = new ArrayList<>();
        List<Double> labels = new ArrayList<>();

        // 23 + 1 for bias
        Double[] features = new Double[24];

        for (int n = 0; n < creditCards.size(); n++) {

            features[0] = 1.0; // this is the bias
            features[1] = creditCards.get(n).getLimitBalance();
            features[2] = creditCards.get(n).getSex();
            features[3] = creditCards.get(n).getEducation();
            features[4] = creditCards.get(n).getMarriage();
            features[5] = creditCards.get(n).getAge();
            features[6] = creditCards.get(n).getPay0();
            features[7] = creditCards.get(n).getPay2();
            features[8] = creditCards.get(n).getPay3();
            features[9] = creditCards.get(n).getPay4();
            features[10] = creditCards.get(n).getPay5();
            features[11] = creditCards.get(n).getPay6();
            features[12] = creditCards.get(n).getBillAmount1();
            features[13] = creditCards.get(n).getBillAmount2();
            features[14] = creditCards.get(n).getBillAmount3();
            features[15] = creditCards.get(n).getBillAmount4();
            features[16] = creditCards.get(n).getBillAmount5();
            features[17] = creditCards.get(n).getBillAmount6();
            features[18] = creditCards.get(n).getPayAmount1();
            features[19] = creditCards.get(n).getPayAmount2();
            features[20] = creditCards.get(n).getPayAmount3();
            features[21] = creditCards.get(n).getPayAmount4();
            features[22] = creditCards.get(n).getPayAmount5();
            features[23] = creditCards.get(n).getPayAmount6();

            dataset.add(features);
            labels.add(creditCards.get(n).getDefaultNextMonth());
        }

        double[] thetaVector = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        RegressionFunction targetFunction = new LogisticRegressionFunction(thetaVector);

        double cost = cost(targetFunction, dataset, labels);

        log.info("Cost for the model before training is  : {}", cost(targetFunction, dataset, labels));

        assertEquals(0.125, cost, 0.0000001);

        // @formatter:off
        LearningInputs inputs = LearningInputs.builder()
                                            .targetFunction(targetFunction)
                                            .dataset(dataset)
                                            .labels(labels)
                                            .alpha(0.001)
                                            .epsilon(0.01)
                                            .maximumIterations(10000000)
                                            .build();
        // @formatter:on


        GradientDescent gradientDescentAlgorithm = new GradientDescent();

        RegressionFunction trainedModel = gradientDescentAlgorithm.apply(inputs);

        log.info("Cost for the model is  : {}", cost(trainedModel, dataset, labels));

        Map<Integer, CostAndGradient> costAndGradientMap = gradientDescentAlgorithm.getCostAndGradientPlot();

        // Plot the graph
        XYChart chart = costGradientPlot.plotGraph(costAndGradientMap).costChart();
        XYChart gradChart = costGradientPlot.plotGraph(costAndGradientMap).gradientChart();

        try {
            saveBitmap(chart, "target/costChart", BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            saveBitmap(gradChart, "target/gradientChart", BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Double> predictions = new ArrayList<>();

        // replaced predicted values with labels. Apply confusion nd
        for (int i = 0; i < dataset.size(); i++) {
            Double sigmoid_z = trainedModel.apply(dataset.get(i));
            if (sigmoid_z > 0.5) {
                sigmoid_z = 1.0;
            } else {
                sigmoid_z = 0.0;
            }
            predictions.add(i, sigmoid_z);
        }

        int errored = 0;
        // calculate errors
        for (int i = 0; i < predictions.size(); i++) {

            // if prediction not equal to label
            if (Double.compare(predictions.get(i), labels.get(i)) != 0) {

                errored++;
            }
        }

        log.info("{} Items out of {} have been misclassified", errored, creditCards.size());

        double errorRate = ((double) errored / (double) creditCards.size()) * 100.00;

        log.info("Error rate : {}%", errorRate);
    }
}
