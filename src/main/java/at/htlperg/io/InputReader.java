package at.htlperg.io;

import at.htlperg.algebra.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputReader {
    private final BufferedReader inputReader;

    public InputReader(InputStream inputStream) {
        this.inputReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public Veco<Mat<String>> matrix3d() {
        Veci dimensions = line().asVeci();
        List<Mat<String>> matrices = new ArrayList<>();

        for (int m = 0; m < dimensions.x(); m++) {
            List<Veco<String>> rows = new ArrayList<>();

            skip(); // index is always increasing
            for (int l = 0; l < dimensions.y(); l++) {
                rows.add(line().asVecs());
            }

            matrices.add(new Mat<>(new Veco<>(rows)));
        }

        return new Veco<>(matrices);
    }

    public Mat<String> matrix() {
        int rowCount = line().asInt();
        List<Veco<String>> rows = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            rows.add(line().asVecs());
        }

        return new Mat<>(new Veco<>(rows));
    }

    public Line line() {
        try {
            return new Line(inputReader.readLine().split(" "));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void skip() {
        line();
    }

    public record Line(String[] line) {
        public int asInt() {
            return Integer.parseInt(line[0]);
        }

        public double asDouble() {
            return Double.parseDouble(line[0]);
        }

        public Veci asVeci() {
            return new Veci(Arrays.stream(line).map(Integer::parseInt).toArray(Integer[]::new));
        }

        public Vecd asVecd() {
            return new Vecd(Arrays.stream(line).map(Double::parseDouble).toArray(Double[]::new));
        }

        public Veco<String> asVecs() {
            return new Veco<>(line);
        }

        public Veco<Object> asVeco() {
            return new Veco<>(line);
        }
    }
}
