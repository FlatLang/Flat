package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extensions that contains all of the public FieldNode
 * instances of a ClassNode.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class InstanceFieldList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public InstanceFieldList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		if (getNumChildren() > 0)
		{
			ClassDeclaration parent = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class, true);
			
			if (parent.getMethodListNode().getNumChildren() > 0)
			{
				builder.append('\n');
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		ClassDeclaration clazz = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		
		ClassDeclaration extended = clazz.getExtendedClass();
		
		if (extended != null)
		{
			boolean publicList = this == clazz.getFieldListNode().getPublicFieldListNode();
			
			InstanceFieldList fields = null;
			
			if (publicList)
			{
				fields = extended.getFieldListNode().getPublicFieldListNode();
			}
			else
			{
				fields = extended.getFieldListNode().getPrivateFieldListNode();
			}
			
			builder.append(fields.generateCHeader());
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateCHeader());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		boolean hasMethods    = false;
		
		if (getNumChildren() > 0)
		{
			ClassDeclaration parent = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class, true);
			
			if (parent.getMethodListNode().getNumChildren() > 0)
			{
				hasMethods = true;
			}
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateCSource());
		}
		
		if (hasMethods)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public InstanceFieldList clone(Node temporaryParent, Location locationIn)
	{
		InstanceFieldList node = new InstanceFieldList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given PublicFieldListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InstanceFieldList cloneTo(InstanceFieldList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}