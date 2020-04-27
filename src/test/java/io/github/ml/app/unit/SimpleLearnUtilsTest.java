package io.github.ml.app.unit;

import io.github.ml.app.excel.ExcelFileDeserializer;
import io.github.ml.app.models.Iris;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static io.github.ml.app.excel.ExcelTestUtil.irisExcelFileDeserializer;
import static io.github.ml.app.excel.ExcelTestUtil.readFile;
import static io.github.ml.app.excel.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Please refer to the excel file iris.xlsx for manual cost computation.
 * The theta vector is assumed to consist of 4 elements each 0.5 which is applied to the feature
 * vector, the result of which is compared to the actual label, resulting in the h computation
 * based on a sigmoid from an estimate of the natural log of e
 */
@Slf4j
public class SimpleLearnUtilsTest {

    private List<Iris> irisData;
    List<Double[]> dataset = new ArrayList<>();
    List<Double> labels = new ArrayList<>();

    @BeforeEach
    void setUp() throws IOException {
        //        File irisFile = new File("/data/iris.xlsx");
        ExcelFileDeserializer<Iris> deserializer = irisExcelFileDeserializer();
        irisData = deserializer.deserialize(toBytes(readFile("iris.xlsx")));

        // populate data
        irisData.forEach(item -> {
            // add 1 as the bias column
            dataset.add(new Double[] {1.0, item.getSepalLength(), item.getSepalWidth(), item.getPetalLength(), item.getPetalWidth()});
            labels.add(item.getLabel());
        });
    }

    @Test
    public void cost() throws Exception {

        RegressionFunction targetFunction = new LogisticRegressionFunction(new double[] {0.5, 0.5, 0.5, 0.5, 0.5});

        double cost = RegressionUtils.logCost(targetFunction, dataset, labels);

        assertEquals(2.78535550505717, cost, 0.0000001);
    }

    @Test
    public void computeDerivativeTerms() throws Exception {

        RegressionFunction targetFunction = new LogisticRegressionFunction(new double[] {0.5, 0.5, 0.5, 0.5, 0.5});

        double[] derivatives = RegressionUtils.computeDerivativeTerm(targetFunction, dataset, labels);

        assertEquals(0.497649529501739, derivatives[0], 0.0000001);
        assertEquals(2.49129688749583, derivatives[1], 0.0000001);
        assertEquals(1.70146991719616, derivatives[2], 0.0000001);
        assertEquals(0.727889206598816, derivatives[3], 0.0000001);
        assertEquals(0.121173858591461, derivatives[4], 0.0000001);
    }
}
