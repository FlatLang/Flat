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
import net.fathomsoft.fathom.tree.variables.VariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:04:31 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:04:31 PM
 * @version	v
 */
public class MethodCallNode extends IdentifierNode
{
	private boolean	externalCall;
	
	public MethodCallNode()
	{
		ArgumentListNode arguments = new ArgumentListNode();
		
		addChild(arguments);
	}
	
	public ArgumentListNode getArgumentListNode()
	{
		return (ArgumentListNode)getChild(0);
	}
	
	public boolean isExternal()
	{
		return externalCall;
	}
	
	public boolean hasVariableNode()
	{
		return getChildren().size() >= 2;
	}
	
	public TreeNode getVariableNode()
	{
		return getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getName()).append('(');
		
		builder.append(getArgumentListNode().generateJavaSourceOutput());
		
		builder.append(");").append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
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
		
		if (hasVariableNode())
		{
			builder.append(getVariableNode().generateCSourceFragment()).append("->");
		}
		
		builder.append(getName()).append('(');
		
		builder.append(getArgumentListNode().generateCSourceOutput());
		
		builder.append(')');
		
		return builder.toString();
	}

	public static MethodCallNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		return decodeStatement(parentNode, statement, location, true);
	}
	
	public static MethodCallNode decodeStatement(TreeNode parentNode, String statement, Location location, boolean needsReference)
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
				SyntaxMessage.error("Expected a ')' ending parenthesis", location);
				
				return null;
			}
			
			FileNode fileNode = (FileNode)parentNode.getAncestorOfType(FileNode.class, true);
			
			Location argsLocation = new Location();
			argsLocation.setLineNumber(location.getLineNumber());
			argsLocation.setOffset(bounds.getStart());
			
			String  methodCall   = statement.substring(0, nameEnd);
			
			String  argumentList = statement.substring(bounds.getStart(), bounds.getEnd());
			
//			if (parentNode.isWithinTry())
//			{
//				argumentList = "__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__exception_data, " + argumentList;
//			}
//			else
//			{
//				argumentList = ", " + argumentList;
//			}
//			TreeNode variableNode = null;
//			
//			String objectRef = null;
			
//			if (needsReference)
//			{
//				int dotIndex = methodCall.lastIndexOf(".");
//				
//				if (dotIndex > 0)
//				{
//					objectRef = methodCall.substring(0, dotIndex);
//					
//					if (fileNode.getImportListNode().isExternal(objectRef))
//					{
//						externalCall = true;
//					}
//					else
//					{
//						objectRef.replace(".", "->");
//						
//						if (fileNode.getImportListNode().isExternal(objectRef))
//						{
//							objectRef = null;
//						}
//					}
//				}
//				else
//				{
//					objectRef = MethodNode.getObjectReferenceIdentifier();
//				}
//			}
			
			final StringBuilder objectInstance = new StringBuilder();
			
			MethodCallNode n   = new MethodCallNode()
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
			}
			
			if (!n.isExternal())
			{
				boolean staticCall = false;
				
				argumentList = "__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__exception_data, " + argumentList;
				
				if (objectInstance.length() > 0)
				{
					// If only referenced through one variable.
					if (objectInstance.indexOf("->") < 0)
					{
						String varName     = objectInstance.toString();
						
						IdentifierNode var = TreeNode.getExistingNode(parentNode, varName);
						
						if (var == null)
						{
							if (fileNode.getImportListNode().contains(varName) && !fileNode.getImportListNode().isExternal(varName))
							{
								objectInstance.insert(0, "__static__");
								
								staticCall = true;
							}
							else
							{
								objectInstance.delete(0, objectInstance.length());
							}
						}
					}
					
					argumentList = objectInstance + ", " + argumentList;
					
					if (!staticCall)
					{
						String caller = objectInstance.toString();
						
						TreeNode variableNode = TreeNode.getExistingNode(parentNode, caller);
						
						if (variableNode != null)
						{
							variableNode = variableNode.clone();
						}
						else
						{
							variableNode = TreeNode.decodeStatement(parentNode, caller, location);
						}
						
						n.addChild(variableNode);
					}
					else
					{
						IdentifierNode variableNode = new IdentifierNode();
						variableNode.setName(objectInstance.toString());
						
						n.addChild(variableNode);
					}
				}
				else
				{
					if (needsReference)
					{
						argumentList = MethodNode.getObjectReferenceIdentifier() + ", " + argumentList;
					}
				}
			}

			String arguments[] = Regex.splitCommas(argumentList);
			
			n.addArguments(parentNode, arguments, argsLocation);
			
			return n;
		}
		
		return null;
	}
	
	private boolean addArguments(TreeNode parent, String arguments[], Location location)
	{
		for (int i = 0; i < arguments.length; i++)
		{
			String argument = arguments[i];
			
			if (argument.length() > 0)
			{
//				if (argument.indexOf('(') >= 0)
//				{
//					if (argument.indexOf('(') == 0)
//					{
//						argument = argument.substring(1, argument.length() - 1);
//					}
//				}
				
				TreeNode arg = TreeNode.getExistingNode(parent, argument);
				
				if (arg != null)
				{
					if (arg instanceof VariableNode)
					{
						VariableNode n = (VariableNode)arg;
						
//						VariableNode var = new VariableNode();
//						var.setArray(n.isArray());
//						var.setConst(n.isConst());
//						var.setName(n.getName());
//						var.setType(n.getType());
						
//						LiteralNode var = new LiteralNode();
//						var.setValue(n.getName(), isWithinExternalContext());
						
						arg = n.clone();
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
					
					if (fileNode.getImportListNode().contains(argument) && !fileNode.getImportListNode().isExternal(argument))
					{
						argument = "__static__" + argument;
					}
				}
				if (arg == null)
				{
					LiteralNode literal = new LiteralNode();
					
					literal.setValue(argument, isWithinExternalContext());
					
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
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public MethodCallNode clone()
	{
		MethodCallNode clone = new MethodCallNode();
		clone.setName(getName());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
