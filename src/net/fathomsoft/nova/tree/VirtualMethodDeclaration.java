package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link BodyMethodDeclaration} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class VirtualMethodDeclaration extends BodyMethodDeclaration
{
	public NovaMethodDeclaration base;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VirtualMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public void searchVirtualMethodDeclaration()
	{
		
	}
	
	@Override
	public VirtualMethodDeclaration getVirtualMethod()
	{
		return this;
	}
	
	@Override
	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateCVirtualMethodName(builder);
	}
	
	/**
	 * Get the identifier for the virtual abstract method in the vtable.
	 *
	 * @return The identifier for the virtual method in the vtable.
	 */
	public StringBuilder generateCVirtualMethodName()
	{
		return generateCVirtualMethodName(new StringBuilder());
	}
	
	/**
	 * Get the identifier for the virtual abstract method in the vtable.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The identifier for the virtual method in the vtable.
	 */
	public StringBuilder generateCVirtualMethodName(StringBuilder builder)
	{
		String prefix = "virtual";
		
		if (base instanceof PropertyMethod)
		{
			prefix += "_" + ((PropertyMethod)base).getMethodPrefix();
		}
		
		return generateCSourceName(builder, prefix, true);
	}
	
	@Override
	public NovaParameterList getParameterList()
	{
		return base.getParameterList();
	}
	
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		generateCSourceSignature(builder);
		
		/*
		if (getType() == null)
		{
			builder.append("{}");
		}
		else
		{
			builder.append("{return 0;}");
		}
		*/
		
		builder.append("\n{\n");
		
		if (getType() != null)
		{
			builder.append("return ");
		}
		
		super.getParameterList().getObjectReference().generateCSourceFragment(builder).append("->");
		
		builder.append(VTable.IDENTIFIER).append("->");
		
		if (getParentClass() instanceof Interface)
		{
			builder.append(InterfaceVTable.IDENTIFIER).append(".");
		}
		
		String call = getName() + "(";
		
		for (int i = 0; i < getParameterList().getNumVisibleChildren(); i++)
		{
			if (i > 0)
			{
				call += ", ";
			}
			
			call += getParameterList().getVisibleChild(i).getName();
		}
		
		call += ")";
		
		MethodCall output = MethodCall.decodeStatement(getScope(), call, getLocationIn().asNew(), true, true, this);
		
		generateCVirtualMethodName(builder);
		output.getArgumentList().generateCSourceFragment(builder);
		
		return builder.append(";\n}\n");
	}
	
	@Override
	public void setOverloadIDs(MethodDeclaration[] methods)
	{
		
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public VirtualMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		VirtualMethodDeclaration node = new VirtualMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public VirtualMethodDeclaration cloneTo(VirtualMethodDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link VirtualMethodDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VirtualMethodDeclaration cloneTo(VirtualMethodDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link VirtualMethodDeclaration} class type to make sure everything
	 * is working properly.
	 *
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}