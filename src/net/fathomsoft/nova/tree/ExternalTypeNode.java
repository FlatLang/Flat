package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents an external type of variable or
 * method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 8, 2014 at 6:55:51 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class ExternalTypeNode extends IdentifierNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ExternalTypeNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ';' + '\n';
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return super.generateChildrenCSourceFragment(false);
	}
	
	/**
	 * Decode the given statement into a ExternalTypeNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>externalHeaderName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ExternalTypeNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ExternalTypeNode.
	 */
	public static ExternalTypeNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		FileNode file = parent.getFileNode();
		
		if (file.isExternalImport(statement))
		{
			ExternalTypeNode n = new ExternalTypeNode(parent, location);
			
			n.setName(statement);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ExternalTypeNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ExternalTypeNode node = new ExternalTypeNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExternalTypeNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExternalTypeNode cloneTo(ExternalTypeNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}