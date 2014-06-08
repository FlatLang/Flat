package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents all of the external types that
 * were declared within a class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:29:22 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class ExternalTypeNodeList extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ExternalTypeNodeList(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the ClassNode contains an ExternalTypeNode with
	 * the specified type name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	external type FILE;
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getType("FILE")</code>" would
	 * return the ExternalTypeNode for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return Whether or not the ClassNode contains the MethodNode with
	 * 		the specified name.
	 */
	public boolean containsType(String typeName)
	{
		return getType(typeName) != null;
	}
	
	/**
	 * Get the ClassNode's ExternalTypeNode with the specified type.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	external type FILE;
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getType("FILE")</code>" would
	 * return the ExternalTypeNode for the "<code>FILE</code>" external
	 * type.
	 * 
	 * @param typeName The name of the external type to search for.
	 * @return The ExternalTypeNode for the external type, if it exists.
	 */
	public ExternalTypeNode getType(String typeName)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			ExternalTypeNode type = (ExternalTypeNode)getChild(i);
			
			if (type.getType().equals(typeName))
			{
				return type;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ExternalTypeNodeList clone(TreeNode temporaryParent, Location locationIn)
	{
		ExternalTypeNodeList node = new ExternalTypeNodeList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExternalTypeNodeList with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExternalTypeNodeList cloneTo(ExternalTypeNodeList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
