package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * LocalDeclaration extension that represents a Parameter of a method.
 * See {@link #decodeStatement(Node, String, Location, boolean)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:52:01 PM
 * @version	v0.2.43 Jan 16, 2015 at 11:59:17 AM
 */
public class Parameter extends LocalDeclaration
{
	private Node	defaultValue;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Parameter(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public boolean isObjectReference()
	{
		return getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
	}
	
	/**
	 * Get the default value of the parameter, if no value is passed to
	 * the method.
	 * 
	 * @return The value that the parameter will be set to, if no value is
	 * 		passed to a method.
	 */
	public Node getDefaultValue()
	{
		return defaultValue;
	}
	
	/**
	 * Set the default value of the parameter, if no value is passed to
	 * the method.
	 * 
	 * @param defaultValue The value that the parameter will be set to,
	 * 		if no value is passed to a method.
	 */
	public void setDefaultValue(Node defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateCTypeName(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCTypeName(StringBuilder builder)
	{
		if (isObjectReference() && getType() != null)
		{
			return generateCTypeClassName(builder);
		}
		/*else if (getTypeClass() != null && getTypeClass().equals(getProgram().getClassDeclaration(Nova.getClassLocation("Number"))))
		{
			return builder.append("long_long");
		}*/
		
		return super.generateCTypeName(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCModifiersSource(builder);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCHeader(builder).append(' ').append(generateCSourceName());
	}
	
	/**
	 * Decode the given statement into a Parameter instance, if
	 * possible. If it is not possible, this method returns null.
	 * A parameter node is essentially a variable declaration, but in
	 * the context of a method declaration.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>String name</li>
	 * 	<li>int age</li>
	 * 	<li>Node parent</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Parameter instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Parameter.
	 */
	public static Parameter decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true, true);
	}
	
	/**
	 * Decode the given statement into a Parameter instance, if
	 * possible. If it is not possible, this method returns null.
	 * A parameter node is essentially a variable declaration, but in
	 * the context of a method declaration.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>String name</li>
	 * 	<li>int age</li>
	 * 	<li>Node parent</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Parameter instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param checkName Whether or not to check for naming conflicts.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Parameter.
	 */
	public static Parameter decodeStatement(Node parent, String statement, Location location, boolean require, boolean checkName, boolean checkType)
	{
		VariableDeclaration node = LocalDeclaration.decodeStatement(parent, statement, location, require, checkName, checkType);
		
		if (node == null)
		{
			node = ClosureDeclaration.decodeStatement(parent, statement, location, require);
			
			if (node == null)
			{
				SyntaxMessage.queryError("Could not decode parameter", parent, location, require);
				
				return null;
			}
		}
		
		if (node instanceof Parameter)
		{
			return (Parameter)node;
		}
		
		Parameter n = new Parameter(parent, location);
		node.cloneTo(n);
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#validate(int)
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
			if (isPrimitiveType() && getParentMethod() != null)
			{
				int index = ((NovaParameterList)getAncestorOfType(NovaParameterList.class)).getVisibleParameterIndex(getName());
				
				if (index >= 0)
				{
					NovaMethodDeclaration current = getParentMethod().getOverriddenMethod(); 
					
					while (current != null)
					{
						if (!current.getParameter(index).isPrimitiveType())//.isGenericType())
						{
							/*setPrimitiveWrapperType();
							
							LocalDeclaration decl = LocalDeclaration.decodeStatement(getParentMethod(), getType() + " " + getParentMethod().generateTemporaryVariableName("prim"),
									Location.INVALID, true);
							
							getParentMethod().addChild(decl);
							
							Variable assignee = decl.generateUsableVariable(getParentMethod(), getLocationIn());
							
							Assignment assign = Assignment.decodeStatement(getParentMethod(), assignee.getName() + " = " + getName() + ".value", Location.INVALID, true, true,
									new Value[] { assignee }, null);
							
							getParentMethod().addChild(assign);
							
							swapNames(decl);*/
							
							current = null;
						}
						else
						{
							current = current.getOverriddenMethod();
						}
					}
				}
				
				/*if (!isObjectReference() && getTypeClass() != null && getTypeClass().equals(getProgram().getClassDeclaration(Nova.getClassLocation("Number"))))
				{
					setDataType(VALUE);
				}*/
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.LocalDeclaration#clone(Node, Location)
	 */
	@Override
	public Parameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Parameter node = new Parameter(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Parameter cloneTo(Parameter node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Parameter} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Parameter cloneTo(Parameter node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.defaultValue = defaultValue;
		
		return node;
	}
	
	/**
	 * Test the Parameter class type to make sure everything
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