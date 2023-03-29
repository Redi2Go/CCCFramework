package at.htlperg.main;

import at.htlperg.io.LevelManager;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new LevelManager(new File("src/main/resources/levels")).solveLevel(new Level3());
    }
}
