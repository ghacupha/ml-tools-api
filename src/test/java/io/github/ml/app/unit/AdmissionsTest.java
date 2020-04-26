package io.github.ml.app.unit;

import io.github.ml.app.chart.CostAndGradient;
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
import static io.github.ml.app.unit.RegressionUtils.cost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.knowm.xchart.BitmapEncoder.saveBitmap;

/**
 * This test uses the admissions data to perform a quasi classification though ideally that should be the work of a
 * <p>
 * logistic regression model
 */
@Slf4j
public class AdmissionsTest {

    private List<Admission> admissions;

    @BeforeEach
    void setUp() throws IOException {
        //        File irisFile = new File("/data/iris.xlsx");
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

        LinearRegressionFunction targetFunction = new LinearRegressionFunction(new double[]{0, 0, 0, 0});

        double cost = cost(targetFunction, dataset, labels);

        log.info("Cost for the model before training is  : {}", cost(targetFunction, dataset, labels));

        assertEquals(0.15875, cost, 0.0000001);

        // @formatter:off
        LearningInputs inputs = LearningInputs.builder()
            .targetFunction(targetFunction)
            .dataset(dataset)
            .labels(labels)
            .alpha(0.01)
            .epsilon(0.001)
            .maximumIterations(100)
            .build();
        // @formatter:on


        GradientDescent gradientDescentAlgorithm = new GradientDescent();

        RegressionFunction trainedModel = gradientDescentAlgorithm.apply(inputs);

        log.info("Cost for the model is  : {}", cost(trainedModel, dataset, labels));

        Map<Integer, Double> costMap = gradientDescentAlgorithm.getPlot();

        log.info("Cost map populated with {} items", costMap.size());

        // Create visuals for the reducing cost
        double[] iterations = new double[costMap.keySet().size()];
        double[] costs = new double[costMap.keySet().size()];
        double[] gradients = new double[costMap.keySet().size()];

        // populate chart data
        AtomicInteger iteration = new AtomicInteger();
        costMap.forEach((iter, costGrad) -> {
            int index = iteration.getAndIncrement();
            iterations[index] = iter;
            costs[index] = costGrad;
        });

        XYChart chart = QuickChart.getChart("Cost Chart", "Iterations", "Cost", "cost(iter)", iterations, costs);

        try {
            saveBitmap(chart, "target/costChart", BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
