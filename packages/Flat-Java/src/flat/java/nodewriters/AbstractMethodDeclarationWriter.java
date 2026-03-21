package flat.java.nodewriters;

import flat.tree.*;

public abstract class AbstractMethodDeclarationWriter extends FlatMethodDeclarationWriter {
    public abstract AbstractMethodDeclaration node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        return writeSignature(builder).append(";\n");
    }

    @Override
    public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
        builder.append("abstract ");

        writeStatic(builder);
        writeGenericTypeParameters(builder);
        writeType(builder, true, true, false, null);
        writeName(builder, name);
        writeParameters(builder, context);

        return builder;
    }
}

