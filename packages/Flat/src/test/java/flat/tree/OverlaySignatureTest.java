package flat.tree;

import flat.Flat;
import flat.ValidationResult;
import flat.error.SyntaxErrorException;
import flat.tree.annotations.NativeAnnotation;
import flat.tree.annotations.NativeArrayAnnotation;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameter;
import flat.util.Location;
import java.io.File;

/** Dependency-free AST regression entrypoint; run with the isolated bootstrap JAR.
 * Requires -Dflat.test.engineJar=<isolated>/Flat-ES6/target/flat-es6.jar copied
 * from the existing local backend. Flat eagerly initializes that engine in its
 * constructor; these AST tests never generate or compile target output.
 * Does not install tools or require a reduced Core closure.
 */
public final class OverlaySignatureTest {
    private static final Location LOCATION = Location.INVALID;
    private static Flat controller;
    private static Program program;

    public static void main(String[] args) throws java.io.IOException {
        String engineJar = System.getProperty("flat.test.engineJar");
        if (engineJar == null || !new File(engineJar).isFile()) {
            throw new IllegalArgumentException("flat.test.engineJar must select an existing isolated Flat-ES6 backend JAR");
        }
        File output = File.createTempFile("overlay-signature-", ".js", new File("."));
        output.deleteOnExit();
        controller = new Flat(new String[] {"-install-dir", ".", "-target", "es6",
            "-engine-jar", engineJar, "-o", output.getAbsolutePath()}) {
            @Override public SyntaxTree getTree() {
                // Engine constructors query the controller before this fixture's
                // Program exists; preserve the controller's startup contract.
                return program == null ? super.getTree() : program.getTree();
            }
        };
        controller.isTesting = true;
        program = new Program(controller, new SyntaxTree(new File[0], new String[0], controller) {
            @Override public Program getRoot() { return program; }
        });
        ClassDeclaration object = clazz("Object.flat", "Object");
        ClassDeclaration string = clazz("String.flat", "String");
        object.getPackage().location = "flat";
        string.getPackage().location = "flat";
        // Constructor parents are temporary decoding context, not membership.
        // Register without detaching from a Program index that does not yet exist.
        program.addChild(program.getNumChildren(), object.getFileDeclaration(), program, false);
        program.addChild(program.getNumChildren(), string.getFileDeclaration(), program, false);
        program.addClassesToImports();
        String group = args.length == 0 ? "all" : args[0];
        if (!group.equals("all") && !group.equals("lookup") && !group.equals("overlay")
            && !group.equals("representation") && !group.equals("duplicates") && !group.equals("slots") && !group.equals("extensions")) {
            throw new IllegalArgumentException("Unknown test group: " + group);
        }
        if (group.equals("all") || group.equals("lookup")) nullableClassLookup(object);
        if (group.equals("all") || group.equals("overlay") || group.equals("duplicates")) {
            for (String target : new String[] {"js", "java"}) {
                controller.target = target;
                if (!group.equals("duplicates")) {
                    for (boolean reverse : new boolean[] {false, true}) overlay(target, reverse);
                }
                if (!group.equals("overlay")) {
                    duplicates(target, false);
                    duplicates(target, true);
                }
            }
        }
        if (group.equals("all") || group.equals("extensions")) extensions();
        if (group.equals("all") || group.equals("slots")) overrideSlots();
        if (group.equals("all") || group.equals("representation")) representation();
        System.out.println("OverlaySignatureTest passed");
    }

    private static ClassDeclaration clazz(String filename, String name) {
        FileDeclaration file = new FileDeclaration(program, LOCATION, new File(filename));
        ClassDeclaration clazz = new ClassDeclaration(file, LOCATION);
        clazz.setName(name);
        file.addChild(clazz);
        return clazz;
    }

    // Keep merge validation scoped to the merge contract, not unrelated method
    // body/typechecking phases. Parameters carry real declaration representation.
    private static final class Method extends BodyMethodDeclaration {
        Method(ClassDeclaration parent) { super(parent, LOCATION); }
        @Override public ValidationResult validate(int phase) { return new ValidationResult(this); }
    }

