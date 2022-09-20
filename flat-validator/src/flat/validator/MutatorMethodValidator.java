package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class MutatorMethodValidator extends PropertyMethodValidator
{
	public static final String DISABLED_IDENTIFIER = "no";
	public static final String IDENTIFIER = "set";
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public MutatorMethodValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public String getMethodPrefix()
	{
		return "Mutator";
	}
	
	/**
	 * Decode the given statement into a {@link MutatorMethodValidator} instance, if
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
	 * 		{@link MutatorMethodValidator} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link MutatorMethodValidator}.
	 */
	public static MutatorMethodValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, null);
	}
	
	public static MutatorMethodValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require, ValueValidator type)
	{
		type = type == null ? (ValueValidator)parent : type;
		
		if (StringUtils.findNextWord(statement).equals(DISABLED_IDENTIFIER))
		{
			String remainder = statement.substring(DISABLED_IDENTIFIER.length() + 1).trim();
			
			if (remainder.equals(IDENTIFIER))
			{
				MutatorMethodValidator n = new MutatorMethodValidator(parent, location);
				n.setName(n.getParentField().getName());
				n.setType(type.getType());
				n.setDisabled(true);
				
				return n;
			}
		}
		else if (StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			MutatorMethodValidator n = new MutatorMethodValidator(parent, location);

			n.setName(n.getParentField().getName());
			n.setType(type);
			n.setVisibility(n.getParentField().getVisibility());
			
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
				n.addDefaultParameter(type);
			}

			return n;
		}
		
		return null;
	}
	
	private void addDefaultParameter(ValueValidator type)
	{
		Parameter p = Parameter.decodeStatement(this, type.generateFlatType() + " " + PARAMETER_NAME, getLocationIn().asNew(), true);
		getParentField().cloneTo(p, true, false);
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
				Return returnValue = (Return)SyntaxTree.decodeScopeContents(this, "return " + getParameter(0).getName(), getLocationIn());
				
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
	public MutatorMethodValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		MutatorMethodValidator node = new MutatorMethodValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public MutatorMethodValidator cloneTo(MutatorMethodValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link MutatorMethodValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MutatorMethodValidator cloneTo(MutatorMethodValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link MutatorMethodValidator} class type to make sure everything
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