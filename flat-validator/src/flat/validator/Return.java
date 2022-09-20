package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.lambda.LambdaExpression;
import flat.tree.variables.Variable;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class Return extends IValueValidator
{
	public static final String IDENTIFIER = "return";
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public Return(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentListValidator returnValues = new ArgumentListValidator(this, locationIn.asNew());
		
		addChild(returnValues);
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	public ArgumentListValidator getReturnValues()
	{
		return (ArgumentListValidator)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public int getNumReturnValues()
	{
		return getReturnValues().getNumVisibleChildren();
	}
	
	public boolean containsMultipleReturnValues()
	{
		return getNumReturnValues() > 1;
	}
	
	@Override
	public ValueValidator getReturnedNode()
	{
		return getValueNode().getReturnedNode();
	}
	
	public ValueValidator[] getTypes()
	{
		return getReturnValues().getTypes();
	}
	
	/**
	 * Get the Value that the return statement returns.
	 * 
	 * @return The Value Node that the return statement returns.
	 */
	public ValueValidator getValueNode()
	{
		if (getNumReturnValues() <= 0)
		{
			return null;
		}
		if (getReturnValues().getVisibleChild(0) instanceof ValueValidator == false) {
			return null;
		}
		
		return (ValueValidator)getReturnValues().getVisibleChild(0);
	}
	
	/**
	 * @see NodeValidator#generateFlatInput(StringBuilder, boolean)
	 */
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append("return");
		
		if (getValueNode() != null)
		{
			builder.append(' ').append(getValueNode().generateFlatInput(outputChildren));
		}
		
		return builder;
	}
	
	@Override
	public NodeValidator getDecodedParent()
	{
		if (getValueNode() == null) {
			return this;
		}

		return getReturnedNode();
	}
	
	/**
	 * Decode the given statement into a Return instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>return</li>
	 * 	<li>return node</li>
	 * 	<li>return 0</li>
	 * 	<li>return getAge()</li>
	 * 	<li>return age + 32</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Return instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Return.
	 */
	public static Return decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true);
	}
	
	public static Return decodeStatement(NodeValidator parent, String statement, Location location, boolean require, boolean validateType)
	{
		if (StringUtils.startsWithWord(statement, IDENTIFIER))
		{
			Return n = new Return(parent, location);
			
			if (n.decodeReturnValue(statement, location, require, validateType))
			{
				return n;
			}
		}

		return null;
	}
	
	/**
	 * Decode the return value of a return statement, if the return
	 * statement returns a value.<br>
	 * <br>
	 * The value of a return statement looks like the following:
	 * <blockquote><pre>
	 * return node</pre></blockquote>
	 * "<u><code>node</code></u>" is the value in the above return
	 * statement.
	 * 
	 * @param statement The statement to decode the return value from.
	 * @param location The location of the return statement in the source
	 * 		code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the return value decoded correctly.
	 */
	private boolean decodeReturnValue(String statement, Location location, boolean require)
	{
		return decodeReturnValue(statement, location, require, true);
	}
	
	private boolean decodeReturnValue(String statement, Location location, boolean require, boolean validateType)
	{
		String postReturn = generatePostReturn(statement);
		
		if (postReturn != null)
		{
			String values[] = StringUtils.splitCommas(postReturn, 2);
			
			Location newLoc = location.asNew();
			newLoc.addBounds(statement.indexOf(postReturn), statement.length());
			
			for (String v : values)
			{
				ValueValidator value = decodeReturnValue(v, getParentMethod(), newLoc, validateType);
				
				if (value instanceof AccessibleValidator && ((AccessibleValidator)value).canAccess() && getParentMethod().getImmutableAnnotation() != null)
				{
					getParentMethod().getImmutableAnnotation().convertAssignment(value);
				}
				
				getReturnValues().addChild(value);
			}
		}
		else if (getParentMethod().isFunctionType())
		{
			getReturnValues().addChild(LambdaExpression.decodeStatement(this, "{", location, require));
		}
		else if (getParentMethod().getType() != null)
		{
			return queryReturnError(getParentMethod(), require);
		}
		
		//setType(getParentMethod().getType());
		
		return true;
	}
	
	/**
	 * Decode the return value of a return statement.<br>
	 * <br>
	 * The value of a return statement looks like the following:
	 * <blockquote><pre>
	 * return node</pre></blockquote>
	 * "<u><code>node</code></u>" is the value in the above return
	 * statement.
	 * 
	 * @param statement The statement containing the return value.
	 * @param method The method that the return statement is returning
	 * 		from.
	 * @param location The location of the return statement value in the
	 * 		source code.
	 * @return The Value representing the return value.
	 */
	private ValueValidator decodeReturnValue(String statement, MethodDeclarationValidator method, Location location)
	{
		return decodeReturnValue(statement, method, location, true);
	}
	
	private ValueValidator decodeReturnValue(String statement, MethodDeclarationValidator method, Location location, boolean validateType)
	{
		ValueValidator value = SyntaxTree.decodeValue(this, statement, location, false);
		
		if (value == null)
		{
			SyntaxTree.decodeValue(this, statement, location, false);
			SyntaxMessage.error("Could not decode return statement '" + statement + "'", this, location);
		}
		else
		{
			boolean skipCompatible = false;
			
			if (value.getReturnedNode() instanceof MethodCall)
			{
				FlatMethodDeclarationValidator m = ((MethodCall)value.getReturnedNode()).getFlatMethod();
				
				if (m != null && m.usedShorthandAction)
				{
					skipCompatible = true;
				}
			}
			else if (value.getReturnedNode() instanceof Closure)
			{
				((Closure)value.getReturnedNode()).findDeclaration();
				
				skipCompatible = true;
			}
			else if (value.getReturnedNode().isFunctionType() && method.isFunctionType())
			{
				skipCompatible = true;
			}
			else if (value.getReturnedNode() instanceof ControlStatement)
			{
				skipCompatible = true;
			}
			
			if (!skipCompatible && validateType && !SyntaxUtils.validateCompatibleTypes(method, value.getReturnedNode()))
			{
				SyntaxUtils.validateCompatibleTypes(method, value.getReturnedNode());
				queryReturnError(method, true);
			}
		}
		
		return checkPrimitiveType(value);
	}
	
	private ValueValidator checkPrimitiveType(ValueValidator node)
	{
		if (!getParentMethod().isFunctionType())
		{
			if (!node.getReturnedNode().isPrimitive() && getParentMethod().isPrimitive() && !Literal.isNullLiteral(node))
			{
				return SyntaxUtils.unboxPrimitive(node);
			}
			if (node.getReturnedNode().isPrimitive() && !getParentMethod().isPrimitive() && !Literal.isNullLiteral(node))
			{
				return SyntaxUtils.autoboxPrimitive(node, node.getReturnedNode().getType());
			}
		}
		
		return node;
	}
	
	public void convertPrimitiveType()
	{
		ValueValidator old = getValueNode();
		NodeValidator oldParent = old != null ? old.parent : null;
		
		if (old != null)
		{
			ValueValidator v = checkPrimitiveType(old);
			
			if (!oldParent.containsChild(v, false))
			{
				old.replaceWith(v);
			}
		}
	}
	
	private boolean queryReturnError(MethodDeclarationValidator method, boolean require)
	{
		String expected = method.getType();
		String name     = method.getName();
		
		if (expected == null)
		{
			return SyntaxMessage.queryError("Method '" + name + "' does not have a return type, and therefore cannot return a value", this, require);
		}
		else
		{
			return SyntaxMessage.queryError("Method '" + name + "' must return a type of '" + expected + "'", this, require);
		}
	}
	
	/**
	 * Get a String representing the return value, if the return
	 * statement returns a value. If it does not, it returns null.<br>
	 * <br>
	 * The value of a return statement looks like the following:
	 * <blockquote><pre>
	 * return node</pre></blockquote>
	 * "<u><code>node</code></u>" is the value in the above return
	 * statement.
	 * 
	 * @param statement The statement to generate the String from.
	 * @return The String representing the return value. If the return
	 * 		statement does not return a value, then null.
	 */
	private static String generatePostReturn(String statement)
	{
		int index = StringUtils.findNextNonWhitespaceIndex(statement, 7);
		
		if (index < 0)
		{
			return null;
		}
		
		return statement.substring(index);
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		convertPrimitiveType();
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (containsMultipleReturnValues())
			{
				GenericCompatible[] contexts = new GenericCompatible[getTypes().length];
				
				for (int i = 0; i < contexts.length; i++)
				{
					contexts[i] = getParentMethod();
				}
				
				if (!SyntaxUtils.areTypesCompatible(contexts, getParentMethod().getTypes(), getTypes()))
				{
					SyntaxMessage.error("Invalid return values", this, false);
					
					return result.errorOccurred(this);
				}
				
				Variable temp = getAncestorWithScope().getScope().registerLocalVariable(getValueNode());
				getReturnValues().replace(getValueNode(), temp);
				
				for (int i = 1; i < getNumReturnValues(); i++)
				{
					Parameter param  = (Parameter)getParentMethod().getParameterList().getReturnTypes()[i - 1];
					NodeValidator retVal = getReturnValues().getVisibleChild(i);
					
					String statement = param.generateFlatType() + " " + getParentMethod().generateTemporaryVariableName() + " = " + retVal.generateFlatInput();
					
					AssignmentValidator assignmentValidator = AssignmentValidator.decodeStatement(getAncestorWithScope(), statement, getLocationIn().asNew(), true, false);
					
					assignmentValidator.setAssigneeNode(param.generateUsableVariable(this, getLocationIn().asNew()));
					
					getParent().addChildBefore(this, assignmentValidator);
				}
				
				result.nextIncrement += getNumReturnValues();
			}

			if (getPreviousNode() instanceof Return) {
				detach();
				result.skipCycle = true;
			}
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public Return clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Return node = new Return(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public Return cloneTo(Return node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Return} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Return cloneTo(Return node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the Return class type to make sure everything
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