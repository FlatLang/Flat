package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.ExtendedClass;

public abstract class ExtendedClassWriter extends IValueWriter {
    public abstract ExtendedClass node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return writeType(builder, false);
    }
}

