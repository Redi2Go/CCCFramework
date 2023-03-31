package at.htlperg.main;

import at.htlperg.io.InputReader;
import at.htlperg.io.Level;
import at.htlperg.simplereader.SimpleReader;

import java.util.List;

public class Level1 extends Level {
    public Level1() {
        super(1);
    }

    @Override
    public void readLevel(InputReader inputReader, SimpleReader simpleReader) {
            inputReader.matrix().getRows().forEach(row -> {
                for(int i =0 ; i < row.x().toCharArray().length; i++){
                    System.out.print(row.x().toCharArray()[i]);
                    System.out.println();
                }
            });
    }

    @Override
    public List<String> solveLevel() {
        return null;
    }
}
