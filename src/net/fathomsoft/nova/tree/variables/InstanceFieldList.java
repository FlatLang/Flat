package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Node extensions that contains all of the public Field
 * instances of a ClassDeclaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
 */
public class InstanceFieldList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InstanceFieldList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		ClassDeclaration clazz = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		
		ClassDeclaration extended = clazz.getExtendedClass();
		
		if (extended != null)
		{
			boolean publicList = this == clazz.getFieldList().getPublicFieldList();
			
			InstanceFieldList fields = null;
			
			if (publicList)
			{
				fields = extended.getFieldList().getPublicFieldList();
			}
			else
			{
				fields = extended.getFieldList().getPrivateFieldList();
			}
			
			fields.generateCHeader(builder);
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateCHeader(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		boolean hasMethods    = false;
		
		if (getNumChildren() > 0)
		{
			ClassDeclaration parent = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class, true);
			
			if (parent.getMethodList().getNumChildren() > 0)
			{
				hasMethods = true;
			}
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateCSource(builder);
		}
		
		if (hasMethods)
		{
			builder.append('\n');
		}
		
		return builder;
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
	 * Fill the given PublicFieldList with the data that is in the
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