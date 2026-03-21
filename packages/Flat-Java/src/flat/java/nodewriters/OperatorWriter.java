package flat.java.nodewriters;

import flat.tree.*;

public abstract class OperatorWriter extends IValueWriter {
    public abstract Operator node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        return writeExpression(builder);
    }

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return builder.append(node().operator);
    }
}

