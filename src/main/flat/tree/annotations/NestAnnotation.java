package flat.tree.annotations;

import flat.tree.FlatMethodDeclaration;
import flat.tree.Parameter;
import flat.util.Location;

interface NestAnnotation {
    default void addOutputStreamParameter(FlatMethodDeclaration method) {
        method.getFileDeclaration().addImport("flat/io/OutputStream");

        Parameter param = Parameter.decodeStatement(method.getParameterList(),
            "OutputStream out = Console.out", Location.INVALID, true);

        method.getParameterList().addChild(param);
        param.onAfterDecoded();
        param.validate(method.getProgram().getPhase());
        param.setProperty("userMade", false);
        method.addDefaultParameterInitializations();
    }
}

