package business;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.Arrays;

public class ImageSequence {

    private Path[] secuencia;
    private boolean cheat;

    public ImageSequence() {
        secuencia = new Path[3];
        cheat = false;
    }

    public ImageSequence(Path[] sequence) {
        secuencia = sequence;
        cheat = false;
    }

    public ImageSequence(ImageSequence sequence) {
        this.secuencia = Arrays.copyOf(sequence.getSecuencia(), sequence.getSecuencia().length);
        this.cheat = sequence.isCheat();
    }

    public void addImage(Path imagePath)  {
        for(int i = 0; i< secuencia.length-1; i++)    {
            secuencia[i] = secuencia[i+1];
        }
        secuencia[secuencia.length-1] = imagePath;
        this.cheat = false;
    }

    public Path[] getSecuencia() {
        return Arrays.copyOf(secuencia, secuencia.length);
    }


    public boolean isCheat() {
        return cheat;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }
}
