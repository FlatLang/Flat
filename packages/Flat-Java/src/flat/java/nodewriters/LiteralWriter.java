package flat.java.nodewriters;

import flat.tree.*;

import java.util.Objects;

public abstract class LiteralWriter extends IValueWriter implements AccessibleWriter {
    public abstract Literal node();

    @Override
    public StringBuilder writeUseExpression(StringBuilder builder) {
        if (node().isStringInstantiation()) {
            getWriter(node().getStringInstantiation()).writeExpression(builder);
        } else {
            builder.append(node().value);

            if (isFloat()) {
                builder.append("f");
            } else if (isLong() && !isInt()) {
                builder.append("L");
            }
        }

        return writeArrayAccess(builder);
    }

    @Override
    public StringBuilder writeExpression(final StringBuilder builder, Accessible stopAt) {
        if (node() == stopAt)
            return builder;

        writeUseExpression(builder);

        return writeAccessedExpression(builder, stopAt);
    }

    public boolean isFloat() {
        if (!isDouble())
            return false;

        try {
            Float.valueOf(node().value);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble() {
        if (!node().value.contains("."))
            return false;

        try {
            Double.valueOf(node().value);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isInt() {
        try {
            Integer.valueOf(node().value);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isLong() {
        try {
            Long.valueOf(node().value);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

