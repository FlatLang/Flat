package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.Closure;
import flat.tree.ExtensionDeclaration;
import flat.tree.Identifier;
import flat.tree.variables.ObjectReference;
import flat.tree.variables.Variable;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter {
    public abstract Identifier node();

    public boolean isExtensionDeclaration() {
        return node() instanceof Variable && getWriter((Variable) node()).isExtensionDeclaration();
    }

    // FIXME: This needs to handle Accessibles as the path start
    public AccessorPath<Identifier, Variable> getExtensionPath() {
        AtomicInteger count = new AtomicInteger();

        return node().getAccessedNodes().stream()
            .peek(a -> count.incrementAndGet())
            .filter(a -> a instanceof Variable)
            .map(a -> (Variable) a)
            .filter(v -> getWriter(v).isExtensionDeclaration())
            .findFirst()
            .map(variable -> new AccessorPath<>(node(), variable, count.get()))
            .orElse(null);
    }

    public AccessorPath<Identifier, Accessible> getChainPath() {
        AtomicInteger count = new AtomicInteger();

        return node().getAccessedNodes().stream()
            .peek(a -> count.incrementAndGet())
            .filter(Accessible::isChainNavigation)
            .findFirst()
            .map(accessible -> new AccessorPath<>(node(), accessible, count.get()))
            .orElse(null);
    }

    public StringBuilder writeExtensionUseExpression(StringBuilder builder, Identifier start) {
        throw new UnsupportedOperationException("should be a method call (" + node().getClass()
            .getName() + "): " + node().getParentClass().getName() + "."
            + node().getParentMethod()
                .getName());
    }

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        if (node() == stopAt)
            return builder;

        if (node() != node().getReturnedNode() && node().getReturnedNode() instanceof Closure
            && !node().isAccessed()) {
            getWriter((Closure) node().getReturnedNode()).writeLambdaParams(builder);
        }

        AccessorPath<Identifier, Accessible> chainPath = getChainPath();
        AccessorPath<Identifier, Variable> extensionPath = getExtensionPath();

        if ((!node().isAccessed() || node().isChainNavigation()) && chainPath != null
            && (extensionPath == null || extensionPath.distance > chainPath.distance)) {
            builder.append("FlatUtilities.chain(");
            writeUntil(builder, chainPath.to).append(", _cr -> _cr.");
            return getWriter(chainPath.to.toValue()).writeExpression(builder, stopAt).append(')');
        }
        if (extensionPath != null) {
            if (extensionPath.to != stopAt) {
                return getWriter(extensionPath.to).writeExtensionUseExpression(builder,
                    extensionPath.from);
            }
        }

        writeUseExpression(builder);
        writeAccessedExpression(builder, stopAt);

        return builder;
    }

    public StringBuilder writeExtensionReferenceAccess(StringBuilder builder) {
        if (node().getParentClass() instanceof ExtensionDeclaration == false)
            return builder;
        if (node().getReferenceTypeNode() instanceof ObjectReference == false)
            return builder;
        if (node().isAccessedWithinStaticContext())
            return builder;

        return builder.append("_this.");
    }

    public StringBuilder writeUseExpression(StringBuilder builder) {
        writeExtensionReferenceAccess(builder);
        return writeName(builder).append(writeArrayAccess());
    }

    public final StringBuilder writeName() {
        return writeName(new StringBuilder());
    }

    public final StringBuilder writeName(StringBuilder builder) {
        return writeName(builder, null);
    }

    public StringBuilder writeName(StringBuilder builder, String name) {
        name = name != null ? name : node().getName();

        switch (name) {
            case "arguments":
                return builder.append("_java_arguments");
            case "public":
                return builder.append("_java_public");
            case "private":
                return builder.append("_java_private");
            case "package":
                return builder.append("_java_package");
            case "class":
                return builder.append("_java_class");
            case "default":
                return builder.append("_java_default");
            case "case":
                return builder.append("_java_case");
            case "function":
                return builder.append("_java_function");
            case "char":
                return builder.append("_java_char");
            default:
                return builder.append(name);
        }
    }

    public final StringBuilder writeOptionalName() {
        return writeOptionalName(new StringBuilder());
    }

    public final StringBuilder writeOptionalName(StringBuilder builder) {
        return writeOptionalName(builder, null);
    }

    public StringBuilder writeOptionalName(StringBuilder builder, String name) {
        return writeName(builder, name).append("_optional");
    }

    static class AccessorPath<TFrom extends Accessible, TTo extends Accessible> {
        TFrom from;
        TTo to;
        int distance;

        public AccessorPath(TFrom from, TTo to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }
    }
}
