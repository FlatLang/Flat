package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.Assignment;
import flat.tree.Value;

public abstract class AssignmentWriter extends ValueWriter {
    public abstract Assignment node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        Value leftType = node().getAssigneeNode().getReturnedNode();
        Value rightType = node().getAssignmentNode().getReturnedNode();
        String leftTypeStr = getWriter(leftType).writeType(new StringBuilder(), false).toString();
        String rightTypeStr = getWriter(rightType).writeType(new StringBuilder(), false).toString();

        String cast = "";

        if (!leftTypeStr.equals(rightTypeStr)) {
            cast += '(';

            if (leftTypeStr.startsWith("FlatClass<")) {
                cast += getWriter(leftType).writeType(new StringBuilder(), false, true,
                    leftType.isPrimitiveType() && leftType.isPointer(), null, false);
            } else {
                cast += leftTypeStr;
            }

            cast += ')';
        }

        if (getWriter(node().getAssignedNode()).requiresLambdaWrapperClass()) {
            getWriter(node().getAssigneeNode()).writeExpression(builder, stopAt);

            int start = builder.length() - ".get()".length();
            int end = builder.length();

            if (start > 0 && builder.subSequence(start, end).equals(".get()")) {
                builder.delete(start, end);
            }

            builder.append(".set(");
            builder.append(cast);
            getWriter(node().getAssignmentNode()).writeExpression(builder, stopAt);
            return builder.append(')');
        }

        getWriter(node().getAssigneeNode()).writeExpression(builder, stopAt).append(" = ");

        if (cast.length() > 0) {
            builder.append(cast);
            builder.append('(');
        }

        getWriter(node().getAssignmentNode()).writeExpression(builder, stopAt);

        if (cast.length() > 0) {
            builder.append(')');
        }

        return builder;
    }
}

