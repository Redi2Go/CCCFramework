package main.java.at.htlperg.algebra;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Vec<T> {
    private final List<T> components;

    protected Vec(List<T> components) {
        this.components = components;
    }

    protected List<T> reverse0() {
        return mapIndexed0((i, t) -> component(length() - i - 1));
    }

    protected List<T> repeat0(int count) {
        List<T> components = new ArrayList<>();
        for (int i = 0; i < length() * count; i++)
            components.add(component(i % length()));

        return components;
    }

    protected <U> List<U> mapIndexed0(BiFunction<Integer, T, U> function) {
        List<U> components = new ArrayList<>();
        for (int i = 0; i < length(); i++)
            components.add(function.apply(i, component(i)));

        return components;
    }

    protected <U> List<U> map0(Function<T, U> function) {
        List<U> components = new ArrayList<>();
        for (int i = 0; i < length(); i++)
            components.add(function.apply(component(i)));

        return components;
    }

    protected List<T> doOperation(Vec<T> other, BiFunction<T, T, T> operation) {
        if (length() != other.length())
            throw new IllegalStateException("Vector length mismatch!");

        List<T> result = new ArrayList<>();
        for (int i = 0; i < length(); i++)
            result.add(operation.apply(component(i), other.component(i)));

        return result;
    }

    public void forEach(Consumer<T> consumer) {
        for (T t : components)
            consumer.accept(t);
    }

    public int length() {
        return components.size();
    }

    public T component(int index) {
        return components.get(index);
    }

    public T x() {
        return component(0);
    }

    public T y() {
        return component(1);
    }

    public T z() {
        return component(2);
    }
}
