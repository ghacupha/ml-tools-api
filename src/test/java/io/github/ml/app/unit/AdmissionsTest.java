package io.github.ml.app.unit;

import io.github.ml.app.excel.ExcelFileDeserializer;
import io.github.ml.app.models.Admission;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.ml.app.excel.ExcelTestUtil.admissionExcelFileDeserializer;
import static io.github.ml.app.excel.ExcelTestUtil.readFile;
import static io.github.ml.app.excel.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.knowm.xchart.BitmapEncoder.saveBitmap;

@Slf4j
public class AdmissionsTest {

    private List<Admission> admissions;

    @BeforeEach
    void setUp() throws IOException {
        ExcelFileDeserializer<Admission> deserializer = admissionExcelFileDeserializer();
        admissions = deserializer.deserialize(toBytes(readFile("admissions-data.xlsx")));
    }

    @Test
    public void apply() {

        List<Double[]> dataset = new ArrayList<>();
        List<Double> labels = new ArrayList<>();

        admissions.forEach(item -> {
            dataset.add(new Double[]{1.0, item.getGre(), item.getGpa(), item.getRank()});
            labels.add(item.getAdmit());
        });

        // setup logistic regression function
        RegressionFunction targetFunction = new LogisticRegressionFunction(new double[]{0, 0, 0, 0});

        // calculate cost
        double cost = targetFunction.cost(dataset, labels);

        log.info("Cost for the model before training is  : {}", cost);

        assertEquals(0.6931471805599427, cost, 0.0000001);

        // @formatter:off
        LearningInputs inputs = LearningInputs.builder()
            .targetFunction(targetFunction)
            .dataset(dataset)
            .labels(labels)
            .alpha(0.00001)
            .epsilon(0.00001)
            .maximumIterations(10000)
            .build();
        // @formatter:on


        LogGradientDescent gradientDescentAlgorithm = new LogGradientDescent();

        RegressionFunction trainedModel = gradientDescentAlgorithm.apply(inputs);

        cost = trainedModel.cost(dataset, labels);

        assertEquals(0.34125, cost, 0.0000001);

        Map<Integer, Double> costMap = gradientDescentAlgorithm.getPlot();

        log.info("Cost map populated with {} items", costMap.size());

        // Create visuals for the reducing cost
        double[] iterations = new double[costMap.keySet().size()];
        double[] costs = new double[costMap.keySet().size()];

        // populate chart data
        AtomicInteger iteration = new AtomicInteger();
        costMap.forEach((iter, costValue) -> {
            int index = iteration.getAndIncrement();
            iterations[index] = iter;
            costs[index] = costValue;
        });

        XYChart chart = QuickChart.getChart("Cost Chart", "Iterations", "Cost", "cost(iter)", iterations, costs);

        try {
            saveBitmap(chart, "target/costChart", BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
