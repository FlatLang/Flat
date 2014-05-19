/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.variables.VariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * ValueNode extension that represents the declaration of a method
 * call node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:04:31 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class MethodCallNode extends IdentifierNode
{
	private boolean	externalCall;
	
	/**
	 * Instantiate a new MethodCallNode and initialize the default values.
	 */
	public MethodCallNode()
	{
		ArgumentListNode arguments = new ArgumentListNode();
		
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
	 * Get the TreeNode that represents the variable that contains
	 * the method. For example:<br>
	 * <blockquote><pre>
	 * ClassName obj = new ClassName();
	 * 
	 * obj.methodName();</pre></blockquote>
	 * In the previous statements, 'obj' is the variable and the method
	 * 'methodName()' is being called through the 'obj' variable.
	 * 
	 * @return The TreeNode that represents the calling variable.
	 */
	public ValueNode getValueNode()
	{
		return getValueNode(getParent());
	}
	
	/**
	 * Get the TreeNode that represents the variable that contains
	 * the method. For example:<br>
	 * <blockquote><pre>
	 * ClassName obj = new ClassName();
	 * 
	 * obj.methodName();</pre></blockquote>
	 * In the previous statements, 'obj' is the variable and the method
	 * 'methodName()' is being called through the 'obj' variable.
	 * 
	 * @param parent The parent of the MethodCallNode.
	 * @return The TreeNode that represents the calling variable.
	 */
	private ValueNode getValueNode(TreeNode parent)
	{
		if (parent instanceof ReturnNode)
		{
			parent = parent.getParent();
		}
		if (parent instanceof ValueNode == false)
		{
			TreeNode value = parent.getAncestorOfType(MethodNode.class);
			
			if (value == null)
			{
				value = parent.getAncestorOfType(ClassNode.class);
				
				//return null;
			}
			
			parent = value;
		}
		if (parent instanceof MethodNode)
		{
//			MethodNode method = parent.getFileNode().getClass(getName());
//			
//			if (method.getFileNode().containsImport(getName()))
//			{
//				
//			}
			
			TreeNode value = getObjectReferenceValue((MethodNode)parent);
			
			if (value == null)
			{
				value = parent.getAncestorOfType(ClassNode.class);
				
				//return null;
			}
			
			parent = value;
		}
		if (parent instanceof ClassNode)
		{
			return (ClassNode)parent;
		}
		
		return (ValueNode)parent;
	}
	
	/**
	 * Get the MethodNode instance that this MethodCallNode is calling.
	 * 
	 * @return The MethodNode instance that this MethodCallNode is
	 * 		calling.
	 */
	public MethodNode getMethodNode()
	{
		return getMethodNode(getParent());
	}
	
	/**
	 * Get the MethodNode instance that this MethodCallNode is calling.
	 * 
	 * @param parent The parent node of the Method call node.
	 * @return The MethodNode instance that this MethodCallNode is
	 * 		calling.
	 */
	public MethodNode getMethodNode(TreeNode parent)
	{
		if (isExternal())
		{
			return null;
		}
		
		FileNode    file    = parent.getFileNode();
		
		ProgramNode program = file.getProgramNode();
		
		if (file.containsImport(getName()))
		{
			ClassNode  clazz  = program.getClass(getName());
			
			MethodNode method = clazz.getMethod(getName());
			
			return method;
		}
		
		ValueNode  val    = getValueNode(parent);
		
		String     type   = StringUtils.trimToIdentifier(val.getType());
		
		ClassNode  clazz  = program.getClass(type);
		
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
		return getObjectReferenceIdentifier(getMethodNode());
	}
	
	/**
	 * Get the name of the object reference identifier for the given
	 * Method node. Static methods return "__static__ClassName" and
	 * non-static methods return "this". The given method cannot be
	 * external.
	 * 
	 * @param method The method to get the object reference identifier
	 * 		name from.
	 * @return The name of the object reference identifier.
	 */
	public String getObjectReferenceIdentifier(MethodNode method)
	{
		if (!method.isStatic())
		{
			return ParameterListNode.OBJECT_REFERENCE_IDENTIFIER;
		}
		
		ClassNode clazz = (ClassNode)method.getAncestorOfType(ClassNode.class);
		
		return "__static__" + clazz.getName();
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
		return getObjectReferenceValue(getMethodNode());
	}
	
	/**
	 * Get the ValueNode that the method was called with for the given
	 * MethodCallNode's method node, if it was not called with a specific
	 * object. Static methods return "__static__ClassName" and non-static
	 * methods return "this". The call cannot be that of an external
	 * method.
	 * 
	 * 
	 * @param method The method to get the ValueNode from.
	 * @return The ValueNode that the method was called with.
	 */
	public ValueNode getObjectReferenceValue(MethodNode method)
	{
		String identifier = getObjectReferenceIdentifier(method);
		
		ValueNode val = (ValueNode)getExistingNode(method, identifier);
		
		if (val != null)
		{
			return val.clone();
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSource()
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceFragment()).append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return generateCSourceFragment(true);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	public String generateCSourceFragment(boolean checkSpecial)
	{
		if (checkSpecial && isSpecialFragment())
		{
			return generateSpecialFragment();
		}
		
		StringBuilder builder = new StringBuilder();
		
		MethodNode    method  = getMethodNode();
		
		if (method != null)
		{
			builder.append(method.generateCSourceName());
		}
		else
		{
			builder.append(getName());
		}
		
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
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append("->").append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	public String generateUseOutput()
	{
		return generateCSourceFragment(false);
	}
	
//	/**
//	 * Decode the given statement into a MethodCallNode instance, if
//	 * possible. If it is not possible, this method returns null.<br>
//	 * To determine whether or not a method is called externally,
//	 * refer to {@link #isExternal()} for more details on what an
//	 * external call looks like.
//	 * <br>
//	 * Example inputs include:<br>
//	 * <ul>
//	 * 	<li>methodName(5, varName, methodThatReturnsAValue(), "arg1")</li>
//	 * 	<li>externalFile.cFunctionName()</li>
//	 * 	<li>methodName('q', 5 * (2 / 3))</li>
//	 * </ul>
//	 * 
//	 * @param parent The parent node of the statement.
//	 * @param statement The statement to try to decode into a
//	 * 		MethodCallNode instance.
//	 * @param location The location of the statement in the source code.
//	 * @return The generated node, if it was possible to translated it
//	 * 		into a MethodCallNode.
//	 */
//	public static MethodCallNode decodeStatement(TreeNode parent, String statement, Location location)
//	{
//		return decodeStatement(parent, statement, location, true);
//	}
	
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
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCallNode.
	 */
	public static MethodCallNode decodeStatement(final TreeNode parent, String statement, final Location location)//, boolean needsReference)
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
			
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (bounds.getEnd() < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", parent.getFileNode(), location, parent.getController());
				
				return null;
			}
			
			FileNode fileNode = (FileNode)parent.getAncestorOfType(FileNode.class, true);
			
			Location argsLocation = new Location();
			argsLocation.setLineNumber(location.getLineNumber());
			argsLocation.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
			
			String  methodCall   = statement.substring(0, nameEnd);
			
			String  argumentList = statement.substring(bounds.getStart(), bounds.getEnd());
			
			final StringBuilder objectInstance = new StringBuilder();
			
			final boolean error[] = new boolean[1];
			
			MethodCallNode n = new MethodCallNode()
			{
				public void interactWord(String word, int wordNumber, Bounds bounds, int numWords, String leftDelimiter, String rightDelimiter)
				{
					if (wordNumber == numWords - 1 && (leftDelimiter.length() == 0 || leftDelimiter.equals(".")))
					{
						setName(word);
					}
					else if (rightDelimiter.equals("."))
					{
						objectInstance.append(word).append("->");
					}
					else if (rightDelimiter.length() > 0)
					{
						SyntaxMessage.error("Unknown characters '" + rightDelimiter + "'", parent.getFileNode(), location, parent.getController());
						
						error[0] = true;
					}
				}
			};
			
			n.externalCall = checkExternal(parent);
			
			n.iterateWords(methodCall, Patterns.IDENTIFIER_BOUNDARIES);
			
			if (error[0])
			{
				return null;
			}
			
			if (objectInstance.length() > 0)
			{
				objectInstance.delete(objectInstance.length() - 2, objectInstance.length());
			}
			
			if (objectInstance.length() > 0)
			{
				if (fileNode.isExternalImport(objectInstance.toString()))
				{
					n.externalCall = true;
				}
			}
			
			FileNode     file     = parent.getFileNode();
			MethodNode   method   = n.getMethodNode(parent);
			MethodNode   context  = (MethodNode)parent.getAncestorOfType(MethodNode.class, true);
			VariableNode accessor = context.getClassNode();
			
			if (method != null && !SyntaxUtils.isVisible(accessor, method))
			{
				SyntaxMessage.error("Method '" + method.getName() + "' is not visible", file, argsLocation, file.getController());
				
				return null;
			}
			
			if (method == null && !n.isExternal())
			{
				SyntaxMessage.error("Undeclared method '" + n.getName() + "'", parent.getFileNode(), location, parent.getController());
				
				return null;
			}
			
			if (method != null)
			{
				n.setType(method.getType());
			}
			
			String arguments[] = StringUtils.splitCommas(argumentList);
			
			n.addArguments(parent, arguments, argsLocation);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the method call is external.
	 * 
	 * @return Whether or not the method call is external.
	 */
	private static boolean checkExternal(TreeNode parent)
	{
		TreeNode current = parent;
		
		while (current instanceof ValueNode)
		{
			if (current instanceof ExternalTypeNode)
			{
				return true;
			}
			
			current = current.getParent();
		}
		
		return false;
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
				
				TreeNode arg = TreeNode.getExistingNode(parent, argument);
				
				if (arg != null)
				{
					arg = arg.clone();
					
					VariableNode var = (VariableNode)arg;
					
					if (prefix == '*')
					{
						var.setPointer(true);
					}
					else if (prefix == '&')
					{
						var.setReference(true);
					}
				}
				
				/*if (arg != null)
				{
					if (arg instanceof VariableNode)
					{
						VariableNode var = (VariableNode)arg.clone();
						
						if (prefix != '\0')
						{
							if (prefix == '*')
							{
								var.setPointer(true);
							}
							else if (prefix == '&')
							{
								var.setReference(true);
							}
						}
						
						arg = var;
					}
				}
				
				if (Regex.startsWith(argument, Patterns.PRE_INSTANTIATION))
				{
					arg = InstantiationNode.decodeStatement(parent, argument, location);
				}
				if (arg == null && !SyntaxUtils.isLiteral(argument))
				{
					arg = BinaryOperatorNode.decodeStatement(parent, argument, location);
				}
				if (arg == null && SyntaxUtils.isMethodCall(argument))
				{
					arg = MethodCallNode.decodeStatement(parent, argument, location);
				}
				if (arg == null && SyntaxUtils.isValidIdentifier(argument))
				{
					FileNode fileNode = (FileNode)parent.getAncestorOfType(FileNode.class, true);
					
					if (fileNode.getImportListNode().containsImport(argument) && !fileNode.getImportListNode().isExternal(argument))
					{
						argument = "__static__" + argument;
					}
				}
				if (arg == null && SyntaxUtils.isValidIdentifierAccess(argument))
				{
					if (SyntaxUtils.isExternal(parent.getFileNode(), argument))
					{
						argument = argument.substring(argument.indexOf('.') + 1);
					}
				}
				if (((isExternal() || isWithinExternalContext()) && arg == null) || SyntaxUtils.isLiteral(argument))// || prefix != '\0')
				{
					LiteralNode literal = new LiteralNode();
					
//					if (prefix != '\0')
//					{
//						literal.setValue(prefix + arg.generateCSourceFragment(), isWithinExternalContext());
//					}
//					else
//					{
						literal.setValue(argument, isWithinExternalContext());
//					}
					
					arg = literal;
				}
				
				if (arg == null)
				{
					SyntaxMessage.error("Incorrect argument definition", parent.getFileNode(), location, parent.getController());
					
					return;
				}
				else
				{*/
				
				if (SyntaxUtils.isLiteral(argument))
				{
					LiteralNode literal = new LiteralNode();
					literal.setValue(argument, isWithinExternalContext());
					
					arg = literal;
				}
				if (arg == null)
				{
					arg = BinaryOperatorNode.decodeStatement(parent, argument, location);
				}
				if (arg == null)
				{
					arg = TreeNode.decodeScopeContents(parent, argument, location);
				}
				if (arg == null && isWithinExternalContext())
				{
					LiteralNode literal = new LiteralNode();
					literal.setValue(argument, isWithinExternalContext());
					
					arg = literal;
				}
				
				getArgumentListNode().addChild(arg);
//				}
			}
		}
	}
	
	/**
	 * Get the Fathom input representation of this Method Call node.
	 * That is, the String that the decoder method decoded to attain
	 * this node.
	 * 
	 * @return The String representing the method call in Fathom syntax.
	 */
	public String generateFathomInput()
	{
		StringBuilder builder = new StringBuilder();
		
		ValueNode value = getValueNode();
		
		if (value instanceof VariableNode)
		{
			MethodNode method = getMethodNode();
			
			if (method instanceof ConstructorNode == false)
			{
				builder.append(value.generateFathomInput()).append('.');
			}
		}
		
		builder.append(getName()).append('(').append(getArgumentListNode().generateFathomInput()).append(')');
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append('.').append(child.generateFathomInput());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public MethodCallNode clone()
	{
		MethodCallNode node = new MethodCallNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given MethodCallNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCallNode clone(MethodCallNode node)
	{
		super.clone(node);
		
		node.externalCall = externalCall;
		
		return node;
	}
}
