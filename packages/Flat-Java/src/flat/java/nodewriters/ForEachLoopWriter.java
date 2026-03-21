package flat.java.nodewriters;

import flat.tree.*;

public abstract class ForEachLoopWriter extends LoopWriter {
    public abstract ForEachLoop node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        Value hasNextCheck = node().getHasNextCheck();

        builder.append("while (").append(getWriter(hasNextCheck).writeExpression()).append(") {\n");

        writeIteratorElementDeclaration(builder);

        node().forEachChild(child -> {
            if (child != node().getArgumentList()) {
                getWriter(child).write(builder);
            }
        });

        return builder.append("}\n");
    }

    public StringBuilder writeIteratorElementDeclaration(StringBuilder builder) {
        getWriter(node().getVariable()).writeType(builder, true);
        getWriter(node().getVariable()).writeName(builder);
        return builder.append(";\n");
    }
}

