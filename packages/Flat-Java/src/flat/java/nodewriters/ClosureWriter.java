package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.lambda.LambdaMethodDeclaration;

public abstract class ClosureWriter extends VariableWriter {
    public abstract Closure node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        FlatMethodDeclaration method = node().getMethodDeclaration();

        if (!node().isAccessed()) {
            writeLambdaParams(builder);
        }

        if (method instanceof LambdaMethodDeclaration) {
            return getWriter(method.getScope()).write(builder);
        }

        super.writeExpression(builder, stopAt);

        builder.append("(");

        FlatParameterList params = method.getParameterList();

        if (params.getNumVisibleChildren() > 0) {
            for (int i = 0; i < params.getNumVisibleChildren(); i++) {
                if (i > 0)
                    builder.append(", ");

                Parameter param = params.getVisibleChild(i);

                if (param.isOptional()) {
                    builder.append("Optional.ofNullable(");
                }

                getWriter(param).writeName(builder);

                if (param.isOptional()) {
                    builder.append(')');
                }
            }
        }

        return builder.append(")");
    }

    @Override
    public StringBuilder writeName(StringBuilder builder, String name) {
        FlatMethodDeclaration method = node().getMethodDeclaration();

        if (method != null) {
            return getWriter(method).writeName(builder, name);
        }

        return super.writeName(builder, name);
    }

    public StringBuilder writeLambdaParams(StringBuilder builder) {
        FlatMethodDeclaration method = node().getMethodDeclaration();

        ParameterList<Value> params = node().getClosureDeclaration().getParameterList();

        builder.append("(");

        FlatParameterList usedParams = method.getParameterList();

        if (params.getNumVisibleChildren() > 0) {
            for (int i = 0; i < params.getNumVisibleChildren(); i++) {
                if (i > 0)
                    builder.append(", ");

                if (i < usedParams.getNumVisibleChildren()) {
                    getWriter(usedParams.getVisibleChild(i)).writeName(builder);
                } else {
                    builder.append("_").append(i);
                }
            }
        }

        return builder.append(") -> ");
    }
}

