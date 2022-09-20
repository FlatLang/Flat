package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class Until extends IfStatement
{
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public Until(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see IfStatement#pendingScopeFragment(NodeValidator)
	 */
	@Override
	public boolean pendingScopeFragment(NodeValidator nodeValidator)
	{
		return false;
	}
	
	/**
	 * Decode the given statement into a Until instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>until (currentNode != null)</li>
	 * 	<li>until (ready)</li>
	 * 	<li>until (number.isEven())</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Until instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Until.
	 */
	public static Until decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		if (StringUtils.findNextWord(statement, 0).equals("until"))
		{
			Until n = new Until(parent, location);
			
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			if (bounds.isValid())
			{
				Location newLoc = location.asNew();
				newLoc.addBounds(bounds.getStart(), bounds.getEnd());
				
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				if (n.decodeCondition(contents, newLoc))
				{
					return n;
				}
			}
			else
			{
				SyntaxMessage.error("Until statement missing condition", n);
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the given condition of the until statement.
	 * 
	 * @param contents The condition expression to decode.
	 * @param location The location of the condition in the source code.
	 * @return Whether or not the condition decoded successfully.
	 */
	private boolean decodeCondition(String contents, Location location)
	{
		contents = "!(" + contents + ")";
		
		ValueValidator condition = UnaryOperation.decodeStatement(this, contents, location, true);//BinaryOperation.decodeStatement(getParent(), contents, location, true);
		
		if (condition == null)
		{
			condition = SyntaxTree.getUsableExistingNode(this, contents, location);
			
			if (condition == null)
			{
				condition = Literal.decodeStatement(this, contents, location, true, true);
				
				if (condition == null)
				{
					return false;
				}
			}
		}
		
		addChild(condition, this);
		
		if (!"Bool".equals(condition.getReturnedNode().getType()))
		{
			condition.replaceWithNullCheck();
		}
		
		return true;
	}
	
	/**
	 * Actually set up the until statement structure.
	 * 
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
			String      statement = "if (" + getCondition().generateFlatInput().toString() + ")";
			
			IfStatement clonable  = IfStatement.decodeStatement(getScope(), statement, getLocationIn(), true);
			IfStatement clone     = null;
			
			Scope scope = getScope();
			
			for (int i = scope.getNumVisibleChildren() - 1; i >= 0; i--)
			{
				IfStatement newClone = (IfStatement)clonable.clone(getScope(), getLocationIn());
				
				NodeValidator nodeValidator = scope.getVisibleChild(i);
				
				newClone.addChild(nodeValidator);
				
				if (clone != null)
				{
					newClone.addChild(clone);
				}
				
				clone = newClone;
			}
			
			if (clone != null)
			{
				scope.addChild(clone);
				
				getParent().replace(this, clone);
				
				result.returnedNodeValidator = clone;
			}
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public Until clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Until node = new Until(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public Until cloneTo(Until node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Until} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Until cloneTo(Until node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the Until class type to make sure everything
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