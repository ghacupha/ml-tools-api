package io.github.ml.app.excel;

import org.springframework.context.annotation.Configuration;


/**
 * This is the container for excel deserializers used in the entire system
 *
 * This code is ported from the ifris-main microservice, which models are no longer needed here
 *
 * for now you can think of them as place holders
 *
 */
@Configuration
public class ExcelDeserializerContainer {

    // Sample code for EVM deserialization
//    @Bean("schemeTableExcelFileDeserializer")
//    public ExcelFileDeserializer<SchemeTableEVM> schemeTableExcelFileDeserializer() {
//        return excelFile -> new DefaultExcelFileDeserializer<>(SchemeTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
//    }
//
//    @Bean("currencyTableExcelFileDeserializer")
//    public ExcelFileDeserializer<CurrencyTableEVM> currencyTableExcelFileDeserializer() {
//        return excelFile -> new DefaultExcelFileDeserializer<>(CurrencyTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
//    }
//
//    @Bean("branchTableExcelFileDeserializer")
//    public ExcelFileDeserializer<BranchTableEVM> branchTableExcelFileDeserializer() {
//        return excelFile -> new DefaultExcelFileDeserializer<>(BranchTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
//    }
//
//    @Bean("typeTableExcelFileDeserializer")
//    public ExcelFileDeserializer<TypeTableEVM> typeTableExcelFileDeserializer() {
//        return excelFile -> new DefaultExcelFileDeserializer<>(TypeTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
//    }
//
//    @Bean("sbuTableExcelFileDeserializer")
//    public ExcelFileDeserializer<SBUTableEVM> sbuTableExcelFileDeserializer() {
//        return excelFile -> new DefaultExcelFileDeserializer<>(SBUTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
//    }
}
