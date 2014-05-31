package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * ValueNode extension that represents a literal within the code. For
 * example, a number literal and a String literal.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:34:30 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class LiteralNode extends ValueNode
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
	public void setValue(String value)
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
	 * Decode the given statement into a LiteralNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>123</li>
	 * 	<li>-321</li>
	 * 	<li>121.32</li>
	 * 	<li>'a'</li>
	 * 	<li>'\''</li>
	 * 	<li>"Text String"</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LiteralNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LiteralNode.
	 */
	public static LiteralNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		String literalType = SyntaxUtils.getLiteralTypeName(statement);
		
		if (literalType != null || parent.isWithinExternalContext())
		{
			LiteralNode n = new LiteralNode(parent, location);
			n.setValue(statement);
			n.setType(literalType);
			
			return n;
		}
		
		return null;
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