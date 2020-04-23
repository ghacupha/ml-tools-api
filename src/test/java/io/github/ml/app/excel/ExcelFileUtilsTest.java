package io.github.ml.app.excel;

import io.github.ml.app.excel.deserializer.DefaultExcelFileDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.ml.app.excel.ExcelTestUtil.getDefaultPoijiOptions;
import static io.github.ml.app.excel.ExcelTestUtil.readFile;
import static io.github.ml.app.excel.ExcelTestUtil.toBytes;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * So basically testing if the excel deserializer works. This workflow is not something we expect
 * <p>
 * to use as much
 */
public class ExcelFileUtilsTest {


    @Test
    public void deserializeSchemesFile() throws Exception {

        ExcelFileDeserializer<SchemeTableEVM> deserializer = schemeTableExcelFileDeserializer();

        // @formatter:off
        List<SchemeTableEVM> schemes =
            deserializer.deserialize(toBytes(readFile("schemes.xlsx")));
        // @formatter:on

        assertThat(schemes.size()).isEqualTo(3);
        assertThat(schemes.get(0)).isEqualTo(SchemeTableEVM.builder().rowIndex(1).schemeCode("scheme1").description("scheme1description").build());
        assertThat(schemes.get(1)).isEqualTo(SchemeTableEVM.builder().rowIndex(2).schemeCode("scheme2").description("scheme2description").build());
        assertThat(schemes.get(2)).isEqualTo(SchemeTableEVM.builder().rowIndex(3).schemeCode("scheme3").description("scheme3description").build());
    }


    public ExcelFileDeserializer<SchemeTableEVM> schemeTableExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(SchemeTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }
}
