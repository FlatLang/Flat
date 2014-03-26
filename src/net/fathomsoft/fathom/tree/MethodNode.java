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
import net.fathomsoft.fathom.tree.variables.LocalVariableListNode;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:10:53 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:10:53 PM
 * @version	v
 */
public class MethodNode extends DeclarationNode
{
	public MethodNode()
	{
		ParameterListNode     parameterList = new ParameterListNode();
		LocalVariableListNode variablesNode = new LocalVariableListNode();
		
		super.addChild(parameterList);
		super.addChild(variablesNode);
	}
	
	public ParameterListNode getParameterListNode()
	{
		return (ParameterListNode)getChild(0);
	}
	
	public LocalVariableListNode getLocalVariableListNode()
	{
		return (LocalVariableListNode)getChild(1);
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof LocalVariableNode)
		{
			getLocalVariableListNode().addChild(child);
		}
		else
		{
			super.addChild(child);
		}
	}
	
	/**
	 * Get the name of the identifier for the Object reference that
	 * the method is using.
	 * 
	 * @return The name of the identifier for the Object reference that
	 * 		the method is using.
	 */
	public static String getObjectReferenceIdentifier()
	{
		return ParameterListNode.OBJECT_REFERENCE_IDENTIFIER;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			builder.append(getVisibilityText()).append(' ');
		}
		if (isStatic())
		{
			builder.append(getStaticText()).append(' ');
		}
		if (isConst())
		{
			builder.append(getConstText()).append(' ');
		}
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append(getReferenceText());
		}
		else if (isPointer())
		{
			builder.append(getPointerText());
		}
		
		if (isArray())
		{
			builder.append(getArrayText());
		}
		
		builder.append(' ').append(getName()).append('(');

		ParameterListNode parameterList = getParameterListNode();
		
		builder.append(parameterList.generateJavaSourceOutput());
		
		builder.append(')').append('\n').append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != parameterList)
			{
				builder.append(child.generateJavaSourceOutput());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			if (getVisibility() == DeclarationNode.PRIVATE)
			{
				return "";
			}
		}
//		if (isStatic())
//		{
//			SyntaxError.outputNewError("Static methods are not supported in the C implementation yet", getLocationIn());
//			
//			return null;
//		}
		if (isConst())
		{
			SyntaxMessage.error("Const methods are not supported in the C implementation yet", getLocationIn());
			
			return null;
		}
		
		builder.append("FUNC(");
		
		builder.append(getType());
		
		if (isArray())
		{
			builder.append(getArrayText());
		}
		if (!isPrimitiveType())
		{
			builder.append('*');
		}
		
		builder.append(", ");
		
		builder.append(getName()).append(", ");

		ParameterListNode parameterList = getParameterListNode();
		
		builder.append(parameterList.generateCHeaderOutput());
		
		builder.append(");").append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceSignature()).append('\n').append('{').append('\n');
		
		ParameterListNode parameterList = getParameterListNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != parameterList)
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	public String generateCSourcePrototype()
	{
		return generateCSourceSignature().concat(";");
	}
	
	public String generateCSourceSignature()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("static ");
		
		if (isConst())
		{
			builder.append(getConstText()).append(' ');
		}
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append(getReferenceText());
		}
		else if (isPointer())
		{
			builder.append(getPointerText());
		}
		else if (isArray())
		{
			builder.append(getArrayText());
		}
		if (!isPrimitiveType())
		{
			builder.append('*');
		}
		
		builder.append(' ');
		
		builder.append(getName()).append('(');
		
		builder.append(getParameterListNode().generateCSourceOutput());
		
		builder.append(')');
		
		return builder.toString();
	}
	
	public static MethodNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		int firstParenthIndex = Regex.indexOf(statement, '(', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
		int lastParenthIndex  = Regex.lastIndexOf(statement, ')', new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
		
		if (firstParenthIndex >= 0)
		{
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", location);
				
				return null;
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String parameters[]  = Regex.splitCommas(parameterList);
			
			statement = statement.substring(0, firstParenthIndex);
			
			final String statement2 = statement;
			
			MethodNode n = new MethodNode()
			{
				public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
				{
					setAttribute(word, wordNumber);
					
					if (wordNumber == numWords - 1)
					{
						setName(word);
					}
					else if (wordNumber == numWords - 2)
					{
						setType(word);
						
						// If it is an array declaration.
						if (Regex.matches(statement2, bounds.getEnd(), Patterns.EMPTY_ARRAY_BRACKETS))
						{
							int dimensions = SyntaxUtils.getArrayDimensions(statement2);
							
							setArrayDimensions(dimensions);
						}
					}
				}
			};
			
			n.setLocationIn(location);
			
			for (int i = 0; i < parameters.length; i++)
			{
				if (parameters[i].length() > 0)
				{
					ParameterNode param = ParameterNode.decodeStatement(parentNode, parameters[i], location);
					
					if (param == null)
					{
						SyntaxMessage.error("Incorrect parameter definition", location);
						
						return null;
					}
					
					n.getParameterListNode().addChild(param);
				}
			}
			
			n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES);
			
			if (SyntaxUtils.isMainMethod(n))
			{
				n.setName("__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__main");
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public MethodNode clone()
	{
		MethodNode clone = new MethodNode();
		clone.setStatic(isStatic());
		clone.setVisibility(getVisibility());
		clone.setConst(isConst());
		clone.setArrayDimensions(getArrayDimensions());
		clone.setType(getType());
		clone.setReference(isReference());
		clone.setPointer(isPointer());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}