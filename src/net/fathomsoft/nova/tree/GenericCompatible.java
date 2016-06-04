package net.fathomsoft.nova.tree;

import java.util.ArrayList;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
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
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
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
	 * public class Map&lt;Key, Value&gt;
	 * {
	 * 	
	 * }</pre></blockquote>
	 * The example above displays two generic parameters "Key" and "Value"<br>
	 * These are used to abstract the data type used within the class "Map"
	 * 
	 * @return A String array containing the names of the generic parameters.
	 */
	public GenericTypeArgumentList getGenericTypeArgumentList();
	
	public default int getNumGenericTypeArguments()
	{
		return getGenericTypeArgumentList().getNumVisibleChildren();
	}
	
	public default int getGenericTypeArgumentIndex(String parameterName)
	{
		if (parameterName == null)
		{
			return -1;
		}
		
		GenericTypeArgumentList implementation = getGenericTypeArgumentList();
		
		for (int i = 0; i < getNumGenericTypeArguments(); i++)
		{
			IValue type = implementation.getVisibleChild(i);
			
			if (type.getType().equals(parameterName))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public default boolean containsGenericTypeArgument(String parameterName)
	{
		return getGenericTypeArgument(parameterName) != null;
	}
	
	public default GenericTypeArgument getGenericTypeArgument(String parameterName)
	{
		int index = getGenericTypeArgumentIndex(parameterName);
		
		if (index < 0)
		{
			return null;
		}
		
		return getGenericTypeArgument(index);
	}
	
	public default GenericTypeArgument getGenericTypeArgument(int index)
	{
		return getGenericTypeArgument(index, (Node)this);
	}
	
	public default GenericTypeArgument getGenericTypeArgument(int index, Node value)
	{
		if (index < 0 || index >= getNumGenericTypeArguments())
		{
			SyntaxMessage.error("Missing generic type declaration", value);
		}
		
		return getGenericTypeArgumentList().getVisibleChild(index);
	}
	
	public default String getGenericTypeArgumentType(String parameterName)
	{
		return getGenericTypeArgumentType(parameterName, (Value)this);
	}
	
	public default String getGenericTypeArgumentType(String parameterName, Value value)
	{
		GenericTypeArgument type = getGenericTypeArgumentInstance(parameterName, value);
		
		if (type.isGenericType())
		{
			return type.getDefaultType();
		}
		
		return type.getType();
	}
	
	public default GenericTypeArgument getGenericTypeArgumentInstance(String parameterName)
	{
		return getGenericTypeArgumentInstance(parameterName, (Node)this);
	}
	
	public default GenericTypeArgument getGenericTypeArgumentInstance(String parameterName, Node value)
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
		
		int index = clazz.getGenericTypeParameterIndex(parameterName);
		
		return getGenericTypeArgument(index, value);
	}
	
	public default GenericTypeArgument getGenericTypeArgumentDeclaration(String parameterName)
	{
		VariableDeclaration decl = (VariableDeclaration)this;
		
		int index = decl.getGenericTypeArgumentIndex(parameterName);
		
		ClassDeclaration clazz = decl.getTypeClass();
		
		return clazz.getGenericTypeArgument(index);
	}
	
	/**
	 * Add a generic parameter name to the list of generic parameters
	 * that the class accepts. See {@link #getGenericParameterNames()}
	 * for more information on what generic parameters are.
	 * 
	 * @param parameterName The name of the generic parameter to add.
	 */
	public default void addGenericTypeArgumentName(String parameterName)
	{
		GenericTypeArgument type = getGenericTypeArgumentName(parameterName);
		
		getGenericTypeArgumentList().addChild(type);
	}
	
	public default GenericTypeArgument getGenericTypeArgumentName(String parameterName)
	{
		GenericTypeArgument type = new GenericTypeArgument((Node)this, Location.INVALID);
		type.setType(parameterName, true, false, false);

		DeclarationData data = new DeclarationData();
		
		GenericTypeParameterDeclaration.searchGenerics(parameterName, data);
		type.iterateWords(parameterName, data, true);
		
		if (data.containsSkipBounds())
		{
			type.setType(data.getSkipBounds(0).trimString(parameterName), true, false);
		}
		
		return type;
	}

	public default void decodeGenericTypeArguments(String statement, Bounds genericBounds)
	{
		decodeGenericTypeArguments(statement, genericBounds, true);
	}
	
	public default void decodeGenericTypeArguments(String statement, Bounds genericBounds, boolean endingsIncluded)
	{
		Bounds clone = genericBounds.clone();
		
		if (endingsIncluded)
		{
			clone.setStart(genericBounds.getStart() + GENERIC_START.length());
			clone.setEnd(genericBounds.getEnd() - GENERIC_END.length());
		}
		
		String params = clone.extractString(statement);
		
		decodeGenericTypeArguments(params);
	}
	
	public default void decodeGenericTypeArguments(String params)
	{
		GenericTypeArgument[] args = getGenericTypeArguments(params);
		
		for (GenericTypeArgument arg : args)
		{
			getGenericTypeArgumentList().addChild(arg);
		}
	}
	
	public default GenericTypeArgument[] getGenericTypeArguments(String params)
	{
		String paramsList[] = StringUtils.splitCommas(params);
		
		ArrayList<GenericTypeArgument> args = new ArrayList<>();
		
		for (String param : paramsList)
		{
			args.add(getGenericTypeArgumentName(param));
		}
		
		return args.toArray(new GenericTypeArgument[0]);
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