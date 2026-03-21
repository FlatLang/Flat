package flat.java.nodewriters;

public abstract class ListWriter extends NodeWriter {
    @Override
    public StringBuilder write(StringBuilder builder) {
        node().forEachVisibleChild(child -> {
            getWriter(child).write(builder);
        });

        return builder;
    }
}

