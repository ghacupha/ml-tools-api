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
 * vector, the result of which is compared to the actual label, resulting in the error.
 * The error is squared for each example and the sum is computed as 3199.925
 * If the above is divided by 2 * m, the result should be around 15.999625
 */
@Slf4j
public class SimpleLearnUtilsTest {

    private List<Iris> irisData;

    @BeforeEach
    void setUp() throws IOException {
        //        File irisFile = new File("/data/iris.xlsx");
        ExcelFileDeserializer<Iris> deserializer = irisExcelFileDeserializer();
        irisData = deserializer.deserialize(toBytes(readFile("iris.xlsx")));
    }

    @Test
    public void cost() {

        List<Double[]> dataset = new ArrayList<>();
        List<Double> labels = new ArrayList<>();

        irisData.forEach(item -> {
            dataset.add(new Double[] {item.getSepalLength(), item.getSepalWidth(), item.getPetalLength(), item.getPetalWidth()});
            labels.add(item.getLabel());
        });

        Function<Double[], Double> targetFunction = new LinearRegressionFunction(new double[] {0.5, 0.5, 0.5, 0.5});

        double cost = RegressionUtils.cost(targetFunction, dataset, labels);

        assertEquals(15.999625, cost, 0.0000001);
    }
}
