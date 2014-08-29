package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.GenericCompatible;
import net.fathomsoft.nova.tree.GenericType;
import net.fathomsoft.nova.tree.IIdentifier;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Identifier extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class VariableDeclaration extends IIdentifier implements GenericCompatible
{
	private boolean		constantVal, volatileVal, external;
	
	private GenericType	genericTypes[];
	
	public static final String	GENERIC_START = "<";
	public static final String	GENERIC_END   = ">";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VariableDeclaration(Node temporaryParent, Location locationIn)
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
	 * @see net.fathomsoft.nova.tree.Identifier#isDeclaration()
	 */
	@Override
	public boolean isDeclaration()
	{
		return true;
	}
	
	/**
	 * Get whether or not the variable is external. For more information
	 * on external variables, see {@link #setExternal(boolean)}.
	 * 
	 * @return Whether or not the variable is external.
	 */
	public boolean isExternal()
	{
		if (getParent() instanceof FieldDeclaration)
		{
			FieldDeclaration field = (FieldDeclaration)getParent();
			
			return field.isExternal();
		}
		
		return external;
	}
	
	/**
	 * Set whether or not the variable is external. A variable is external
	 * if it is referenced from a language outside of Nova. For example,
	 * a variable from the C language. Furthermore, a variable is external
	 * if it begins with an externally imported C file's name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * import "externalFile.h";
	 * 
	 * ...
	 * 
	 * public static void main(String args[])
	 * {
	 *	// This is the external variable declaration.
	 * 	externalFile.externalType varName;
	 * 	
	 * 	// This is the external variable assignment.
	 * 	varName = externalFile.variableInstance;
	 * }</pre></blockquote>
	 * In this example, 'externalFile' is the C header file that is
	 * imported. 'variableInstance' is the name of a variable that
	 * is contained within the imported header file.<br>
	 * 
	 * @param external Whether or not the variable will be external.
	 */
	public void setExternal(boolean external)
	{
		this.external = external;
		
		setForceOriginalName(true);
	}
	
	/**
	 * Get whether or not the variable's value is constant. To
	 * see more detail, look at {@link #setConstant(boolean)}.
	 * 
	 * @return Whether or not the variable's value is constant.
	 */
	public boolean isConstant()
	{
		return constantVal;
	}
	
	/**
	 * Get the C equivalent of the 'constant' keyword.
	 * 
	 * @return The C equivalent of the 'constant' keyword.
	 */
	public String getConstantText()
	{
		return "const";
	}
	
	/**
	 * Set whether or not the variable's value is constant.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private constant int MAX_PEOPLE = 10;</pre></blockquote>
	 * This variable is constant, as defined by the 'constant' keyword.
	 * 
	 * @param constVal Whether or not the variable's value
	 * 		is constant.
	 */
	public void setConstant(boolean constVal)
	{
		this.constantVal = constVal;
	}

	/**
	 * Get whether or not the variable's value is volatile. This is used
	 * for exception handling to make sure local variables keep their
	 * values after an exception has been thrown.
	 * 
	 * @return Whether or not the variable's value is volatile.
	 */
	public boolean isVolatile()
	{
		return volatileVal;
	}
	
	/**
	 * Get the C equivalent of the 'constant' keyword.
	 * 
	 * @return The C equivalent of the 'constant' keyword.
	 */
	public String getVolatileText()
	{
		return "volatile";
	}
	
	/**
	 * Set whether or not the variable's value is volatile. This is used
	 * for exception handling to make sure local variables keep their
	 * values after an exception has been thrown.
	 * 
	 * @param volatileVal Whether or not the variable's value
	 * 		is volatile.
	 */
	public void setVolatile(boolean volatileVal)
	{
		this.volatileVal = volatileVal;
	}
	
	/**
	 * Set the name of the Variable.
	 * 
	 * @see net.fathomsoft.nova.tree.Identifier#setName(java.lang.String)
	 * 
	 * @param name The String to set as the new name.
	 */
	public void setName(String name)
	{
		setName(name, false);
	}
	
	/**
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> sets the visibility of the declaration
	 * to private. <u><code>static</code></u> sets the variable as static.
	 * 
	 * @param attribute The attribute to set true.
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> is the first attribute (index: 0) that
	 * sets the visibility of the declaration to private.
	 * "<u><code>static</code></u>" is the second attribute (index: 1) that
	 * sets the variable as static.
	 * 
	 * @param attribute The attribute to set true.
	 * @param argNum The index of the attribute in the order that it
	 * 		came in.
	 * @return Whether or not an attribute was successfully set.
	 */
	public boolean setAttribute(String attribute, int argNum)
	{
		if (attribute.equals(getConstantText()))
		{
			setConstant(true);
		}
		else
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Compare the specified variable with the given one to see if they
	 * come from the same declaration.
	 * 
	 * @param other The variable to compare with.
	 * @return Whether or not the variables come from the same
	 * 		declaration.
	 */
	public boolean isSameVariable(Variable other)
	{
		VariableDeclaration second = other.getDeclaration();
		
		return this == second;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCSource(builder);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCDeclarationFragment(builder).append(";\n");
	}
	
	/**
	 * Generate a String with the declaration modifiers and the name of
	 * the variable declared.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The appended StringBuilder.
	 */
	public StringBuilder generateCDeclarationFragment(StringBuilder builder)
	{
		return generateCModifiersSource(builder).append(' ').append(generateCSourceName());
	}
	
	/**
	 * Generate the modifiers for the specified Variable.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * Person people[] = new Person[42];</pre></blockquote>
	 * In the above Variable declaration, the modifiers are the type of
	 * the variable ("<u><code>Person</code></u>") and the type of
	 * declaration is an array.<br>
	 * This also checks if the type requires a pointer.
	 * 
	 * @param builder The StringBuilder to append to.
	 * @return The appended StringBuilder.
	 */
	public StringBuilder generateCModifiersSource(StringBuilder builder)
	{
		if (isVolatile())//!(this instanceof Parameter || this instanceof FieldDeclaration))
		{
			builder.append(getVolatileText()).append(' ');
		}
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		generateCType(builder);
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getType()).append(' ').append(getName());
		
		for (int i = 0; i < getArrayDimensions(); i++)
		{
			builder.append("[]");
		}
		
		return builder;
	}
	
	/**
	 * Generate a String for the code used to free memory of the
	 * specified variable.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The generated String for the code.
	 */
	public StringBuilder generateFreeOutput(StringBuilder builder)
	{
		if (isConstant())
		{
			return builder;
		}
		
		if (isPrimitiveType() || isExternalType())
		{
			if (!isPrimitive())
			{
				builder.append("NOVA_FREE(");
				
				generateCUseOutput(builder, true).append(");\n");
			}
		}
		else
		{
			builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_").append(getType()).append('(').append('&');
			
			generateCUseOutput(builder, true).append(", ").append(Exception.EXCEPTION_DATA_IDENTIFIER).append(");\n");
		}
		
		return builder;
	}
	
	/**
	 * Check to see if the statement is declaring an array.
	 * e.g. "<u><code>String names[]</code></u>"
	 * 
	 * @param statement The statement possibly containing array brackets.
	 * @param index The index to start the search for array brackets at.
	 * @param rightDelimiter The right delimiter possibly containing
	 * 		array brackets.
	 */
	public void checkArray(String statement, int index, String rightDelimiter)
	{
		// If it is an array declaration.
		if (rightDelimiter.length() > 0 && rightDelimiter.charAt(0) == '[')
		{
			int dimensions = SyntaxUtils.findArrayDimensions(statement, index, false);
			
			if (dimensions < 0)
			{
				SyntaxMessage.error("Array brackets cannot contain data", this);
			}
			
			setArrayDimensions(dimensions);
		}
	}
	
	/**
	 * Generate a usable Variable instance that refers to the specified
	 * VariableDeclaration.
	 * 
	 * @param parent The parent of the newly generated Variable.
	 * @param location The location of the newly generated Variable.
	 * @return The newly generated Variable.
	 */
	public Variable generateUsableVariable(Node parent, Location location)
	{
		Variable var = new Variable(parent, location);
		
		return generateUsableVariable(var);
	}
	
	/**
	 * Generate a usable Variable instance that refers to the specified
	 * VariableDeclaration from the given Variable instance.
	 * 
	 * @param toVar The Variable to set up as a reference to the
	 * 		VariableDeclaration.
	 * @return The correctly set up Variable.
	 */
	public Variable generateUsableVariable(Variable toVar)
	{
		toVar.setDeclaration(this);
		
		return toVar;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (getType() != null && !setType(getType(), false, true))
			{
				SyntaxMessage.error("Type '" + getType() + "' does not exist", this);
			}
		}
		
		return super.validate(phase);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public VariableDeclaration clone(Node temporaryParent, Location locationIn)
	{
		VariableDeclaration node = new VariableDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link VariableDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableDeclaration cloneTo(VariableDeclaration node)
	{
		super.cloneTo(node);

		node.constantVal = constantVal;
		node.external    = external;
		node.volatileVal = volatileVal;
		
		return node;
	}
	
	/**
	 * Test the VariableDeclaration class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	/**
	 * Implementation of the ExtraData for this class.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.29 Aug 28, 2014 at 6:56:45 PM
	 * @version	v0.2.29 Aug 28, 2014 at 6:56:45 PM
	 */
	public static class DeclarationData extends ExtraData
	{
		private int	genericsRemaining;
		
		public int getGenericsRemaining()
		{
			return genericsRemaining;
		}
		
		public void setGenericsRemaining(int remaining)
		{
			this.genericsRemaining = remaining;
		}
		
		public void decrementGenericsRemaining()
		{
			genericsRemaining--;
		}
	}
}