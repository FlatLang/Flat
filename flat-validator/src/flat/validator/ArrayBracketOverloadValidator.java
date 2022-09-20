package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.StringUtils;

public class ArrayBracketOverloadValidator extends IValueValidator implements ShorthandAccessible
{
	private boolean twoWayBinding, decoded;
	
	private String accessorValue;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public ArrayBracketOverloadValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean alreadyDecoded()
	{
		return decoded;
	}
	
	@Override
	public boolean isTwoWayBinding()
	{
		return twoWayBinding;
	}
	
	@Override
	public void setTwoWayBinding(boolean twoWayBinding)
	{
		this.twoWayBinding = twoWayBinding;
	}
	
	@Override
	public String getShorthandAccessor()
	{
		return accessorValue;
	}
	
	@Override
	public void setShorthandAccessor(String shorthand)
	{
		this.accessorValue = shorthand;
	}
	
	public LocalDeclarationValidator getIndexValue()
	{
		return (LocalDeclarationValidator)getChild(0);
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	@Override
	public void addChild(NodeValidator nodeValidator)
	{
		if (getNumChildren() == getNumDefaultChildren())
		{
			getParentClass().addChild(nodeValidator);
		}
		else
		{
			super.addChild(nodeValidator);
		}
	}
	
	/**
	 * Decode the given statement into a {@link ArrayBracketOverloadValidator} instance, if
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
	 * 		{@link ArrayBracketOverloadValidator} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ArrayBracketOverloadValidator}.
	 */
	public static ArrayBracketOverloadValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		ArrayBracketOverloadValidator n = new ArrayBracketOverloadValidator(parent, location);
		
		statement = n.parseModifiers(statement);
		
		if (statement.startsWith("this["))
		{
			int end = StringUtils.findEndingMatch(statement, "this".length(), '[', ']');
			
			if (end > 1)
			{
				String original = statement;
				String contents = statement.substring("this[".length(), end).trim();
				
				LocalDeclarationValidator indexValue = LocalDeclarationValidator.decodeStatement(n, contents, location.asNew(), require);
				
				n.addChild(indexValue);
				
				int index = n.getShorthandAccessorIndex(statement);
				
				if (index > 0)
				{
					statement = statement.substring(0, index - (n.twoWayBinding ? 1 : 0)).trim();
				}
				
				String rest = statement.substring(end + 1).trim();
				
				if (rest.length() > 0)
				{
					if (!rest.startsWith("->"))
					{
						SyntaxMessage.error("Invalid array bracket overload '" + original + "'", n);
					}
					
					String type = rest.substring(2).trim();
					
					n.setType(type);
				}
				
				return n;
			}
		}
		
		return null;
	}
	
	@Override
	public FlatMethodDeclarationValidator addDefaultAccessor()
	{
		ArrayAccessorMethodValidator method = ArrayAccessorMethodValidator.decodeStatement(this, "get", getLocationIn(), true);
		
		Return returned = Return.decodeStatement(method, "return null", getLocationIn(), true);
		
		returned.getValueNode().replaceWith(generateDefaultValue(returned, getLocationIn()));
		
		method.addChild(returned);
		
		addChild(method);
		
		return method;
	}
	
	@Override
	public FlatMethodDeclarationValidator addDefaultMutator()
	{
		ArrayMutatorMethodValidator method = ArrayMutatorMethodValidator.decodeStatement(this, "set", getLocationIn(), true);
		
		addChild(method);
		
		return method;
	}
	
	@Override
	public void addDisabledAccessor()
	{
		ArrayAccessorMethodValidator method = ArrayAccessorMethodValidator.decodeStatement(this, "no get", getLocationIn(), true);
		
		ValueValidator type = getArrayAccessorMethod();
		
		method.setType(type);
		
		addChild(method);
	}
	
	@Override
	public void addDisabledMutator()
	{
		ArrayMutatorMethodValidator method = ArrayMutatorMethodValidator.decodeStatement(this, "no set", getLocationIn(), true);
		
		ValueValidator type = getArrayAccessorMethod();
		
		method.setType(type);
		method.getParameter(1).setType(type);
		
		addChild(method);
	}
	
	@Override
	public boolean containsAccessorMethod()
	{
		return getArrayAccessorMethod() != null;
	}
	
	public ArrayAccessorMethodValidator getArrayAccessorMethod()
	{
		return getParentClass().getArrayAccessorMethod();
	}
	
	@Override
	public boolean containsMutatorMethod()
	{
		return getArrayMutatorMethod() != null;
	}
	
	public ArrayMutatorMethodValidator getArrayMutatorMethod()
	{
		return getParentClass().getArrayMutatorMethod();
	}
	
	@Override
	public BodyMethodDeclarationValidator decodeShorthandAccessor()
	{
		ArrayAccessorMethodValidator method = ArrayAccessorMethodValidator.decodeStatement(this, "get", getLocationIn(), true);
		
		ValueValidator type = SyntaxTree.decodeValue(method, accessorValue, getLocationIn(), true).getReturnedNode();
		type.onAfterDecoded();
		method.setType(type);
		
		decoded = true;
		
		return method;
	}
	
	@Override
	public BodyMethodDeclarationValidator decodeShorthandMutator(ValueValidator context)
	{
		ArrayMutatorMethodValidator method = ArrayMutatorMethodValidator.decodeStatement(this, "set", getLocationIn(), true);
		
		ValueValidator type = getArrayAccessorMethod();
		
		method.setType(type);
		method.getParameter(1).setType(type);
		
		return method;
	}
	
	/**
	 * @see VariableDeclaration#validate(int)
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			decodeArrowBinding();
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public ArrayBracketOverloadValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ArrayBracketOverloadValidator node = new ArrayBracketOverloadValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public ArrayBracketOverloadValidator cloneTo(ArrayBracketOverloadValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ArrayBracketOverloadValidator} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArrayBracketOverloadValidator cloneTo(ArrayBracketOverloadValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.twoWayBinding = twoWayBinding;
		node.decoded = decoded;
		node.accessorValue = accessorValue;
		
		return node;
	}

	@Override
	public String toString() {
		return "this[" + getIndexValue().generateFlatInput() + "] => " + accessorValue;
	}

	/**
	 * Test the {@link ArrayBracketOverloadValidator} class type to make sure everything
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