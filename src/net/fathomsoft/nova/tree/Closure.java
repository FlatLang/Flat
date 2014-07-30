package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Identifier extension that represents the use of a variable
 * type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 5, 2014 at 9:02:42 PM
 * @version	v0.2.21 Jul 30, 2014 at 1:45:00 PM
 */
public class Closure extends Variable
{
	private MethodDeclaration[] declarations;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Closure(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public boolean isSpecial()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		getClosureDeclaration().generateCTypeCast(builder);
		
		if (getMethodDeclaration().isVirtual() && !isVirtualTypeKnown())
		{
			getRootReferenceNode().generateCArgumentReference(builder, this).append("->").append(VTable.IDENTIFIER).append("->");
			
			builder.append(getMethodDeclaration().generateCVirtualMethodName());
		}
		else
		{
			builder.append('&').append(getMethodDeclaration().generateCSourceName());
		}
		
		builder.append(", ");
		
		if (getRootReferenceNode() instanceof ClassDeclaration == false)
		{
			getRootReferenceNode().generateCArgumentReference(builder, this);
		}
		else
		{
			getMethodDeclaration().getParameterList().getObjectReference().generateCNullOutput(builder);
		}
		
		return builder;
	}
	
	/**
	 * Get the ClosureDeclaration instance that corresponds with the
	 * specified Closure.
	 * 
	 * @return The Closure Declaration instance.
	 */
	public ClosureDeclaration getClosureDeclaration()
	{
//		return (ClosureDeclaration)getParentMethod().getParameterList().getParameter(name);
		
		if (isDecoding())
		{
			int argNum = getMethodCall().getArgumentList().getNumChildren();
			
			return (ClosureDeclaration)getMethodCall().getCallableDeclaration().getParameterList().getParameter(argNum);
		}
		
		return (ClosureDeclaration)getMethodCall().getCorrespondingParameter(getRootNode());
	}
	
	/**
	 * Get the MethodCall instance that this Closure is being passed
	 * within.
	 * 
	 * @return The parent MethodCall instance.
	 */
	public MethodCall getMethodCall()
	{
		return (MethodCall)getRootNode().getParent().getAncestorOfType(MethodCall.class, true);
	}
	
	/**
	 * Get the MethodDeclaration that this Closure is representing.
	 * 
	 * @return The MethodDeclaration that is being passed as the closure
	 * 		to the MethodCall.
	 */
	public NovaMethodDeclaration getMethodDeclaration()
	{
		return getMethodDeclaration(getName());
	}
	
	/**
	 * Get the MethodDeclaration that this Closure is representing.
	 * 
	 * @param name The name of the method that may be needed if the
	 * 		specified Node is still being decoded.
	 * @return The MethodDeclaration that is being passed as the closure
	 * 		to the MethodCall.
	 */
	private NovaMethodDeclaration getMethodDeclaration(String name)
	{
		return (NovaMethodDeclaration)getReferenceNode().getTypeClass().getMethod(name, getClosureDeclaration().getParameterList().getTypes());
	}
	
	/**
	 * Decode the given statement into a Closure instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>Person findPerson(String, int)</li>
	 * 	<li>int calculateArea(int, int)</li>
	 * 	<li>void callback()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Closure instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Closure.
	 */
	public static Closure decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		Closure n = new Closure(parent, location);
		
		if (n.decodeName(statement, require))
		{
			return n;
		}
		
