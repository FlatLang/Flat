package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.MethodList.SearchFilter;
import net.fathomsoft.nova.tree.exceptionhandling.Throw;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
import net.fathomsoft.nova.tree.lambda.LambdaExpression;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.lang.reflect.Method;

/**
 * Value extension that represents the declaration of a method
 * call node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:04:31 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class MethodCall extends Variable
{
	/**
	 * Instantiate a new MethodCall and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public MethodCall(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		MethodCallArgumentList arguments = new MethodCallArgumentList(this, new Location(locationIn));
		
		addChild(arguments);
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
	 * The the Node that represents the arguments to the method call.
	 * For example:<br>
	 * <blockquote><pre>
	 * methodName(5, "Arg2", 3 * n);</pre></blockquote>
	 * In the previous statement, the data within the parenthesis are the
	 * arguments passed to the method. The Argument returned by this
	 * method would contain a node for each of the arguments passed, in
	 * the correct order from left to right.
	 * 
	 * @return The Node that represents the arguments to the method
	 * 		call.
	 */
	public MethodCallArgumentList getArgumentList()
	{
		return (MethodCallArgumentList)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#isVirtualTypeKnown()
	 */
	@Override
	public boolean isVirtualTypeKnown()
	{
		return getParent() instanceof Instantiation || super.isVirtualTypeKnown();
	}
	
	/**
	 * Get whether or not the specified Node is used within an
	 * external context.
	 * 
	 * @return Whether or not the specified Node is used within an
	 * 		external context.
	 */
	public boolean isWithinExternalContext()
	{
		CallableMethod method = null;
		
		if (isDecoding())
		{
			ClassDeclaration declaring = getDeclaringClass();
			
			if (declaring != null)
			{
				MethodDeclaration methods[] = declaring.getMethods(getName());
				
				if (methods.length > 0)
				{
					method = methods[0];
				}
			}
		}
		else
		{
			method = getCallableDeclaration();
		}
		
		return method != null && method.isExternal() || super.isWithinExternalContext();
	}
	
	@Override
	public boolean isConstant()
	{
		return false;
	}
	
	@Override
	public boolean isConsistent()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#isSpecial()
	 */
	@Override
	public boolean isSpecial()
	{
		/*if (getCallableDeclaration().isVirtual())
		{
			if (isVirtualTypeKnown())//isAccessed() && ((Value)getAccessingNode()).isVirtualTypeKnown())
			{
				return true;
			}
			
			return false;
		}*/
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#isSpecialFragment()
	 */
	@Override
	public boolean isSpecialFragment()
	{
		/*if (getCallableDeclaration().isVirtual() && !isVirtualTypeKnown())
		{
			return false;
		}*/
		
		return /*doesAccess() && getAccessedNode() instanceof MethodCall;*/super.isSpecialFragment();
	}
	
	/**
	 * Get the Method instance that this MethodCall is calling in the
	 * form of a CallableMethod.
	 * 
	 * @return The Method instance that this MethodCall is
	 * 		calling.
	 */
	public CallableMethod getCallableDeclaration()
	{
		return (CallableMethod)getMethodDeclaration();
	}
	
	/**
	 * Get the Method instance that this MethodCall is calling.
	 * 
	 * @return The Method instance that this MethodCall is
	 * 		calling.
	 */
	private VariableDeclaration searchMethodDeclaration()
	{
		return searchMethodDeclaration(getName());
	}
	
	private VariableDeclaration searchMethodDeclaration(String name)
	{
		ClosureDeclaration closure = searchClosureDeclaration(name);
		
		if (closure != null)
		{
			return closure;
		}
		
		ClassDeclaration declaring = getDeclaringClass();
		
		if (declaring == null)
		{
			return null;
		}
		
		// If constructor
		if (getFileDeclaration().getImportList().getAbsoluteClassLocation(name) != null)
		{
			setName(getFileDeclaration().getImportedClass(declaring, name).getName());
		}
		
		return declaring.getMethod(getContext(), name, getArgumentList().getTypes());
	}
		
	/**
	 * Get the Method instance that this MethodCall is calling.
	 * 
	 * @return The Method instance that this MethodCall is
	 * 		calling.
	 */
	public VariableDeclaration getMethodDeclaration()
	{
		return (VariableDeclaration)getDeclaration();
	}
	
	private ClosureDeclaration searchClosureDeclaration(String name)
	{
		if (getParentMethod() == null)
		{
			return null;
		}
		
		Parameter param = getParentMethod().getParameter(name);
		
		if (param instanceof ClosureDeclaration)
		{
			return (ClosureDeclaration)param;
		}
		
		return null;
	}

	private ClosureDeclaration getClosureDeclaration()
	{
		VariableDeclaration declaration = getDeclaration();
		
		if (declaration instanceof ClosureDeclaration)
		{
			return (ClosureDeclaration)declaration;
		}
		
		return null;
	}
	
	public boolean isClosureCall()
	{
		return getClosureDeclaration() != null;
	}
	
	/**
	 * Get the ClassDeclaration instance that declares the method that
	 * this MethodCall is calling.
	 * 
	 * @return The Class that this MethodCall's declaration is declared
	 * 		in.
	 */
	public ClassDeclaration getDeclaringClass()
	{
		if (getFileDeclaration().getImportList().getAbsoluteClassLocation(getName()) != null)
		{
			return SyntaxUtils.getImportedClass(getFileDeclaration(), getName());
		}
		
		return ((Value)getReferenceNode()).getTypeClass();
	}
	
	/**
	 * Get the name of the object reference identifier for the given
	 * MethodCall's method node. Static methods return
	 * "__static__ClassName" and non-static methods return "this".
	 * The call cannot be that of an external method.
	 * 
	 * @return The name of the object reference identifier.
	 */
	public String getObjectReferenceIdentifier()
	{
		return getObjectReferenceIdentifier(getCallableDeclaration());
	}
	
	/**
	 * Get the Value that the method was called with for the given
	 * MethodCall's method node, if it was not called with a specific
	 * object. Static methods return "__static__ClassName" and non-static
	 * methods return "this". The call cannot be that of an external
	 * method.
	 * 
	 * @return The Value that the method was called with.
	 */
	public Value getObjectReferenceValue()
	{
		return getObjectReferenceNode(getCallableDeclaration());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getReferenceNode()
	 */
	@Override
	public Accessible getReferenceNode()
	{
		Accessible ref = super.getReferenceNode();
		
		if (ref.getParent() instanceof Instantiation)
		{
			return (Instantiation)ref.getParent();
		}
		
		return ref;
	}
	
	/*public Accessible getAccessingNode()
	{
		Accessible a = super.getAccessingNode();
		
		if (a instanceof Instantiation)
		{
			return null;
		}
		
		return a;
	}*/
	
	/**
	 * Get the Parameter that the given argument represents.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void run(int a, int b, int c)
	 * {
	 * 	...
	 * }
	 * 
	 * run(432, 1, 5);</pre></blockquote>
	 * If you were to call getCorrespondingParameter(1) on the method
	 * call above, you would receive the b Parameter.
	 * 
	 * @param argument The argument to get the corresponding parameter
	 * 		from.
	 * @return The Parameter that represents the given argument.
	 */
	public Value getCorrespondingParameter(Value argument)
	{
		ArgumentList args = getArgumentList();
		
		for (int i = 0; i < args.getNumChildren(); i++)
		{
			Value abstractValue = (Value)args.getChild(i);
			
			if (abstractValue == argument)
			{
				return getCorrespondingParameter(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Get the Parameter that the given index represents. The
	 * parameters are ordered from left to right, 0 being the first.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void run(int a, int b, int c)
	 * {
	 * 	...
	 * }
	 * 
	 * run(432, 1, 5);</pre></blockquote>
	 * If you were to call getCorrespondingParameter(2) on the method
	 * call above, you would receive the c Parameter.
	 * 
	 * @param argIndex The index of the argument to get the corresponding
	 * 		parameter from.
	 * @return The Parameter at the given index.
	 */
	public Value getCorrespondingParameter(int argIndex)
	{
		return getCallableDeclaration().getParameterList().getParameter(argIndex);
	}
	
	@Override
	public String generateGenericType()
	{
		StringBuilder builder = new StringBuilder();
		
		GenericTypeArgumentList args = getGenericTypeArgumentList();
		
		if (args.getNumVisibleChildren() > 0)
		{
			builder.append(GenericCompatible.GENERIC_START);
			
			for (int i = 0; i < args.getNumVisibleChildren(); i++)
			{
				if (i > 0)
				{
					builder.append(", ");
				}
				
				getIntelligentGenericTypeArgument(i).generateNovaInput(builder);
			}
			
			builder.append(GenericCompatible.GENERIC_END);
		}
		else
		{
			return super.generateGenericType();
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCArgumentReference(StringBuilder, Identifier)
	 */
	@Override
	public StringBuilder generateCArgumentReference(StringBuilder builder, Identifier callingMethod)
	{
		// TODO: there is a better way. <-- i'll get to it in the re-write
		if (getCallableDeclaration().isVirtual() && isAccessed() && !isVirtualTypeKnown())
		{
			Accessible node = getAccessingNode().getLastAccessingOfType(new Class<?>[] { MethodCall.class, Closure.class }, true);
			
			if (node != null)
			{
				node.generateCSourceUntil(builder, "->", this);
			}
		}
		
		return super.generateCArgumentReference(builder, callingMethod);
	}
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder).append(';').append('\n');
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	public StringBuilder generatedCSourceFragment(StringBuilder builder, boolean checkSpecial)
	{
		if (checkSpecial && isSpecialFragment())
		{
			return generateSpecialFragment(builder);
		}
		
		return generateCUseOutput(builder);
	}
	
	/**
	 * Generate a String representing the output of the children of the
	 * MethodCall.
	 * 
	 * @return A String representing the output of the children of the
	 * 		MethodCall.
	 */
	public StringBuilder generateChildrenCSourceFragment(StringBuilder builder)
	{
		for (int i = 1; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append("->");
			
			child.generateCSourceFragment(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateCTypeName(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCTypeName(StringBuilder builder)
	{
		if (isPrimitiveGenericType())
		{
			return generateCTypeClassName(builder);
		}
		
		return super.generateCTypeName(builder);
	}
	
	/**
	 * Generate the representation of when the method call is being used
	 * in action.
	 * 
	 * @see net.fathomsoft.nova.tree.Identifier#generateCUseOutput(java.lang.StringBuilder, boolean)
	 * 
	 * @return What the method call looks like when it is being used in
	 * 		action.
	 */
	@Override
	public StringBuilder generateCUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
		VariableDeclaration method   = getMethodDeclaration();
		CallableMethod      callable = (CallableMethod)method;
		
		/*if (callable.isVirtual() && !isVirtualTypeKnown())
		{
			NovaMethodDeclaration novaMethod = (NovaMethodDeclaration)method;
			
			if (!isAccessed())
			{
				builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->");
			}
			
			if (getParent() instanceof Variable)
			{
				//((Variable)getParent()).generateCUseOutput(builder).append("->");
			}
			
			builder.append(VTable.IDENTIFIER).append("->");
			
			if (method.getParentClass() instanceof Interface)
			{
				builder.append(InterfaceVTable.IDENTIFIER).append(".");
			}
			
			builder.append(novaMethod.generateCVirtualMethodName());
		}
		else*/
		{
			method.generateCSourceName(builder);
		}
		
		builder.append(getArgumentList().generateCSource());
		
		/*if (checkAccesses && isGenericType() && doesAccess())
		{
			builder.append(')');
		}*/
		
		return builder;
	}
	
	@Override
	public GenericTypeParameterDeclaration getGenericTypeParameterDeclaration()
	{
		GenericCompatible gen = getGenericCompatibleDeclaration(false);
		
		if (gen == null || !(gen instanceof Value))
		{
			return null;
		}
		
		return ((Value)gen).getGenericTypeParameterDeclaration();
	}
	
	public GenericCompatible getGenericCompatibleDeclaration()
	{
		return getGenericCompatibleDeclaration(true);
	}
	
	public Variable getGenericCompatible()
	{
		return getGenericCompatible(true);
	}
	
	/**
	 * 
	 * 
	 * @param throwException Whether or not to throw an exception if a
	 * 		generic type cannot be found.
	 * @return The 
	 */
	private GenericCompatible getGenericCompatibleDeclaration(boolean throwException)
	{
		return getGenericCompatible(throwException).getDeclaration();
	}
	
	private Variable getGenericCompatible(boolean throwException)
	{
		Node last = getLastAncestorOfType(new Class[] { MethodCallArgumentList.class, Variable.class, Instantiation.class }, false);
		
		if (last instanceof Variable)
		{
			Variable var = (Variable)last;
			
			if (var.getDeclaration() instanceof MutatorMethod)
			{
				return var;//.getDeclaringClass().getField(var.getName());
			}
		}
		
		Assignment assign = null;
		
		if (getParent() instanceof Instantiation && getParent().getParent() instanceof Assignment)
		{
			assign = (Assignment)getParent().getParent();
		}
		if (assign != null)
		{
			Value val = assign.getAssigneeNode();
			
			if (val instanceof Variable)
			{
				return ((Variable)val);//.getDeclaration();
			}
		}
		
		Accessible identifier = getReferenceNode();
		
		if (identifier instanceof Variable)
		{
			/*VariableDeclaration decl = ((Variable)identifier).getDeclaration();
			
			if (decl instanceof Parameter && ((Parameter)decl).isObjectReference())
			{
				return decl.getTypeClass();
			}*/
			
			return (Variable)identifier;//decl;
		}
		
		if (throwException)
		{
			SyntaxMessage.error("Unable to determine generic type declaration for method call '" + getName() + "'", this);
		}
		
		return null;
	}
	
	@Override
	public String getGenericReturnType()
	{
		VariableDeclaration method  = getMethodDeclaration();
		GenericCompatible   generic = getGenericCompatibleDeclaration();
		
		if (method.isGenericType())
		{
			GenericTypeArgument arg = generic.getGenericTypeArgumentInstance(method.getType(), this, false);
			
			if (arg != null)
			{
				return arg.getGenericReturnType();
			}
			else if (getParentMethod().containsGenericTypeParameter(method.getType()))
			{
				return getParentMethod().getGenericTypeParameter(method.getType()).getDefaultType();
			}
			else
			{
				SyntaxMessage.error("Invalid generic type '" + method.getType() + "'", this);
			}
		}
		
		return super.getGenericReturnType();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.Variable#getName()
	 */
	@Override
	public String getName()
	{
		if (Constructor.IDENTIFIER.equals(super.getName()))
		{
			return getDeclaration().getParentClass().getName();
		}
		
		return super.getName();
	}
	
	public Value[] getTypes()
	{
		return getMethodDeclaration().getTypes();
	}
	
	@Override
	public GenericCompatible getContext()
	{
		return getReferenceNode().getContext();
	}
	
	/**
	 * Decode the given statement into a MethodCall instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * To determine whether or not a method is called externally,
	 * refer to {@link #isExternal()} for more details on what an
	 * external call looks like.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>methodName(5, varName, methodThatReturnsAValue(), "arg1")</li>
	 * 	<li>externalFile.cFunctionName()</li>
	 * 	<li>methodName('q', 5 * (2 / 3))</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodCall instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCall.
	 */
	public static MethodCall decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		return decodeStatement(parent, statement, location, require, true);
	}
	
	/**
	 * Decode the given statement into a MethodCall instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * To determine whether or not a method is called externally,
	 * refer to {@link #isExternal()} for more details on what an
	 * external call looks like.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>methodName(5, varName, methodThatReturnsAValue(), "arg1")</li>
	 * 	<li>externalFile.cFunctionName()</li>
	 * 	<li>methodName('q', 5 * (2 / 3))</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodCall instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCall.
	 */
	public static MethodCall decodeStatement(Node parent, String statement, Location location, boolean require, boolean validateAccess)
	{
		return decodeStatement(parent, statement, location, require, validateAccess, null);
	}
	
	/**
	 * Decode the given statement into a MethodCall instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * To determine whether or not a method is called externally,
	 * refer to {@link #isExternal()} for more details on what an
	 * external call looks like.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>methodName(5, varName, methodThatReturnsAValue(), "arg1")</li>
	 * 	<li>externalFile.cFunctionName()</li>
	 * 	<li>methodName('q', 5 * (2 / 3))</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodCall instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param validateAccess Whether or not to check if method call can be
	 * 		accessed legally.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCall.
	 */
	public static MethodCall decodeStatement(Node parent, String statement, Location location, boolean require, boolean validateAccess, CallableMethod callableMethod)
	{
		if (SyntaxUtils.isMethodCall(statement))
		{
			MethodCall n  = new MethodCall(parent, location);
			
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);

			MethodData data = new MethodData();
			
			if (!n.decodeMethodName(statement, bounds, data, require))
			{
				return null;
			}
			
			VariableDeclaration temp = null;
			
			if (callableMethod == null)
			{
				temp = new VariableDeclaration(null, Location.INVALID);
				temp.setName(data.name);
				n.setDeclaration(temp);
			}
			else
			{
				n.setDeclaration((VariableDeclaration)callableMethod);
			}
			
			if (!n.decodeArguments(statement, bounds, require))
			{
				return null;
			}
			
			if (callableMethod == null)
			{
				callableMethod = (CallableMethod)n.searchMethodDeclaration(data.name);
			}
			
			n.setDeclaration((VariableDeclaration)callableMethod);
			
			CallableMethod method = n.getCallableDeclaration();
			
			if (method == null)
			{
				n.searchMethodDeclaration(data.name);
				SyntaxMessage.error("Undeclared method '" + temp.getName() + "'", n);
			}
			
			if (!n.validateArguments(n.getFileDeclaration(), n.getLocationIn(), require))
			{
				return null;
			}
			
			if (validateAccess)
			{
				n.validateAccess(method);
			}
			
			for (int i = 0; i < n.getArgumentList().getNumVisibleChildren(); i++)
			{
				n.getArgumentList().getVisibleChild(i).onAfterDecoded();
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * Decode the name of the method call. Check that it is all up to
	 * flying squid.
	 * 
	 * @param statement The method call statement.
	 * @param bounds The bounds of the arguments.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the method name decoded correctly.
	 */
	private boolean decodeMethodName(String statement, Bounds bounds, MethodData data, boolean require)
	{
		// Parenthesis index
		int    parenIndex = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getStart() - 1, -1);
		int    nameEnd    = StringUtils.findNextNonWhitespaceIndex(statement, parenIndex - 1, -1) + 1;
		
		String methodCall = statement.substring(0, nameEnd);
		
		try
		{
			String error = iterateWords(methodCall, Patterns.IDENTIFIER_BOUNDARIES, data, require).error;
			
			if (error != null)
			{
				return false;
			}
		}
		catch (SyntaxErrorException e)
		{
			if (!require)
			{
				return false;
			}
			
			throw e;
		}
		
		return true;
	}
	
	/**
	 * Make sure that the method call is authorized to be made. Check if
	 * the method is visible and that it is referenced in the correct
	 * context. (e.g. Accessing a non-static method within a static
	 * context)
	 * 
	 * @param method The MethodDeclaration that this method call is
	 * 		calling.
	 */
	private void validateAccess(CallableMethod method)
	{
		if (method instanceof ClosureDeclaration)
		{
			return;
		}
		
		if (!SyntaxUtils.isVisible(getParentClass(), ((MethodDeclaration)method)))
		{
			SyntaxMessage.error("Method '" + method.getName() + "' is not visible", this);
		}
	}
	
	/**
	 * Decode all of the given arguments to the method call. Return
	 * true if it was successfully decoded.
	 * 
	 * @param statement The method call statement.
	 * @param bounds The bounds of the arguments.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return Whether or not the arguments decoded successfully.
	 */
	private boolean decodeArguments(String statement, Bounds bounds, boolean require)
	{
		String argumentList = statement.substring(bounds.getStart(), bounds.getEnd());
		
		if (argumentList.length() <= 0)
		{
			return true;
		}
		
		String arguments[] = StringUtils.splitCommas(argumentList);

		Location argsLocation = new Location(getLocationIn());
		argsLocation.addBounds(bounds.getStart(), bounds.getEnd());
		
		addArguments(arguments, argsLocation);
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, net.fathomsoft.nova.util.Bounds, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		MethodData data = (MethodData)extra;
		
		if (extra.isLastWord() && leftDelimiter.length() == 0)
		{
			data.name = word;
		}
		else
		{
			String characters = null;
			
			if (leftDelimiter.length() > 0)
			{
				characters = leftDelimiter;
			}
			else if (rightDelimiter.length() > 0)
			{
				characters = rightDelimiter;
			}
			else
			{
				SyntaxMessage.error("Could not decode method name", this);
			}
			
			extra.error = "Unknown characters '" + characters + "'";
			
			SyntaxMessage.queryError(extra.error, this, extra.require);
		}
	}
	
	/**
	 * Check to see if the arguments passed to the method call
	 * fulfills the required parameters of the method declaration.
	 * 
	 * @param fileDeclaration The FileDeclaration that this method call is within.
	 * @param location The location of the arguments that are being
	 * 		passed.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return True if the method call's arguments fulfill the
	 * 		requirements of the Method declaration's parameters.
	 */
	private boolean validateArguments(FileDeclaration fileDeclaration, Location location, boolean require)
	{
		CallableMethod methodDeclaration = getCallableDeclaration();
		
		if (methodDeclaration == null)
		{
			return SyntaxMessage.queryError("Incompatible arguments for method call '" + getName() + "'", this, require);
		}
		
		ParameterList<Value> parameters = methodDeclaration.getParameterList();
		ArgumentList         arguments  = getArgumentList();
		
		if (parameters.getNumVisibleChildren() != arguments.getNumVisibleChildren())
		{
			if (parameters.getNumVisibleChildren() > arguments.getNumVisibleChildren())
			{
				SyntaxMessage.error("Too few arguments to method call '" + getName() + "'", this);
			}
			else
			{
				SyntaxMessage.error("Too many arguments to method call '" + getName() + "'", this);
			}
		}
		
		return methodDeclaration.areCompatibleParameterTypes(getContext(), arguments.getTypes());
	}
	
	/**
	 * Decode and add the arguments given within the array into Nodes that
	 * are translatable into C.
	 * 
	 * @param arguments The arguments to decode and then add.
	 * @param location The location of the method call in the source code.
	 */
	private void addArguments(String arguments[], Location location)
	{
		Node parent = getArgumentList();
		
		for (int i = 0; i < arguments.length; i++)
		{
			String argument = arguments[i];
			
			if (argument.length() > 0)
			{
				Node arg = LambdaExpression.decodeStatement(parent, argument, location, false);
				
				if (arg == null)
				{
					arg = Literal.decodeStatement(parent, argument, location, true, true);
					
					if (arg == null)
					{
						arg = BinaryOperation.decodeStatement(parent, argument, location, false);
						
						if (arg == null)
						{
							arg = SyntaxTree.decodeScopeContents(parent, argument, location, false);
							
							if (arg == null)
							{
								if (arg == null)
								{
									arg = Array.decodeStatement(parent, argument, location, false);
									
									if (parent.isWithinExternalContext())
									{
										arg = Literal.decodeStatement(parent, argument, location, true);
									}
									
									if (arg == null)
									{
										validateCharacters(parent, argument, location);
										
										SyntaxMessage.error("Could not decode argument '" + argument + "'", parent, location);
									}
								}
							}
						}
					}
				}
				
				parent.addChild(arg);
			}
			else
			{
				SyntaxMessage.error("Expected an argument definition", this);
			}
		}
	}
	
	/**
	 * Validate that the characters in the given argument are valid
	 * identifier-type characters with no symbols.
	 * 
	 * @param parent The MethodCallArgumentList Node that the argument
	 * 		was being added to.
	 * @param argument The argument String to validate.
	 * @param location The location of the argument in the source text.
	 */
	private void validateCharacters(Node parent, String argument, Location location)
	{
		String prefix  = StringUtils.findGroupedSymbols(argument, 0);
		String postfix = StringUtils.findGroupedSymbols(argument, argument.length() - 1, -1);
		
		if (prefix.length() > 0)
		{
			SyntaxMessage.error("Unknown symbol" + (prefix.length() > 1 ? "s" : "") + " '" + prefix + "'", parent, location);
		}
		else if (postfix.length() > 0)
		{
			SyntaxMessage.error("Unknown symbol" + (postfix.length() > 1 ? "s" : "") + " '" + postfix + "'", parent, location);
		}
	}
	
	/**
	 * Get the Nova input representation of this Method Call node.
	 * That is, the String that the decoder method decoded to attain
	 * this node.
	 * 
	 * @param outputChildren Whether or not to output the children of the
	 * 		children of the Node as well.
	 * @return The String representing the method call in Nova syntax.
	 */
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getName()).append('(').append(getArgumentList().generateNovaInput()).append(')');
		
		if (outputChildren && doesAccess())
		{
			builder.append('.').append(getAccessedNode().generateNovaInput());
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}

		Identifier returned  = this;
		
		Accessible reference = returned.getReferenceNode();
		Accessible accessing = returned.getAccessingNode();
		Identifier accessed  = returned.getAccessedNode();
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (isPrimitiveGenericType())
			{
				if (accessed instanceof Variable)
				{
					if (accessed.getName().equals("value"))
					{
						return result;
					}
				}
				
				String input = returned.generateNovaInputUntil(returned) + ".value";
				
				if (accessed != null)
				{
					input += "." + accessed.generateNovaInput();
				}
				
				Identifier value = (Identifier)SyntaxTree.decodeIdentifierAccess(getParent(), input, getLocationIn(), false, false);
				
				if (value == null)
				{
					return result.errorOccurred();
				}
				
				returned.getParent().replace(returned, value);
				
				result.returnedNode = value;
				
				return result;
				
//				setDataType(VALUE);
			}
			
			if (accessing instanceof Variable)
			{
				Variable var = (Variable)accessing;
				
				if (var.isPrimitiveType() && var.getTypeClass() != null)
				{
					SearchFilter filter  = new SearchFilter();
					filter.checkAncestor = false;
					filter.checkStatic   = true;
					filter.staticValue   = true;
					
					if (var.getTypeClass().getMethods(getName(), filter).length > 0)
					{
						Value staticCall = SyntaxUtils.replaceWithPrimitiveFacade(this);
						
						if (staticCall == null)
						{
							return result.errorOccurred();
						}
						
						result.returnedNode = staticCall;
					}
					else if (!(var.getParent() instanceof Instantiation))
					{
						Instantiation instantiation = SyntaxUtils.replaceWithAutoboxPrimitive(var);
						
						result.returnedNode = instantiation;
					}
					
					return result;
				}
			}
		}
		else if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (localDeclarationRequired())
			{
				if (reference instanceof Instantiation)
				{
					reference = reference.getAccessedNode();
				}
				
				MethodCall calling   = (MethodCall)reference;
				Node ancestor = returned.getAncestorWithScope();
				Node addBefore = calling.getBaseNode();
				
				/*if (ancestor instanceof ForEachLoop)
				{
					ForEachLoop loop = (ForEachLoop)ancestor;
					
					Accessible root = getContextNode();
					
					if (loop.getIteratorValue() == root)
					{
						ancestor = ancestor.getParent().getAncestorWithScope();
						addBefore = loop;
					}
				}*/
				
				Variable replacement = ancestor.getScope().registerLocalVariable(calling, addBefore, false);
				
				if (replacement == null)
				{
					return result.errorOccurred();
				}
				
				Node replacing = (Node)calling.getRootReferenceNode(true);
				
				replacing.replaceWith(replacement);
				
				replacement.setAccessedNode(this);
				
				result.returnedNode = this;
				
				return result;
			}
		}
		
		return result;
	}
	
	public CallableMethod getRootDeclaration()
	{
		if (getDeclaration() instanceof NovaMethodDeclaration)
		{
			return ((NovaMethodDeclaration)getDeclaration()).getRootDeclaration();
		}
		
		return (CallableMethod)getDeclaration();
	}
	
	/**
	 * Check to see if a local declaration is required to keep a virtual
	 * method from being called too many times.
	 * 
	 * @return Whether or not a local declaration for a virtual method
	 * 		call is required.
	 */
	private boolean localDeclarationRequired()
	{
		return false;//getCallableDeclaration().isVirtual() && !isVirtualTypeKnown() && isAccessed() && getAccessingNode() instanceof MethodCall;// && !getAccessingNode().isVirtualTypeKnown();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public MethodCall clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		MethodCall node = new MethodCall(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public MethodCall cloneTo(MethodCall node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link MethodCall} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCall cloneTo(MethodCall node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the MethodCall class type to make sure everything
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
	public static class MethodData extends ExtraData
	{
		private String name;
	}
}