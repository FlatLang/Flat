package flat.java.nodewriters;

import flat.tree.*;

import java.util.Arrays;

public abstract class FileDeclarationWriter extends NodeWriter {
    public abstract FileDeclaration node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        getWriter(node().getPackage()).write(builder).append('\n');

        builder.append("import java.util.Optional;\n");
        builder.append("import flat.FlatUtilities;\n");
        getWriter(node().getImportList()).write(builder).append('\n');

        ClassDeclaration fileClass = node().getFileClassDeclaration();

        getWriter(fileClass).write(builder);

        Arrays.stream(node().getClassDeclarations())
            .filter(clazz -> clazz != fileClass && clazz.encapsulatingClass == null)
            .forEach(clazz -> getWriter(clazz).write(builder));

        return builder;
    }

    public StringBuilder writeName() {
        return writeName(new StringBuilder());
    }

    public StringBuilder writeName(StringBuilder builder) {
        return getWriter(node().getClassDeclaration()).writeName(builder).append(".java");
    }
}

