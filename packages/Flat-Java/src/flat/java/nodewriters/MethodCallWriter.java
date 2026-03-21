package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.generics.GenericTypeArgumentList;

public abstract class MethodCallWriter extends VariableWriter {
    public abstract MethodCall node();

    @Override
    public StringBuilder writeName(StringBuilder builder, String name) {
        if (node().isSuperCall()) {
            return builder.append("super");
        }

        if (node().getCallableDeclaration() instanceof FlatMethodDeclaration) {
            boolean appendStatic =
                node().isWithinStaticContext() || node().isAccessedWithinStaticContext();

            return getWriter((FlatMethodDeclaration) node().getCallableDeclaration())
                .writeName(builder, name, appendStatic);
        } else if (node().getCallableDeclaration() instanceof MethodDeclaration) {
            return getWriter((MethodDeclaration) node().getCallableDeclaration()).writeName(builder,
                name);
        }

        return super.writeName(builder, name);
    }

    @Override
    public GenericTypeArgumentList getGenericTypeArgumentList() {
        return node().getMethodGenericTypeArgumentList();
    }

    @Override
    public StringBuilder writeExtensionReferenceAccess(StringBuilder builder) {
        if (node().declaration instanceof AnonymousCompilerMethod)
            return builder;

        return super.writeExtensionReferenceAccess(builder);
    }

    @Override
    public StringBuilder writeGenericArguments(StringBuilder builder, Value context) {
        if (getGenericTypeArgumentList().getChildTypeStream().anyMatch(a -> !a.autoAdded)) {
            return super.writeGenericArguments(builder, context);
        }

        return builder;
    }

    @Override
    public StringBuilder writeUseExpression(StringBuilder builder) {
        if (isExtensionDeclaration()) {
            return writeExtensionUseExpression(builder, node());
        }

        StringBuilder prefix = new StringBuilder();

        writeExtensionReferenceAccess(prefix);

        StringBuilder genericArgs = node().getCallableDeclaration() instanceof ClosureDeclaration
            ? new StringBuilder()
            : writeGenericArguments(new StringBuilder());

        builder.append(prefix);

        if (genericArgs.length() > 0 &&
            node().declaration instanceof Constructor == false &&
            !node().isAccessed() &&
            prefix.length() == 0) {
            if (node().isAccessedWithinStaticContext()) {
                getWriter((Identifier) node().getReferenceTypeNode()).writeName(builder);
            } else {
                builder.append("this");
            }

            builder.append('.');

            builder.append(genericArgs);
        }

        writeName(builder);

        if (genericArgs.length() > 0 && node().declaration instanceof Constructor) {
            builder.append(genericArgs);
        }

        if (node().getCallableDeclaration() instanceof ClosureDeclaration) {
            builder.append(".call");
        }

        return getWriter(node().getArgumentList()).write(builder);
    }

    @Override
    public StringBuilder writeExtensionUseExpression(StringBuilder builder, Identifier start) {
        writeName(builder).append('(');

        if (node() == start) {
            builder.append("_this");
        } else {
            getWriter(start).writeExpression(builder, node());
        }

        StringBuilder args = getWriter(node().getArgumentList()).write(new StringBuilder(), false);

        if (args.length() > 0) {
            builder.append(", ").append(args);
        }

        builder.append(')');

        return writeAccessedExpression(builder);
    }
}

