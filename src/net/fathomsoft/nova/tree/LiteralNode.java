package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * TreeNode extension that represents a literal within the code. For
 * example, a number literal and a String literal.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:34:30 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class LiteralNode extends TreeNode
{
	private String	value;
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public LiteralNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the value of the literal.
	 * 
	 * @return The value of the literal.
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * Set the value of the literal. If the literal's value is set within
	 * an external context, the value will be generated in a c syntax sort
	 * of way.
	 * 
	 * @param value The value to set the literal as.
	 * @param external Whether or not the literal is within an external
	 * 		context.
	 */
	public void setValue(String value, boolean external)
	{
//		if (!external && SyntaxUtils.isStringLiteral(value))
//		{
//			value = Nova.LANGUAGE_NAME.toLowerCase() + "_String_String(" + ExceptionNode.EXCEPTION_DATA_IDENTIFIER + ", " + value + ")";
//		}
		
		this.value = value;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput()
	 */
	@Override
	public String generateNovaInput()
	{
		if (isStringInstantiation())
		{
			return "new String(" + value + ")";
		}
		
		return value;
	}
	
	/**
	 * Get whether or not the value of the literal is an
	 * instantiation of a String from a String constructor.
	 * 
	 * @return Whether or not the value of the literal is an
	 * 		instantiation of a String from a String constructor.
	 */
	private boolean isStringInstantiation()
	{
		if (SyntaxUtils.isStringLiteral(value))
		{
			if (getParent() instanceof ArgumentListNode)
			{
				MethodCallNode node = (MethodCallNode)getParent().getParent();
				
				if (node.getName().equals("String"))
				{
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return value;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (!isWithinExternalContext() && isStringInstantiation())
		{
			return Nova.LANGUAGE_NAME.toLowerCase() + "_String_String(" + ExceptionNode.EXCEPTION_DATA_IDENTIFIER + ", " + value + ")";
		}
		
		return value;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public LiteralNode clone(TreeNode temporaryParent, Location locationIn)
	{
		LiteralNode node = new LiteralNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LiteralNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LiteralNode cloneTo(LiteralNode node)
	{
		super.cloneTo(node);
		
		node.value = value;
		
		return node;
	}
}