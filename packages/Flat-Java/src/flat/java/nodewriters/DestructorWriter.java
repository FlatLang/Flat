package flat.java.nodewriters;

import flat.tree.*;

public abstract class DestructorWriter extends BodyMethodDeclarationWriter {
    public abstract Destructor node();

    @Override
    public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
        return builder;
    }
}

