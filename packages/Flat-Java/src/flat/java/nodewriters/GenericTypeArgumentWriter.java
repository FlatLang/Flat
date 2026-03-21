package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.generics.GenericTypeArgument;

public abstract class GenericTypeArgumentWriter extends IIdentifierWriter {
    public abstract GenericTypeArgument node();

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive,
        boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
        if (node().getTypeObject() instanceof FunctionType) {
            FirstClassClosureDeclaration closure = ((FunctionType) node().getTypeObject()).closure;

            return getWriter(closure).writeType(builder, space, true, boxPrimitive, context,
                writeGenerics, writeArray);
        }

        return super.writeType(builder, space, convertPrimitive, boxPrimitive, context,
            writeGenerics, writeArray);
    }

    @Override
    public StringBuilder writeTypeName(StringBuilder builder, boolean convertPrimitive,
        boolean boxPrimitive, Value context) {
        ClassDeclaration c = node().getTypeClass();

        if (c == null) {
            return builder.append(node().getName());
        }

        return super.writeTypeName(builder, convertPrimitive, boxPrimitive, context);
    }

    @Override
    public final StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return writeExpression(builder, stopAt, null);
    }

    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt, Value context) {
        if (node().isGenericType()) {
            return writeGenericType(builder, context);
        }

        return writeType(builder, false, false, true, context);
    }

    @Override
    public StringBuilder writeGenericType(StringBuilder builder, Value context) {
        if (context == null) {
            return writeName(builder, node().getName());
        }

        return super.writeGenericType(builder, context);
    }

    @Override
    public StringBuilder writeName(StringBuilder builder, String name) {
        ClassDeclarationWriter.prependClassNamePrefix(builder, node().getName());
        return super.writeName(builder, name);
    }
}