		return null;
	}
	
	/**
	 * Decode the name of the Closure. Validate that the closure is a
	 * valid representation for the ClosureDeclaration.
	 * 
	 * @param name The name of the method that is being passed as a
	 * 		closure.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the name decoded successfully.
	 */
	private boolean decodeName(String name, boolean require)
	{
		if (StringUtils.containsChar(name, StringUtils.SYMBOLS_CHARS))
		{
			return false;
		}
		
//		MethodDeclaration declaration = getMethodDeclaration(name);
//		
		declarations = getReferenceNode().getTypeClass().getMethods(name);
		
		if (declarations.length <= 0)
		{
			return SyntaxMessage.queryError("Method '" + name + "' not found", this, require);
		}
		
//		setDeclaration(declaration);
		
		return true;
	}
	
	public Node validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			MethodDeclaration declaration = getMethodDeclaration(declarations[0].getName());
			
			if (declaration == null)
			{
				SyntaxMessage.error("Method '" + declarations[0].getName() + "' is not compatible", this);
			}
			
			setDeclaration(declaration);
			validateType(declaration);
			
			if (declaration.isInstance())
			{
				SyntaxUtils.validateMethodAccess(getReferenceNode(), declaration, this);
			}
		}
		
		return this;
	}
	
	/**
	 * Validate that the given MethodDeclaration is compatible with
	 * the ClosureDeclaration.
	 * 
	 * @param method The MethodDeclaration that is being passed as
	 * 		a Closure.
	 */
	private void validateType(MethodDeclaration method)
	{
		ClosureDeclaration declaration = getClosureDeclaration();
		
		if (!declaration.getType().equals("void") && (method.getType().equals("void") || !method.getTypeClass().isOfType(declaration.getTypeClass())))
		{
			SyntaxMessage.error("The method '" + method.getName() + "()' return type of '" + method.getType() + "' is not compatible with required closure type of '" + declaration.getType() + "'", this);
		}
		
		validateParameters(declaration, method);
	}
	
	/**
	 * Validate that the parameters of the MethodDeclaration are
	 * compatible with the ClosureDeclaration.
	 * 
	 * @param declaration The ClosureDeclaration that is required from
	 * 		the Closure method call argument.
	 * @param method The MethodDeclaration that is being passed as
	 * 		a Closure.
	 */
	private void validateParameters(ClosureDeclaration declaration, MethodDeclaration method)
	{
		ParameterList<Value> list1 = declaration.getParameterList();
		ParameterList<Value> list2 = method.getParameterList();
		
		validateNumParameters(method, list1, list2);
		validateIndividualParameters(method, list1, list2);
	}
	
	/**
	 * Validate that the number of parameters of the MethodDeclaration
	 * that is being passed as a Closure equals the number of parameters
	 * required by the ClosureDeclaration.
	 * 
	 * @param method The MethodDeclaration that is being passed as
	 * 		a Closure.
	 * @param list1 The ParameterList of the ClosureDeclaration.
	 * @param list2 The ParameterList of the MethodDeclaration.
	 */
	private void validateNumParameters(MethodDeclaration method, ParameterList<Value> list1, ParameterList<Value> list2)
	{
		if (list1.getNumVisibleChildren() != list2.getNumVisibleChildren())
		{
			if (list1.getNumVisibleChildren() < list2.getNumVisibleChildren())
			{
				SyntaxMessage.error("The method '" + method.getName() + "()' contains too many parameters for the closure", this);
			}
			else
			{
				SyntaxMessage.error("The method '" + method.getName() + "()' contains not enough parameters for the closure", this);
			}
		}
	}
	
	/**
	 * Validate the individual parameters of the MethodDeclaration
	 * to see if they are compatible with the required parameters
	 * of the ClosureDeclaration.
	 * 
	 * @param method The MethodDeclaration that is being passed as
	 * 		a Closure.
	 * @param list1 The ParameterList of the ClosureDeclaration.
	 * @param list2 The ParameterList of the MethodDeclaration.
	 */
	private void validateIndividualParameters(MethodDeclaration method, ParameterList<Value> list1, ParameterList<Value> list2)
	{
		for (int i = 0; i < list1.getNumVisibleChildren(); i++)
		{
			Value value1 = list1.getParameter(i);
			Value value2 = list2.getParameter(i);
			
			if (!value1.getTypeClass().isOfType(value2.getTypeClass()))
			{
				SyntaxMessage.error("Argument " + (i + 1) + " of the method '" + method.getName() + "()' of type '" + value2.getType() + "' is not applicable for required closure type of '" + value1.getType() + "'", this);
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Closure clone(Node temporaryParent, Location locationIn)
	{
		Closure node = new Closure(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Closure} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Closure cloneTo(Closure node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Closure class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}