package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclaration.DeclarationData;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.29 Aug 28, 2014 at 11:51:16 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public interface GenericCompatible
{
	public static final String	GENERIC_START = "<";
	public static final String	GENERIC_END   = ">";
	
	/**
	 * Get the list of names that the ClassDeclaration accepts as generic
	 * declarations.<br>
	 * For example:
	 * <blockquote><pre>
	 * public class Map<Key, Value>
	 * {
	 * 	
	 * }</pre></blockquote>
	 * The example above displays two generic parameters "Key" and "Value"<br>
	 * These are used to abstract the data type used within the class "Map"
	 * 
	 * @return A String array containing the names of the generic parameters.
	 */
	public GenericType[] getGenericParameterNames();
	
	public void setGenericTypes(GenericType types[]);
	
	public default int getGenericParameterIndex(String parameterName)
	{
		if (parameterName == null)
		{
			return -1;
		}
		
		for (int i = 0; i < getGenericParameterNames().length; i++)
		{
			GenericType type = getGenericParameterNames()[i];
			
			if (type.getType().equals(parameterName))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public default boolean containsGenericParameter(String parameterName)
	{
		return getGenericParameter(parameterName) != null;
	}
	
	public default GenericType getGenericParameter(String parameterName)
	{
		int index = getGenericParameterIndex(parameterName);
		
		if (index < 0)
		{
			return null;
		}
		
		return getGenericParameter(index);
	}
	
	public default GenericType getGenericParameter(int index)
	{
		return getGenericParameter(index, (Node)this);
	}
	
	public default GenericType getGenericParameter(int index, Node value)
	{
		if (index < 0 || index >= getGenericParameterNames().length)
		{
			SyntaxMessage.error("Missing generic type declaration", value);
		}
		
		return getGenericParameterNames()[index];
	}
	
	public default String getGenericParameterType(String parameterName)
	{
		GenericType type = getGenericParameterInstance(parameterName);
		
		if (type.isGenericType())
		{
			return type.getDefaultType();
		}
		
		return type.getType();
	}
	
	public default GenericType getGenericParameterInstance(String parameterName)
	{
		return getGenericParameterInstance(parameterName, (Node)this);
	}
	
	public default GenericType getGenericParameterInstance(String parameterName, Node value)
	{
		VariableDeclaration decl  = (VariableDeclaration)this;
		ClassDeclaration    clazz = decl.getTypeClass();
		
		int index = clazz.getGenericParameterIndex(parameterName);
		
		return getGenericParameter(index, value);
	}
	
	public default GenericType getGenericParameterDeclaration(String parameterName)
	{
		VariableDeclaration decl = (VariableDeclaration)this;
		
		int index = decl.getGenericParameterIndex(parameterName);
		
		ClassDeclaration clazz = decl.getTypeClass();
		
		return clazz.getGenericParameter(index);
	}
	
	/**
	 * Add a generic parameter name to the list of generic parameters
	 * that the class accepts. See {@link #getGenericParameterNames()}
	 * for more information on what generic parameters are.
	 * 
	 * @param parameterName The name of the generic parameter to add.
	 */
	public default void addGenericParameterNames(String parameterName)
	{
		GenericType type = new GenericType((Node)this, Location.INVALID, parameterName);

		DeclarationData data = new DeclarationData();
		
		type.searchGenericParameters(parameterName, data);
		type.iterateWords(parameterName, data, true);
		
		if (data.containsSkipBounds())
		{
			type.setType(data.getSkipBounds(0).trimString(parameterName), true, false);
		}
		
		setGenericTypes(StringUtils.appendElement(getGenericParameterNames(), new GenericType[getGenericParameterNames().length + 1], type));
	}

	public default void decodeGenericParameters(String statement, Bounds genericBounds)
	{
		decodeGenericParameters(statement, genericBounds, true);
	}
	
	public default void decodeGenericParameters(String statement, Bounds genericBounds, boolean endingsIncluded)
	{
		Bounds clone = genericBounds.clone();
		
		if (endingsIncluded)
		{
			clone.setStart(genericBounds.getStart() + GENERIC_START.length());
			clone.setEnd(genericBounds.getEnd() - GENERIC_END.length());
		}
		
		String params = clone.extractString(statement);
		
		decodeGenericParameters(params);
	}
	
	public default void decodeGenericParameters(String params)
	{
		String paramsList[] = StringUtils.splitCommas(params);
		
		for (String param : paramsList)
		{
			addGenericParameterNames(param);
		}
	}
	
	public default void searchGenericParameters(String statement, DeclarationData data)
	{
		int    index  = 0;
		Bounds bounds = null;
		
		do
		{
			bounds = StringUtils.findContentBoundsWithin(statement, GENERIC_START, GENERIC_END, index);
			
			if (bounds.isValid())
			{
				index = bounds.getEnd();
				
				data.addSkipBounds(bounds);
			}
		}
		while (bounds.isValid());
		
		data.setGenericsRemaining(data.getNumSkipBounds());
	}
	
	public default GenericType[] cloneGenericTypes(Node parent)
	{
		GenericType genericTypes[] = getGenericParameterNames();
		GenericType types[] = new GenericType[genericTypes.length];
		
		for (int i = 0; i < genericTypes.length; i++)
		{
			types[i] = (GenericType)genericTypes[i].clone(parent, Location.INVALID);
		}
		
		return types;
	}
	
	/**
	 * Test the GenericCompatible class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		context.importClass(Nova.getClassLocation("Stack"));
		
		Node declaration = SyntaxTree.decodeScopeContents(context.method, "Stack<String> s = new Stack()", Location.INVALID, false);
		
		if (declaration == null)
		{
			return "Could not decode generic declaration";
		}
		
		context.method.addChild(declaration);
		
		try
		{
			SyntaxTree.decodeScopeContents(context.method, "s.push(4)", Location.INVALID, true);
			
			return "Did not throw an error for passing incorrect generic type";
		}
		catch (SyntaxErrorException e)
		{
			
		}
		
		try
		{
			Node node = SyntaxTree.decodeScopeContents(context.method, "s.push(\"str\")", Location.INVALID, true);
			
			context.method.addChild(node);
		}
		catch (SyntaxErrorException e)
		{
			return "Could not add correct generic type";
		}
		
		try
		{
			Node node = SyntaxTree.decodeScopeContents(context.method, "s.push(null)", Location.INVALID, true);
			
			context.method.addChild(node);
		}
		catch (SyntaxErrorException e)
		{
			return "Could not add null generic type";
		}
		
		return null;
	}
}