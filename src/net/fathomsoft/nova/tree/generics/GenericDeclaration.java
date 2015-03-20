package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.AccessorMethod;
import net.fathomsoft.nova.tree.GenericCompatible;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.TypeList;
import net.fathomsoft.nova.tree.variables.VariableDeclaration.DeclarationData;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * {@link TypeList} extension that represents a generic type declaration.
 * Contains the information of a generic type declaration.
 * Contains all of the generic type parameter names.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.40 Dec 7, 2014 at 4:05:23 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class GenericDeclaration extends TypeList<GenericParameter>
{
	public static final String EXTENDS_IDENTIFIER = "extends";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public int getNumParameters()
	{
		return getNumVisibleChildren();
	}
	
	public boolean containsParameter(String parameterName)
	{
		return getParameterIndex(parameterName) >= 0;
	}
	
	public GenericParameter getParameter(int index)
	{
		return getVisibleChild(index);
	}
	
	public GenericParameter getParameter(String parameterName)
	{
		int index = getParameterIndex(parameterName);
		
		if (index < 0)
		{
			return null;
		}
		
		return getParameter(index);
	}
	
	public int getParameterIndex(String parameterName)
	{
		for (int i = 0; i < getNumParameters(); i++)
		{
			GenericParameter param = getParameter(i);
			Nova.debuggingBreakpoint(param == null);
			getParameter(i);
			
			if (param.getName().equals(parameterName))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public void decodeGenericParameters(String statement, Bounds genericBounds)
	{
		decodeGenericParameters(statement, genericBounds, true);
	}
	
	public void decodeGenericParameters(String statement, Bounds genericBounds, boolean endingsIncluded)
	{
		Bounds clone = genericBounds.clone();
		
		if (endingsIncluded)
		{
			clone.setStart(genericBounds.getStart() + GenericCompatible.GENERIC_START.length());
			clone.setEnd(genericBounds.getEnd() - GenericCompatible.GENERIC_END.length());
		}
		
		String params = clone.extractString(statement);
		
		decodeGenericParameters(params);
	}
	
	private void addGenericParameterName(String parameterName)
	{
		GenericParameter type = new GenericParameter((Node)this, Location.INVALID);
		
		int numWords = StringUtils.findNumWords(parameterName);
		
		if (numWords > 1)
		{
			boolean failed = true;
			
			if (numWords == 3)
			{
				Bounds bounds     = StringUtils.findNextWordBounds(parameterName);
				Bounds nextBounds = StringUtils.findNextWordBounds(parameterName, bounds.getEnd());
				
				if (nextBounds.extractString(parameterName).equals(EXTENDS_IDENTIFIER))
				{
					String defaultType = StringUtils.findNextWord(parameterName, nextBounds.getEnd());
					
					type.setDefaultType(defaultType);
					
					parameterName = bounds.extractString(parameterName);
					
					failed = false;
				}
			}
			
			if (failed)
			{
				SyntaxMessage.error("Could not decode Generic Parameter Declaration '" + parameterName + "'", this);
			}
		}
		
		type.setName(parameterName);
		
		DeclarationData data = new DeclarationData();
		
		searchGenericTypes(parameterName, data);
		type.iterateWords(parameterName, data, true);
		
		if (data.containsSkipBounds())
		{
			type.setName(data.getSkipBounds(0).trimString(parameterName));
		}
		
		addChild(type);
	}
	
	public void decodeGenericParameters(String params)
	{
		String paramsList[] = StringUtils.splitCommas(params);
		
		for (String param : paramsList)
		{
			addGenericParameterName(param);
		}
	}
	
	public void searchGenericParameters(String statement, DeclarationData data)
	{
		int    index  = 0;
		Bounds bounds = null;
		
		do
		{
			bounds = StringUtils.findContentBoundsWithin(statement, GenericCompatible.GENERIC_START, GenericCompatible.GENERIC_END, index);
			
			if (bounds.isValid())
			{
				index = bounds.getEnd();
				
				data.addSkipBounds(bounds);
			}
		}
		while (bounds.isValid());
		
		data.setGenericsRemaining(data.getNumSkipBounds());
	}
	
	public static void searchGenericTypes(String statement, DeclarationData data)
	{
		int    index  = 0;
		Bounds bounds = null;
		
		do
		{
			bounds = StringUtils.findContentBoundsWithin(statement, GenericCompatible.GENERIC_START, GenericCompatible.GENERIC_END, index);
			
			if (bounds.isValid())
			{
				index = bounds.getEnd();
				
				data.addSkipBounds(bounds);
			}
		}
		while (bounds.isValid());
		
		data.setGenericsRemaining(data.getNumSkipBounds());
	}
	
	/**
	 * Decode the given statement into a {@link GenericDeclaration} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li></li>
	 * 	<li></li>
	 * 	<li></li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link GenericDeclaration} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link GenericDeclaration}.
	 */
	public static GenericDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericDeclaration node = new GenericDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericDeclaration cloneTo(GenericDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericDeclaration cloneTo(GenericDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericDeclaration} class type to make sure everything
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