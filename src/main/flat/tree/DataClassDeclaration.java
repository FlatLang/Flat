package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
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
 * @author	Braden Steffaniak
 */
public class DataClassDeclaration extends ClassDeclaration
{
	public static final String IDENTIFIER = "data";
	private boolean addedDataClassFunctionality;

	/**
	 * @see Node#Node(Node, Location)
	 */
	public DataClassDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link DataClassDeclaration} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li></li>
	 * 	<li></li>
	 * 	<li></li>
	 * </ul>
	 *
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link DataClassDeclaration} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link DataClassDeclaration}.
	 */
	public static DataClassDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		int index = SyntaxUtils.findStringInBaseScope(statement, IDENTIFIER);
		
		if (index >= 0 && StringUtils.findNextWhitespaceIndex(statement, index + IDENTIFIER.length()) == index + IDENTIFIER.length())
		{
			statement = statement.substring(0, index) + statement.substring(index + IDENTIFIER.length() + 1);
			
			ClassData data = new ClassData(false, false, true);
			
			ClassDeclaration clazz = decodeStatement(parent, statement, location, require, data);
			
			if (clazz != null)
			{
				DataClassDeclaration n = new DataClassDeclaration(parent, location);
				
				clazz.cloneTo(n);
				
				return n;
			}
		}
		
		return null;
	}
	
	@Override
	public void addDefaultConstructor()
	{
		
	}
	
	@Override
	public void addDefaultDestructor()
	{
		
	}

	@Override
	public ValidationResult validate(int phase) {
		ValidationResult result = super.validate(phase);

		if (!result.continueValidation) {
			return result;
		}

		checkAddDataClassFunctionality(phase);

		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
			ClassDeclaration builderClass = getBuilderClass();

			getFields().forEach((field) -> {
				if (field.initializationValue instanceof Value) {
					FieldDeclaration builderField = builderClass.getField(field.getName() + "_value");
					builderField.initializationValue = ((Value) field.initializationValue).generateFlatInput().toString();
					builderField.decodeInitializationValue();
				}
			});
		}

		return result;
	}

	private void checkAddDataClassFunctionality(int phase) {
		if (phase >= SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
			if (!addedDataClassFunctionality) {
				addedDataClassFunctionality = true;
				addMissingImports();
				validateDefaultConstructor();
				addCopyFunction();
				addEqualsFunctions();
				addToStringFunction();
				addBuilderClass();
			}
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
			.map(c -> (FieldDeclaration)c)
			.filter(f -> f.getShorthandAccessor() == null);

		if (doesExtendClass() && getExtendedClassDeclaration() instanceof DataClassDeclaration) {
			return Stream.concat(fields, ((DataClassDeclaration)getExtendedClassDeclaration()).getFields().stream()).collect(Collectors.toList());
		} else {
			return fields.collect(Collectors.toList());
		}
	}

	private void addCopyFunction() {
		List<FieldDeclaration> fields = getFields();

		String params = fields.stream()
			.map(f -> f.getFlatType() + ": " + f.getName() + " = " + f.getName())
			.collect(Collectors.joining(", "));

		BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this, "public copy(" + params + ") -> " + getFlatType(), Location.INVALID, true);

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

		Value returnValue = Instantiation.decodeStatement(
			func.getScope(),
			getName() + "(" + args + ")",
			Location.INVALID,
			true
		);

		Return returnStatement = new Return(func.getScope(), Location.INVALID);
		returnStatement.getReturnValues().addChild(returnValue);

		func.getScope().addChild(returnStatement);

		addChild(func);
	}

	private void addEqualsFunctions() {
		BodyMethodDeclaration objectFunc = BodyMethodDeclaration.decodeStatement(this, "override public equals(Object other) -> Bool", Location.INVALID, true);

		if (objectFunc == null) {
			SyntaxMessage.error("Failed to create equals function override for data class", this);
			return;
		}

		objectFunc.shorthandAction = "(other || !this) && " +
			"other.class.isOfType(" + getName() + ".class) && " +
			"equals((" + getName() + ")other)";

		addChild(objectFunc);

		BodyMethodDeclaration classFunc = BodyMethodDeclaration.decodeStatement(this, "public equals(" + getName() + " other) -> Bool", Location.INVALID, true);

		if (classFunc == null) {
			SyntaxMessage.error("Failed to create equals function overload for data class", this);
			return;
		}

		List<FieldDeclaration> fields = getFields();

		classFunc.shorthandAction = "(other || !this)" +
			fields.stream()
				.map(f -> " && (" + f.getName() + " == other." + f.getName() + ")")
				.collect(Collectors.joining(""));

		addChild(classFunc);
	}

	private void addToStringFunction() {
		BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this, "override public toString() -> String", Location.INVALID, true);

		if (func == null) {
			SyntaxMessage.error("Failed to create toString function override for data class", this);
			return;
		}

		List<FieldDeclaration> fields = getFields();

		String values = fields.stream()
			.map(f -> "      \\\"" + f.getName() + "\\\": #{" + f.getName() + ".toString()}")
			.collect(Collectors.joining(",\n"))
			.trim();

		if (values.length() == 0) {
			func.shorthandAction = "\"{}\"";
		} else {
			func.shorthandAction = "\"|\n" +
				"    {\n" +
				"      " + values.trim() + "\n" +
				"    }\n" +
				"    |\"";
		}

		addChild(func);
	}

	private ClassDeclaration getBuilderClass() {
		TypeList<ClassDeclaration> innerClasses = getInnerClasses(false);

		return innerClasses.filterListChildren(c -> c.getName().equals("Builder")).stream().findFirst().get();
	}

	private void addBuilderClass() {
		ClassDeclaration builderClass = ClassDeclaration.decodeStatement(this, "public static class Builder", Location.INVALID, true);

		List<FieldDeclaration> fields = getFields();

		fields.forEach((field) -> {
			FieldDeclaration mutableField = FieldDeclaration.decodeStatement(builderClass, "var " + field.getFlatType() + " " + field.getName() + "_value", Location.INVALID, true);

			BodyMethodDeclaration assignFunc = BodyMethodDeclaration.decodeStatement(builderClass, "public " + field.getName() + "(" + field.getFlatType() + " value) -> Builder", Location.INVALID, true);
			assignFunc.shorthandAction = "this";

			builderClass.addChild(mutableField);
			builderClass.addChild(assignFunc);

			Assignment assignment = Assignment.decodeStatement(assignFunc, field.getName() + "_value = value", Location.INVALID, true);

			assignFunc.addChild(assignment);
		});

		BodyMethodDeclaration buildFunc = BodyMethodDeclaration.decodeStatement(builderClass, "public build() -> " + getName(), Location.INVALID, true);

		String args = getDataClassConstructor()
			.getParameterList()
			.getChildStream()
			.filter(f -> f instanceof ReferenceParameter == false)
			.map(param -> ((Parameter) param).getName() + ": " + ((Parameter) param).getName() + "_value")
			.collect(Collectors.joining(", "));

		buildFunc.shorthandAction = getName() + "(" + args + ")";

		builderClass.addChild(buildFunc);

		String constructorParams = fields.stream()
			.map(f -> f.getFlatType() + ": " + f.getName() + " = " + f.getName() + "_value")
			.collect(Collectors.joining(", "));

		Constructor constructor = Constructor.decodeStatement(
			builderClass,
			"public construct(" + constructorParams + ")",
			Location.INVALID,
			true
		);

		builderClass.addChild(constructor);

		fields.forEach((field) -> {
			Assignment assignment = Assignment.decodeStatement(constructor, "this." + field.getName() + "_value = " + field.getName(), Location.INVALID, true);

			constructor.addChild(assignment);
		});

		addChild(builderClass);
	}

	private Constructor getDataClassConstructor() {
		List<FieldDeclaration> fields = getFields();

		return (Constructor) getMethod(this, getName(), fields.toArray(new FieldDeclaration[0]));
	}

	private void validateDefaultConstructor() {
		Constructor constructor = getDataClassConstructor();

		if (constructor == null) {
			List<FieldDeclaration> fields = getFields();

			String params = fields.stream()
				.map(f -> {
					String initialization = "";

					if (f.initializationValue instanceof String) {
						initialization = " = " + f.initializationValue;
					}

					return "this " + f.getFlatType() + ": " + f.getName() + initialization;
				})
				.collect(Collectors.joining(", "));

			constructor = Constructor.decodeStatement(
				this,
				"public construct(" + params + ")",
				Location.INVALID,
				true
			);

			addChild(constructor);
		}
	}

	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public DataClassDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		DataClassDeclaration node = new DataClassDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public DataClassDeclaration cloneTo(DataClassDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link DataClassDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public DataClassDeclaration cloneTo(DataClassDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link DataClassDeclaration} class type to make sure everything
	 * is working properly.
	 *
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
