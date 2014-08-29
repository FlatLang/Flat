package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.variables.VariableDeclaration.DeclarationData;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

import static net.fathomsoft.nova.tree.variables.VariableDeclaration.GENERIC_END;
import static net.fathomsoft.nova.tree.variables.VariableDeclaration.GENERIC_START;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.29 Aug 28, 2014 at 11:51:16 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public interface GenericCompatible
{
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
	
	public default boolean containsGenericParameter(String parameterName)
	{
		return getGenericParameter(parameterName) != null;
	}
	
	public default GenericType getGenericParameter(String parameterName)
	{
		for (GenericType type : getGenericParameterNames())
		{
			if (type.getType().equals(parameterName))
			{
				return type;
			}
		}
		
		return null;
	}
	
	/**
	 * Add a generic parameter name to the list of generic parameters
	 * that the class accepts. See {@link #getGenericParameterNames()}
	 * for more information on what generic parameters are.
	 * 
	 * @param parameterName The name of the generic parameter to add.
	 */
	public default void addGenericParameterName(String parameterName)
	{
		setGenericTypes(StringUtils.appendElement(getGenericParameterNames(), new GenericType[getGenericParameterNames().length + 1], new GenericType((Node)this, Location.INVALID, parameterName)));
	}

	public default void decodeGenericParameter(String statement, Bounds genericBounds)
	{
		decodeGenericParameter(statement, genericBounds, true);
	}
	
	public default void decodeGenericParameter(String statement, Bounds genericBounds, boolean endingsIncluded)
	{
		if (endingsIncluded)
		{
			genericBounds.setStart(genericBounds.getStart() + GENERIC_START.length());
			genericBounds.setEnd(genericBounds.getEnd() - GENERIC_END.length());
		}
		
		String params = genericBounds.extractString(statement);
		
		decodeGenericParameter(params);
	}
	
	public default void decodeGenericParameter(String params)
	{
		String paramsList[] = StringUtils.splitCommas(params);
		
		for (String param : paramsList)
		{
			addGenericParameterName(param);
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
}