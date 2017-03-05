package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.MethodList.SearchFilter;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Identifier extension that represents the use of a variable
 * type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 5, 2014 at 9:02:42 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Closure extends Variable
{
	public MethodDeclaration[] declarations;
	
	public ClosureDeclaration closureDeclaration;
	
	public Variable variable;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Closure(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/*public Closure(Variable variable)
	{
		this(variable.getParent(), variable.getLocationIn());
		
		setDeclaration(variable.getDeclaration());
		
		if (variable.getDeclaration() instanceof ClosureDeclaration)
		{
			closureDeclaration = (ClosureDeclaration)variable.getDeclaration();
		}
		
		this.variable = variable;
	}*/
	
	public boolean isLambda()
	{
		return getMethodDeclaration() instanceof LambdaMethodDeclaration;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#isSpecial()
	 */
	@Override
	public boolean isSpecial()
	{
		return true;
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
		
		if (closureDeclaration != null)
		{
			return closureDeclaration;
		}
		else if (isDecoding() && getMethodCall() != null)
		{
			int argNum = getMethodCall().getArgumentList().getArgumentsInOrder().length;
			
			return (ClosureDeclaration)getMethodCall().getInferredDeclaration().getParameterList().getParameter(argNum);
		}
		
		return null;//(ClosureDeclaration)getMethodCall().getCorrespondingParameter((Value)getRootNode());
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
	
	@Override
	public Accessible getCArgumentReferenceContext()
	{
		return this;
	}
	
	/**
	 * Get the MethodDeclaration that this Closure is representing.
	 * 
	 * @return The MethodDeclaration that is being passed as the closure
	 * 		to the MethodCall.
	 */
	public NovaMethodDeclaration getMethodDeclaration()
	{
		if (getDeclaration() != null)
		{
			return (NovaMethodDeclaration)getDeclaration();
		}
		
		return getMethodDeclaration(getMethodCall().getReferenceNode().getContext(), getName());
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
		return getMethodDeclaration((GenericCompatible)null, name);
	}
	
	private NovaMethodDeclaration getMethodDeclaration(GenericCompatible context, String name)
	{
		return getMethodDeclaration(context, name, null);
	}
	
	private NovaMethodDeclaration getMethodDeclaration(GenericCompatible context, String name, ClassDeclaration declaringClass)
	{
		return getMethodDeclaration(context == null ? null : new GenericCompatible[] { context }, name, declaringClass);
	}
	
	private NovaMethodDeclaration getMethodDeclaration(GenericCompatible[] contexts, String name)
	{
		return getMethodDeclaration(contexts, name, null);
	}
	
	private NovaMethodDeclaration getMethodDeclaration(GenericCompatible[] contexts, String name, ClassDeclaration declaringClass)
	{
		SearchFilter filter = new SearchFilter();
		filter.staticValue = true;
		filter.allowMoreParameters = true;
		
		if (declaringClass == null)
		{
			if (getParentMethod() instanceof ExtensionMethodDeclaration)
			{
				declaringClass = getParentClass();
			}
			else
			{
				declaringClass = ((Value)getReferenceNode()).getTypeClass();
			}
		}
		
		ClosureDeclaration closure = getClosureDeclaration();
		
		if (closure != null)
		{
			return (NovaMethodDeclaration)declaringClass.getMethod(contexts, name, filter, closure.getParameterList().getTypes(), false, true);
		}
		
		return null;
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
		return decodeStatement(parent, statement, location, require, null);
	}
	
	public static Closure decodeStatement(Node parent, String statement, Location location, boolean require, ClassDeclaration declaringClass)
	{
		Closure n = new Closure(parent, location);
		
		if (n.decodeName(statement, require, declaringClass))
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
	private boolean decodeName(String name, boolean require, ClassDeclaration declaringClass)
	{
		if (StringUtils.containsChar(name, StringUtils.SYMBOLS_CHARS))
		{
			return false;
		}
		
		if (declaringClass == null)
		{
			if (getParentMethod() instanceof ExtensionMethodDeclaration)
			{
				declaringClass = getParentClass();
			}
			else
			{
				Value reference = (Value)getReferenceNode();
				
				if (reference == null)
				{
					return false;
				}
				
				declaringClass = reference.getTypeClass();
			}
		}
		
		if (declaringClass == null)
		{
			return false;
		}
		
		declarations = declaringClass.getMethods(name);
		
		if (declarations.length <= 0)
		{
			return SyntaxMessage.queryError("Method '" + name + "' not found", this, require);
		}
		
		return true;
	}
	
	public ClosureDeclaration searchClosureDeclaration()
	{
		if (getParent() instanceof Parameter)
		{
			Parameter param = (Parameter)getParent();
			
			if (param.getDefaultValue() == this)
			{
				return (ClosureDeclaration)param;
			}
		}
		
		Node base = getBaseNode();
		
		if (base instanceof Return || parent instanceof NovaMethodDeclaration)
		{
			Type type = getParentMethod().getTypeObject();
			
			if (type instanceof FunctionType)
			{
				return ((FunctionType)type).closure;
			}
		}
		if (getMethodCall() == null)
		{
			return null;
		}
		
		Parameter param = (Parameter)getMethodCall().getCorrespondingParameter((Value)getRootNode());
		
		if (param instanceof ClosureDeclaration)
		{
			return (ClosureDeclaration)param;
		}
		
		if (base instanceof Value && parent instanceof MethodCallArgumentList)
		{
			Value value = (Value)base;
			MethodCall call = (MethodCall)parent.parent;
			
			NovaMethodDeclaration method = call.getNovaMethod();
			
			if (method != null)
			{
				param = method.getParameter(getVisibleIndex());
				
				if (param != null && param.isGenericType())
				{
					GenericTypeArgument arg = param.getGenericTypeParameter().getCorrespondingArgument(call);
					
					if (arg != null && arg.getTypeObject() instanceof FunctionType)
					{
						return ((FunctionType)arg.getTypeObject()).closure;
					}
				}
			}
		}
		
		return null;
	}
	
	@Override
	public boolean onAfterDecoded()
	{
		return findDeclaration();
	}
	
	public boolean findDeclaration()
	{
		return findDeclaration(null);
	}
	
	public boolean findDeclaration(ClassDeclaration declaringClass)
	{
		boolean skipValidation = closureDeclaration != null;
		
		MethodCall call = getMethodCall();
		
		if (call != null)
		{
//			ClassDeclaration type = call.getReferenceNode().toValue().getTypeClass();
//			
//			if (type.isPrimitiveOverload())
//			{
//				MethodDeclaration[] methods = type.getMethods(call.getName());
//				
//				for (MethodDeclaration m : methods)
//				{
//					NovaMethodDeclaration method = (NovaMethodDeclaration)m;
//					
//					method.checkConvertToClass(type);
//				}
//			}
		}
		
		if (closureDeclaration == null)
		{
			closureDeclaration = searchClosureDeclaration();
		}
		
		if (getDeclaration() != null)
		{
			return true;
		}

		MethodDeclaration declaration = getMethodDeclaration(call, declarations[0].getName(), declaringClass);

		if (declaration == null)
		{
			getMethodDeclaration(call, declarations[0].getName(), declaringClass);
			SyntaxMessage.error("Method '" + declarations[0].getName() + "' is not compatible", this);
			
			return false;
		}
		
		setDeclaration(declaration);
		
		if (!skipValidation)
		{
			validateType(declaration);
			
			if (declaration.isInstance())
			{
				SyntaxUtils.validateMethodAccess(getReferenceNode(), declaration, this);
			}
		}
		
		return true;
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
		
		if (variable != null && !variable.isDecoding())
		{
			variable.getParent().replace(variable, this);
			
			variable = null;
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			closureDeclaration = searchClosureDeclaration();
		}
		
		return result;
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
		
		if (declaration.getType() != null && declaration.getTypeObject() instanceof FunctionType == false && (method.getTypeClass(true, true) == null || !method.getTypeClass(true, true).isOfType(declaration.getTypeClass(true, true))))
		{
			method.getTypeClass(true, true).isOfType(declaration.getTypeClass(true, true));
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
		ParameterList<Value>     list1 = declaration.getParameterList();
		ParameterList<Parameter> list2 = method.getParameterList();
		
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
	private void validateNumParameters(MethodDeclaration method, ParameterList<Value> list1, ParameterList<Parameter> list2)
	{
//		if (list1.getNumVisibleChildren() != list2.getNumVisibleChildren())
//		{
			if (list1.getNumVisibleChildren() < list2.getNumVisibleChildren())
			{
				SyntaxMessage.error("The method '" + method.getName() + "()' contains too many parameters for the closure", this);
			}
//			else
//			{
//				SyntaxMessage.error("The method '" + method.getName() + "()' contains not enough parameters for the closure", this);
//			}
//		}
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
	private void validateIndividualParameters(MethodDeclaration method, ParameterList<Value> list1, ParameterList<Parameter> list2)
	{
		for (int i = 0; i < list2.getNumVisibleChildren(); i++)
		{
			Value value1 = list1.getParameter(i);
			Value value2 = list2.getParameter(i);
			
			ClassDeclaration class1 = value1.getNovaTypeValue(getMethodCall()).getTypeClass();
			ClassDeclaration class2 = value2.getNovaTypeValue(getMethodCall()).getTypeClass();
			
			if (class1 == null || !class1.isOfType(class2))
			{
				boolean valid = false;
				
				if (value1.isGenericType())
				{
					GenericTypeParameter param = value1.getGenericTypeParameter();
					
					if (param != null)
					{
						GenericTypeArgument arg = param.getCorrespondingArgument(getMethodCall());
						
						if (arg == null)
						{
							SyntaxMessage.error("Unable to find generic argument", this);
						}
						else
						{
							ClassDeclaration type = arg.getTypeClass();
							
							if (type != null)
							{
								valid = type.isOfType(value2.getTypeClass());
							}
						}
					}
				}
				
				if (!valid)
				{
					SyntaxMessage.error("Argument " + (i + 1) + " of the method '" + method.getName() + "()' of type '" + value2.getType() + "' is not compatible for required closure type of '" + value1.getType() + "'", this, false);
				}
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Closure clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Closure node = new Closure(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Closure cloneTo(Closure node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Closure} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Closure cloneTo(Closure node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.declarations = declarations;
		node.closureDeclaration = closureDeclaration;
		node.variable = variable;
		
		return node;
	}
	
	/**
	 * Test the Closure class type to make sure everything
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