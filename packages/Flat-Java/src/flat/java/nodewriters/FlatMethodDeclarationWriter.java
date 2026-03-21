package flat.java.nodewriters;

import flat.tree.*;

public abstract class FlatMethodDeclarationWriter extends MethodDeclarationWriter {
    public abstract FlatMethodDeclaration node();

    public final StringBuilder writeParameters() {
        return writeParameters(new StringBuilder());
    }

    public final StringBuilder writeParameters(StringBuilder builder) {
        return writeParameters(builder, null);
    }

    public final StringBuilder writeParameters(StringBuilder builder, Value context) {
        return writeParameters(builder, context, null);
    }

    public final StringBuilder writeParameters(StringBuilder builder, Value context,
        String[] paramNames) {
        return writeParameters(builder, context, paramNames, true, true, false);
    }

    public StringBuilder writeParameters(StringBuilder builder, Value context, String[] paramNames,
        boolean parenthesis, boolean useGivenNames, boolean box) {
        if (node().doesOverride()) {
            String[] names = new String[node().getParameterList().getNumVisibleChildren()];

            for (int i = 0; i < names.length; i++) {
                names[i] = node().getParameterList().getVisibleChild(i).getName();
            }

            return getWriter(node().getRootDeclaration()).writeParameters(builder, node(), names,
                parenthesis, useGivenNames, box);
        }

        return getWriter(node().getParameterList()).write(builder, parenthesis, useGivenNames, box,
            context, paramNames);
    }

    @Override
    public final StringBuilder writeName(StringBuilder builder, String name) {
        return writeName(builder, name, true);
    }

    public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic) {
        super.writeName(builder, name);

        if (node().isOverloaded()) {
            builder.append('_').append(node().getOverloadID());
        } else if (node().doesOverride() && node().getRootDeclaration().getOverloadID() != -1) {
            builder.append('_').append(node().getRootDeclaration().getOverloadID());
        }

        if (appendStatic && node().isStatic()) {
            builder.append("_static");
        }

        return builder;
    }

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive,
        boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
        if (node().doesOverride()) {
            if (node().getOverriddenMethod().isPrimitive()) {
                return getWriter(node().getRootDeclaration()).writeType(builder, space,
                    convertPrimitive, boxPrimitive, context, writeGenerics, writeArray);
            }

            return super.writeType(builder, space, false, true, context, writeGenerics, writeArray);
        }

        return super.writeType(builder, space, convertPrimitive, boxPrimitive, context,
            writeGenerics, writeArray);
    }

    public StringBuilder writeGenericTypeParameters(StringBuilder builder) {
        if (node().getMethodGenericTypeParameterDeclaration().getNumVisibleChildren() == 0)
            return builder;

        builder.append("<");

        final int[] i = new int[] {0};

        node().getMethodGenericTypeParameterDeclaration().forEachVisibleChild(param -> {
            if (i[0]++ > 0)
                builder.append(", ");

            getWriter(param).writeExpression(builder);
        });

        return builder.append("> ");
    }
}

