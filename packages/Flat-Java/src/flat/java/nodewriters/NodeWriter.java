package flat.java.nodewriters;

import flat.tree.*;

public abstract class NodeWriter extends Writer {
    public abstract Node node();

    public final StringBuilder write() {
        return write(new StringBuilder());
    }

    public StringBuilder write(final StringBuilder builder) {
        return writeExpression(builder).append(";\n");
    }

    public final StringBuilder writeExpression() {
        return writeExpression(new StringBuilder());
    }

    public final StringBuilder writeExpression(final StringBuilder builder) {
        return writeExpression(builder, null);
    }

    public StringBuilder writeExpression(final StringBuilder builder, Accessible stopAt) {
        return builder.append("{{").append(node().getClass().getSimpleName()).append("}}");
    }
}

