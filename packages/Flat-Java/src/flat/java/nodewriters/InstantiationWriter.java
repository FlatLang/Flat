package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.variables.Array;

public abstract class InstantiationWriter extends IIdentifierWriter {
    public abstract Instantiation node();

    @Override
    public StringBuilder writeUseExpression(StringBuilder builder) {
        builder.append("new ");

        if (node().getDeclaringClass().getClassLocation().equals("flat/Object")) {
            return builder.append("FlatUtilities.BaseObject()");
        }

        return getWriter(node().getIdentifier()).writeUseExpression(builder);
    }

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive,
        boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
        return super.writeType(builder, space, false, true, context, writeGenerics, writeArray);
    }
}

