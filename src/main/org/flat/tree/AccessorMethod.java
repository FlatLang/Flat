package org.flat.tree;

import org.flat.TestContext;
import org.flat.util.Location;
import org.flat.util.StringUtils;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version	v0.2.39 Dec 7, 2014 at 3:17:17 AM
 */
public class AccessorMethod extends PropertyMethod
{
	public static final String DISABLED_IDENTIFIER = "no";
	public static final String IDENTIFIER          = "get";
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public AccessorMethod(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public String getMethodPrefix()
	{
		return "Accessor";
	}
	
	/**
	 * Decode the given statement into a {@link AccessorMethod} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>no get</li>
	 * 	<li>get</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link AccessorMethod} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link AccessorMethod}.
	 */
	public static AccessorMethod decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (StringUtils.findNextWord(statement).equals(DISABLED_IDENTIFIER))
		{
			String remainder = statement.substring(DISABLED_IDENTIFIER.length() + 1).trim();
			
			if (remainder.equals(IDENTIFIER))
			{
				AccessorMethod n = new AccessorMethod(parent, location);
				n.setName(n.getParentField().getName());
				n.setType(n.getParentField().getType());
				n.setDisabled(true);
				
				return n;
			}
		}
		else if (statement.equals(IDENTIFIER))
		{
			AccessorMethod n = new AccessorMethod(parent, location);
			
			n.getParentField().cloneTo(n);
			n.setLocationIn(location);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public AccessorMethod clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AccessorMethod node = new AccessorMethod(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public AccessorMethod cloneTo(AccessorMethod node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link AccessorMethod} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AccessorMethod cloneTo(AccessorMethod node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link AccessorMethod} class type to make sure everything
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