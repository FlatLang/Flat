package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.Location;

/**
 * {@link TypeList} extension that represents a generic type implementation.
 * Contains the information of a generic type implementation.
 * Contains all of the types that are being implemented into a generic
 * declaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.41 Dec 7, 2014 at 9:49:27 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class GenericTypeArgumentList extends TypeList<GenericTypeArgument>
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericTypeArgumentList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return generateNovaInput(builder, outputChildren, null);
	}
	
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren, Value context)
	{
		if (getNumVisibleChildren() > 0)
		{
			builder.append(GenericCompatible.GENERIC_START);
		}
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			getVisibleChild(i).generateNovaInput(builder, true, context);
		}
		
		if (getNumVisibleChildren() > 0)
		{
			builder.append(GenericCompatible.GENERIC_END);
		}
		
		return builder;
	}
	
	public Value[] getTypes()
	{
		Value[] types = new Value[getNumVisibleChildren()];
		
		for (int i = 0; i < types.length; i++)
		{
			types[i] = getVisibleChild(i);
		}
		
		return types;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			for (int i = 0; i < getNumVisibleChildren(); i++)
			{
				getVisibleChild(i).convertToPrimitiveType();
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeArgumentList clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		GenericTypeArgumentList node = new GenericTypeArgumentList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericTypeArgumentList cloneTo(GenericTypeArgumentList node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link GenericTypeArgumentList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericTypeArgumentList cloneTo(GenericTypeArgumentList node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericTypeArgumentList} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		
		for (GenericTypeArgument arg : this)
		{
			if (s.length() > 0)
			{
				s += ", ";
			}
			
			s += arg;
		}
		
		return s;
	}
}