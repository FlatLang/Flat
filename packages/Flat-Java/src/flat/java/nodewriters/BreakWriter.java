package flat.java.nodewriters;

import flat.tree.*;

public abstract class BreakWriter extends NodeWriter {
    public abstract Break node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return builder.append("break");
    }
}

