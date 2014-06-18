package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that keeps track of all of the arguments that
 * are passed during a method call. The children of this node are
 * all Argument instances. They are stored in the order that
 * they will be passed to the method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 10, 2014 at 3:12:54 AM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ArgumentList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ArgumentList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the MethodCall instance that contains the specified
	 * arguments.
	 * 
	 * @return The parent MethodCall instance.
	 */
	public MethodCall getMethodCall()
	{
		return (MethodCall)getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (i > 0)
			{
				builder.append(", ");
			}
			
			builder.append(child.generateNovaInput());
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder  builder = new StringBuilder();
		
		MethodCall caller  = getMethodCall();
		
		Method     method  = caller.getMethodDeclaration();
		
		if (!caller.isExternal())
		{
			if (method.needsReference())
			{
				if (method instanceof Destructor)
				{
					builder.append('&');
				}
				
				Identifier context = caller.getContextNode();
				
				builder.append(context.generateArgumentReference(getMethodCall())).append(", ");
			}
			
			builder.append(Exception.EXCEPTION_DATA_IDENTIFIER);
			
			if (getNumChildren() > 0)
			{
				builder.append(", ");
			}
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateCSourceFragment());
			
			if (i < getNumChildren() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ArgumentList clone(Node temporaryParent, Location locationIn)
	{
		ArgumentList node = new ArgumentList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArgumentList with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArgumentList cloneTo(ArgumentList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}