    private static Method method(ClassDeclaration owner, ClassDeclaration declaredClass, boolean nullable) {
        Method method = new Method(owner);
        method.setName("equals");
        Parameter parameter = new Parameter(method.getParameterList(), LOCATION) {
            @Override public ClassDeclaration getTypeClass(boolean cast, boolean generic) {
                return declaredClass;
            }
        };
        parameter.setName("other");
        parameter.setTypeValue(declaredClass.getName());
        parameter.explicitlyNullable = nullable;
        parameter.setDataType(Value.POINTER);
        method.getParameterList().addChild(parameter);
        owner.addChild(method);
        return method;
    }

    private static void overlay(String target, boolean reverse) {
        ClassDeclaration shared = clazz("StringCharArray.flat", "StringCharArray");
        ClassDeclaration platform = clazz("StringCharArray." + target + ".flat", "StringCharArray");
        ClassDeclaration object = program.getClassDeclaration("flat/Object");
        Method broad, exact;
        if (reverse) {
            exact = method(shared, shared, true);
            broad = method(shared, object, true);
        } else {
            broad = method(shared, object, true);
            exact = method(shared, shared, true);
        }
        Method implementation = method(platform, platform, true);
        check(DeclaredMethodSignature.matches(exact, implementation), "exact nullable overlay");
        check(!DeclaredMethodSignature.matches(broad, implementation), "no subtype compatibility");
        // mergeClasses moves the receiver's platform implementations into shared.
        platform.mergeClasses(shared, -1);
        check(shared.getMethodList().getNumVisibleChildren() == 2, target + " overload count");
        check(broad.getParentClass() == shared, "Object? overload retained");
        check(implementation.getParentClass() == shared, "platform implementation selected");
        check(exact.getParent() != shared.getMethodList(), "shared declaration replaced");

        controller.errors.clear();
        broad.setOverloadIDs(new MethodDeclaration[] {broad, implementation});
        check(controller.errors.isEmpty(), "distinct overloads have no duplicate diagnostic");
        check(broad.overloadID != implementation.overloadID, "distinct overload IDs");

        ClassDeclaration nullable = clazz("Nullable.flat", "Nullable");
        ClassDeclaration overlay = clazz("Nullable." + target + ".flat", "Nullable");
        Method nonnull;
        if (reverse) {
            method(nullable, nullable, true);
            nonnull = method(nullable, nullable, false);
        } else {
            nonnull = method(nullable, nullable, false);
            method(nullable, nullable, true);
        }
        Method replacement = method(overlay, overlay, true);
        overlay.mergeClasses(nullable, -1);
        check(nullable.getMethodList().getNumVisibleChildren() == 2, "nullable overload retained");
        check(nonnull.getParentClass() == nullable, "nonnull overload retained");
        check(replacement.getParentClass() == nullable, "nullable exact replacement");
        controller.errors.clear();
        nonnull.setOverloadIDs(new MethodDeclaration[] {nonnull, replacement});
        check(controller.errors.isEmpty(), "nullable distinction is not a duplicate");
        check(nonnull.overloadID != replacement.overloadID, "nullable overload IDs distinct");
    }

    private static void nullableClassLookup(ClassDeclaration object) {
        ClassDeclaration owner = clazz("Lookup.flat", "Lookup");
        Method method = new Method(owner);
        method.setName("lookup");
        owner.addChild(method);
        Parameter parameter = Parameter.decodeStatement(method.getParameterList(), "Object? other",
            LOCATION, true, true, false);
        check(parameter != null && parameter.explicitlyNullable, "nullable declaration parsed");
        method.getParameterList().addChild(parameter);
        check(parameter.getFlatTypeClass() == object, "nullable underlying class lookup");
        check(parameter.getFlatTypeClass(parameter, false, false) == object, "context nullable class lookup");
        check(parameter.explicitlyNullable && parameter.getFlatType().endsWith("?"),
            "class lookup preserves nullable declaration");
    }

