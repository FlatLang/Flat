package flat.java.nodewriters;

import flat.tree.*;

public abstract class BinaryOperationWriter extends IValueWriter {
    public abstract BinaryOperation node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        boolean cast = requiresCast();

        if (cast) {
            builder.append("(");
            writeType(builder, false);
            builder.append(")");
            builder.append("(");
        }

        getWriter(node().getLeftOperand()).writeExpression(builder, stopAt);

        writeOperator(builder);

        getWriter(node().getRightOperand()).writeExpression(builder, stopAt);

        if (cast) {
            builder.append(")");
        }

        return builder;
    }

    public StringBuilder writeOperator(StringBuilder builder) {
        builder.append(' ');
        getWriter(node().getOperator()).write(builder);
        return builder.append(' ');
    }

    private boolean requiresCast() {
        return node().getParent() instanceof BinaryOperation == false &&
            node().isPrimitive() &&
            node().getOperator().isArithmetic() &&
            !node().getOperator().isShorthand();
    }
}

