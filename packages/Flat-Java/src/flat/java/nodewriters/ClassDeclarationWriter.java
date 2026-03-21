package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameterList;

import java.util.Arrays;
import java.util.Optional;

public abstract class ClassDeclarationWriter extends InstanceDeclarationWriter {
    public abstract ClassDeclaration node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        writeSignature(builder).append(" {\n");

        getWriter(node().getFieldList()).write(builder).append('\n');

        getWriter(node().getConstructorList()).write(builder);
        getWriter(node().getPropertyMethodList()).write(builder);
        getWriter(node().getMethodList()).write(builder);

        Arrays.stream(node().getEncapsulatedClasses())
            .map(Writer::getWriter)
            .forEach(w -> w.write(builder).append('\n'));

        return builder.append("}\n");
    }

    @Override
    public StringBuilder writeStatic(StringBuilder builder) {
        if (node().encapsulatingClass != null) {
            return builder.append("static ");
        }

        return super.writeStatic(builder);
    }

    @Override
    public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
        writeVisibility(builder);
        writeStatic(builder);

        if (node().isAbstract()) {
            builder.append("abstract ");
        }

        builder.append("class ");
        writeName(builder, name);
        writeGenericTypeParametersDeclaration(builder);

        if (node().doesExtendClass()) {
            builder.append(" extends ");
            getWriter(node().getExtendedClass()).writeExpression(builder);
        } else if (node().getClassLocation().equals("flat/exception/Exception")) {
            builder.append(" extends RuntimeException");
        }

        writeInterfaceExtensions(builder, "implements");

        return builder;
    }

    public StringBuilder writeInterfaceExtensions(StringBuilder builder, String keyword) {
        Trait[] traits = node().getImplementedInterfaces(false);

        if (traits.length > 0) {
            builder.append(" ").append(keyword).append(" ");

            for (int i = 0; i < traits.length; i++) {
                if (i > 0)
                    builder.append(", ");

                getWriter(traits[i]).writeName(builder);

                final int index = i;

                TraitImplementation imp = node().getInterfacesImplementationList().getChildStream()
                    .map(t -> (TraitImplementation) t)
                    .filter(c -> c.getType().equals(traits[index].getName()))
                    .findFirst().get();

                GenericTypeArgumentList args = imp.getGenericTypeArgumentList();

                if (args.getNumVisibleChildren() > 0) {
                    builder.append("<");

                    for (int n = 0; n < args.getNumVisibleChildren(); n++) {
                        if (n > 0)
                            builder.append(", ");

                        GenericTypeArgument arg = args.getVisibleChild(n);

                        getWriter(arg).writeExpression(builder);
                    }

                    builder.append(">");
                }
            }
        }

        return builder;
    }

    public StringBuilder writeGenericTypeParametersDeclaration(StringBuilder builder) {
        GenericTypeParameterList params = node().getGenericTypeParameterDeclaration();

        if (params.getNumVisibleChildren() > 0) {
            builder.append("<");

            for (int i = 0; i < params.getNumVisibleChildren(); i++) {
                if (i > 0)
                    builder.append(", ");

                getWriter(params.getVisibleChild(i)).writeExpression(builder);
            }

            builder.append(">");
        }

        return builder;
    }

    @Override
    public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive,
        boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
        return builder.append("class").append(space ? ' ' : "");
    }

    @Override
    public StringBuilder writeName(StringBuilder builder, String name) {
        name = name != null ? name : node().getName();

        prependClassNamePrefix(builder, name);

        return super.writeName(builder, name);
    }

    public static StringBuilder prependClassNamePrefix(StringBuilder builder, String name) {
        switch (name) {
            case "Object":
            case "String":
            case "Console":
            case "Array":
            case "Date":
            case "Math":
            case "Node":
            case "Int":
            case "Double":
            case "Byte":
            case "Short":
            case "Long":
            case "Float":
            case "Class":
                builder.append("Flat");
        }

        return builder;
    }
}

