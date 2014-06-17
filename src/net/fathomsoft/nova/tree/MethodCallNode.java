package net.fathomsoft.nova.tree;

import java.io.ObjectInputStream.GetField;

import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * ValueNode extension that represents the declaration of a method
 * call node type. See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:04:31 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class MethodCallNode extends IdentifierNode
{
	private boolean	externalCall;
	
	/**
	 * Instantiate a new MethodCallNode and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public MethodCallNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentListNode arguments = new ArgumentListNode(this, locationIn);
		
		addChild(arguments);
	}
	
	/**
	 * The the TreeNode that represents the arguments to the method call.
	 * For example:<br>
	 * <blockquote><pre>
	 * methodName(5, "Arg2", 3 * n);</pre></blockquote>
	 * In the previous statement, the data within the parenthesis are the
	 * arguments passed to the method. The ArgumentNode returned by this
	 * method would contain a node for each of the arguments passed, in
	 * the correct order from left to right.
	 * 
	 * @return The TreeNode that represents the arguments to the method
	 * 		call.
	 */
	public ArgumentListNode getArgumentListNode()
	{
		return (ArgumentListNode)getChild(0);
	}
	
	/**
	 * Get whether or not the specified TreeNode is used within an
	 * external context.
	 * 
	 * @return Whether or not the specified TreeNode is used within an
	 * 		external context.
	 */
	public boolean isWithinExternalContext()
	{
		MethodNode method = getMethodDeclarationNode();
		
		if (method.isExternal())
		{
			return true;
		}
		
		return super.isWithinExternalContext();
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
	 * Get the MethodNode instance that this MethodCallNode is calling.
	 * 
	 * @return The MethodNode instance that this MethodCallNode is
	 * 		calling.
	 */
	public MethodNode getMethodDeclarationNode()
	{
		FileNode    file    = getParent().getFileNode();
		
		ProgramNode program = file.getProgramNode();
		
		if (file.containsImport(getName()))
		{
			ClassNode  clazz  = program.getClassNode(getName());
			
			MethodNode method = clazz.getMethod(getName());
			
			return method;
		}
		
		ValueNode  val    = getReferenceNode();
		
		ClassNode  clazz  = val.getTypeClass();
		
		MethodNode method = clazz.getMethod(getName());
		
		return method;
	}
	
	/**
	 * Get the name of the object reference identifier for the given
	 * MethodCallNode's method node. Static methods return
	 * "__static__ClassName" and non-static methods return "this".
	 * The call cannot be that of an external method.
	 * 
	 * @return The name of the object reference identifier.
	 */
	public String getObjectReferenceIdentifier()
	{
		return getObjectReferenceIdentifier(getMethodDeclarationNode());
	}
	
	/**
	 * Get the ValueNode that the method was called with for the given
	 * MethodCallNode's method node, if it was not called with a specific
	 * object. Static methods return "__static__ClassName" and non-static
	 * methods return "this". The call cannot be that of an external
	 * method.
	 * 
	 * @return The ValueNode that the method was called with.
	 */
	public ValueNode getObjectReferenceValue()
	{
		return getObjectReferenceNode(getMethodDeclarationNode());
	}
	
	/**
	 * Get the ParameterNode that the given argument represents.<br>
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
	 * call above, you would receive the b ParameterNode.
	 * 
	 * @param argument The argument to get the corresponding parameter
	 * 		from.
	 * @return The ParameterNode that represents the given argument.
	 */
	public ParameterNode getCorrespondingParameter(ValueNode argument)
	{
		int argIndex = -1;
		
		ArgumentListNode args = getArgumentListNode();
		
		for (int i = 0; i < args.getNumChildren(); i++)
		{
			ValueNode value = (ValueNode)args.getChild(i);
			
			if (value == argument)
			{
				argIndex = i;
				
				break;
			}
		}
		
		// If no matching argument was found.
		if (argIndex == -1)
		{
			return null;
		}
		
		return getCorrespondingParameter(argIndex);
	}
	
	/**
	 * Get the ParameterNode that the given index represents. The
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
	 * call above, you would receive the c ParameterNode.
	 * 
	 * @param argIndex The index of the argument to get the corresponding
	 * 		parameter from.
	 * @return The ParameterNode at the given index.
	 */
	public ParameterNode getCorrespondingParameter(int argIndex)
	{
		MethodNode method = getMethodDeclarationNode();
		
		return method.getParameterNode(argIndex);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getName()).append('(');
		
		builder.append(getArgumentListNode().generateJavaSource());
		
		builder.append(");").append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceFragment()).append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return generateCSourceFragment(true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	public String generateCSourceFragment(boolean checkSpecial)
	{
		if (checkSpecial && isSpecialFragment())
		{
			return generateSpecialFragment();
		}
		
		StringBuilder builder = new StringBuilder();
		
		MethodNode    method  = getMethodDeclarationNode();
		
		if (!isSpecialFragment())
		{
			ValueNode node = getRootAccessNode();
			
			builder.append(node.generateDataTypeOutput(getDataType()));
		}
		
		builder.append(method.generateCSourceName());
		
		builder.append('(');
		
		builder.append(getArgumentListNode().generateCSource());
		
		builder.append(')');
		
//		builder.append(generateChildrenCSourceFragment());
		
		return builder.toString();
	}
	
	/**
	 * Generate a String representing the output of the children of the
	 * MethodCallNode.
	 * 
	 * @return A String representing the output of the children of the
	 * 		MethodCallNode.
	 */
	public String generateChildrenCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 1; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append("->").append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate the representation of when the method call is being used
	 * in action.
	 * 
	 * @return What the method call looks like when it is being used in
	 * 		action
	 */
	public String generateUseOutput()
	{
		return generateCSourceFragment(false);
	}
	
	/**
	 * Decode the given statement into a MethodCallNode instance, if
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
	 * 		MethodCallNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCallNode.
	 */
	public static MethodCallNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isMethodCall(statement))
		{
			Bounds bounds  = new Bounds(0, 0);//Regex.boundsOf(statement, Patterns.POST_METHOD_CALL);
			
			int    start   = statement.indexOf('(');
			
			int    nameEnd = StringUtils.findNextNonWhitespaceIndex(statement, start - 1, -1) + 1;
			
			int    end     = StringUtils.findEndingMatch(statement, start, '(', ')');
			
			if (end >= 0)
			{
				end = StringUtils.findNextNonWhitespaceIndex(statement, end - 1, -1) + 1;
			}
			
			start = StringUtils.findNextNonWhitespaceIndex(statement, start + 1);
			
			bounds.setStart(start);
			bounds.setEnd(end);
			
			MethodCallNode n = new MethodCallNode(parent, location);
			
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (bounds.getEnd() < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", n);
			}
			
			Location argsLocation = new Location();
			argsLocation.setLineNumber(location.getLineNumber());
			argsLocation.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
			
			String  methodCall   = statement.substring(0, nameEnd);
			
			String  argumentList = statement.substring(bounds.getStart(), bounds.getEnd());
			
//			n.externalCall = parent instanceof ExternalTypeNode;
			
			try
			{
				n.iterateWords(methodCall, Patterns.IDENTIFIER_BOUNDARIES);
			}
			catch (SyntaxErrorException e)
			{
				if (!require)
				{
					return null;
				}
				
				throw e;
			}
			
			FileNode     file     = parent.getFileNode();
			MethodNode   method   = n.getMethodDeclarationNode();
			MethodNode   context  = (MethodNode)parent.getAncestorOfType(MethodNode.class, true);
			VariableNode accessor = context.getClassNode();
			
			if (method == null)
			{
				SyntaxMessage.error("Undeclared method '" + n.getName() + "'", n);
			}
			
			if (!SyntaxUtils.isVisible(accessor, method))
			{
				SyntaxMessage.error("Method '" + method.getName() + "' is not visible", n);
			}
			
			n.setDataType(method.getDataType());
			
			if (method.isExternal())
			{
				n.externalCall = true;
			}
			else
			{
				ValueNode contextNode = n.getContextNode();
				
				if (contextNode instanceof ClassNode && method instanceof ConstructorNode == false && !method.isStatic())
				{
					SyntaxMessage.error("Cannot call a non-static method from a static context", n);
				}
			}
			
			n.setType(method.getType());
			
			String arguments[] = StringUtils.splitCommas(argumentList);
			
			n.addArguments(parent, arguments, argsLocation);
			
			if (method != null)
			{
				if (!n.fulfillsParameters(method, file, location))
				{
					return null;
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#interactWord(java.lang.String, int, net.fathomsoft.nova.util.Bounds, int, java.lang.String, java.lang.String, net.fathomsoft.nova.tree.TreeNode.ExtraData)
	 */
	@Override
	public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter, ExtraData extra)
	{
		if (wordNumber == numWords - 1 && leftDelimiter.length() == 0)
		{
			setName(word);
		}
		else if (rightDelimiter.length() > 0)
		{
			SyntaxMessage.error("Unknown characters '" + rightDelimiter + "'", this);
		}
	}
	
	/**
	 * Check to see if the arguments passed to the method call
	 * fulfills the required parameters of the method declaration.
	 * 
	 * @param method The Method to check the arguments against.
	 * @param fileNode The FileNode that this method call is within.
	 * @param location The location of the arguments that are being
	 * 		passed.
	 * @return True if the method call's arguments fulfill the
	 * 		requirements of the Method declaration's parameters.
	 */
	private boolean fulfillsParameters(MethodNode method, FileNode fileNode, Location location)
	{
		ParameterListNode parameters = method.getParameterListNode();
		ArgumentListNode  arguments  = getArgumentListNode();
		
		int offset = 2;
		
		if (method.isExternal())
		{
			offset = 0;
		}
		else if (method.isStatic())
		{
			offset = 1;
		}
		
		if (parameters.getNumChildren() - offset != arguments.getNumChildren())
		{
			if (parameters.getNumChildren() - offset > arguments.getNumChildren())
			{
				SyntaxMessage.error("Too few arguments to method call '" + getName() + "'", this);
			}
			else
			{
				SyntaxMessage.error("Too many arguments to method call '" + getName() + "'", this);
			}
			
//			return false;
		}
		
		return true;
	}
	
	/**
	 * Decode the arguments given within the array into TreeNodes that
	 * are translatable into C.
	 * 
	 * @param parent The parent of the current method call.
	 * @param arguments The arguments to decode.
	 * @param location The location of the method call in the source code.
	 */
	private void addArguments(TreeNode parent, String arguments[], Location location)
	{
		for (int i = 0; i < arguments.length; i++)
		{
			String argument = arguments[i];
			
			if (argument.length() > 0)
			{
				char prefix = '\0';
				
				if (argument.startsWith("*") || argument.startsWith("&"))
				{
					prefix   = argument.substring(0, 1).charAt(0);
					
					argument = StringUtils.trimSurroundingWhitespace(argument.substring(1));
				}
				
				TreeNode arg = null;
				
				if (arg == null && SyntaxUtils.isLiteral(argument))
				{
					LiteralNode literal = LiteralNode.decodeStatement(parent, argument, location, true, false);
					
					arg = literal;
				}
				if (arg == null)
				{
					arg = BinaryOperatorNode.decodeStatement(parent, argument, location, false, false);
				}
				if (arg == null)
				{
					arg = SyntaxTree.decodeScopeContents(parent, argument, location, false, false);//SyntaxTree.getExistingNode(parent, argument);
					
					if (arg != null && (prefix == '*' || prefix == '&'))
					{
						VariableNode var = (VariableNode)arg;
						
						if (prefix == '*')
						{
							var.setDataType(VariableNode.POINTER);
						}
						else if (prefix == '&')
						{
							var.setDataType(VariableNode.REFERENCE);
						}
					}
				}
				if (arg == null && parent.isWithinExternalContext())
				{
					LiteralNode literal = LiteralNode.decodeStatement(parent, argument, location, true, false);
					
					arg = literal;
				}
				
				if (arg == null)
				{
					SyntaxMessage.error("Could not decode argument '" + argument + "'", parent, location);
				}
				else
				{
					getArgumentListNode().addChild(arg);
				}
			}
		}
	}
	
	/**
	 * Get the Nova input representation of this Method Call node.
	 * That is, the String that the decoder method decoded to attain
	 * this node.
	 * 
	 * @param outputChildren Whether or not to output the children of the
	 * 		children of the TreeNode as well.
	 * @return The String representing the method call in Nova syntax.
	 */
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getName()).append('(').append(getArgumentListNode().generateNovaInput()).append(')');
		
		if (outputChildren)
		{
			IdentifierNode accessed = getAccessedNode();
			
			if (accessed != null)
			{
				builder.append('.').append(accessed.generateNovaInput());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.IdentifierNode#getAccessedNode()
	 */
	public IdentifierNode getAccessedNode()
	{
		if (getNumChildren() <= 1)
		{
			return null;
		}
		
		return (IdentifierNode)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#validate(int)
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	@Override
	public TreeNode validate(int phase)
	{
		ValueNode reference = getReferenceNode();
		
		if (reference instanceof VariableNode)
		{
			VariableNode var = (VariableNode)reference;
			
			if (var.isActiveVariable())
			{
				if (var.isPrimitiveType())
				{
					InstantiationNode instantiation = SyntaxUtils.autoboxPrimitive(var);
					
					var.getParent().replace(var, instantiation);
				}
			}
		}
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public MethodCallNode clone(TreeNode temporaryParent, Location locationIn)
	{
		MethodCallNode node = new MethodCallNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given MethodCallNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCallNode cloneTo(MethodCallNode node)
	{
		super.cloneTo(node);
		
		node.externalCall = externalCall;
		
		return node;
	}
}
