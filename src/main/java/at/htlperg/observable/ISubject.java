package at.htlperg.observable;

import java.util.ArrayList;
import java.util.List;

public abstract class ISubject {
    private final List<IObserver> observers = new ArrayList<>();

    public IObserver attach(IObserver observer) {
        observers.add(observer);

        return observer;
    }

    public IObserver attachAndFire(IObserver observer) {
        attach(observer);
        observer.onUpdate();

        return observer;
    }

    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (IObserver observer : observers)
            observer.onUpdate();
    }
}
