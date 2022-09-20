package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.variables.Variable;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Closure extends Variable
{
	public MethodDeclarationValidator[] declarations;
	
	public ClosureDeclarationValidator closureDeclaration;
	
	public Variable variable;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public Closure(NodeValidator temporaryParent, Location locationIn)
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
	 * @see NodeValidator#isSpecial()
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
	public ClosureDeclarationValidator getClosureDeclaration()
	{
//		return (ClosureDeclaration)getParentMethod().getParameterList().getParameter(name);
		
		if (closureDeclaration != null)
		{
			return closureDeclaration;
		}
		else if (isDecoding() && getMethodCall() != null)
		{
			int argNum = getMethodCall().getArgumentList().getArgumentsInOrder().length;
			
			return (ClosureDeclarationValidator)getMethodCall().getInferredDeclaration().getParameterList().getParameter(argNum);
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
	public AccessibleValidator getCArgumentReferenceContext()
	{
		return this;
	}
	
	/**
	 * Get the MethodDeclaration that this Closure is representing.
	 * 
	 * @return The MethodDeclaration that is being passed as the closure
	 * 		to the MethodCall.
	 */
	public FlatMethodDeclarationValidator getMethodDeclaration()
	{
		if (getDeclaration() != null)
		{
			return (FlatMethodDeclarationValidator)getDeclaration();
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
	private FlatMethodDeclarationValidator getMethodDeclaration(String name)
	{
		return getMethodDeclaration((GenericCompatible)null, name);
	}
	
	private FlatMethodDeclarationValidator getMethodDeclaration(GenericCompatible context, String name)
	{
		return getMethodDeclaration(context, name, null);
	}
	
	private FlatMethodDeclarationValidator getMethodDeclaration(GenericCompatible context, String name, ClassDeclarationValidator declaringClass)
	{
		return getMethodDeclaration(context == null ? null : new GenericCompatible[] { context }, name, declaringClass);
	}
	
	private FlatMethodDeclarationValidator getMethodDeclaration(GenericCompatible[] contexts, String name)
	{
		return getMethodDeclaration(contexts, name, null);
	}
	
	private FlatMethodDeclarationValidator getMethodDeclaration(GenericCompatible[] contexts, String name, ClassDeclarationValidator declaringClass)
	{
		MethodList.SearchFilter filter = new MethodList.SearchFilter();
		filter.staticValue = true;
		filter.allowMoreParameters = true;
		
		if (declaringClass == null)
		{
			if (getParentMethod() instanceof ExtensionMethodDeclarationValidator)
			{
				declaringClass = getParentClass();
			}
			else
			{
				declaringClass = ((ValueValidator)getReferenceNode()).getTypeClass();
			}
		}
		
		ClosureDeclarationValidator closure = getClosureDeclaration();
		
		if (closure != null)
		{
			return (FlatMethodDeclarationValidator)declaringClass.getMethod(contexts, name, filter, closure.getParameterList().getTypes(), false, true);
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
	public static Closure decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, null);
	}
	
	public static Closure decodeStatement(NodeValidator parent, String statement, Location location, boolean require, ClassDeclarationValidator declaringClass)
	{
		Closure n = new Closure(parent, location);
		
		if (n.decodeName(statement, require, declaringClass))
		{
			return n;
		}
		
		return null;
	}
	
	@Override
	public Type getTypeObject()
	{
		if (closureDeclaration instanceof FirstClassClosureDeclarationValidator)
		{
			return ((FirstClassClosureDeclarationValidator)closureDeclaration).reference.getTypeObject();
		}
		
		return super.getTypeObject();
	}
	
	@Override
	public String getType(boolean checkCast)
	{
		if (closureDeclaration instanceof FirstClassClosureDeclarationValidator)
		{
			return ((FirstClassClosureDeclarationValidator)closureDeclaration).reference.getType(checkCast);
		}
		
		return super.getType(checkCast);
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
	private boolean decodeName(String name, boolean require, ClassDeclarationValidator declaringClass)
	{
		if (StringUtils.containsChar(name, StringUtils.SYMBOLS_CHARS))
		{
			return false;
		}
		
		if (declaringClass == null)
		{
			if (getParentMethod() instanceof ExtensionMethodDeclarationValidator)
			{
				declaringClass = getParentClass();
			}
			else
			{
				ValueValidator reference = (ValueValidator)getReferenceNode();
				
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
	
	public ClosureDeclarationValidator searchClosureDeclaration()
	{
		if (getParent() instanceof Parameter)
		{
			Parameter param = (Parameter)getParent();
			
			if (param.getDefaultValue() == this)
			{
				return (ClosureDeclarationValidator)param;
			}
		}
		
		NodeValidator base = getBaseNode();
		
		if (getMethodCall() == null)
		{
			if (base instanceof AssignmentValidator)
			{
				AssignmentValidator a = (AssignmentValidator)base;
				
				if (this == a.getAssignmentNode().getReturnedNode())
				{
					if (a.getAssignedNodeValue() instanceof Variable &&
						a.getAssignedNode().declaration instanceof LocalDeclarationValidator)
					{
						LocalDeclarationValidator local = (LocalDeclarationValidator)a.getAssignedNode().declaration;
						
						if (local.isImplicit())
						{
							ClassDeclarationValidator ref = getReferenceNode().toValue().getTypeClass();
							
							if (ref != null)
							{
								ArrayList<MethodDeclarationValidator> methods = new ArrayList<>(Arrays.asList(ref.getMethods(declarations[0].getName())));
								
								methods = ClassDeclarationValidator.filterOverrides(methods);
								
								if (methods.size() > 1)
								{
									SyntaxMessage.error("Ambiguous function '" + methods.get(0).getName() + "' required for function reference", this);
								}
								else if (methods.size() == 1)
								{
									FlatMethodDeclarationValidator method = (FlatMethodDeclarationValidator)methods.get(0);
									
									String type = method.getFunctionReferenceType();
									
									local.setType(type);
									
									return ((FunctionType)local.getTypeObject()).closure;
								}
							}
						}
					}
				}
			}
			
			if (base instanceof Return || parent instanceof FlatMethodDeclarationValidator)
			{
				Type type = getParentMethod().getTypeObject();
				
				if (type instanceof FunctionType)
				{
					return ((FunctionType)type).closure;
				}
			}
			
			return null;
		}
		
		Parameter param = (Parameter)getMethodCall().getCorrespondingParameter((ValueValidator)getRootNode());
		
		if (param instanceof ClosureDeclarationValidator)
		{
			return (ClosureDeclarationValidator)param;
		}
		
		if (base instanceof ValueValidator && parent instanceof MethodCallArgumentListValidator)
		{
			ValueValidator value = (ValueValidator)base;
			MethodCall call = (MethodCall)parent.parent;
			
			FlatMethodDeclarationValidator method = call.getFlatMethod();
			
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
	
	public boolean findDeclaration(ClassDeclarationValidator declaringClass)
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
//					FlatMethodDeclaration method = (FlatMethodDeclaration)m;
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
		
		MethodDeclarationValidator declaration = getMethodDeclaration(call, declarations[0].getName(), declaringClass);
		
		if (declaration == null)
		{
			if (declarations[0] instanceof LambdaMethodDeclaration)
			{
				declaration = declarations[0];
			}
			else
			{
				getMethodDeclaration(call, declarations[0].getName(), declaringClass);
				SyntaxMessage.error("Method '" + declarations[0].getName() + "' is not compatible", this);
				
				return false;
			}
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
	 * @see NodeValidator#validate(int)
	 */
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
			if (closureDeclaration instanceof FirstClassClosureDeclarationValidator == false)
			{
				closureDeclaration = searchClosureDeclaration();
			}
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
	private void validateType(MethodDeclarationValidator method)
	{
		ClosureDeclarationValidator declaration = getClosureDeclaration();
		
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
	private void validateParameters(ClosureDeclarationValidator declaration, MethodDeclarationValidator method)
	{
		ParameterList<ValueValidator>     list1 = declaration.getParameterList();
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
	private void validateNumParameters(MethodDeclarationValidator method, ParameterList<ValueValidator> list1, ParameterList<Parameter> list2)
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
	private void validateIndividualParameters(MethodDeclarationValidator method, ParameterList<ValueValidator> list1, ParameterList<Parameter> list2)
	{
		for (int i = 0; i < list2.getNumVisibleChildren(); i++)
		{
			ValueValidator value1 = list1.getParameter(i);
			ValueValidator value2 = list2.getParameter(i);
			
			ClassDeclarationValidator class1 = value1.getFlatTypeValue(getMethodCall()).getTypeClass();
			ClassDeclarationValidator class2 = value2.getFlatTypeValue(getMethodCall()).getTypeClass();
			
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
							ClassDeclarationValidator type = arg.getTypeClass();
							
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
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public Closure clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		Closure node = new Closure(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
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