package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.Package;

public abstract class PackageWriter extends NodeWriter {
    public abstract Package node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        return builder.append("package ").append(node().location.replace('/', '.'));
    }
}

