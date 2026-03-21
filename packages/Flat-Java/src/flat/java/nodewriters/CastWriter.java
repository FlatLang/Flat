package flat.java.nodewriters;

import flat.tree.*;

public abstract class CastWriter extends IValueWriter {
    public abstract Cast node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        if (node() == stopAt)
            return builder;

        builder.append('(');
        writeType(builder, false, !node().isPointer());
        builder.append(')');

        return getWriter(node().getValueNode()).writeExpression(builder, stopAt);
    }
}

