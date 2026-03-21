package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.variables.FieldDeclaration;

public abstract class InstanceDeclarationWriter extends VariableDeclarationWriter {
    public abstract InstanceDeclaration node();

    public StringBuilder writeVisibility() {
        return writeVisibility(new StringBuilder());
    }

    public StringBuilder writeVisibility(StringBuilder builder) {
        if (node().getVisibility() == InstanceDeclaration.PRIVATE) {
            builder.append("private");
        } else if (node().getVisibility() == InstanceDeclaration.PUBLIC
            || node().getVisibility() == FieldDeclaration.VISIBLE) {
            builder.append("public");
        } else if (node().getVisibility() == InstanceDeclaration.PROTECTED) {
            builder.append("protected");
        }

        return builder.append(' ');
    }

    public StringBuilder writeStatic() {
        return writeStatic(new StringBuilder());
    }

    public StringBuilder writeStatic(StringBuilder builder) {
        if (node().isStatic()) {
            builder.append("static ");
        }

        return builder;
    }

    @Override
    public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
        writeVisibility(builder);
        writeStatic(builder);
        return super.writeSignature(builder, context, name);
    }
}