    private static void representation() {
        ClassDeclaration owner = clazz("Owner.flat", "Owner");
        ClassDeclaration type = clazz("Type.flat", "Type");
        Method a = method(owner, type, false), b = method(owner, type, false);
        Parameter left = (Parameter)a.getParameter(0), right = (Parameter)b.getParameter(0);
        check(DeclaredMethodSignature.matches(a, b), "same representation");
        right.explicitlyNullable = true;
        check(!DeclaredMethodSignature.matches(a, b), "nullable distinct");
        right.explicitlyNullable = false;
        right.setArrayDimensions(1);
        check(!DeclaredMethodSignature.matches(a, b), "array distinct");
        right.setArrayDimensions(0);
        right.setDataType(Value.DOUBLE_POINTER);
        check(!DeclaredMethodSignature.matches(a, b), "pointer representation distinct");
        right.setDataType(Value.POINTER);
        right.addAnnotation(new NativeAnnotation(right, LOCATION));
        check(!DeclaredMethodSignature.matches(a, b), "native distinct");
        left.addAnnotation(new NativeAnnotation(left, LOCATION));
        check(DeclaredMethodSignature.matches(a, b), "matching native representation");
        right.addAnnotation(new NativeArrayAnnotation(right, LOCATION));
        check(!DeclaredMethodSignature.matches(a, b), "native array distinct");
        left.addAnnotation(new NativeArrayAnnotation(left, LOCATION));
        check(DeclaredMethodSignature.matches(a, b), "matching native array representation");
        GenericTypeArgument l = new GenericTypeArgument(left.getGenericTypeArgumentList(), LOCATION);
        l.setTypeValue("Object");
        left.getGenericTypeArgumentList().addChild(l);
        GenericTypeArgument r = new GenericTypeArgument(right.getGenericTypeArgumentList(), LOCATION);
        r.setTypeValue("String");
        right.getGenericTypeArgumentList().addChild(r);
        check(!DeclaredMethodSignature.matches(a, b), "generic argument distinct");
        r.setTypeValue("Object");
        check(DeclaredMethodSignature.matches(a, b), "matching generic argument");
        r.explicitlyNullable = true;
        check(!DeclaredMethodSignature.matches(a, b), "nullable generic argument distinct");
        r.explicitlyNullable = false;
        GenericTypeParameter parameterA = new GenericTypeParameter(
            a.getMethodGenericTypeParameterDeclaration(), LOCATION);
        parameterA.setName("T");
        a.getMethodGenericTypeParameterDeclaration().addChild(parameterA);
        check(!DeclaredMethodSignature.matches(a, b), "method generic arity distinct");
        GenericTypeParameter parameterB = new GenericTypeParameter(
            b.getMethodGenericTypeParameterDeclaration(), LOCATION);
        parameterB.setName("U");
        b.getMethodGenericTypeParameterDeclaration().addChild(parameterB);
        check(DeclaredMethodSignature.matches(a, b), "method generic names do not select overload");
        b.setStatic(true);
        check(!DeclaredMethodSignature.matches(a, b), "static receiver distinct");
    }

    private static ExtensionMethodDeclaration extension(ClassDeclaration owner, String receiver) {
        ExtensionMethodDeclaration method = ExtensionMethodDeclaration.decodeStatement(owner,
            "public getWriter(" + receiver + " this)", LOCATION, true);
        check(method != null, "extension decoded");
        owner.addChild(method);
        check(method.getParameterList().getTypes().length == 0, "receiver is hidden, not a visible parameter");
        return method;
    }

