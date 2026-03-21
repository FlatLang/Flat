package flat.java.nodewriters;

import flat.tree.ExtensionDeclaration;
import flat.tree.ExtensionMethodDeclaration;
import flat.tree.FlatMethodDeclaration;
import flat.tree.PropertyMethod;
import flat.tree.variables.ObjectReference;

public abstract class ObjectReferenceWriter extends VariableWriter {
    public abstract ObjectReference node();

    @Override
    public StringBuilder writeName(StringBuilder builder, String name) {
        FlatMethodDeclaration method = node().getParentMethod();

        if (method instanceof ExtensionMethodDeclaration || method instanceof PropertyMethod
            && method.getParentClass() instanceof ExtensionDeclaration) {
            return builder.append("_this");
        }

        return super.writeName(builder, name);
    }
}

