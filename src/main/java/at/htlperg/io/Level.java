package at.htlperg.io;

import at.htlperg.simplereader.SimpleReader;

import java.io.InputStream;
import java.util.List;

public abstract class Level {
    private final int level;

    public Level(int level) {
        this.level = level;
    }

    public abstract void readLevel(InputReader inputReader, SimpleReader simpleReader);
//    public abstract void readLevel(InputReader inputReader);

    public abstract List<String> solveLevel();

    public List<String> solveLevel(InputStream inputStream, String content) {
        readLevel(new InputReader(inputStream), new SimpleReader(content));
//        readLevel(new InputReader(inputStream));
        return solveLevel();
    }

    public int getLevel() {
        return level;
    }
}
