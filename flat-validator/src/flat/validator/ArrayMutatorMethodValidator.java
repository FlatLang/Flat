package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class ArrayMutatorMethodValidator extends ArrayOverloadMethodValidator
{
	public static final String DISABLED_IDENTIFIER = "no";
	public static final String IDENTIFIER = "set";
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public ArrayMutatorMethodValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link ArrayMutatorMethodValidator} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>no set</li>
	 * 	<li>set</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link ArrayMutatorMethodValidator} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ArrayMutatorMethodValidator}.
	 */
	public static ArrayMutatorMethodValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		if (StringUtils.findNextWord(statement).equals(DISABLED_IDENTIFIER))
		{
			String remainder = statement.substring(DISABLED_IDENTIFIER.length() + 1).trim();
			
			if (remainder.equals(IDENTIFIER))
			{
				ArrayMutatorMethodValidator n = new ArrayMutatorMethodValidator(parent, location);
				
				n.setName(IDENTIFIER);
				n.getArrayBracketOverload().cloneTo(n, false, true);
				n.setLocationIn(location);
				n.setType(n.getArrayBracketOverload());
				n.setDisabled(true);
				
				n.addIndexParameter();
				n.addDefaultParameter();
				
				return n;
			}
		}
		else if (StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			ArrayMutatorMethodValidator n = new ArrayMutatorMethodValidator(parent, location);

			n.setName(IDENTIFIER);
			n.getArrayBracketOverload().cloneTo(n, false, true);
			n.setLocationIn(location);
			n.setType(n.getArrayBracketOverload());
			
			n.addIndexParameter();
			
			if (StringUtils.findNextNonWhitespaceChar(statement, IDENTIFIER.length()) == '(')
			{
				String parameterList = SyntaxUtils.findInnerParenthesesBounds(n, statement).extractString(statement);
				
				if (!n.decodeParameters(parameterList, require))
				{
					return null;
				}
			}
			else
			{
				n.addDefaultParameter();
			}

			return n;
		}
		
		return null;
	}
	
	private void addDefaultParameter()
	{
		Parameter p = Parameter.decodeStatement(this, getArrayBracketOverload().generateFlatType() + " " + PARAMETER_NAME, getLocationIn().asNew(), true);
		getArrayBracketOverload().cloneTo(p, false, false);
		p.setName(PARAMETER_NAME);
		
		getParameterList().addChild(p);
	}
	
	/**
	 * @see NodeValidator#validate(int)
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (!isDisabled())
			{
				Return returnValue = (Return)SyntaxTree.decodeScopeContents(this, "return " + getParameter(1).getName(), getLocationIn());
				
				if (returnValue == null)
				{
					SyntaxMessage.error("Could not decode implicit return statement for mutator method", this);
				}
				
				addChild(returnValue);
			}
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public ArrayMutatorMethodValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ArrayMutatorMethodValidator node = new ArrayMutatorMethodValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public ArrayMutatorMethodValidator cloneTo(ArrayMutatorMethodValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ArrayMutatorMethodValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArrayMutatorMethodValidator cloneTo(ArrayMutatorMethodValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link ArrayMutatorMethodValidator} class type to make sure everything
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