/**
 * The Nova Programming Language. Write Explosive Code.
 * Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * The Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * ValueNode extension that represents the declaration of a method
 * call node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:04:31 PM
 * @version	v0.2.5 May 22, 2014 at 2:56:28 PM
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
		
		ValueNode  val    = getReferenceNode(parent);
		
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
				
				TreeNode arg = BinaryOperatorNode.decodeStatement(parent, argument, location);
				
				if (arg == null && SyntaxUtils.isLiteral(argument))
				{
					LiteralNode literal = new LiteralNode();
					literal.setValue(argument, isWithinExternalContext());
					
					arg = literal;
				}
				if (arg == null)
				{
					arg = TreeNode.decodeScopeContents(parent, argument, location);//TreeNode.getExistingNode(parent, argument);
					
					if (arg != null && (prefix == '*' || prefix == '&'))
					{
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
				}
				if (arg == null && isWithinExternalContext())
				{
					LiteralNode literal = new LiteralNode();
					literal.setValue(argument, isWithinExternalContext());
					
					arg = literal;
				}
				
				if (arg == null)
				{
					SyntaxMessage.error("Could not decode argument '" + argument + "'", parent.getFileNode(), location, parent.getController());
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
	 * @return The String representing the method call in Nova syntax.
	 */
	public String generateNovaInput()
	{
		StringBuilder builder = new StringBuilder();
		
		ValueNode value = getReferenceNode();
		
		if (value instanceof VariableNode)
		{
			MethodNode method = getMethodNode();
			
			if (method instanceof ConstructorNode == false)
			{
				builder.append(value.generateNovaInput()).append('.');
			}
		}
		
		builder.append(getName()).append('(').append(getArgumentListNode().generateNovaInput()).append(')');
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append('.').append(child.generateNovaInput());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public MethodCallNode clone()
	{
		MethodCallNode node = new MethodCallNode();
		
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
