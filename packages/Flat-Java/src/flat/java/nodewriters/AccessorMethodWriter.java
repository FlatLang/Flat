package flat.java.nodewriters;

import flat.tree.AccessorMethod;

public abstract class AccessorMethodWriter extends PropertyMethodWriter {
    public abstract AccessorMethod node();

    @Override
    public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic) {
        builder.append("accessor_");

        return super.writeName(builder, name, appendStatic);
    }
}

