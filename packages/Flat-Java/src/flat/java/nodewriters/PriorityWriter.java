package flat.java.nodewriters;

import flat.tree.*;

public abstract class PriorityWriter extends ValueWriter implements AccessibleWriter {
    public abstract Priority node();

    @Override
    public StringBuilder writeUseExpression(StringBuilder builder) {
        return builder.append('(').append(getWriter(node().getContents()).writeExpression())
            .append(')').append(writeArrayAccess());
    }

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        if (stopAt == node())
            return builder;

        writeUseExpression(builder);

        return writeAccessedExpression(builder, stopAt);
    }
}

