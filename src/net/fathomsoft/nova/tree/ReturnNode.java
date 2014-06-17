package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ValueNode extension that represents a return statement node type.
 * See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:58:29 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ReturnNode extends ValueNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ReturnNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		String str = "return";
		
		ValueNode value = getReturnedNode();
		
		if (value != null)
		{
			str += " " + value.generateJavaSource();
		}
		
		return str;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ValueNode#getReturnedNode()
	 */
	public ValueNode getReturnedNode()
	{
		if (getNumChildren() <= 0)
		{
			return null;
		}
		
		return (ValueNode)getChild(0);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ";\n";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		String str = "return";
		
		ValueNode value = getReturnedNode();
		
		if (value != null)
		{
			str += " " + value.generateCSourceFragment();
		}
		
		return str;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	public String generateNovaInput(boolean outputChildren)
	{
		String str = "return";
		
		ValueNode value = getReturnedNode();
		
		if (value != null)
		{
			str += " " + value.generateNovaInput(outputChildren);
		}
		
		return str;
	}
	
	/**
	 * Decode the given statement into a ReturnNode instance, if
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
	 * 		ReturnNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ReturnNode.
	 */
	public static ReturnNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.startsWith(statement, Patterns.PRE_RETURN))
		{
			ReturnNode n = new ReturnNode(parent, location);
			
			MethodNode method = (MethodNode)parent.getAncestorOfType(MethodNode.class, true);
			n.setType(method.getType());
			
			int returnStartIndex = Regex.indexOf(statement, Patterns.POST_RETURN);
			
			Location newLoc = new Location(location);
			newLoc.setBounds(location.getStart() + returnStartIndex, location.getStart() + statement.length());
			
			MethodNode parentMethod = (MethodNode)parent.getAncestorOfType(MethodNode.class);
				
			if (returnStartIndex >= 0)
			{
				statement = statement.substring(returnStartIndex);
				
				ValueNode child = (ValueNode)SyntaxTree.decodeScopeContents(n, statement, newLoc, false);
				
				if (child == null)
				{
					SyntaxMessage.error("Could not decode return statement '" + statement + "'", n, newLoc);
				}
				
//				if (parentMethod.getType().equals(child.getType()))
//				{
//					SyntaxMessage.error("Method '" + parentMethod.getName() + "' must return a type of '" + parentMethod.getType() + "'", n, newLoc);
//				}
				
				n.addChild(child);
			}
			else if (parentMethod.getType() != null)
			{
				if (!require)
				{
					return null;
				}
				
				SyntaxMessage.error("Method '" + parentMethod.getName() + "' must return a type of '" + parentMethod.getType() + "'", n, newLoc);
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public ReturnNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ReturnNode node = new ReturnNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ReturnNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ReturnNode cloneTo(ReturnNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}