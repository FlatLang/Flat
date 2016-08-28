package net.fathomsoft.nova.tree.variables;

import java.util.ArrayList;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Identifier extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.43 Jan 16, 2015 at 11:59:17 AM
 */
public class VariableDeclaration extends IIdentifier
{
	private boolean            volatileVal, external, reference;
	
	public  String[]           extraDeclarations;
	
	public ArrayList<Variable> references = new ArrayList<>();
	
	public boolean isValueReference()
	{
		return reference;
	}
	
	public void setIsValueReference(boolean reference)
	{
		this.reference = reference;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VariableDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		GenericTypeArgumentList implementation = new GenericTypeArgumentList(this, locationIn.asNew());
		addChild(implementation, this);
		
		extraDeclarations = new String[0];
	}
	
	public VariableDeclaration getOriginalDeclaration()
	{
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * Get the Bounds that contains the extra field declarations.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * public String firstName, lastName, middleI</pre></blockquote>
	 * In the above Field Declaration, the '<code><i>lastName, middleI</i></code>' part of
	 * the declaration is classified as an 'extra' declaration. The Bounds would start
	 * before the comma after '<code><i>firstName</i></code>' and end after
	 * '<code><i>lastName, middleI</i></code>' section.
	 * 
	 * @param statement The statement to search for the extra declarations in.
	 * @return The Bounds containing the extra declarations. If there are no
	 * 		extra declarations, then Bounds.EMPTY is returned.
	 */
	public Bounds findExtraDeclarations(String statement)
	{
		String declarations[] = StringUtils.splitCommas(statement, true);
		
		if (declarations.length > 1)
		{
			extraDeclarations = new String[declarations.length - 1];
			
			System.arraycopy(declarations, 1, extraDeclarations, 0, declarations.length - 1);
			
			return new Bounds(declarations[0].length(), statement.length());
		}
		
		return Bounds.EMPTY;
	}
	
	public boolean isTangible()
	{
		return isAccessible();
	}
	
	public boolean isAccessible()
	{
		return true;
	}
	
	public boolean isSettable()
	{
		return true;
	}
	
	@Override
	public ClassDeclaration getDeclaringClass()
	{
		return getParentClass();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getGenericReturnType()
	 */
	@Override
	public String getGenericReturnType()
	{
		return getGenericTypeParameter().getDefaultType();
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
	 * @see net.fathomsoft.nova.tree.Node#isWithinExternalContext()
	 */
	@Override
	public boolean isWithinExternalContext()
	{
		return isExternal() || super.isWithinExternalContext();
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
		return false;
	}
	
	public void swapNames(Variable other)
	{
		swapNames(other.getDeclaration());
	}
	
	public void swapNames(VariableDeclaration other)
	{
		String  temp  = getName();
		boolean force = doesForceOriginalName();
		
		setName(other.getName(), other.doesForceOriginalName());
		other.setName(temp, force);
	}
	
	@Override
	public boolean onAfterDecoded()
	{
		convertArrays();
		
		return super.onAfterDecoded();
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
		
		generateCType(builder);
		
		return builder;
	}
	
	public StringBuilder generateCDefaultValue(StringBuilder builder)
	{
		if (isPrimitive())
		{
			builder.append(0);
		}
		else
		{
			builder.append(generateCTypeCast()).append(Value.NULL_IDENTIFIER);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		generateNovaAnnotations(builder);
		
		return generateNovaType(builder).append(' ').append(getName());
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
			getTypeClass().getDestructor().generateCSourceName(builder).append('(').append('&');
			
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
			
			setArrayDimensions(getArrayDimensions() + dimensions);
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
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (!validateType())
			{
				return result.errorOccurred();
			}
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (!isTangible())
			{
				getParent().removeChild(this);
				
				result.skipCycle = true;
				result.nextIncrement = 0;
				
				return result;
			}
		}
		
		addDefaultGenericTypeArguments();
		
		return result;
	}
	
	public boolean isUsed()
	{
		return !isUserMade() || references.size() > 0;
	}
	
	public void addDefaultGenericTypeArguments()
	{
		addDefaultGenericTypeArguments(false);
	}

	public void addDefaultGenericTypeArguments(boolean clearChildren)
	{
		if (getTypeClass() != null && !isGenericType())
		{
			GenericTypeArgumentList args = getGenericTypeArgumentList();
			GenericTypeParameterDeclaration decl = getTypeClass().getGenericTypeParameterDeclaration();

			if (clearChildren && args.getNumVisibleChildren() > 0)
			{
				args.slaughterEveryLastChild();
			}
			
			for (int i = args.getNumVisibleChildren(); i < decl.getNumParameters(); i++)
			{
				args.addChild(SyntaxUtils.getGenericTypeArgumentName(this, decl.getParameter(i).getDefaultType()));
			}
		}
	}
	
	public boolean validateType()
	{
		if (getType() != null && !setType(getType(), false, !isGenericType()))
		{
			setType(getType(), false, !isGenericType());
			SyntaxMessage.error("Type '" + getType() + "' does not exist", this, false);
			
			isGenericType();
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public VariableDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		VariableDeclaration node = new VariableDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public VariableDeclaration cloneTo(VariableDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link VariableDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableDeclaration cloneTo(VariableDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.external     = external;
		node.volatileVal  = volatileVal;
		node.reference    = reference;
		
		node.extraDeclarations = new String[extraDeclarations.length];
		
		for (int i = 0; i < extraDeclarations.length; i++)
		{
			node.extraDeclarations[i] = extraDeclarations[i];
		}
		
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