package flat.java.nodewriters;

import flat.tree.ClosureVariableDeclaration;

public abstract class ClosureVariableDeclarationWriter extends VariableDeclarationWriter {
    public abstract ClosureVariableDeclaration node();

    @Override
    public boolean requiresLambdaWrapperClass() {
        return getWriter(node().originalDeclaration).requiresLambdaWrapperClass()
            || super.requiresLambdaWrapperClass();
    }

    @Override
    public StringBuilder write(StringBuilder builder) {
        return builder;
    }
}

