package main.java.at.htlperg.algebra;

import java.util.ArrayList;
import java.util.List;

public class Mat<T> {
    private final Veco<Veco<T>> content;

    public Mat(Veco<Veco<T>> content) {
        this.content = content;
    }

    public Mat<T> mirrorX() {
        return new Mat<>(content.map(Veco::reverse));
    }

    public Mat<T> mirrorY() {
        return new Mat<>(content.reverse());
    }

    public Mat<T> transpose() {
        List<List<T>> transposed = new ArrayList<>();
        for (int x = 0; x < getWidth(); x++) {
            List<T> newY = new ArrayList<>();
            for (int y = 0; y < getHeight(); y++)
                newY.add(content.component(y).component(x));

            transposed.add(newY);
        }

        return new Mat<>(new Veco<>(transposed).map(list -> new Veco<>(list)));
    }

    public Veco<T> scanline() {
        List<T> result = new ArrayList<>();
        content.forEach(row -> row.forEach(result::add));

        return new Veco<>(result);
    }

    public int getWidth() {
        return content.x().length();
    }

    public int getHeight() {
        return content.length();
    }
}
