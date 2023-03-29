package at.htlperg.io;

import java.io.InputStream;
import java.util.List;

public abstract class Level {
    private final int level;

    public Level(int level) {
        this.level = level;
    }

    public abstract void readLevel(InputReader inputReader);

    public abstract List<String> solveLevel();

    public List<String> solveLevel(InputStream inputStream) {
        readLevel(new InputReader(inputStream));
        return solveLevel();
    }

    public int getLevel() {
        return level;
    }
}
