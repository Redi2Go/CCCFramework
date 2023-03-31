package at.htlperg.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LevelManager {
    private final File levelsFolder;
    private final File rawFolder;

    public LevelManager(File levelsFolder) {
        this.levelsFolder = levelsFolder;
        if (!levelsFolder.exists())
            throw new RuntimeException("Cannot find levels folder!");

        this.rawFolder = new File(levelsFolder.getAbsolutePath(), "raw");
        this.rawFolder.mkdirs();
    }

    public void solveLevel(Function<Void, Level> factory) throws IOException {
        handleRawFiles();

        Level level = factory.apply(null);
        trySolveExampleLevel(level, new File(levelsFolder, "level" + level.getLevel()));

        for (File levelInputFile : Objects.requireNonNull(new File(levelsFolder, "level" + level.getLevel() + "/in").listFiles())) {
            if (levelInputFile.getName().endsWith("_example.in"))
                continue;

            redirectStdio(levelInputFile);

            level = factory.apply(null);
            List<String> result = level.solveLevel(new FileInputStream(levelInputFile), Files.readString(levelInputFile.getAbsoluteFile().toPath()));
            Files.writeString(
                    new File(levelInputFile.getParentFile().getParentFile(), "out/" + levelInputFile.getName().replace(".in", ".out")).toPath(),
                    String.join("\n", result)
            );
            resetStdio();
        }
    }

    private void trySolveExampleLevel(Level level, File levelFolder) throws IOException {
        InputStream inputStream = new FileInputStream(new File(levelFolder.getAbsolutePath(), "in/level%d_example.in".formatted(level.getLevel())));

        List<String> expectedResult = Files.readAllLines(new File(levelFolder, "out/level%d_example.out".formatted(level.getLevel())).toPath());
        List<String> result = level.solveLevel(inputStream, Files.readString(Path.of(levelFolder.getAbsolutePath() + "/in/level%d_example.in".formatted(level.getLevel()))));

        int length = Integer.max(expectedResult.size(), result.size());
        for (int i = 0; i < length; i++) {
            String resultLine = i < result.size() ? result.get(i) : "$EOF$";
            String expectedLine = i < expectedResult.size() ? expectedResult.get(i) : "$EOF$";

            if (!resultLine.equals(expectedLine)) {
                System.out.printf("Example mismatch in line %d:\nExpected:\t%s\nGot:\t\t%s\n", i, expectedLine, resultLine);
                return;
            }
        }
    }

    private void redirectStdio(File levelInputFile) throws FileNotFoundException {
        File logOutputFile = new File(levelInputFile.getParentFile().getParentFile(), "log");
        logOutputFile.mkdirs();

        OutputStream logOutputStream = new FileOutputStream(new File(logOutputFile, levelInputFile.getName() + ".log"));

        System.setOut(new PrintStream(new TeeOutputStream(logOutputStream, System.out)));
        System.setErr(new PrintStream(new TeeOutputStream(logOutputStream, System.err)));
    }

    private void resetStdio() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    }

    private void handleRawFiles() {
        for (File file : Objects.requireNonNull(rawFolder.listFiles())) {
            String fileExtension = file.getName().replaceAll(".*\\.", "");
            String level = file.getName().split("_")[0];

            File fileDestination = new File(levelsFolder, level + "/" + fileExtension + "/" + file.getName());
            fileDestination.getParentFile().mkdirs();
            file.renameTo(fileDestination);
        }
    }
}
