package io.github.ml.app.models;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Iris implements Serializable {
    private static final long serialVersionUID = 523669477650084375L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private double sepalLength;

    @ExcelCell(1)
    private double sepalWidth;

    @ExcelCell(2)
    private double petalLength;

    @ExcelCell(3)
    private double petalWidth;

    @ExcelCell(4)
    private double label;
}
