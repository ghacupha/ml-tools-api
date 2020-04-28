package io.github.ml.app.unit;

import edu.stanford.nlp.optimization.QNMinimizer;
import io.github.ml.app.LogisticDiffFunction;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.ml.app.excel.ExcelTestUtil.admissionExcelFileDeserializer;
import static io.github.ml.app.excel.ExcelTestUtil.readFile;
import static io.github.ml.app.excel.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.knowm.xchart.BitmapEncoder.saveBitmap;

@Slf4j
public class AdmissionsTestAdvancedOptimization {


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

        LogisticDiffFunction targetFunction = new LogisticDiffFunction(dataset, labels);

        admissions.forEach(item -> {
            dataset.add(new Double[]{1.0, item.getGre(), item.getGpa(), item.getRank()});
            labels.add(item.getAdmit());
        });

        QNMinimizer minimizer = new QNMinimizer();

        double[] newTheta = minimizer.minimize(targetFunction, 1, new double[]{0, 0, 0, 0} );

        log.info("New-Theta computed as : {}", Arrays.toString(newTheta));

        assertEquals("[-0.11465533907980097, -0.0661370446640735, -0.17438083368820134, -1.0086243006669238]", Arrays.toString(newTheta));
    }
}
