package flat.js.nodewriters;

import java.util.ArrayList;
import java.util.Arrays;
import flat.tree.*;

public abstract class StaticBlockWriter extends NodeWriter {
    public abstract StaticBlock node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        Scope scope = node().getScope();

        if (scope.getNumVisibleChildren() == 0) {
            return builder;
        }

        builder.append("var ");
        generateMethodName(builder, node().getParentClass(), node().getIndex())
            .append("_initialized").append(" = false;\n");

        if (isAsync())
            builder.append("async ");

        builder.append("function ");
        generateMethodName(builder, node().getParentClass(), node().getIndex()).append("() {\n");

        builder.append("if (!");
        generateMethodName(builder, node().getParentClass(), node().getIndex())
            .append("_initialized");
        builder.append(") {\n");

        generateMethodName(builder, node().getParentClass(), node().getIndex())
            .append("_initialized = true;\n");

        for (ClassDeclaration c : scope.getDependencies()) {
            TypeList<StaticBlock> blocks = c.getStaticBlockList();

            if (blocks.getNumVisibleChildren() > 0) {
                c.getStaticBlockList().forEachVisibleChild(node -> {
                    StaticBlock block = (StaticBlock) node;
                    if (block.getScope().getNumVisibleChildren() > 0) {
                        if (getWriter(block).isAsync()) {
                            builder.append("await ");
                        }
                        generateMethodName(builder, c, block.getIndex()).append("();\n");
                    }
                });
            }
        }

        getWriter(scope).write(builder, false, true);

        return builder.append("}\n}\n");
    }

    public boolean isAsync() {
        return isAsync(new ArrayList<>());
    }

    public boolean isAsync(ArrayList<Node> checked) {
        if (checked.contains(node()))
            return node().isAsync();
        checked.add(node());
        return node().isAsync() || Arrays.stream(node().getScope().getDependencies())
            .anyMatch(
                x -> x.getStaticBlockList().getChildStream().map(s -> getWriter((StaticBlock) s))
                    .anyMatch(s -> s.isAsync(checked)));
    }

    public static StringBuilder generateMethodName(StringBuilder builder, ClassDeclaration clazz,
        Integer index) {
        return builder.append(getWriter(clazz).writeName()).append(index)
            .append(StaticBlock.C_PREFIX).append(StaticBlock.IDENTIFIER);
    }
}
