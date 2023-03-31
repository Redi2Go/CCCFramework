package at.htlperg.main.testlevel;

import at.htlperg.algebra.Mat;
import at.htlperg.algebra.Veci;
import at.htlperg.algebra.Veco;
import at.htlperg.io.InputReader;
import at.htlperg.io.Level;
import at.htlperg.simplereader.SimpleReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class S37Level3 extends Level {
    private final Veci[] deltas = new Veci[] {
            new Veci(-2, 0),
            new Veci(2, 0),

            new Veci(-1, -1),
            new Veci(1, -1),
            new Veci(-1, 1),
            new Veci(1, 1),
    };

    List<String> outputs = new LinkedList<>();

    public S37Level3() {
        super(3);
    }

    @Override
    public void readLevel(InputReader inputReader, SimpleReader simpleReader) {
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

            boolean trapped = false;
            wasp: for (Veci delta : deltas) {
                trapped = false;
                Veci wasp = chars.indexOf('W');
                while (!trapped) {
                    wasp = wasp.add(delta);
                    if (chars.component(wasp) == null) {
                        break wasp;
                    }
                    else if (chars.component(wasp) == 'X') {
                        trapped = true;
                    }
                }
            }

            outputs.add((trapped)?"TRAPPED":"FREE");

        }
    }

    @Override
    public List<String> solveLevel() {
        return outputs;
    }
}
