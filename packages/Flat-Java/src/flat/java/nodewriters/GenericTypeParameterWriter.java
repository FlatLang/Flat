package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.generics.GenericTypeParameter;

public abstract class GenericTypeParameterWriter extends ValueWriter {
    public abstract GenericTypeParameter node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        builder.append(node().getName()).append(" extends ");

        return writeDefaultType(builder);
    }

    public StringBuilder writeDefaultType(StringBuilder builder) {
        if (node().getDefaultType() != null && !node().getDefaultType().equals("Object")) {
            return builder.append(node().getDefaultType());
        }

        return builder.append("FlatObject");
    }
}
