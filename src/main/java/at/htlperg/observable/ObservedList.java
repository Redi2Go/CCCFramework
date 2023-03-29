package at.htlperg.observable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObservedList<T> extends ISubject {
    private final List<T> state = new ArrayList<>();

    public void add(T t) {
        state.add(t);
        notifyObservers();
    }

    public void remove(T t) {
        state.remove(t);
        notifyObservers();
    }

    public boolean contains(T t) {
        return state.contains(t);
    }

    public void forEach(Consumer<T> consumer) {
        state.forEach(consumer);
    }

    public T get(int index) {
        return state.get(index);
    }
}
