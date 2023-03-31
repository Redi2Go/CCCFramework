package at.htlperg.main.testlevel;

import at.htlperg.io.InputReader;
import at.htlperg.io.Level;
import at.htlperg.simplereader.SimpleReader;

import java.util.List;

// 37 School level 4
public class Level43 extends Level {
    public Level43() {
        super(43);
    }

    @Override
    public void readLevel(InputReader inputReader, SimpleReader simpleReader) {
        var mList = inputReader.matrixList();
        System.out.println();
    }

    @Override
    public List<String> solveLevel() {
        return null;
    }
}
