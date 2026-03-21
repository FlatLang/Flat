package flat.es6.nodewriters;

import flat.Flat;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.util.Location;
import flat.es6.engines.ES6CompileEngine;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static flat.Flat.LIBRARY;

public abstract class ProgramWriter extends TypeListWriter {
    public abstract Program node();

    public ClassDeclaration[] getClassesHierarchicalInOrder() {
        final ArrayList<ClassDeclaration> list = new ArrayList<>();

        node().forEachVisibleListChild(file -> {
            Arrays.stream(file.getClassDeclarations()).forEach(c -> {
                ClassDeclaration[] hierarchy = c.getClassHierarchy(true, true);

                for (int i = hierarchy.length - 1; i >= 0; i--) {
                    if (!list.contains(hierarchy[i])) {
                        list.add(hierarchy[i]);
                    }
                }
            });
        });

        return list.toArray(new ClassDeclaration[0]);
    }

    @Override
    public StringBuilder write(final StringBuilder builder) {
        ES6CompileEngine engine =
            (ES6CompileEngine) node().getProgram().getController().compileEngine;
        boolean localScope = engine.localScope;

        if (localScope) {
            builder.append("(function () {\n");
        }

        ClassDeclaration[] classes = getClassesHierarchicalInOrder();

        builder
            .append("function __copyStaticFunctions() {\n")
            .append("[].slice.call(arguments).forEach(f =>\n")
            .append("Object.getOwnPropertyNames(f)\n")
            .append("  .filter(d => !f.prototype.hasOwnProperty(d))\n")
            .append("  .forEach(d => f.prototype[d] = f[d])\n")
            .append(");\n")
            .append("}\n\n");

        builder
            .append("function __copyPrototype(f, ...g) {\n")
            .append("let v = f.prototype;\n")
            .append("g.map(a => a.prototype)\n")
            .append("  .forEach(p => Object.getOwnPropertyNames(p)\n")
            .append("  .filter(d => !v.hasOwnProperty(d))\n")
            .append("  .forEach(d => v[d] = p[d])\n")
            .append(");\n")
            .append("return __copyPrototype;\n")
            .append("}\n\n");

        builder.append("var flat_null = undefined;\n\n");

        ArrayList<ClassDeclaration> classesInOrder = new ArrayList<>();

        HashSet<String> addedClasses = new HashSet<>();
        Arrays.stream(classes).forEach(c -> addClassesInOrder(classesInOrder, addedClasses, c));
        ConcurrentHashMap<String, StringBuilder> printedClasses = new ConcurrentHashMap<>();

        Stream<ClassDeclaration> classStream = Arrays.stream(classes);

        if (!Flat.instance.isFlagEnabled(Flat.SINGLE_THREAD)) {
            classStream = classStream.parallel();
        }

        classStream.forEach(child -> {
            Flat.instance.log("Writing class " + child.getClassLocation());
            printedClasses.put(child.getClassLocation(), getWriter(child).write());
        });

        int classIndex = 0;
        for (ClassDeclaration c : classesInOrder) {
            if (classIndex++ > 0)
                builder.append("\n");

            builder.append(printedClasses.get(c.getClassLocation()));
        }

        builder.append("\n");
        builder.append("__copyStaticFunctions(");
        classIndex = 0;
        for (ClassDeclaration child : classes) {
            if (classIndex++ > 0) {
                builder.append(", ");
            }
            builder.append(getWriter(child).writeName());
        }
        builder.append(");\n\n");
        builder.append("__copyPrototype");
        for (ClassDeclaration child : classes) {
            if (child.getImplementedInterfaces().length > 0) {
                builder.append("\n  (").append(getWriter(child).writeName());
                Arrays.stream(child.getImplementedInterfaces()).forEach(i -> {
                    builder.append(", ").append(getWriter(i).writeName());
                });
                builder.append(")");
            }
        }
        builder.append(";\n");

        Node parent = Arrays.stream(node().getChildrenOfType(FileDeclaration.class))
            .map(f -> (FileDeclaration) f)
            .map(FileDeclaration::getClassDeclaration)
            .filter(Objects::nonNull)
            .flatMap(c -> Arrays.stream(c.getMethods()))
            .filter(Node::isUserMade)
            .filter(m -> !(m.getParentClass() instanceof InterfaceDeclaration))
            .findFirst()
            .get();

        Value flatNullString =
            Instantiation.decodeStatement(parent, "Null()", Location.INVALID, true);

        builder.append("\n");
        builder.append("flat_null = ").append(getWriter(flatNullString).writeUseExpression())
            .append(";\n\n");

        for (ClassDeclaration child : classes) {
            getWriter(child).writeStaticLazyDeclarations(builder);
        }

        builder.append("\n");

        for (ClassDeclaration child : classes) {
            getWriter(child).writeEncapsulationAssignments(builder);
        }

        for (ClassDeclaration child : classes) {
            getWriter(child).writeStaticBlocks(builder);
        }

        builder.append("\n");

        builder.append("async function __callStaticBlocks() {\n");
        for (ClassDeclaration child : classes) {
            getWriter(child).writeStaticBlockCalls(builder);
        }
        builder.append("}\n");

        Value emptyArgsArray =
            Instantiation.decodeStatement(parent, "Array<String>()", Location.INVALID, true);
        Accessible argvArray = SyntaxTree.decodeIdentifierAccess(parent,
            "Array.jsStringArrayToFlatArray(null)", Location.INVALID, true);

        MethodCall jsStringArrayToFlatArrayCall = (MethodCall) argvArray.getAccessedNode();

        if (!node().getController().isFlagEnabled(LIBRARY)) {
            Value nullArg = jsStringArrayToFlatArrayCall.getArgumentList().getArgumentsInOrder()[0];
            Literal processArgv = new Literal(nullArg.getParent(), Location.INVALID);
            processArgv.value = "process.argv.slice(1)";

            nullArg.replaceWith(processArgv);

            builder.append("\n");
            builder.append(
                "var flat_main_args = typeof process !== 'undefined' && process.argv ?\n  ");
            getWriter(argvArray.toValue()).writeExpression(builder);
            builder.append(" :\n  ");
            getWriter(emptyArgsArray).writeExpression(builder);
            builder.append(";\n\n");

            builder
                .append(
                    "if (typeof process !== 'undefined' && typeof process.on === 'function') {\n")
                .append("process.on('unhandledRejection', (reason, promise) => {\n")
                .append("console.error(reason);\n")
                .append("process.exit(1);\n")
                .append("});\n")
                .append("}\n\n");

            BodyMethodDeclaration method = node().getTree()
                .getMainMethod(node().getController().codeGeneratorEngine.mainClass);

            if (method == null) {
                SyntaxMessage.error("Could not find main function", node().getController());

                return builder;
            }

            if (!engine.module) {
                builder.append("(async () => {\n");
            }

            builder.append("await __callStaticBlocks();\n");

            getWriter(method.getDeclaringClass()).writeName(builder).append('.')
                .append(getWriter(method).writeName()).append("(flat_main_args);\n");

            if (!engine.module) {
                builder.append("})();\n");
            }
        } else {
            builder.append("await __callStaticBlocks();\n");
        }

        if (localScope) {
            builder.append("})();");
        }

        return builder;
    }

    private void printPrototypes(StringBuilder builder, HashSet<String> printedClasses,
        ClassDeclaration c) {
        if (!printedClasses.contains(c.getClassLocation())) {
            printedClasses.add(c.getClassLocation());

            ClassDeclaration extension = c.getExtendedClassDeclaration();

            if (extension != null) {
                printPrototypes(builder, printedClasses, extension);
            }

            getWriter(c).writeExtensionPrototypeAssignments(builder);

            builder.append("\n\n");
        }
    }

    private void addClassesInOrder(ArrayList<ClassDeclaration> classes,
        HashSet<String> addedClasses, ClassDeclaration c) {
        if (!addedClasses.contains(c.getClassLocation())) {
            addedClasses.add(c.getClassLocation());

            ClassDeclaration extension = c.getExtendedClassDeclaration();

            if (extension != null) {
                addClassesInOrder(classes, addedClasses, extension);
            }

            classes.add(c);
        }
    }
}
