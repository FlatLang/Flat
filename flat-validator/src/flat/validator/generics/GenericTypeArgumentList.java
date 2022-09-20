package flat.validator.generics;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;
import flat.validator.NodeValidator;
import flat.validator.TypeList;

public class GenericTypeArgumentList extends TypeList<GenericTypeArgument>
{
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public GenericTypeArgumentList(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		return generateFlatInput(builder, outputChildren, null);
	}
	
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, Value context)
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
			
			getVisibleChild(i).generateFlatInput(builder, true, context);
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
	 * @see NodeValidator#validate(int)
	 */
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
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public GenericTypeArgumentList clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		GenericTypeArgumentList node = new GenericTypeArgumentList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
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