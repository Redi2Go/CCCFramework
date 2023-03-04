package main.java.at.htlperg.io;

import java.io.IOException;
import java.io.OutputStream;

public class TeeOutputStream extends OutputStream {
    private final OutputStream stream1, stream2;

    public TeeOutputStream(OutputStream stream1, OutputStream stream2) {
        this.stream1 = stream1;
        this.stream2 = stream2;
    }

    @Override
    public void write(int b) throws IOException {
        stream1.write(b);
        stream2.write(b);
    }
}
