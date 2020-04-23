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
public class Credit implements Serializable {
    private static final long serialVersionUID = -6364017495600860013L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private double limitBalance;
    @ExcelCell(1)
    private double sex;
    @ExcelCell(2)
    private double education;
    @ExcelCell(3)
    private double marriage;
    @ExcelCell(4)
    private double age;
    @ExcelCell(5)
    private double pay0;
    @ExcelCell(6)
    private double pay2;
    @ExcelCell(7)
    private double pay3;
    @ExcelCell(8)
    private double pay4;
    @ExcelCell(9)
    private double pay5;
    @ExcelCell(10)
    private double pay6;
    @ExcelCell(11)
    private double billAmount1;
    @ExcelCell(12)
    private double billAmount2;
    @ExcelCell(13)
    private double billAmount3;
    @ExcelCell(14)
    private double billAmount4;
    @ExcelCell(15)
    private double billAmount5;
    @ExcelCell(16)
    private double billAmount6;
    @ExcelCell(17)
    private double payAmount1;
    @ExcelCell(18)
    private double payAmount2;
    @ExcelCell(19)
    private double payAmount3;
    @ExcelCell(20)
    private double payAmount4;
    @ExcelCell(21)
    private double payAmount5;
    @ExcelCell(22)
    private double payAmount6;
    @ExcelCell(23)
    private double defaultNextMonth;

}
