package io.github.ml.app.unit;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Contains data that is the output of an optimization process
 */
@Data
@Builder
public class OptimizationArtefact {

    private int iterations;
    private boolean isConverged;
    private RegressionFunction regressionFunction;
    private Map<Integer, Double> costMap;
}
