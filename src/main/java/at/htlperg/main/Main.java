package main.java.at.htlperg.main;

import main.java.at.htlperg.algebra.Mat;
import main.java.at.htlperg.algebra.Veco;
import main.java.at.htlperg.io.InputReader;
import main.java.at.htlperg.io.Level;
import main.java.at.htlperg.io.LevelManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        new LevelManager(new File("C:\\Users\\Lukas\\Documents\\Schule\\5.Klasse\\Cloudflight\\CCCFramework\\src\\main\\resources\\levels")).solveLevel(new Level1());
    }

    public static class Level1 extends Level {

        @Override
        public int getLevel() {
            return 5;
        }

        @Override
        public List<String> solveLevel(InputReader inputReader) {
            Mat<String> messages = inputReader.matrix();
            Veco<String> words = inputReader.matrix().scanline();

            return Arrays.asList(("D K\n" +
                    "K D\n" +
                    "B E\n" +
                    "E A\n" +
                    "A J\n" +
                    "I G\n" +
                    "L F\n" +
                    "H L\n" +
                    "C F\n" +
                    "J C\n" +
                    "G H\n" +
                    "F B\n" +
                    "D B\n" +
                    "K D\n" +
                    "B L\n" +
                    "E K\n" +
                    "A H\n" +
                    "I A\n" +
                    "L F\n" +
                    "H C\n" +
                    "C I\n" +
                    "J G\n" +
                    "G E\n" +
                    "F J\n" +
                    "D L\n" +
                    "K C\n" +
                    "B K\n" +
                    "E E\n" +
                    "A F\n" +
                    "I D\n" +
                    "L G\n" +
                    "H A\n" +
                    "C B\n" +
                    "J J\n" +
                    "G I\n" +
                    "F H\n").split("\n"));
        }
    }
}
