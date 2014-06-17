package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents all of the external types that
 * were declared within a class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:29:22 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ExternalTypeList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ExternalTypeList(Node temporaryParent, Location locationIn)
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
	public ExternalType getType(String typeName)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			ExternalType type = (ExternalType)getChild(i);
			
			if (type.getType().equals(typeName))
			{
				return type;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ExternalTypeList clone(Node temporaryParent, Location locationIn)
	{
		ExternalTypeList node = new ExternalTypeList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExternalTypeNodeList with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExternalTypeList cloneTo(ExternalTypeList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
