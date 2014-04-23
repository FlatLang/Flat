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

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.fathom.tree.variables.VariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * IdentifierNode extension that represents the declaration of a method
 * call node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:04:31 PM
 * @version	v0.2 Apr 3, 2014 at 10:51:56 PM
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
	 * Get whether or not the method is called using a variable.
	 * See {@link #getVariableNode()} for more details on
	 * what a variable node looks like.
	 * 
	 * @return Whether or not the method is called using a variable.
	 */
	public boolean hasVariableNode()
	{
		return getChildren().size() >= 2;
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
	public VariableNode getVariableNode()
	{
		return (VariableNode)getChild(1);
	}
	
	/**
	 * Get the MethodNode instance that this MethodCallNode is calling.
	 * 
	 * @return The MethodNode instance that this MethodCallNode is
	 * 		calling.
	 */
	public MethodNode getMethodNode()
	{
		if (isExternal())
		{
			return null;
		}
		
		VariableNode var   = getVariableNode();
		ProgramNode  pNode = getProgramNode();
		
		ClassNode    clazz = pNode.getClass(var.getType());
		
		FileNode     fNode = (FileNode)getAncestorOfType(FileNode.class);
		
		if (fNode.getImportListNode().containsImport(getName()))
		{
			clazz = pNode.getClass(getName());
			
			MethodNode method = clazz.getMethod(getName());
			
			return method;
		}
		
		MethodNode method = clazz.getMethod(getName());
		
		return method;
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return null;
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
		StringBuilder builder = new StringBuilder();
		
//		if (hasVariableNode())
//		{
//			builder.append(getVariableNode().generateCSourceFragment());
//			
//			if (builder.length() > 0)
//			{
//				builder.append("->");
//			}
//		}
		
//		if (!isExternal())
//		{
//			builder.append("__").append(Fathom.LANGUAGE_NAME.toUpperCase()).append("__");
//		}
		
		MethodNode method = getMethodNode();
		
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
		
		return builder.toString();
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
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCallNode.
	 */
	public static MethodCallNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		return decodeStatement(parent, statement, location, true);
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
	 * @param needsReference Whether or not the method needs an object
	 * 		to reference where the method is located.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodCallNode.
	 */
	public static MethodCallNode decodeStatement(TreeNode parent, String statement, Location location, boolean needsReference)
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
				SyntaxMessage.error("Expected a ')' ending parenthesis", location, parent.getController());
				
				return null;
			}
			
			FileNode fileNode = (FileNode)parent.getAncestorOfType(FileNode.class, true);
			
			Location argsLocation = new Location();
			argsLocation.setLineNumber(location.getLineNumber());
			argsLocation.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
			
			String  methodCall   = statement.substring(0, nameEnd);
			
			String  argumentList = statement.substring(bounds.getStart(), bounds.getEnd());
			
			final StringBuilder objectInstance = new StringBuilder();
			
			MethodCallNode n = new MethodCallNode()
			{
				public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
				{
					if (wordNumber == numWords - 1)
					{
						setName(word);
					}
					else
					{
						objectInstance.append(word).append("->");
					}
				}
			};
			
			n.iterateWords(methodCall, Patterns.IDENTIFIER_BOUNDARIES);
			
			if (objectInstance.length() > 0)
			{
				objectInstance.delete(objectInstance.length() - 2, objectInstance.length());
			}
			
			if (needsReference)
			{
				if (objectInstance.length() > 0)
				{
					if (fileNode.getImportListNode().isExternal(objectInstance.toString()))
					{
						n.externalCall = true;
					}
				}
				else
				{
					objectInstance.append("this");
				}
			}
			else
			{
				objectInstance.delete(0, objectInstance.length());
			}
			
			if (!n.isExternal())
			{
				boolean staticCall = false;
				
				if (objectInstance.length() > 0)
				{
					// If only referenced through one variable.
					if (objectInstance.indexOf("->") < 0)
					{
						String     varName = objectInstance.toString();
						
						IdentifierNode var = TreeNode.getExistingNode(parent, varName);
						
						if (var == null)
						{
							if (!fileNode.getImportListNode().isExternal(varName) && (fileNode.getImportListNode().containsImport(varName) || fileNode.containsClass(varName)))
							{
								objectInstance.insert(0, "__static__");
								
								staticCall = true;
							}
						}
					}
					
					if (!staticCall)
					{
						String       caller       = objectInstance.toString();
						
						VariableNode variableNode = (VariableNode)TreeNode.getExistingNode(parent, caller);
						
						if (variableNode != null)
						{
							variableNode = variableNode.clone();
						}
						else
						{
							variableNode = (VariableNode)TreeNode.decodeStatement(parent, caller, location);
						}
						
						if (variableNode != null)
						{
							if (variableNode instanceof VariableNode == false)
							{
								SyntaxMessage.error("The method must be referenced from a variable type", location, parent.getController());
								
								return null;
							}
							
							n.addChild(variableNode);
						}
						else
						{
							SyntaxMessage.error("Undefined variable '" + caller + "'", argsLocation, parent.getController());
							
							return null;
						}
					}
				}
				
				if (objectInstance.length() <= 0 || staticCall)
				{
					ClassNode clazz = null;
					
					if (objectInstance.length() <= 0)
					{
						clazz = (ClassNode)parent.getAncestorOfType(ClassNode.class, true);
					}
					else
					{
						ProgramNode    p = parent.getProgramNode();
						
						String    prefix = "__static__";
						
						String className = objectInstance.substring(prefix.length());
						
						clazz = p.getClass(className);
					}
					
					VariableNode variableNode = new VariableNode();
					variableNode.setName(objectInstance.toString(), true);
					variableNode.setType(clazz.getName());
					
					n.addChild(variableNode);
				}
			}
			
			String arguments[] = StringUtils.splitCommas(argumentList);
			
			n.addArguments(parent, arguments, argsLocation);
			
			return n;
		}
		
		return null;
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
				
				if (!SyntaxUtils.isLiteral(argument))
				{
					if (arg == null)
					{
						arg = BinaryOperatorNode.decodeStatement(parent, argument, location);
					}
					if (arg == null)
					{
						arg = TreeNode.decodeStatement(parent, argument, location);
					}
				}
				
				if (arg == null && SyntaxUtils.isValidIdentifier(argument))
				{
					FileNode fileNode = (FileNode)parent.getAncestorOfType(FileNode.class, true);
					
					if (fileNode.getImportListNode().containsImport(argument) && !fileNode.getImportListNode().isExternal(argument))
					{
						argument = "__static__" + argument;
					}
				}
				if (arg == null)// || prefix != '\0')
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
				
//				if (arg == null)
//				{
//					SyntaxMessage.error("Incorrect argument definition", location);
//					
//					return false;
//				}
				
				getArgumentListNode().addChild(arg);
			}
		}
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
