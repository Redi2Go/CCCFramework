package at.htlperg.algebra;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public <K, V> Map<K, V> toDict(Function<T, Map.Entry<K, V>> function) {
        return Map.ofEntries(components.stream().map(function).toArray(Map.Entry[]::new));
    }

    public <K, V> Map<K, V> toDict() {
        return Map.ofEntries(components.toArray(new Map.Entry[0]));
    }

    public Veco<T> filter(Predicate<T> predicate) {
        return new Veco<T>(components.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Veco<T> subVector(int inclusiveIndex) {
        return new Veco<T>(components.stream().skip(inclusiveIndex).collect(Collectors.toList()));
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

    public int indexOf(T t) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).equals(t))
                return i;
        }

        return -1;
    }

    @Override
    public String toString() {
        return components.toString();
    }
}