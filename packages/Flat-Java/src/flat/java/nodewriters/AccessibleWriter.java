package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.Value;

import static flat.java.nodewriters.Writer.getWriter;

public interface AccessibleWriter {
    public abstract Accessible node();

    default StringBuilder writeAccessedExpression(StringBuilder builder) {
        return writeAccessedExpression(builder, null);
    }

    default StringBuilder writeAccessedExpression(Accessible stopAt) {
        return writeAccessedExpression(new StringBuilder(), stopAt);
    }

    default StringBuilder writeAccessedExpression(boolean dot, Accessible stopAt) {
        return writeAccessedExpression(new StringBuilder(), stopAt, dot);
    }

    default StringBuilder writeAccessedExpression(StringBuilder builder, Accessible stopAt) {
        return writeAccessedExpression(builder, stopAt, true);
    }

    default StringBuilder writeAccessedExpression(StringBuilder builder, Accessible stopAt,
        boolean dot) {
        if (node() == stopAt)
            return builder;

        if (node().doesAccess()) {
            if (node().getAccessedNode() == stopAt)
                return builder;

            if (dot) {
                builder.append('.');
            }

            getWriter(node().getAccessedNode()).writeExpression(builder, stopAt);
        }

        return builder;
    }

    default StringBuilder writeUntil(Accessible stopBefore) {
        return writeUntil(new StringBuilder(), stopBefore);
    }

    default StringBuilder writeUntil(StringBuilder builder, Accessible stopBefore) {
        Value current = node().toValue();

        while (current != null && current != stopBefore) {
            if (current != node()) {
                builder.append('.');
            }

            getWriter(current).writeUseExpression(builder);

            current = ((Accessible) current).getAccessedNode();
        }

        return builder;
    }
}

