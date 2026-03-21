package flat.java.nodewriters;

import flat.tree.*;

public abstract class InitializationMethodWriter extends BodyMethodDeclarationWriter {
    public abstract InitializationMethod node();

    @Override
    public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic) {
        builder.append("init_");

        getWriter(node().getParentClass()).writeName(builder, name);

        return builder;
    }

    @Override
    public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
        return builder;
    }

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive,
        boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
        return super.writeType(builder, space, false, true, context, writeGenerics, writeArray);
    }
}

