package main.java.at.htlperg.algebra;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Veci extends Vec<Integer> {
    public Veci(Integer...components) {
        this(Arrays.asList(components));
    }

    public Veci map(Function<Integer, Integer> function) {
        return new Veci(map0(function));
    }

    public Veci mapIndexed(BiFunction<Integer, Integer, Integer> function) {
        return new Veci(mapIndexed0(function));
    }

    public Veci reverse() {
        return new Veci(reverse0());
    }

    public Veci repeat(int count) {
        return new Veci(repeat0(count));
    }
    public Veci(List<Integer> components) {
        super(components);
    }

    public Veci add(Veci other) {
        return new Veci(doOperation(other, Integer::sum));
    }

    public Veci sub(Veci other) {
        return new Veci(doOperation(other, (t1, t2) -> t1 - t2));
    }

    public Veci mul(Veci other) {
        return new Veci(doOperation(other, (t1, t2) -> t1 * t2));
    }

    public Veci div(Veci other) {
        return new Veci(doOperation(other, (t1, t2) -> t1 / t2));
    }
}
