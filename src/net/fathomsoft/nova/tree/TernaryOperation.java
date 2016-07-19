package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link IValue} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class TernaryOperation extends IValue
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public TernaryOperation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 3;
	}
	
	public Value getCondition()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 0);
	}
	
	public Value getTrueValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 1);
	}
	
	public Value getFalseValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 2);
	}
	
	/**
	 * Decode the given statement into a {@link TernaryOperation} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>expression ? trueValue : falseValue</li>
	 * 	<li>expression ?: ifExpressionNullValue</li>
	 * </ul>
	 *
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link TernaryOperation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link TernaryOperation}.
	 */
	public static TernaryOperation decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		int questionMarkIndex = SyntaxUtils.findCharInBaseScope(statement, '?');
		
		if (questionMarkIndex > 0)
		{
			int colonIndex = SyntaxUtils.findCharInBaseScope(statement, ':', questionMarkIndex + 1);
			
			TernaryOperation n = new TernaryOperation(parent, location);
			
			String conditionString = statement.substring(0, questionMarkIndex).trim();
			
			Value condition = SyntaxTree.decodeValue(n, conditionString, location, require);
			
			if (condition == null)
			{
				SyntaxMessage.queryError("Unable to decode ternary operation condition '" + conditionString + "'", n, require);
				
				return null;
			}
			
			String trueValueString = statement.substring(questionMarkIndex + 1, colonIndex).trim();
			
			Value trueValue = null;
			
			if (trueValueString.length() > 0)
			{
				trueValue = SyntaxTree.decodeValue(n, trueValueString, location, require);
			}
			else // elvis operator
			{
				conditionString = condition.generateNovaInput() + " != null";
				
				trueValue = condition;
				
				condition = SyntaxTree.decodeValue(n, conditionString, location, require);
				
				if (condition == null)
				{
					SyntaxMessage.queryError("Unable to decode elvis operation condition '" + conditionString + "'", n, require);
					
					return null;
				}
			}
			
			String falseValueString = statement.substring(colonIndex + 1).trim();
			
			Value falseValue = SyntaxTree.decodeValue(n, falseValueString, location, require);
			
			if (falseValue == null)
			{
				SyntaxMessage.queryError("Unable to decode ternary operation false value '" + conditionString + "'", n, require);
				
				return null;
			}
			
			n.addChild(condition);
			n.addChild(trueValue);
			n.addChild(falseValue);
			
			ClassDeclaration commonClass = trueValue.getReturnedNode().getTypeClass().getCommonAncestor(falseValue.getReturnedNode().getTypeClass());
			
			n.setType(trueValue.getReturnedNode());
			n.setTypeValue(commonClass.getType());
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return getCondition().generateNovaInput(builder).append(" ? ").append(getTrueValue().generateNovaInput()).append(" : ").append(getFalseValue().generateNovaInput());
	}
	
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		String trueValue = getTrueValue().generateCSourceFragment().toString();
		String falseValue = getFalseValue().generateCSourceFragment().toString();
		
		ClassDeclaration trueType = getTrueValue().getReturnedNode().getTypeClass();
		ClassDeclaration falseType = getFalseValue().getReturnedNode().getTypeClass();
		
		if (trueType != falseType)
		{
			ClassDeclaration commonClass = trueType.getCommonAncestor(getFalseValue().getReturnedNode().getTypeClass());
			
			if (trueType != commonClass)
			{
				trueValue = getFalseValue().getReturnedNode().generateCTypeCast() + trueValue;
			}
			else
			{
				falseValue = getTrueValue().getReturnedNode().generateCTypeCast() + falseValue;
			}
		}
		
		return getCondition().generateCSourceFragment(builder).append(" ? ").append(trueValue).append(" : ").append(falseValue);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public TernaryOperation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		TernaryOperation node = new TernaryOperation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public TernaryOperation cloneTo(TernaryOperation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link TernaryOperation} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TernaryOperation cloneTo(TernaryOperation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link TernaryOperation} class type to make sure everything
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