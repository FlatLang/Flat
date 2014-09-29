package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link IValue} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.29 Aug 25, 2014 at 6:11:02 PM
 * @version	v0.2.33 Sep 29, 2014 at 10:29:33 AM
 */
public class GenericType extends IValue
{
	private String	defaultType;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericType(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericType(Node temporaryParent, Location locationIn, String parameterName)
	{
		this(temporaryParent, locationIn);
		
		defaultType = Nova.getClassLocation("Object");
		setType(parameterName, true, false);
	}
	
	public String getDefaultType()
	{
		return defaultType;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getGenericReturnType()
	 */
	@Override
	public String getGenericReturnType()
	{
		return getType();
	}
	
	/**
	 * Decode the given statement into a {@link GenericType} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>E</li>
	 * 	<li>aSDFAdf</li>
	 * 	<li>AFSFD323DF_ASDFdd</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link GenericType} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link GenericType}.
	 */
	public static GenericType decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (parent.getParentClass(true).containsGenericParameter(statement))
		{
			GenericType n = new GenericType(parent, location, statement);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericType clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericType node = new GenericType(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link GenericType} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericType cloneTo(GenericType node)
	{
		super.cloneTo(node);
		
		node.defaultType = defaultType;
		
		return node;
	}
	
	/**
	 * Test the {@link GenericType} class type to make sure everything
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