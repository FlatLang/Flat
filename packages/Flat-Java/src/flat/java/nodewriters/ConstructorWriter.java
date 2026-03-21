package flat.java.nodewriters;

import flat.tree.*;

public abstract class ConstructorWriter extends BodyMethodDeclarationWriter {
    public abstract Constructor node();

    @Override
    public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
        return builder;
    }

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive,
        boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
        return builder;
    }

    @Override
    public StringBuilder writeStatic(StringBuilder builder) {
        return builder;
    }

    @Override
    public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic) {
        return getWriter(node().getParentClass()).writeName(builder, name);
    }
}

