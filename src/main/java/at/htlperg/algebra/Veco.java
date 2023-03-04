package main.java.at.htlperg.algebra;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Veco<T> extends Vec<T> {
    public Veco(T... components) {
        this(Arrays.asList(components));
    }

    public Veco(List<T> components) {
        super(components);
    }

    public <U> Veco<U> map(Function<T, U> function) {
        return new Veco<>(map0(function));
    }

    public <U> Veco<U> mapIndexed(BiFunction<Integer, T, U> function) {
        return new Veco<>(mapIndexed0(function));
    }

    public Veco<T> reverse() {
        return new Veco<>(reverse0());
    }

    public Veco<T> repeat(int count) {
        return new Veco<>(repeat0(count));
    }
}
