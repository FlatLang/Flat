package flat.java.nodewriters;

import flat.tree.ArrayOverloadMethod;

public abstract class ArrayOverloadMethodWriter extends BodyMethodDeclarationWriter {
    public abstract ArrayOverloadMethod node();


    @Override
    public StringBuilder write(StringBuilder builder) {
        if (!node().isDisabled()) {
            return super.write(builder);
        } else {
            return builder;
        }
    }
}

