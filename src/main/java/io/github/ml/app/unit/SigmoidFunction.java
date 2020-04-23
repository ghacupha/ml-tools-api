package io.github.ml.app.unit;

import java.util.function.Function;

public class SigmoidFunction implements Function<Double, Double> {

    @Override
    public Double apply(final Double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }
}
