package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;
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
	public String  defaultValueString;
	public Value	defaultValue;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Parameter(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public int getIndex()
	{
		ParameterList params = (ParameterList)getParent();
		
		for (int i = 0; i < params.getNumParameters(); i++)
		{
			if (this == params.getParameter(i))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	@Override
	public boolean isLocal()
	{
		return true;
	}
	
	public boolean isOptional()
	{
		return defaultValueString != null || defaultValue != null;
	}
	
	public boolean isRequired()
	{
		return !isOptional();
	}
	
	public boolean isObjectReference()
	{
		return getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
	}
	
	@Override
	public StringBuilder generateNovaAnnotations(StringBuilder builder)
	{
		return generateNovaAnnotations(builder, false);
	}
	
	public boolean isUnnamedParameter()
	{
		NovaMethodDeclaration method = getParentMethod();
		
		if (method instanceof LambdaMethodDeclaration && getName().startsWith("_"))
		{
			Integer parsed = tryParse(getName().substring(1));
			
			return parsed != null && method.getParameterList().getVisibleParameterIndex(getName()) + 1 == parsed;
		}
		
		return false;
	}
	
	private static Integer tryParse(String text)
	{
		try
		{
			return Integer.parseInt(text);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	@Override
	public boolean isUserMade()
	{
		if (!super.isUserMade())
		{
			return false;
		}
		
		NovaMethodDeclaration method = getParentMethod();
		
		return method != null && method.isUserMade() && !isUnnamedParameter();
	}
	
	@Override
	public boolean isUsed()
	{
		if (super.isUsed())
		{
			return true;
		}
		
		NovaMethodDeclaration method = getParentMethod();
		
		return method == null || method.isExternal();
	}

	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		generateNovaAnnotations(builder);
		
		builder.append(generateNovaType()).append(' ').append(getName());
		
		if (isOptional())
		{
			builder.append(" = ").append(defaultValueString != null ? defaultValueString : defaultValue.generateNovaInput());
		}
		
		return builder;
	}

	/**
	 * Get the default value of the parameter, if no value is passed to
	 * the method.
	 * 
	 * @return The value that the parameter will be set to, if no value is
	 * 		passed to a method.
	 */
	public Value getDefaultValue()
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
	public void setDefaultValue(Value defaultValue)
	{
		this.defaultValue = defaultValue;
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
		String defaultValue = null;
		
		if (statement.contains("="))
		{
			defaultValue = statement.substring(statement.indexOf('=') + 1).trim();
			statement = statement.substring(0, statement.indexOf('=')).trim();
		}
		
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
		
		Parameter n = null;
		
		if (node instanceof Parameter)
		{
			n = (Parameter)node;
		}
		else
		{
			n = new Parameter(parent, location);
			node.cloneTo(n);
		}
		
		if (defaultValue != null)
		{
			n.defaultValueString = defaultValue;
		}
		
		return n;
	}
	
	@Override
	public void replace(Node old, Node replacement)
	{
		if (old == defaultValue)
		{
			defaultValue = (Value)replacement;
		}
		else
		{
			super.replace(old, replacement);
		}
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
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS && defaultValueString != null)
		{
			defaultValue = SyntaxTree.decodeValue(this, defaultValueString, getLocationIn(), true);
			defaultValue.onAfterDecoded();
			defaultValue.onAdded(this);
			
			defaultValueString = null;
		}
		
		if (defaultValue != null)
		{
			SyntaxTree.validateNodes(defaultValue, phase);
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
	public Parameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Parameter node = new Parameter(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Parameter cloneTo(Parameter node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Parameter} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Parameter cloneTo(Parameter node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
        
		node.defaultValueString = defaultValueString;
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