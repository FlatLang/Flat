package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ValueNode extension that represents a return statement node type.
 * See {@link #decodeStatement(TreeNode, String, Location)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:58:29 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
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
		StringBuilder builder = new StringBuilder();
		
		builder.append("return ");
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateJavaSource());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceFragment());
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("return");
		
		if (getChildren().size() > 0)
		{
			builder.append(' ');
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	public String generateNovaInput(boolean outputChildren)
	{
		return "return " + getChild(0).generateNovaInput(outputChildren);
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
	 * @return The generated node, if it was possible to translated it
	 * 		into a ReturnNode.
	 */
	public static ReturnNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		if (Regex.startsWith(statement, Patterns.PRE_RETURN))
		{
			ReturnNode n = new ReturnNode(parent, location);
			
			MethodNode method = (MethodNode)parent.getAncestorOfType(MethodNode.class, true);
			n.setType(method.getType());
			
			int returnStartIndex = Regex.indexOf(statement, Patterns.POST_RETURN);
			
			Location newLoc = new Location(location);
			newLoc.setBounds(location.getStart() + returnStartIndex, location.getStart() + statement.length());
			
			if (returnStartIndex >= 0)
			{
				statement = statement.substring(returnStartIndex);
				
				TreeNode child = TreeNode.decodeScopeContents(n, statement, newLoc, false);
				
				if (child == null)
				{
					SyntaxMessage.error("Could not decode return statement '" + statement + "'", n, newLoc);
				}
				
				n.addChild(child);
			}
			else
			{
				MethodNode parentMethod = (MethodNode)parent.getAncestorOfType(MethodNode.class);
				
				if (parentMethod.getType() != null)
				{
					SyntaxMessage.error("Method '" + parentMethod.getName() + "' must return a type of '" + parentMethod.getType() + "'", n, newLoc);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
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