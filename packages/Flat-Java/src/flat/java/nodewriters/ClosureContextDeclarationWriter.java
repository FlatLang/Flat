package flat.java.nodewriters;

import flat.tree.ClosureContextDeclaration;

public abstract class ClosureContextDeclarationWriter extends LocalDeclarationWriter {
    public abstract ClosureContextDeclaration node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        return builder;
    }
}

