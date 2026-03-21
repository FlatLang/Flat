package flat.java.nodewriters;

import flat.tree.variables.FieldDeclaration;

public abstract class FieldDeclarationWriter extends InstanceDeclarationWriter {
    public abstract FieldDeclaration node();

    @Override
    public boolean requiresLambdaWrapperClass() {
        return false;
    }
}

