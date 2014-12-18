package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericArgument;
import net.fathomsoft.nova.tree.generics.GenericDeclaration;
import net.fathomsoft.nova.tree.generics.GenericImplementation;
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
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
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
	public GenericImplementation getGenericImplementation();
	
	public default int getNumGenericArguments()
	{
		return getGenericImplementation().getNumVisibleChildren();
	}
	
	public default int getGenericArgumentIndex(String parameterName)
	{
		if (parameterName == null)
		{
			return -1;
		}
		
		GenericImplementation implementation = getGenericImplementation();
		
		for (int i = 0; i < getNumGenericArguments(); i++)
		{
			IValue type = implementation.getVisibleChild(i);
			
			if (type.getType().equals(parameterName))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public default boolean containsGenericArgument(String parameterName)
	{
		return getGenericArgument(parameterName) != null;
	}
	
	public default GenericArgument getGenericArgument(String parameterName)
	{
		int index = getGenericArgumentIndex(parameterName);
		
		if (index < 0)
		{
			return null;
		}
		
		return getGenericArgument(index);
	}
	
	public default GenericArgument getGenericArgument(int index)
	{
		return getGenericArgument(index, (Node)this);
	}
	
	public default GenericArgument getGenericArgument(int index, Node value)
	{
		if (index < 0 || index >= getNumGenericArguments())
		{
			SyntaxMessage.error("Missing generic type declaration", value);
		}
		
		return getGenericImplementation().getVisibleChild(index);
	}
	
	public default String getGenericArgumentType(String parameterName)
	{
		return getGenericArgumentType(parameterName, (Value)this);
	}
	
	public default String getGenericArgumentType(String parameterName, Value value)
	{
		GenericArgument type = getGenericArgumentInstance(parameterName, value);
		
		if (type.isGenericType())
		{
			return type.getDefaultType();
		}
		
		return type.getType();
	}
	
	public default GenericArgument getGenericArgumentInstance(String parameterName)
	{
		return getGenericArgumentInstance(parameterName, (Node)this);
	}
	
	public default GenericArgument getGenericArgumentInstance(String parameterName, Node value)
	{
		VariableDeclaration decl  = (VariableDeclaration)this;
		ClassDeclaration    clazz = null;
		
		if (!decl.isGenericType())
		{
			clazz = decl.getTypeClass();
		}
		else
		{
			clazz = decl.getDeclaringClass();
		}
		
		int index = clazz.getGenericParameterIndex(parameterName);
		
		return getGenericArgument(index, value);
	}
	
	public default GenericArgument getGenericArgumentDeclaration(String parameterName)
	{
		VariableDeclaration decl = (VariableDeclaration)this;
		
		int index = decl.getGenericArgumentIndex(parameterName);
		
		ClassDeclaration clazz = decl.getTypeClass();
		
		return clazz.getGenericArgument(index);
	}
	
	/**
	 * Add a generic parameter name to the list of generic parameters
	 * that the class accepts. See {@link #getGenericParameterNames()}
	 * for more information on what generic parameters are.
	 * 
	 * @param parameterName The name of the generic parameter to add.
	 */
	public default void addGenericArgumentName(String parameterName)
	{
		GenericArgument type = new GenericArgument((Node)this, Location.INVALID);
		type.setType(parameterName, true, false, false);

		DeclarationData data = new DeclarationData();
		
		GenericDeclaration.searchGenericTypes(parameterName, data);
		type.iterateWords(parameterName, data, true);
		
		if (data.containsSkipBounds())
		{
			type.setType(data.getSkipBounds(0).trimString(parameterName), true, false);
		}
		
		getGenericImplementation().addChild(type);
	}

	public default void decodeGenericArguments(String statement, Bounds genericBounds)
	{
		decodeGenericArguments(statement, genericBounds, true);
	}
	
	public default void decodeGenericArguments(String statement, Bounds genericBounds, boolean endingsIncluded)
	{
		Bounds clone = genericBounds.clone();
		
		if (endingsIncluded)
		{
			clone.setStart(genericBounds.getStart() + GENERIC_START.length());
			clone.setEnd(genericBounds.getEnd() - GENERIC_END.length());
		}
		
		String params = clone.extractString(statement);
		
		decodeGenericArguments(params);
	}
	
	public default void decodeGenericArguments(String params)
	{
		String paramsList[] = StringUtils.splitCommas(params);
		
		for (String param : paramsList)
		{
			addGenericArgumentName(param);
		}
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