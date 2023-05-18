package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.annotations.DataEqualsIgnoreAnnotation;
import flat.tree.annotations.DataIgnoreAnnotation;
import flat.tree.annotations.DataToStringIgnoreAnnotation;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link ClassDeclaration} extension that represents
 *
 * @author Braden Steffaniak
 */
public class DataClassDeclaration extends ClassDeclaration {
    public static final String IDENTIFIER = "data";
    private boolean addedDataClassFunctionality;

    /**
     * @see Node#Node(Node, Location)
     */
    public DataClassDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link DataClassDeclaration} instance, if possible. If it
     * is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li></li>
     * <li></li>
     * <li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link DataClassDeclaration} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a
     *         {@link DataClassDeclaration}.
     */
    public static DataClassDeclaration decodeStatement(Node parent, String statement,
        Location location, boolean require) {
        int index = SyntaxUtils.findStringInBaseScope(statement, IDENTIFIER);

        if (index >= 0 && StringUtils.findNextWhitespaceIndex(statement,
            index + IDENTIFIER.length()) == index + IDENTIFIER.length()) {
            statement = statement.substring(0, index)
                + statement.substring(index + IDENTIFIER.length() + 1);

            ClassData data = new ClassData(false, false, true);

            ClassDeclaration clazz = decodeStatement(parent, statement, location, require, data);

            if (clazz != null) {
                DataClassDeclaration n = new DataClassDeclaration(parent, location);

                clazz.cloneTo(n);

                return n;
            }
        }

        return null;
    }

    @Override
    public void addDefaultConstructor() {

    }

    @Override
    public void addDefaultDestructor() {

    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (!result.continueValidation) {
            return result;
        }

        if (phase > SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            checkAddDataClassFunctionality();
        }

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            ClassDeclaration builderClass = getBuilderClass();

            getFields().forEach((field) -> {
                if (field.initializationValue instanceof Value) {
                    FieldDeclaration builderField =
                        builderClass.getField(field.getName() + "_value");
                    builderField.initializationValue =
                        ((Value) field.initializationValue).generateFlatInput().toString();
                    builderField.decodeInitializationValue();
                }
            });
        }

        return result;
    }

    public void checkAddDataClassFunctionality() {
        if (!addedDataClassFunctionality) {
            addedDataClassFunctionality = true;
            addMissingImports();
            validateDefaultConstructor();
            addCopyFunction();
            addEqualsFunctions();
            addToStringFunction();
            addToJsonFunction();
            addBuilderClass();
            addCopyToFunction();
        }
    }

    public void addMissingImports() {
        getFields().stream()
            .filter(f -> !f.isPrimitiveType())
            .forEach((field) -> getFileDeclaration().addImport(field.getTypeClassLocation()));
    }

    private List<FieldDeclaration> getFields() {
        Stream<FieldDeclaration> fields = getFieldList()
            .getPublicFieldList()
            .getChildStream()
            .map(c -> (FieldDeclaration) c)
            .filter(f -> !f.containsAnnotationOfType(DataIgnoreAnnotation.class))
            .filter(f -> f.getShorthandAccessor() == null)
            .filter(f -> !f.containsAccessorMethod());

        if (doesExtendClass() && getExtendedClassDeclaration() instanceof DataClassDeclaration) {
            return Stream
                .concat(((DataClassDeclaration) getExtendedClassDeclaration()).getFields().stream(),
                    fields)
                .collect(Collectors.toList());
        } else {
            return fields.collect(Collectors.toList());
        }
    }

    private void addCopyFunction() {
        List<FieldDeclaration> fields = getFields();

        String params = fields.stream()
            .map(f -> f.getFlatType(this) + " " + f.getName() + " = " + f.getName())
            .collect(Collectors.joining(", "));

        BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this,
            "public copy(" + params + ") -> " + getFlatType(), Location.INVALID, true);

        if (func == null) {
            SyntaxMessage.error("Failed to create copy function for data class", this);
            return;
        }

        String args = getDataClassConstructor()
            .getParameterList()
            .getChildStream()
            .filter(f -> f instanceof ReferenceParameter == false)
            .map(param -> ((Parameter) param).getName())
            .collect(Collectors.joining(", "));

        String genericArgs = getGenericTypeArgumentList().generateFlatInput().toString();

        Value returnValue = Instantiation.decodeStatement(
            func.getScope(),
            getName() + genericArgs + "(" + args + ")",
            Location.INVALID,
            true);

        Return returnStatement = new Return(func.getScope(), Location.INVALID);
        returnStatement.getReturnValues().addChild(returnValue);

        func.getScope().addChild(returnStatement);

        addChild(func);
    }

    private void addCopyToFunction() {
        List<FieldDeclaration> fields = getFields();

        StringBuilder paramName = new StringBuilder("target");

        while (fields.stream().anyMatch(f -> f.getName().equals(paramName.toString()))) {
            paramName.append("Obj");
        }

        String params = "TargetType " + paramName + fields.stream()
            .map(f -> ", " + f.getFlatType(this) + ": " + f.getName() + " = " + f.getName())
            .collect(Collectors.joining(""));

        BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this,
            "public copyTo<TargetType extends " + getName() + ">(" + params + ") -> TargetType",
            Location.INVALID, true);

        if (func == null) {
            SyntaxMessage.error("Failed to create copyTo function for data class", this);
            return;
        }

        addChild(func);

        String calls = getDataClassConstructor()
            .getParameterList()
            .getChildStream()
            .filter(f -> f instanceof ReferenceParameter == false)
            .map(param -> (Parameter) param)
            .map(param -> "." + param.getName() + "(" + param.getName() + ")")
            .collect(Collectors.joining(""));

        Value returnValue = (Value) SyntaxTree.decodeScopeContents(
            func.getScope(),
            paramName + ".toBuilder()" + calls + ".build()",
            Location.INVALID,
            true);

        Return returnStatement = new Return(func.getScope(), Location.INVALID);
        returnStatement.getReturnValues().addChild(returnValue);

        func.getScope().addChild(returnStatement);

    }

    private void addEqualsFunctions() {
        BodyMethodDeclaration objectFunc = BodyMethodDeclaration.decodeStatement(this,
            "override public equals(Object other) -> Bool", Location.INVALID, true);

        if (objectFunc == null) {
            SyntaxMessage.error("Failed to create equals function override for data class", this);
            return;
        }

        objectFunc.shorthandAction = "(other || !this) && " +
            "other.class.isOfType(" + getName() + ".class) && " +
            "equals((" + getName() + ")other)";

        addChild(objectFunc);

        BodyMethodDeclaration classFunc = BodyMethodDeclaration.decodeStatement(this,
            "public equals(" + getName() + " other) -> Bool", Location.INVALID, true);

        if (classFunc == null) {
            SyntaxMessage.error("Failed to create equals function overload for data class", this);
            return;
        }

        List<FieldDeclaration> fields = getFields().stream()
            .filter(f -> !f.containsAnnotationOfType(DataEqualsIgnoreAnnotation.class))
            .collect(Collectors.toList());

        classFunc.shorthandAction = "(other || !this)" +
            fields.stream()
                .map(f -> " && (" + f.getName() + " == other." + f.getName() + ")")
                .collect(Collectors.joining(""));

        addChild(classFunc);
    }

    private void addToStringFunction() {
        MethodList.SearchFilter filter = new MethodList.SearchFilter();
        filter.checkAncestor = false;

        if (getMethod(this, "toString", filter, new Value[0]) != null) {
            return;
        }

        BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this,
            "override public toString() -> String", Location.INVALID, true);

        if (func == null) {
            SyntaxMessage.error("Failed to create toString function override for data class", this);
            return;
        }

        List<FieldDeclaration> fields = getFields().stream()
            .filter(f -> !f.containsAnnotationOfType(DataToStringIgnoreAnnotation.class))
            .collect(Collectors.toList());

        String values = fields.stream()
            .map(f -> "      \\\"" + f.getName() + "\\\": #{" + f.getName() + " != null && "
                + f.getName() + ".class.isOfType(String.class) ? '\"' + " + f.getName()
                + ".toString() + '\"' : " + f.getName() + ".toString()}")
            .collect(Collectors.joining(",\n"))
            .trim();

        if (values.length() == 0) {
            func.shorthandAction = "\"" + getName() + " {}\"";
        } else {
            func.shorthandAction = "\"|\n" +
                "    " + getName() + " {\n" +
                "      " + values.trim() + "\n" +
                "    }\n" +
                "    |\"";
        }

        addChild(func);
    }

    private void addToJsonFunction() {
        BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this,
            "override public toJson() -> String", Location.INVALID, true);

        if (func == null) {
            SyntaxMessage.error("Failed to create toJson function override for data class", this);
            return;
        }

        List<FieldDeclaration> fields = getFields();

        String values = fields.stream()
            .map(f -> "\\\"" + f.getName() + "\\\":#{" + f.getName() + ".toJson()}")
            .collect(Collectors.joining(","))
            .trim();

        if (values.length() == 0) {
            func.shorthandAction = "\"{}\"";
        } else {
            func.shorthandAction = "\"{" + values.trim() + "}\"";
        }

        addChild(func);
    }

    private ClassDeclaration getBuilderClass() {
        TypeList<ClassDeclaration> innerClasses = getInnerClasses(false);

        return innerClasses.filterListChildren(c -> c.getName().equals("Builder")).stream()
            .findFirst().get();
    }

    private void addBuilderClass() {
        String genericParams = getGenericTypeParameterDeclaration().generateFlatInput().toString();
        String genericArgs = getGenericTypeArgumentList().generateFlatInput().toString();
        ClassDeclaration builderClass = ClassDeclaration.decodeStatement(this,
            "public static class Builder" + genericParams, Location.INVALID, true);

        List<FieldDeclaration> fields = getFields();

        fields.forEach((field) -> {
            String fieldType = field.getFlatType(this);
            FieldDeclaration mutableField = FieldDeclaration.decodeStatement(builderClass,
                "var " + fieldType + " " + field.getName() + "_value", Location.INVALID, true);
            mutableField.onAfterDecoded();

            BodyMethodDeclaration assignFunc = BodyMethodDeclaration.decodeStatement(builderClass,
                "public " + field.getName() + "(" + fieldType + " value) -> Builder"
                    + genericParams,
                Location.INVALID, true);
            assignFunc.shorthandAction = "this";
            assignFunc.onAfterDecoded();

            builderClass.addChild(mutableField);
            builderClass.addChild(assignFunc);

            Assignment assignment = Assignment.decodeStatement(assignFunc,
                field.getName() + "_value = value", Location.INVALID, true);

            assignFunc.addChild(assignment);
        });

        BodyMethodDeclaration buildFunc = BodyMethodDeclaration.decodeStatement(builderClass,
            "public build() -> " + getName() + genericArgs, Location.INVALID, true);

        String args = getDataClassConstructor()
            .getParameterList()
            .getChildStream()
            .filter(f -> f instanceof ReferenceParameter == false)
            .map(param -> ((Parameter) param).getName() + ": " + ((Parameter) param).getName()
                + "_value")
            .collect(Collectors.joining(", "));

        buildFunc.shorthandAction = getName() + genericArgs + "(" + args + ")";

        builderClass.addChild(buildFunc);

        String constructorParams = fields.stream()
            .map(f -> f.getFlatType(this) + " " + f.getName() + " = " + f.getName() + "_value")
            .collect(Collectors.joining(", "));

        Constructor constructor = Constructor.decodeStatement(
            builderClass,
            "public construct(" + constructorParams + ")",
            Location.INVALID,
            true);

        builderClass.addChild(constructor);

        fields.forEach((field) -> {
            Assignment assignment = Assignment.decodeStatement(constructor,
                "this." + field.getName() + "_value = " + field.getName(), Location.INVALID, true);

            constructor.addChild(assignment);
        });

        addChild(builderClass);

        BodyMethodDeclaration toBuilderFunc = BodyMethodDeclaration.decodeStatement(this,
            "public toBuilder() -> Builder" + genericParams, Location.INVALID, true);

        String toBuilderArgs = fields.stream()
            .map(f -> f.getName() + ": " + f.getName())
            .collect(Collectors.joining(", "));

        toBuilderFunc.shorthandAction = "Builder" + genericArgs + "(" + toBuilderArgs + ")";

        addChild(toBuilderFunc);
    }

    private Constructor getDataClassConstructor() {
        List<FieldDeclaration> fields = getFields();

        return getConstructorList()
            .getChildStream()
            .map(x -> (Constructor) x)
            .filter(x -> x.getParameterList()
                .getChildStream()
                .filter(f -> f instanceof ReferenceParameter == false)
                .map(p -> (Parameter) p)
                .allMatch(p -> fields.stream().anyMatch(f -> f.getName().equals(p.getName()))))
            .findFirst()
            .orElse(null);
    }

    private void validateDefaultConstructor() {
        Constructor constructor = getDataClassConstructor();

        if (constructor == null) {
            List<FieldDeclaration> fields = getFields();

            String params = fields.stream()
                .map(f -> {
                    String initialization = f.getName();

                    if (f.initializationValue instanceof String) {
                        initialization = (String) f.initializationValue;
                    }

                    return "this " + f.getFlatType(this) + " " + f.getName() + " = "
                        + initialization;
                })
                .collect(Collectors.joining(", "));

            constructor = Constructor.decodeStatement(
                this,
                "public construct(" + params + ")",
                Location.INVALID,
                true);

            addChild(constructor);
        }
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public DataClassDeclaration clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        DataClassDeclaration node = new DataClassDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public DataClassDeclaration cloneTo(DataClassDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link DataClassDeclaration} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public DataClassDeclaration cloneTo(DataClassDeclaration node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link DataClassDeclaration} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
