package flat.java.nodewriters;

import flat.tree.ExtensionMethodDeclaration;
import flat.tree.Value;

public abstract class ExtensionMethodDeclarationWriter extends BodyMethodDeclarationWriter {
    public abstract ExtensionMethodDeclaration node();

    @Override
    public StringBuilder writeStatic(StringBuilder builder) {
        return builder.append("static ");
    }

    @Override
    public StringBuilder writeParameters(StringBuilder builder, Value context, String[] paramNames,
        boolean parenthesis, boolean useGivenNames, boolean box) {
        if (parenthesis) {
            builder.append('(');
        }

        writeExtensionReferenceParameter(builder);

        super.writeParameters(builder, context, paramNames, false, useGivenNames, box);

        if (parenthesis) {
            builder.append(')');
        }

        return builder;
    }
}

