package io.github.ml.app.unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningInputs {
    private RegressionFunction targetFunction; // the target function we aim to train
    private List<Double[]> dataset; // features of the training examples
    private List<Double> labels; // labels of the training data
    private double  alpha; // learning rate
    private int maximumIterations;
    private double epsilon; // exit criteria
}
