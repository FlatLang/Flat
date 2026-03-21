package flat.java.nodewriters;

import flat.tree.lambda.LambdaMethodDeclaration;

public abstract class LambdaMethodDeclarationWriter extends BodyMethodDeclarationWriter {
    public abstract LambdaMethodDeclaration node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        return builder;
    }

    @Override
    public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
        return builder;
    }
}

