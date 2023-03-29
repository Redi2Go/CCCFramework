package at.htlperg.observable;

public class ObservedObject<T> extends ISubject {
    private T state;

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
        super.notifyObservers();
    }
}
