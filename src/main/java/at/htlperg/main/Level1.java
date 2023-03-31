package at.htlperg.main;

import at.htlperg.io.InputReader;
import at.htlperg.io.Level;
import at.htlperg.simplereader.SimpleReader;

import java.util.ArrayList;
import java.util.List;

public class Level1 extends Level {

    private int result = 0;

    public Level1() {
        super(1);
    }


    @Override
    public void readLevel(InputReader inputReader, SimpleReader simpleReader) {
        System.out.println(simpleReader.getLines());
        for (String s : simpleReader.getLines()) {
            for (char c : s.toCharArray())
                    if (c == 'O')
                        result++;
        }

    }

    @Override
    public List<String> solveLevel() {
        int test = result;
        result = 0;
        return List.of(test + "");
    }
}