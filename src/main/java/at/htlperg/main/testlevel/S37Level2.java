package at.htlperg.main.testlevel;

import at.htlperg.algebra.Mat;
import at.htlperg.algebra.Veci;
import at.htlperg.algebra.Veco;
import at.htlperg.io.InputReader;
import at.htlperg.io.Level;
import at.htlperg.simplereader.SimpleReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class S37Level2 extends Level {
    private final List<String> results = new ArrayList<>();

    public S37Level2() {
        super(2);
    }

    @Override
    public void readLevel(InputReader inputReader, SimpleReader simpleReader) {
//        String lines = String.join("", simpleReader.getLines()).replace("-", "");
//        String[] combs = lines.split("\n");

        int matrixCount = inputReader.line().asInt();
        for (int i = 0; i < matrixCount; i++) {
            Mat<String> matrix = inputReader.matrix();

            Mat<Character> chars = new Mat<>(matrix.getRows().map(row -> {
                char[] c = row.x().toCharArray();
                List<Character> cs = new ArrayList<>();
                for (char cc : c)
                    cs.add(cc);

                return new Veco<Character>(cs);
            }));

            Veci[] deltas = new Veci[] {
                    new Veci(-2, 0),
                    new Veci(2, 0),

                    new Veci(-1, -1),
                    new Veci(1, -1),
                    new Veci(-1, 1),
                    new Veci(1, 1),
            };

            Veci wasp = chars.indexOf('W');
            AtomicInteger count = new AtomicInteger();
            Arrays.stream(deltas).forEach(delta -> {
                try{
                    count.set((chars.component(wasp.add(delta)) == (char) 79) ? count.get()+1 : count.get());
                }catch (Exception e){
                    System.out.println("exception thrown");
                }

            });
            results.add(""+count.get());
        }
    }

    @Override
    public List<String> solveLevel() {
        return results;
    }
}
