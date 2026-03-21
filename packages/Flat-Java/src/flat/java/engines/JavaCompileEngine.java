package flat.java.engines;

import flat.CompileEngine;
import flat.Flat;

public class JavaCompileEngine extends CompileEngine {
    public JavaCompileEngine(Flat controller) {
        super(controller);


    }

    @Override
    public boolean checkArgument(String arg) {
        return false;
    }

    /**
     * Compile the generated c code into an executable file.
     */
    public void compile() {

    }
}

