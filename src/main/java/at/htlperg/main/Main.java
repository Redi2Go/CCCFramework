package at.htlperg.main;

import at.htlperg.io.LevelManager;
import at.htlperg.main.testlevel.Level43;
import at.htlperg.main.testlevel.S37Level4;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new LevelManager(new File("src/main/resources/levels")).solveLevel(v -> new Level1());
    }
}
