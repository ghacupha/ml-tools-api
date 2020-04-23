package io.github.ml.app.util;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;

public class ArrayPrint {

    public static String arrayTable(INDArray array) {

        return "\n" + Arrays.deepToString(array.getRows(0, 1, 2).toDoubleMatrix()).replace("], ", "]\n");
    }
}
