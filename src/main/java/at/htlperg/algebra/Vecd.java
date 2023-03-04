package main.java.at.htlperg.algebra;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Vecd extends Vec<Double> {
    public Vecd(Double...components) {
        this(Arrays.asList(components));
    }

    public Vecd(List<Double> components) {
        super(components);
    }

    public Vecd map(Function<Double, Double> function) {
        return new Vecd(map0(function));
    }

    public Vecd mapIndexed(BiFunction<Integer, Double, Double> function) {
        return new Vecd(mapIndexed0(function));
    }

    public Vecd reverse() {
        return new Vecd(reverse0());
    }

    public Vecd repeat(int count) {
        return new Vecd(repeat0(count));
    }

    public Vecd add(Vecd other) {
        return new Vecd(doOperation(other, (t1, t2) -> t1 + t2));
    }

    public Vecd sub(Vecd other) {
        return new Vecd(doOperation(other, (t1, t2) -> t1 - t2));
    }

    public Vecd mul(Vecd other) {
        return new Vecd(doOperation(other, (t1, t2) -> t1 * t2));
    }

    public Vecd div(Vecd other) {
        return new Vecd(doOperation(other, (t1, t2) -> t1 / t2));
    }
}
