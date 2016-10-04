package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ClosureContext extends TypeList<ClosureVariableDeclaration>
{
	public int id;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClosureContext(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public VariableDeclaration addDeclaration(VariableDeclaration declaration)
	{
		for (ClosureVariableDeclaration decl : this)
		{
			if (decl.originalDeclaration == declaration)
			{
				return decl;
			}
		}
		
		ClosureVariableDeclaration var = new ClosureVariableDeclaration(this, getLocationIn(), declaration);
		
		declaration.cloneTo(var, true);
		var.setIsValueReference(true);
		
		addChild(var);
		
		return var;
	}
	
	public String getName()
	{
		return "Context" + id;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClosureContext clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ClosureContext node = new ClosureContext(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ClosureContext cloneTo(ClosureContext node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ClosureContext} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureContext cloneTo(ClosureContext node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link ClosureContext} class type to make sure everything
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