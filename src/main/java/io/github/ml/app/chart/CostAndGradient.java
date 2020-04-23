package io.github.ml.app.chart;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CostAndGradient<T> {

    private double cost;
    // type of value for gradient real or vector
    private T gradient;
}
