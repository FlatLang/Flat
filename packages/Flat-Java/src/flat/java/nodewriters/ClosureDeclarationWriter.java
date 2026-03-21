package flat.java.nodewriters;

import flat.tree.*;

public abstract class ClosureDeclarationWriter extends ParameterWriter {
    public abstract ClosureDeclaration node();

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive,
        boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
        if (node().getType() == null) {
            builder.append("FlatUtilities.Consumer");
        } else {
            builder.append("FlatUtilities.Function");
        }

        builder.append(node().getParameterList().getNumParameters());

        if (node().getParameterList().getNumVisibleChildren() > 0 || node().getType() != null) {
            builder.append("<");

            getWriter(node().getParameterList()).write(builder, false, false, true, context);

            if (node().getType() != null) {
                if (node().getParameterList().getNumParameters() > 0) {
                    builder.append(", ");
                }

                super.writeType(builder, false, convertPrimitive, true, context, writeGenerics,
                    writeArray);
            }

            builder.append(">");
        }

        if (space) {
            builder.append(" ");
        }

        return builder;
    }

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt, Value context,
        String name) {
        if (node().isOptional()) {
            builder.append("Optional<");
        }

        writeType(builder, false, true, false, context);

        if (node().isOptional()) {
            builder.append("> ");
            writeOptionalName(builder, name);
        } else {
            builder.append(" ");
            getWriter(node()).writeName(builder, name);
        }

        return builder;
    }
}

