package org.flatlang.tree;

import org.flatlang.TestContext;
import org.flatlang.ValidationResult;
import org.flatlang.error.SyntaxMessage;
import org.flatlang.tree.variables.FieldDeclaration;
import org.flatlang.util.Location;
import org.flatlang.util.StringUtils;
import org.flatlang.util.SyntaxUtils;

import java.util.List;
import java.util.stream.Collectors;

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

	private void addCopyFunction() {
		List<FieldDeclaration> fields = getFieldList().getPublicFieldList().getChildStream().map(c -> (FieldDeclaration)c).collect(Collectors.toList());

		String params = fields.stream()
			.map(f -> f.getFlatType() + ": " + f.getName() + " = " + f.getName())
			.collect(Collectors.joining(", "));

		BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this, "public copy(" + params + ") -> " + getFlatType(), Location.INVALID, true);

		if (func == null) {
			SyntaxMessage.error("Failed to create copy function for data class", this);
			return;
		}

		String args = fields.stream()
			.map(IIdentifier::getName)
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
		BodyMethodDeclaration objectFunc = BodyMethodDeclaration.decodeStatement(this, "override public equals(Object other)", Location.INVALID, true);

		if (objectFunc == null) {
			SyntaxMessage.error("Failed to create equals function override for data class", this);
			return;
		}

		objectFunc.shorthandAction = "other && " +
			"other.class.isOfType(" + getName() + ".class) && " +
			"equals((" + getName() + ")other)";

		addChild(objectFunc);

		BodyMethodDeclaration classFunc = BodyMethodDeclaration.decodeStatement(this, "public equals(" + getName() + " other)", Location.INVALID, true);

		if (classFunc == null) {
			SyntaxMessage.error("Failed to create equals function overload for data class", this);
			return;
		}

		List<FieldDeclaration> fields = getFieldList().getPublicFieldList().getChildStream().map(c -> (FieldDeclaration)c).collect(Collectors.toList());

		classFunc.shorthandAction = "other != null" +
			fields.stream()
				.map(f -> " && " + f.getName() + " == other." + f.getName())
				.collect(Collectors.joining(""));

		addChild(classFunc);
	}

	private void addToStringFunction() {
		BodyMethodDeclaration func = BodyMethodDeclaration.decodeStatement(this, "override public toString() -> String", Location.INVALID, true);

		if (func == null) {
			SyntaxMessage.error("Failed to create toString function override for data class", this);
			return;
		}

		List<FieldDeclaration> fields = getFieldList().getPublicFieldList().getChildStream().map(c -> (FieldDeclaration)c).collect(Collectors.toList());

		String values = fields.stream()
			.map(f -> "      \\\"" + f.getName() + "\\\": #{" + f.getName() + "}")
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
		List<FieldDeclaration> fields = getFieldList().getPublicFieldList().getChildStream().map(c -> (FieldDeclaration)c).collect(Collectors.toList());
		Constructor constructor = (Constructor) getMethod(this, getName(), fields.toArray(new FieldDeclaration[0]));

		if (constructor == null) {
			String params = fields.stream()
				.map(f -> f.getFlatType() + ": " + f.getName())
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
