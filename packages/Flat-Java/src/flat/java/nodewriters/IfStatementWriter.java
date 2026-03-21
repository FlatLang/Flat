package flat.java.nodewriters;

import flat.tree.*;

public abstract class IfStatementWriter extends ControlStatementWriter {
    public abstract IfStatement node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        builder.append("if (");

        return getWriter(node().getCondition()).writeExpression(builder, stopAt).append(')');
    }

    @Override
    public StringBuilder write(StringBuilder builder) {
        writeExpression(builder).append(' ');

        return getWriter(node().getScope()).write(builder, true, false);
    }
}

