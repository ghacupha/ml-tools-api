package io.github.ml.app.excel;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * This is an example model for testing purposed only, that represents a row of data
 * <p>
 * for the file schemes.xlsx in the resources
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class SchemeTableEVM implements Serializable {

    private static final long serialVersionUID = 1341014428380735074L;

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String schemeCode;

    @ExcelCell(1)
    private String description;
}
