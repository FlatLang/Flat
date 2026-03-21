package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.variables.ArrayAccess;

public abstract class ArrayAccessWriter extends NodeWriter {
    public abstract ArrayAccess node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return getWriter(node().getDimensions()).writeExpression(builder, stopAt);
    }
}

