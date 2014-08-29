package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.28 Aug 16, 2014 at 8:57:51 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class StaticBlock extends Node implements ScopeAncestor
{
	private int	uniqueID;
	
	private static final String	C_PREFIX   = Nova.LANGUAGE_NAME + "_init_";
	
	public static final String	IDENTIFIER = "static";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public StaticBlock(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Scope scope = new Scope(this, Location.INVALID);
		
		setScope(scope);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ScopeAncestor#generateUniqueID()
	 */
	@Override
	public int generateUniqueID()
	{
		return ++uniqueID;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(java.lang.StringBuilder)
	 */
	public static StringBuilder generateCHeader(StringBuilder builder, ClassDeclaration clazz)
	{
		return generateCMethodHeader(builder, clazz).append(';').append('\n');
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return getScope().generateCSource(builder);
	}
	
	public static StringBuilder generateCMethodHeader(StringBuilder builder, ClassDeclaration clazz)
	{
		builder.append("void ");
		
		return generateCMethodName(builder, clazz).append("()");
	}
	
	public static StringBuilder generateCMethodName(StringBuilder builder, ClassDeclaration clazz)
	{
		return builder.append(clazz.getName()).append(C_PREFIX).append(IDENTIFIER);
	}
	
	public static StringBuilder generateCMethodCall(StringBuilder builder, ClassDeclaration clazz)
	{
		return generateCMethodName(builder, clazz).append("()");
	}
	
	/**
	 * Decode the given statement into a {@link StaticBlock} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>static</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link StaticBlock} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link StaticBlock}.
	 */
	public static StaticBlock decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.equals(IDENTIFIER))
		{
			StaticBlock b = new StaticBlock(parent, location);
			
			return b;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public StaticBlock clone(Node temporaryParent, Location locationIn)
	{
		StaticBlock node = new StaticBlock(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link StaticBlock} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public StaticBlock cloneTo(StaticBlock node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link StaticBlock} class type to make sure everything
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