    private static void extensions() {
        for (boolean reverse : new boolean[] {false, true}) {
            ClassDeclaration temporary = clazz("WriterExtensions.flat", "WriterExtensions");
            FileDeclaration file = temporary.getReferenceFile();
            temporary.detach();
            ExtensionDeclaration owner = new ExtensionDeclaration(file, LOCATION);
            owner.setName("WriterExtensions");
            file.addChild(owner);
            ExtensionMethodDeclaration a, b;
            if (reverse) {
                b = extension(owner, "String");
                a = extension(owner, "Object");
            } else {
                a = extension(owner, "Object");
                b = extension(owner, "String");
            }
            check(!DeclaredMethodSignature.matches(a, b), "distinct extension receiver identity");
            controller.errors.clear();
            a.setOverloadIDs(new MethodDeclaration[] {a, b});
            check(controller.errors.isEmpty(), "distinct extension receivers are not duplicates");
            check(a.getOverloadID() != b.getOverloadID(), "extension receivers have distinct slots");
            ExtensionMethodDeclaration same = extension(owner, "Object");
            check(DeclaredMethodSignature.matches(a, same), "same extension receiver identity");
            same.setStatic(!a.isStatic());
            check(!DeclaredMethodSignature.matches(a, same), "extension staticness distinct");
            same.setStatic(a.isStatic());
            ReferenceParameter left = a.getParameterList().getReferenceParameter();
            ReferenceParameter right = same.getParameterList().getReferenceParameter();
            right.explicitlyNullable = true;
            check(!DeclaredMethodSignature.matches(a, same), "nullable extension receiver distinct");
            right.explicitlyNullable = left.explicitlyNullable;
            GenericTypeArgument l = new GenericTypeArgument(left.getGenericTypeArgumentList(), LOCATION);
            l.setTypeValue("Object");
            left.getGenericTypeArgumentList().addChild(l);
            GenericTypeArgument r = new GenericTypeArgument(right.getGenericTypeArgumentList(), LOCATION);
            r.setTypeValue("String");
            right.getGenericTypeArgumentList().addChild(r);
            check(!DeclaredMethodSignature.matches(a, same), "generic extension receiver distinct");
            r.setTypeValue("Object");
            check(DeclaredMethodSignature.matches(a, same), "matching generic extension receiver");
            controller.errors.clear();
            try { a.setOverloadIDs(new MethodDeclaration[] {same, a}); }
            catch (SyntaxErrorException expected) { }
            check(controller.errors.stream().anyMatch(e -> e.contains("Duplicate method 'getWriter'")),
                "genuine extension duplicate diagnostic");
            controller.errors.clear();
        }
    }

    private static void overrideSlots() {
        for (boolean reverse : new boolean[] {false, true}) {
            ClassDeclaration object = program.getClassDeclaration("flat/Object");
            ClassDeclaration list = clazz("SlotList.flat", "SlotList");
            ClassDeclaration data = clazz("SlotData.flat", "SlotData");
            Method root = method(object, object, true);
            Method traitEquals = method(list, object, true);
            traitEquals.overridenMethod = root;
            Method generatedEquals = method(data, object, false);
            generatedEquals.overridenMethod = root;
            Method typedEquals = method(data, data, false);
            MethodDeclaration[] candidates = reverse
                ? new MethodDeclaration[] {traitEquals, typedEquals, generatedEquals}
                : new MethodDeclaration[] {generatedEquals, typedEquals, traitEquals};
            typedEquals.setOverloadIDs(candidates);
            check(root.getOverloadID() == -1, "Object equality root slot unchanged");
            check(traitEquals.getOverloadID() == root.getOverloadID(),
                "trait equality retains Object dispatch slot");
            check(generatedEquals.getOverloadID() == root.getOverloadID(),
                "data equality retains Object dispatch slot");
            check(typedEquals.getOverloadID() != root.getOverloadID(),
                "typed equality has distinct dispatch slot");
            // A later trait visit must not move either overriding declaration.
            traitEquals.setOverloadIDs(candidates);
            check(traitEquals.getOverloadID() == root.getOverloadID(), "trait slot schedule independent");
            check(generatedEquals.getOverloadID() == root.getOverloadID(), "data slot schedule independent");
            root.detach();
        }
    }

    private static void duplicates(String target, boolean duplicateShared) {
        ClassDeclaration shared = clazz("Duplicate.flat", "Duplicate");
        ClassDeclaration platform = clazz("Duplicate." + target + ".flat", "Duplicate");
        method(shared, shared, true);
        Method sharedB = duplicateShared ? method(shared, shared, true) : null;
        Method a = method(platform, platform, true);
        Method b = duplicateShared ? sharedB : method(platform, platform, true);
        platform.mergeClasses(shared, -1);
        check(shared.getMethodList().getNumVisibleChildren() == 2, "duplicate not erased by overlay");
        controller.errors.clear();
        try { a.setOverloadIDs(new MethodDeclaration[] {a, b}); }
        catch (SyntaxErrorException expected) { }
        check(controller.errors.stream().anyMatch(e -> e.contains("Duplicate method 'equals'")),
            "genuine duplicate diagnostic");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
