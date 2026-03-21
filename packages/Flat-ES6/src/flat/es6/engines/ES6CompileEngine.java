package flat.es6.engines;

import flat.CompileEngine;
import flat.Flat;

public class ES6CompileEngine extends CompileEngine {
    public static final boolean INLINE_ARRAY_INITIALIZERS = false;

    public boolean localScope = false;
    public boolean module = false;

    public String scopeExportName;

    public ES6CompileEngine(Flat controller) {
        super(controller);


    }

    @Override
    public boolean checkArgument(String arg, String[] args, int index) {
        if (arg.equals("-local-scope")) {
            localScope = true;
        } else if (arg.equals("-module")) {
            module = true;
        } else if (arg.equals("-scope-export-name")) {
            if (args.length > index + 1) {
                scopeExportName = args[index + 1];
            } else {
                controller
                    .error("-scope-export-name argument requires the name as the next argument");
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Compile the generated c code into an executable file.
     */
    public void compile() {

    }
}

