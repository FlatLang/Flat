package nova.c.nodewriters;

import net.fathomsoft.nova.tree.List;

public abstract class ListWriter extends NodeWriter
{
    public abstract List node();

    @Override
    public StringBuilder generateSource(StringBuilder builder) {
        node().forEachChild(child -> {
            getWriter(child).generateSource(builder);
        });

        return builder;
    }
}