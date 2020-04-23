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
public class Admission implements Serializable {
    private static final long serialVersionUID = 6057000816122620804L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private double gre;
    @ExcelCell(1)
    private double gpa;
    @ExcelCell(2)
    private double rank;
    @ExcelCell(3)
    private double admit;

}
