package flat.java.nodewriters;

import flat.tree.variables.FieldList;

public abstract class FieldListWriter extends ListWriter {
    public abstract FieldList node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        return writePrivateNonStatic(builder).append('\n')
            .append(writeNonPrivateNonStatic()).append('\n')
            .append(writePrivateStatic()).append('\n')
            .append(writeNonPrivateStatic()).append('\n');
    }

    public StringBuilder writePrivateNonStatic() {
        return writePrivateNonStatic(new StringBuilder());
    }

    public StringBuilder writePrivateNonStatic(StringBuilder builder) {
        return getWriter(node().getPrivateFieldList()).write(builder);
    }

    public StringBuilder writeNonPrivateNonStatic() {
        return writeNonPrivateNonStatic(new StringBuilder());
    }

    public StringBuilder writeNonPrivateNonStatic(StringBuilder builder) {
        return getWriter(node().getPublicFieldList()).write(builder);
    }

    public StringBuilder writePrivateStatic() {
        return writePrivateStatic(new StringBuilder());
    }

    public StringBuilder writePrivateStatic(StringBuilder builder) {
        return getWriter(node().getPrivateStaticFieldList()).write(builder);
    }

    public StringBuilder writeNonPrivateStatic() {
        return writeNonPrivateStatic(new StringBuilder());
    }

    public StringBuilder writeNonPrivateStatic(StringBuilder builder) {
        return getWriter(node().getPublicStaticFieldList()).write(builder);
    }
}

