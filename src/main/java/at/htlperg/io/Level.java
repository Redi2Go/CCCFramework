package main.java.at.htlperg.io;

import java.io.InputStream;
import java.util.List;

public abstract class Level {
    public abstract int getLevel();
    public abstract List<String> solveLevel(InputReader inputReader);

    public List<String> solveLevel(InputStream inputStream) {
        return solveLevel(new InputReader(inputStream));
    }
}
