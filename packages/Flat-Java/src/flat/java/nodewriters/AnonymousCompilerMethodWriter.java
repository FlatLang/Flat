package flat.java.nodewriters;

import flat.tree.AnonymousCompilerMethod;
import flat.tree.ExtensionDeclaration;

public abstract class AnonymousCompilerMethodWriter extends BodyMethodDeclarationWriter {
    public abstract AnonymousCompilerMethod node();

    @Override
    public StringBuilder writeStatic(StringBuilder builder) {
        if (node().getParentClass() instanceof ExtensionDeclaration) {
            return builder.append("static ");
        }

        return super.writeStatic(builder);
    }

    @Override
    public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
        return builder;
    }

    @Override
    public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic) {
        return super.writeName(builder, name, false);
    }
}

