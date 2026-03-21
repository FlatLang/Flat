package flat.java.nodewriters;

import flat.tree.*;

public abstract class AssignmentMethodWriter extends BodyMethodDeclarationWriter {
    public abstract AssignmentMethod node();

    @Override
    public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
        return builder;
    }
}

