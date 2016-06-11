package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.error.SyntaxMessage;
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
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class GenericTypeParameterDeclaration extends TypeList<GenericTypeParameter>
{
	public static final String EXTENDS_IDENTIFIER = "extends";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericTypeParameterDeclaration(Node temporaryParent, Location locationIn)
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
	
	public GenericTypeParameter getParameter(int index)
	{
		return getVisibleChild(index);
	}
	
	public GenericTypeParameter getParameter(String parameterName)
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
			GenericTypeParameter param = getParameter(i);
			
			if (param.getName().equals(parameterName))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public static void searchGenerics(String statement, DeclarationData data)
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
	
	public void decodeGenericTypeParameters(String statement, Bounds genericBounds)
	{
		decodeGenericTypeParameters(statement, genericBounds, true);
	}
	
	public void decodeGenericTypeParameters(String statement, Bounds genericBounds, boolean endingsIncluded)
	{
		Bounds clone = genericBounds.clone();
		
		if (endingsIncluded)
		{
			clone.setStart(genericBounds.getStart() + GenericCompatible.GENERIC_START.length());
			clone.setEnd(genericBounds.getEnd() - GenericCompatible.GENERIC_END.length());
		}
		
		String params = clone.extractString(statement);
		
		decodeGenericTypeParameters(params);
	}
	
	private void addGenericParameterName(String parameterName)
	{
		GenericTypeParameter type = new GenericTypeParameter((Node)this, Location.INVALID);
		
		int numWords = StringUtils.findNumWords(parameterName);
		
		if (numWords > 1)
		{
			boolean failed = true;
			
			Bounds bounds     = StringUtils.findNextWordBounds(parameterName);
			Bounds nextBounds = StringUtils.findNextWordBounds(parameterName, bounds.getEnd());
			
			if (nextBounds.extractString(parameterName).equals(EXTENDS_IDENTIFIER))
			{
				if (numWords == 3)
				{
					String defaultType = StringUtils.findNextWord(parameterName, nextBounds.getEnd());
					
					type.setDefaultType(defaultType);
					
					parameterName = bounds.extractString(parameterName);
				}
				
				failed = false;
			}
			
			if (failed)
			{
				SyntaxMessage.error("Could not decode Generic Parameter Declaration '" + parameterName + "'", this);
			}
		}
		
		type.setName(parameterName);
		
		DeclarationData data = new DeclarationData();
		
		searchGenerics(parameterName, data);
		type.iterateWords(parameterName, data, true);
		
		if (data.containsSkipBounds())
		{
			type.setName(data.getSkipBounds(0).trimString(parameterName));
		}
		
		addChild(type);
	}
	
	public void decodeGenericTypeParameters(String params)
	{
		String paramsList[] = StringUtils.splitCommas(params);
		
		for (String param : paramsList)
		{
			addGenericParameterName(param);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeParameterDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericTypeParameterDeclaration node = new GenericTypeParameterDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericTypeParameterDeclaration cloneTo(GenericTypeParameterDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericTypeParameterDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericTypeParameterDeclaration cloneTo(GenericTypeParameterDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
}