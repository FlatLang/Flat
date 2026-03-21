package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.generics.GenericTypeArgument;

public abstract class ParameterListWriter extends TypeListWriter {
    public abstract ParameterList node();

    @Override
    public final StringBuilder write(StringBuilder builder) {
        return write(builder, true);
    }

    public final StringBuilder write(StringBuilder builder, boolean parenthesis) {
        return write(builder, parenthesis, true, false);
    }

    public final StringBuilder write(StringBuilder builder, boolean parenthesis, boolean names,
        boolean box) {
        return write(builder, parenthesis, names, box, null);
    }

    public final StringBuilder write(StringBuilder builder, boolean parenthesis, boolean names,
        boolean box, Value context) {
        return write(builder, parenthesis, names, box, context, null);
    }

    public StringBuilder write(StringBuilder builder, boolean parenthesis, boolean names,
        boolean box, Value context, String[] parameterNames) {
        boolean nl = node().getNumVisibleChildren() >= 3;
        String delimiter = parenthesis && nl ? ",\n" : ", ";

        if (parenthesis) {
            builder.append('(');
            if (nl)
                builder.append('\n');
        }

        final int[] i = new int[] {0};

        node().forEachVisibleChild(param -> {
            String name = names && parameterNames != null ? parameterNames[i[0]] : null;

            if (i[0]++ > 0) {
                builder.append(delimiter);
            }

            if (!names && box && param instanceof Value && ((Value) param).isPrimitive()) {
                switch (((Value) param).getType()) {
                    case "Byte":
                        builder.append("java.lang.Byte");
                        break;
                    case "Short":
                        builder.append("java.lang.Short");
                        break;
                    case "Int":
                        builder.append("java.lang.Integer");
                        break;
                    case "Long":
                        builder.append("java.lang.Long");
                        break;
                    case "Char":
                        builder.append("java.lang.Character");
                        break;
                    case "Bool":
                        builder.append("java.lang.Boolean");
                        break;
                    case "Float":
                        builder.append("java.lang.Float");
                        break;
                    case "Double":
                        builder.append("java.lang.Double");
                        break;
                    default:
                        builder.append("???");
                }
            } else if (!names && param instanceof Parameter) {
                getWriter((Parameter) param).writeType(builder, false, true, false, context);
            } else if (param instanceof Parameter) {
                getWriter((Parameter) param).writeExpression(builder, null, context, name);
            } else {
                getWriter(param).writeExpression(builder);
            }
        });

        if (parenthesis) {
            if (nl)
                builder.append('\n');
            builder.append(')');
        }

        return builder;
    }
}

