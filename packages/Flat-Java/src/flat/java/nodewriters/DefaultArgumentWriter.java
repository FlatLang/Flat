package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.DefaultArgument;

public abstract class DefaultArgumentWriter extends IValueWriter {
    public abstract DefaultArgument node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return builder.append("null");
    }
}

