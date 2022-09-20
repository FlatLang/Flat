package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DataClassDeclarationValidator extends ClassDeclarationValidator
{
	public static final String IDENTIFIER = "data";
	private boolean addedDataClassFunctionality;

	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public DataClassDeclarationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link DataClassDeclarationValidator} instance, if
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
	 * 		{@link DataClassDeclarationValidator} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link DataClassDeclarationValidator}.
	 */
	public static DataClassDeclarationValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		int index = SyntaxUtils.findStringInBaseScope(statement, IDENTIFIER);
		
		if (index >= 0 && StringUtils.findNextWhitespaceIndex(statement, index + IDENTIFIER.length()) == index + IDENTIFIER.length())
		{
			statement = statement.substring(0, index) + statement.substring(index + IDENTIFIER.length() + 1);
			
			ClassData data = new ClassData(false, false, true);
			
			ClassDeclarationValidator clazz = decodeStatement(parent, statement, location, require, data);
			
			if (clazz != null)
			{
				DataClassDeclarationValidator n = new DataClassDeclarationValidator(parent, location);
				
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

	public ValidationResult validate(int phase) {
		ValidationResult result = super.validate(phase);

		if (!result.continueValidation) {
			return result;
		}

		checkAddDataClassFunctionality(phase);

		return result;
	}

	private void checkAddDataClassFunctionality(int phase) {
		if (phase >= SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
			if (!addedDataClassFunctionality) {
				addedDataClassFunctionality = true;
				validateDefaultConstructor();
				addCopyFunction();
				addEqualsFunctions();
				addToStringFunction();
			}
		}
	}

	private List<FieldDeclaration> getFields() {
		return getFieldList()
			.getPublicFieldList()
			.getChildStream()
			.map(c -> (FieldDeclaration)c)
			.filter(f -> f.getShorthandAccessor() == null)
			.collect(Collectors.toList());
	}

	private void addCopyFunction() {
		List<FieldDeclaration> fields = getFields();

		String params = fields.stream()
			.map(f -> f.getFlatType() + ": " + f.getName() + " = " + f.getName())
			.collect(Collectors.joining(", "));

		BodyMethodDeclarationValidator func = BodyMethodDeclarationValidator.decodeStatement(this, "public copy(" + params + ") -> " + getFlatType(), Location.INVALID, true);

		if (func == null) {
			SyntaxMessage.error("Failed to create copy function for data class", this);
			return;
		}

		String args = fields.stream()
			.map(IIdentifierValidator::getName)
			.collect(Collectors.joining(", "));

		ValueValidator returnValue = Instantiation.decodeStatement(
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
		BodyMethodDeclarationValidator objectFunc = BodyMethodDeclarationValidator.decodeStatement(this, "override public equals(Object other)", Location.INVALID, true);

		if (objectFunc == null) {
			SyntaxMessage.error("Failed to create equals function override for data class", this);
			return;
		}

		objectFunc.shorthandAction = "other && " +
			"other.class.isOfType(" + getName() + ".class) && " +
			"equals((" + getName() + ")other)";

		addChild(objectFunc);

		BodyMethodDeclarationValidator classFunc = BodyMethodDeclarationValidator.decodeStatement(this, "public equals(" + getName() + " other)", Location.INVALID, true);

		if (classFunc == null) {
			SyntaxMessage.error("Failed to create equals function overload for data class", this);
			return;
		}

		List<FieldDeclaration> fields = getFields();

		classFunc.shorthandAction = "other != null" +
			fields.stream()
				.map(f -> " && " + f.getName() + " == other." + f.getName())
				.collect(Collectors.joining(""));

		addChild(classFunc);
	}

	private void addToStringFunction() {
		BodyMethodDeclarationValidator func = BodyMethodDeclarationValidator.decodeStatement(this, "override public toString() -> String", Location.INVALID, true);

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

	private void validateDefaultConstructor() {
		List<FieldDeclaration> fields = getFields();
		Constructor constructor = (Constructor) getMethod(this, getName(), fields.toArray(new FieldDeclaration[0]));

		if (constructor == null) {
			String params = fields.stream()
				.map(f -> "this " + f.getFlatType() + ": " + f.getName())
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
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public DataClassDeclarationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		DataClassDeclarationValidator node = new DataClassDeclarationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public DataClassDeclarationValidator cloneTo(DataClassDeclarationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link DataClassDeclarationValidator} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public DataClassDeclarationValidator cloneTo(DataClassDeclarationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link DataClassDeclarationValidator} class type to make sure everything
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
