package flat.tree;

import flat.tree.generics.GenericTypeParameterList;
import flat.tree.variables.VariableDeclaration.DeclarationData;
import flat.util.Bounds;
import flat.util.SyntaxUtils;

/**
 * Class used to share code between Identifier and Value.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.35 Oct 4, 2014 at 3:36:34 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public interface AbstractValue extends GenericCompatible
{
	/**
	 * Set the type that this statement returns.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * The type of the variable returns is "int"
	 * 
	 * @param type The type that this statement returns.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @param checkType Whether or not to check if the type is valid.
	 * @param checkDataType Whether or not to check the data type of
	 * 		the given type.
	 * @return Whether or not the type was set successfully.
	 */
	public default boolean setType(String type, boolean require, boolean checkType, boolean checkDataType)
	{
		if (type == null)
		{
			setTypeValue(null);
			
			return true;
		}
		
		Value n = (Value)this;
		
		DeclarationData data = new DeclarationData();

		if (type.indexOf('(') == -1 || type.indexOf('<') < type.indexOf('(')) {
			GenericTypeParameterList.searchGenerics(type, data);

			while (data.getGenericsRemaining() > 0) {
				Bounds bounds = data.getSkipBounds(data.getGenericsRemaining() - 1);

				decodeGenericTypeArguments(type, bounds);

				type = bounds.extractPreString(type) + bounds.extractPostString(type);

				data.decrementGenericsRemaining();
			}
		}
		
		if (checkType)
		{
			type = SyntaxUtils.getValidType(n, type);
		}
		
		if (getTypeObject() != null && type != null && type.contains("("))
		{
			getTypeObject().value = null;
		}
		
		setTypeValue(type);
		
		if (checkType)
		{
			if (!n.checkType(type, require))
			{
				return false;
			}
		}
		
		if (checkDataType)
		{
			n.checkDataType(type);
		}
		
		return true;
	}
	
	public default Value[] getTypes()
	{
		return new Value[] { ((Value)this).getReturnedNode() };//((Value)this).getProgram().getClassDeclaration(getType()) };
	}
	
	/**
	 * Get the type that the statement returns. For an example of what a
	 * value type looks like, see {@link #setType(String)}
	 * 
	 * @return The type that the statement returns.
	 */
	default String getType()
	{
		return getType(true);
	}
	
	public String getType(boolean checkCast);
	
	Value getArrayTypeValue();
	
	public String getTypeStringValue();
	
	public abstract Type getTypeObject();
	
	/**
	 * Set the value of the type variable without checking any
	 * precautions. Should not be used outside of the IValue and
	 * IIdentifier classes.
	 * 
	 * @param type The type that the statement returns.
	 */
	public void setTypeValue(String type);
	
	/**
	 * Get the amount of dimensions that the arrayAccess has, if any. For an
	 * example of what a arrayAccess declarations and dimensions look like
	 * {@link #setArrayDimensions(int)}
	 * 
	 * @return The amount of dimensions that the arrayAccess has, if any.
	 */
	public int getArrayDimensions();
	
	/**
	 * Set the amount of dimensions that the arrayAccess has, if any.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * int arrayAccess[][][];</pre></blockquote>
	 * In the previous example, the variable "arrayAccess" has three dimensions.
	 * 
	 * @param arrayDimensions The amount of dimensions that the arrayAccess has,
	 * 		if any.
	 */
	public void setArrayDimensions(int arrayDimensions);
	
	/**
	 * Get whether or not the identifier is a value, pointer, or
	 * reference.<br>
	 * <br>
	 * Possible values include:
	 * <ul>
	 * 	<li><b>Variable.VALUE</b> - If the variable type simply refers to a value.</li>
	 * 	<li><b>Variable.POINTER</b> - If the variable type is a pointer.</li>
	 * 	<li><b>Variable.REFERENCE</b> - If the variable type is a reference.</li>
	 * </ul>
	 * 
	 * @return The data type that the variable is.
	 */
	default byte getDataType()
	{
		return getDataType(true);
	}
	
	public byte getDataType(boolean checkGeneric);
	public byte getDataType(boolean checkGeneric, boolean checkCast);

	/**
	 * Set whether or not the identifier is a value, pointer, or
	 * reference.<br>
	 * <br>
	 * Possible values include:
	 * <ul>
	 * 	<li><b>Variable.VALUE</b> - If the variable type simply refers to a value.</li>
	 * 	<li><b>Variable.POINTER</b> - If the variable type is a pointer.</li>
	 * 	<li><b>Variable.REFERENCE</b> - If the variable type is a reference.</li>
	 * </ul>
	 * 
	 * @param type The data type that the variable is.
	 */
	public void setDataType(byte type);
}