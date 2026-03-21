package flat.java.nodewriters;

import flat.tree.*;

public abstract class ParameterWriter extends LocalDeclarationWriter {
    public abstract Parameter node();

    @Override
    public boolean requiresLambdaWrapperClass() {
        return false;
    }

    @Override
    public StringBuilder write(StringBuilder builder) {
        return writeExpression(builder);
    }

    @Override
    public final StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return writeExpression(builder, stopAt, null);
    }

    public final StringBuilder writeExpression(StringBuilder builder, Accessible stopAt,
        Value context) {
        return writeExpression(builder, stopAt, context, null);
    }

    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt, Value context,
        String name) {
        return writeSignature(builder, context, name);
    }

    public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
        if (node().isOptional()) {
            writeOptionalType(builder, context);
            return writeOptionalName(builder, name);
        }

        return super.writeSignature(builder, context, name);
    }

    public final StringBuilder writeInitialName(StringBuilder builder) {
        return writeInitialName(builder, null);
    }

    public StringBuilder writeInitialName(StringBuilder builder, String name) {
        if (node().isOptional()) {
            return writeOptionalName(builder, name);
        }

        return writeName(builder, name);
    }

    public StringBuilder writeOptionalType() {
        return writeOptionalType(new StringBuilder());
    }

    public StringBuilder writeOptionalType(StringBuilder builder) {
        return writeOptionalType(builder, null);
    }

    public StringBuilder writeOptionalType(StringBuilder builder, Value context) {
        builder.append("Optional<");
        super.writeType(builder, false, true, true, context);
        return builder.append("> ");
    }
}

