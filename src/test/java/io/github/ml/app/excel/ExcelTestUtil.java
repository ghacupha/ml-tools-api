package io.github.ml.app.excel;

import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.ml.app.excel.deserializer.DefaultExcelFileDeserializer;
import io.github.ml.app.models.Admission;
import io.github.ml.app.models.Credit;
import io.github.ml.app.models.Iris;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
public class ExcelTestUtil {

    public static byte[] toBytes(File file) throws IOException {

        return Files.readAllBytes(file.toPath());
    }


    public static PoijiOptions getDefaultPoijiOptions() {

        // @formatter:off
        return PoijiOptionsBuilder.settings()
                           .ignoreHiddenSheets(true)
                           .preferNullOverDefault(true)
                           .datePattern("yyyy/MM/dd")
                           .dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME)
                           .build();
        // @formatter:on
    }

    public static File readFile(String filename) {

        log.info("\nReading file : {}...", filename);

    // @formatter:off
    return new File(
            Objects.requireNonNull(
                ClassLoader.getSystemClassLoader()
                           .getResource("files/" + filename))
                   .getFile());
    // @formatter:on
    }

    public static ExcelFileDeserializer<Iris> irisExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(Iris.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

    public static ExcelFileDeserializer<Admission> admissionExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(Admission.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

    public static ExcelFileDeserializer<Credit> creditExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(Credit.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

}
