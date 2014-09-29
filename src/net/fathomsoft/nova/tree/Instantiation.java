package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Value extension that represents the declaration of an
 * instantiation node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 3, 2014 at 7:53:35 PM
 * @version	v0.2.33 Sep 29, 2014 at 10:29:33 AM
 */
public class Instantiation extends IIdentifier implements GenericCompatible
{
	private GenericType	genericTypes[];
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Instantiation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		genericTypes = new GenericType[0];
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.GenericCompatible#getGenericParameterNames()
	 */
	@Override
	public GenericType[] getGenericParameterNames()
	{
		return genericTypes;
	}

	/**
	 * @see net.fathomsoft.nova.tree.GenericCompatible#setGenericTypes(net.fathomsoft.nova.tree.GenericType[])
	 */
	@Override
	public void setGenericTypes(GenericType[] types)
	{
		this.genericTypes = types;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDecodedChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	/**
	 * Get the node represents the type of instance that is being
	 * instantiated. For example, a method call or an array
	 * initialization node.
	 * 
	 * @return The node that represents the type of instance that is
	 * 		being instantiated.
	 */
	public Identifier getIdentifier()
	{
		return (Identifier)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (getNumChildren() == 0)
		{
			super.addChild(child);
		}
		else
		{
			getIdentifier().addChild(child);
		}
	}
	
//	/**
//	 * @see net.fathomsoft.nova.tree.Identifier#isDecodingAccessedNode(net.fathomsoft.nova.tree.Node)
//	 */
//	@Override
//	public boolean isDecodingAccessedNode(Node node)
//	{
//		return true;
//	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getAccessedNode()
	 */
	public Identifier getAccessedNode()
	{
		return getIdentifier().getAccessedNode();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder).append(";\n");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		return getIdentifier().generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCUseOutput(java.lang.StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateCUseOutput(StringBuilder builder, boolean pointer)
	{
		return getIdentifier().generateCUseOutput(builder, pointer);
	}
	
	/**
	 * Decode the given statement into an Instantiation instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * Instantiations always begin with the 'new' keyword.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>new Person("Joe")</li>
	 * 	<li>new Armadillo()</li>
	 * 	<li>new String("asdf")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Instantiation instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Instantiation.
	 */
	public static Instantiation decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (!SyntaxUtils.isInstantiation(statement))
		{
			return null;
		}
		
		Instantiation n = new Instantiation(parent, location);
		
		int startIndex  = Regex.indexOf(statement, Patterns.POST_INSTANTIATION);
		
		Location newLoc = location.asNew();
		newLoc.addBounds(startIndex, statement.length());
		
		String instantiation = statement.substring(startIndex);
		
		return n.decodeInstantiation(instantiation, newLoc, require);
	}
	
	/**
	 * Decode the given instantiation<br>
	 * <br>
	 * For example: "<code>String()</code>"
	 * 
	 * @param instantiation The instantiation as exemplified above.
	 * @param location The location that the instantiation occurred.
	 * @param require Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated Instantiation.
	 */
	private Instantiation decodeInstantiation(String instantiation, Location location, boolean require)
	{
		Identifier child  = null;
		String     params = null;
		Bounds     bounds = StringUtils.findContentBoundsWithin(instantiation, VariableDeclaration.GENERIC_START, VariableDeclaration.GENERIC_END, 0, false);
		
		if (bounds.isValid())
		{
			params = bounds.extractString(instantiation);
			bounds = StringUtils.findContentBoundsWithin(instantiation, VariableDeclaration.GENERIC_START, VariableDeclaration.GENERIC_END, 0);
			
			instantiation = bounds.trimString(instantiation);
		}
		
		if (bounds.isValid())
		{
			decodeGenericParameters(params);
		}
		
		if (SyntaxUtils.isMethodCall(instantiation))
		{
			MethodCall methodCall = MethodCall.decodeStatement(getParent(), instantiation, location, require);
			
			if (methodCall == null)
			{
				return null;
			}
			
			child = methodCall;
		}
		else if (SyntaxUtils.isArrayInitialization(instantiation))
		{
			child = Array.decodeStatement(getParent(), instantiation, location, require);
		}
		
		if (child == null)
		{
			SyntaxMessage.error("Unable to parse instantiation of '" + instantiation + "'", this);
		}
		
		setName(child.getName());
		setType(child.getType());
		addChild(child);
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return builder.append("new ").append(getIdentifier().generateNovaInput(outputChildren));
	}
	
//	/**
//	 * @see net.fathomsoft.nova.tree.Identifier#generateCSourceName(java.lang.StringBuilder, java.lang.String)
//	 */
//	@Override
//	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix)
//	{
//		return builder.append(getName());
//	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Instantiation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Instantiation node = new Instantiation(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Instantiation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Instantiation cloneTo(Instantiation node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Instantiation class type to make sure everything
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