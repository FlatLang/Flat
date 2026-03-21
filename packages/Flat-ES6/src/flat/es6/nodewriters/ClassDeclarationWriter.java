package flat.es6.nodewriters;

import flat.tree.*;
import flat.es6.engines.ES6CompileEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class ClassDeclarationWriter extends InstanceDeclarationWriter {
    public abstract ClassDeclaration node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        boolean module =
            ((ES6CompileEngine) node().getProgram().getController().compileEngine).module;

        if (module) {
            builder.append("export ");
        }

        builder.append("class ").append(writeName()).append(" ");

        if (node().doesExtendClass()) {
            builder.append("extends ")
                .append(getWriter(node().getExtendedClassDeclaration()).writeName()).append(" ");
        }

        builder.append("{\n");

        StringBuilder contents = new StringBuilder();
        getWriter(node().getFieldList().getPrivateFieldList()).write(contents);
        getWriter(node().getFieldList().getPublicFieldList()).write(contents);
        getWriter(node().getFieldList().getPrivateStaticFieldList()).write(contents);
        getWriter(node().getFieldList().getPublicStaticFieldList()).write(contents);

        node().forEachVisibleChild(child -> {
            contents.append('\n').append(getWriter(child).write());
        });

        StringBuilder constructors = getWriter(node().getConstructorList()).write();
        StringBuilder destructors = getWriter(node().getDestructorList()).write();
        StringBuilder methods = getWriter(node().getMethodList()).write();
        StringBuilder propertyMethods = getWriter(node().getPropertyMethodList()).write();
        StringBuilder hiddenMethods = getWriter(node().getHiddenMethodList()).write();

        if (constructors.length() > 0) {
            contents.append('\n').append(constructors);
        }
        if (destructors.length() > 0) {
            contents.append('\n').append(destructors);
        }
        if (methods.length() > 0) {
            contents.append('\n').append(methods);
        }
        if (propertyMethods.length() > 0) {
            contents.append('\n').append(propertyMethods);
        }
        if (hiddenMethods.length() > 0) {
            contents.append('\n').append(hiddenMethods);
        }

        for (ClassDeclaration c : node().getEncapsulatedClasses()) {
            contents.append("static ");
            getWriter(c).writeName(contents).append("\n");
        }

        if (node().getFileDeclaration().getName().equals(node().getName())) {
            Arrays.stream(node().getSiblingClasses()).forEach(c -> {
                contents.append("static ");
                getWriter(c).writeName(contents).append("\n");
            });
        }

        builder.append(contents.toString().trim());

        return builder.append("\n}\n");
    }

    public StringBuilder writeEncapsulationAssignments() {
        return writeEncapsulationAssignments(new StringBuilder());
    }

    public StringBuilder writeEncapsulationAssignments(StringBuilder builder) {
        for (ClassDeclaration c : node().getEncapsulatedClasses()) {
            writeEncapsulationAssignment(builder, c);
        }

        if (node().getFileDeclaration().getName().equals(node().getName())) {
            Arrays.stream(node().getFileDeclaration().getClassDeclarations())
                .filter(c -> c.encapsulatingClass == null)
                .filter(c -> c != node())
                .forEach(c -> writeEncapsulationAssignment(builder, c));
        }

        return builder;
    }

    private void writeEncapsulationAssignment(StringBuilder builder, ClassDeclaration c) {
        getWriter(node()).writeName(builder).append(".");
        getWriter(c).writeName(builder);
        builder.append(" = ");
        getWriter(c).writeName(builder).append(";\n");
    }

    public StringBuilder writeUseExpression(StringBuilder builder) {
        ClassDeclaration encapsulating = node().encapsulatingClass;
        Stack<ClassDeclaration> classes = new Stack<>();

        while (encapsulating != null) {
            classes.push(encapsulating);

            encapsulating = encapsulating.encapsulatingClass;
        }

        while (!classes.isEmpty()) {
            getWriter(classes.pop()).writeName(builder).append('.');
        }

        return writeName(builder);
    }

    public StringBuilder writeExtensionPrototypeAssignments(StringBuilder builder) {
        if (node().doesExtendClass()) {
            // node().getExtendedClassDeclaration().getMethodList().forEachFlatMethod(method -> {
            // getWriter(method).writePrototypeAssignment(builder, node(), true);
            // });

            // builder.append("\n");
        }

        // node().getInterfacesImplementationList().forEachVisibleListChild(i -> {
        // Arrays.stream(i.getTypeClass().getMethods()).forEach(interfaceMethod -> {
        // if (Arrays.stream(interfaceMethod.getOverridingMethods()).noneMatch(m ->
        // m.getDeclaringClass() == node())) {
        // getWriter(interfaceMethod).writePrototypeAssignment(builder, node(), true);
        // if (Arrays.stream(node().getMethodList().getMethods()).noneMatch(a ->
        // Objects.equals(interfaceMethod.getName(), a.getName()))) {
        // getWriter(interfaceMethod).writePrototypeAssignment(builder, node(), false);
        // }
        // }
        // });
        // });

        return builder;
    }

    public StringBuilder writeStaticBlocks(final StringBuilder builder) {
        if (node().getStaticBlockList().getNumVisibleChildren() > 0) {
            node().getStaticBlockList().forEachVisibleChild(block -> {
                if (block.getScope().getNumVisibleChildren() > 0) {
                    builder.append('\n');
                }

                getWriter(block).write(builder);
            });
        }

        return builder;
    }

    public StringBuilder writeStaticBlockCalls(final StringBuilder builder) {
        if (node().getStaticBlockList().getNumVisibleChildren() > 0) {
            node().getStaticBlockList().forEachVisibleChild(node -> {
                StaticBlock block = (StaticBlock) node;
                if (block.getScope().getNumVisibleChildren() > 0) {
                    if (getWriter(block).isAsync())
                        builder.append("await ");
                    StaticBlockWriter.generateMethodName(builder, node(), block.getIndex())
                        .append("();\n");
                }
            });
        }

        return builder;
    }

    public List<ClassDeclaration> getClassesWithSameName() {
        return node().getProgram()
            .getVisibleListChildren()
            .stream()
            .filter(f -> f != node().getFileDeclaration())
            .map(f -> f.getClassDeclaration(node().getName()))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public StringBuilder writeName(StringBuilder builder) {
        for (String c : new String[] {"flat/Object", "flat/String", "flat/io/Console",
            "flat/datastruct/list/Array",
            "flat/time/Date", "flat/Math", "flat/datastruct/Node", "flat/primitive/number/Int",
            "flat/primitive/number/Double",
            "flat/primitive/number/Byte", "flat/primitive/number/Short",
            "flat/primitive/number/Long", "flat/primitive/number/Float",
            "flat/time/DateTime", "flat/async/Promise"}) {
            if (node().getClassLocation().equals(c)) {
                return builder.append("Flat").append(node().getName());
            }
        }

        List<ClassDeclaration> dupes = getClassesWithSameName();

        if (dupes.size() > 0) {
            return builder.append(node().getClassLocation().replaceAll("[^\\w\\d_]", "_"));
        }

        if (node().encapsulatingClass != null) {
            return builder
                .append(node().getClassLocation(true, false).replaceAll("[^\\w\\d_]", "_"));
        }

        return super.writeName(builder);
    }

    public void writeStaticLazyDeclarations(StringBuilder builder) {
        node().getPropertyMethodList().forEach(methodDeclaration -> {
            if (methodDeclaration instanceof AccessorMethod == false) {
                return;
            }

            AccessorMethod method = (AccessorMethod) methodDeclaration;

            if (method.getLazy() && method.isStatic()) {
                getWriter(method).writeLazyAccess(builder).append(" = undefined;\n");
            }
        });
    }
}
