package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Value extension that represents the declaration of a method
 * call node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:04:31 PM
 * @version	v0.2.31 Sep 24, 2014 at 4:41:04 PM
 */
public class MethodCall extends IIdentifier
{
	private boolean	externalCall;
	
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
			MethodDeclaration methods[] = getDeclaringClass().getMethods(getName());
			
			if (methods.length > 0)
			{
				method = methods[0];
			}
		}
		else
		{
			method = getCallableDeclaration();
		}
		
		return method != null && method.isExternal() || super.isWithinExternalContext();
	}
	
	/**
	 * Get whether or not the method is called externally.
	 * A method is external if it begins with an externally imported
	 * C file's name. For example:<br>
	 * <blockquote><pre>
	 * import "externalFile.h";
	 * 
	 * ...
	 * 
	 * public static void main(String args[])
	 * {
	 *	// This is the external method call.
	 * 	externalFile.cFunctionName();
	 * }</pre></blockquote>
	 * In this example, 'externalFile' is the C header file that is
	 * imported. 'cFunctionName()' is the name of a function that
	 * is contained within the imported header file.<br>
	 * 
	 * @return Whether or not the method is called externally.
	 */
	public boolean isExternal()
	{
		return externalCall;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#isSpecial()
	 */
	@Override
	public boolean isSpecial()
	{
		if (getCallableDeclaration().isVirtual())
		{
			if (isAccessed() && getAccessingNode().isVirtualTypeKnown())
			{
				return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#isSpecialFragment()
	 */
	@Override
	public boolean isSpecialFragment()
	{
		if (getCallableDeclaration().isVirtual() && !isVirtualTypeKnown())
		{
			return false;
		}
		
		return super.isSpecialFragment();
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
	public VariableDeclaration getMethodDeclaration()
	{
		VariableDeclaration closure = getClosureDeclaration();
		
		if (closure != null)
		{
			return closure;
		}
		
		return  getDeclaringClass().getMethod(getName(), getArgumentList().getTypes());
	}
	
	private VariableDeclaration getClosureDeclaration()
	{
		Parameter param = getParentMethod().getParameter(getName());
		
		if (param instanceof ClosureDeclaration)
		{
			return param;
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
		if (getFileDeclaration().containsImport(getName()))
		{
			return getProgram().getClassDeclaration(getName());
		}
		
		return getReferenceNode().getTypeClass();
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
	public Identifier getReferenceNode()
	{
		Identifier ref = super.getReferenceNode();
		
		if (ref.getParent() instanceof Instantiation)
		{
			return (Identifier)ref.getParent();
		}
		
		return ref;
	}
	
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
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCArgumentReference(StringBuilder, Identifier)
	 */
	@Override
	public StringBuilder generateCArgumentReference(StringBuilder builder, Identifier callingMethod)
	{
		// TODO: there is a better way.
		if (getCallableDeclaration().isVirtual() && isAccessed() && !isVirtualTypeKnown())
		{
			getAccessingNode().getLastAccessingOfType(new Class<?>[] { MethodCall.class, Closure.class }, true).generateCSourceUntil(builder, "->", this);
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
	 * Generate the representation of when the method call is being used
	 * in action.
	 * 
	 * @see net.fathomsoft.nova.tree.Identifier#generateCUseOutput(java.lang.StringBuilder, boolean)
	 * 
	 * @return What the method call looks like when it is being used in
	 * 		action.
	 */
	@Override
	public StringBuilder generateCUseOutput(StringBuilder builder, boolean pointer)
	{
		VariableDeclaration method   = getMethodDeclaration();
		CallableMethod      callable = (CallableMethod)method;
		
		if (isGenericType())
		{
			builder.append('(');
			generateCTypeCast(builder);
		}
		
		if (callable.isVirtual() && !isVirtualTypeKnown())
		{
			NovaMethodDeclaration novaMethod = (NovaMethodDeclaration)method;
			
			if (!isAccessed())
			{
				builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->");
			}
			
			builder.append(VTable.IDENTIFIER).append("->").append(novaMethod.generateCVirtualMethodName());
		}
		else
		{
			method.generateCSourceName(builder);
		}
		
		builder.append(getArgumentList().generateCSource());
		
		if (isGenericType())
		{
			builder.append(')');
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getGenericType()
	 */
	@Override
	public GenericType getGenericType()
	{
		return getMethodDeclaration().getGenericType();
	}
	
	@Override
	public VariableDeclaration getGenericDeclaration()
	{
		Identifier identifier = getReferenceNode();
		
		if (identifier instanceof Variable)
		{
			Variable variable = (Variable)identifier;
			
			return variable.getDeclaration();
		}
		
		return null;
	}
	
	@Override
	public String getGenericReturnType()
	{
		VariableDeclaration method = getMethodDeclaration();
		VariableDeclaration decl   = getGenericDeclaration();
		
		if (decl != null)
		{
			return decl.getGenericParameterInstance(method.getType()).getType();
		}
		
		return super.getGenericReturnType();
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
		if (SyntaxUtils.isMethodCall(statement))
		{
			Nova.debuggingBreakpoint(statement.equals("put(0, \"Zero\")"));
			
			MethodCall n  = new MethodCall(parent, location);
			
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			if (!n.decodeMethodName(statement, bounds, require) || !n.decodeArguments(statement, bounds, require))
			{
				return null;
			}
			
			CallableMethod method = n.getCallableDeclaration();
			
			if (method == null)
			{
				SyntaxMessage.error("Undeclared method '" + n.getName() + "'", n);
			}
			
			n.validateAccess(method);
			
			// Align the data with the method declaration.
			n.setDataType(method.getDataType());
			n.setType(method.getType());
			n.setArrayDimensions(method.getArrayDimensions());
			n.externalCall = method.isExternal();
			
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
	private boolean decodeMethodName(String statement, Bounds bounds, boolean require)
	{
		// Parenthesis index
		int    parenIndex = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getStart() - 1, -1);
		int    nameEnd    = StringUtils.findNextNonWhitespaceIndex(statement, parenIndex - 1, -1) + 1;
		
		String methodCall = statement.substring(0, nameEnd);
		
		try
		{
			String error = iterateWords(methodCall, Patterns.IDENTIFIER_BOUNDARIES, require).error;
			
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
		
		return validateArguments(getFileDeclaration(), getLocationIn(), require);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#interactWord(java.lang.String, net.fathomsoft.nova.util.Bounds, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.Node.ExtraData)
	 */
	@Override
	public void interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		if (extra.isLastWord() && leftDelimiter.length() == 0)
		{
			setName(word);
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
		
		if (parameters.getNumVisibleChildren() != arguments.getNumChildren())
		{
			if (parameters.getNumVisibleChildren() > arguments.getNumChildren())
			{
				SyntaxMessage.error("Too few arguments to method call '" + getName() + "'", this);
			}
			else
			{
				SyntaxMessage.error("Too many arguments to method call '" + getName() + "'", this);
			}
		}
		
		return methodDeclaration.areCompatibleParameterTypes(arguments.getTypes());
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
				Node arg = Literal.decodeStatement(parent, argument, location, true, true);
				
				if (arg == null)
				{
					arg = BinaryOperation.decodeStatement(parent, argument, location, false);
					
					if (arg == null)
					{
						arg = SyntaxTree.decodeScopeContents(parent, argument, location, false);
						
						if (arg == null)
						{
							validateCharacters(parent, argument, location);
							
							if (parent.isWithinExternalContext())
							{
								arg = Literal.decodeStatement(parent, argument, location, true);
							}
							
							if (arg == null)
							{
								SyntaxMessage.error("Could not decode argument '" + argument + "'", parent, location);
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
	public Node validate(int phase)
	{
		Identifier reference = getReferenceNode();
		
		if (reference instanceof Variable)
		{
			Variable var = (Variable)reference;
			
			if (var.isPrimitiveType())
			{
				Instantiation instantiation = SyntaxUtils.autoboxPrimitive(var);
				
				var.getParent().replace(var, instantiation);
			}
		}
		
		if (localDeclarationRequired())
		{
			MethodCall calling   = (MethodCall)reference;
			
			Variable replacement = getAncestorWithScope().getScope().registerLocalVariable(calling);
			Node     replacing   = calling.getRootReferenceNode(true);
			
			replacing.getParent().replace(replacing, replacement);
			
			replacement.setAccessedNode(this);
			
			return replacement.getAccessedNode();
		}
		
		return this;
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
		return getCallableDeclaration().isVirtual() && !isVirtualTypeKnown() && isAccessed() && getAccessingNode() instanceof MethodCall;// && !getAccessingNode().isVirtualTypeKnown();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public MethodCall clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		MethodCall node = new MethodCall(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link MethodCall} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCall cloneTo(MethodCall node)
	{
		super.cloneTo(node);
		
		node.externalCall = externalCall;
		
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
}