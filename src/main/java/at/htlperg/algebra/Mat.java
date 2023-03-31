package at.htlperg.algebra;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Mat<T> {
    private final Veco<Veco<T>> rows;

    public Mat(Veco<Veco<T>> rows) {
        this.rows = rows;
    }

    public Mat<T> mirrorX() {
        return new Mat<>(rows.map(Veco::reverse));
    }

    public Mat<T> mirrorY() {
        return new Mat<>(rows.reverse());
    }

    public <U> Mat<U> map(Function<T, U> function) {
        return new Mat<>(rows.map(row -> row.map(function)));
    }

    public Veco<Veco<T>> getRows() {
        return rows;
    }

    public Mat<T> transpose() {
        List<List<T>> transposed = new ArrayList<>();
        for (int x = 0; x < getWidth(); x++) {
            List<T> newY = new ArrayList<>();
            for (int y = 0; y < getHeight(); y++)
                newY.add(rows.component(y).component(x));

            transposed.add(newY);
        }

        return new Mat<>(new Veco<>(transposed).map(list -> new Veco<>(list)));
    }

    public Veco<T> scanline() {
        List<T> result = new ArrayList<>();
        rows.forEach(row -> row.forEach(result::add));

        return new Veco<>(result);
    }

    public T component(Veci position) {
        try {
            return rows.component(position.y()).component(position.x());
        } catch (Exception e) {
            return null;
        }
    }

    public Veci indexOf(T t) {
        for (int y = 0; y < rows.length(); y++) {
            int x = rows.component(y).indexOf(t);
            if (x != -1)
                return new Veci(x, y);
        }
        return null;
    }

    public int getWidth(int y) {
        return rows.component(y).length();
    }

    public int getWidth() {
        return rows.x().length();
    }

    public int getHeight() {
        return rows.length();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        rows.forEach(r -> builder.append(r.toString()).append('\n'));

        return builder.toString();
    }
}