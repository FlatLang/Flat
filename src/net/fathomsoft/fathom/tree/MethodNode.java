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
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * DeclarationNode extension that represents the declaration of a method
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:10:53 PM
 * @version	v0.2 Apr 5, 2014 at 2:54:32 PM
 */
public class MethodNode extends DeclarationNode
{
	/**
	 * Instantiate and initialize default data.
	 */
	public MethodNode()
	{
		ParameterListNode parameterList = new ParameterListNode();
		ScopeNode         scopeNode     = new ScopeNode();
		
		super.addChild(parameterList);
		super.addChild(scopeNode);
	}
	
	/**
	 * The the ParameterListNode that represents the parameters of the
	 * method.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * public void methodName(int age, String name);</pre></blockquote>
	 * In the previous statement, the data within the parenthesis are the
	 * parameters of the method. The ParameterListNode returned by this
	 * method would contain a node for each of the parameter specified, in
	 * the correct order from left to right.
	 * 
	 * @return The ParameterListNode that represents the parameters of the
	 * 		method.
	 */
	public ParameterListNode getParameterListNode()
	{
		return (ParameterListNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(1);
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
//		if (child instanceof LocalVariableNode)
//		{
//			getLocalVariableListNode().addChild(child);
//		}
//		else
//		{
//			super.addChild(child);
//		}
		getScopeNode().addChild(child);
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
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append('&');
		}
		else if (isPointer())
		{
			builder.append('*');
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
		if (isConstant())
		{
			SyntaxMessage.error("Const methods are not supported in the C implementation yet", getLocationIn());
			
			return null;
//			builder.append(getConstantText()).append(' ');
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
		
		builder.append(generateCSourceSignature()).append('\n');
		
		builder.append(getScopeNode().generateCSourceOutput());
//		builder.append('{').append('\n');
		
//		ParameterListNode parameterList = getParameterListNode();
//		
//		for (int i = 0; i < getChildren().size(); i++)
//		{
//			TreeNode child = getChild(i);
//			
//			if (child != parameterList)
//			{
//				builder.append(child.generateCSourceOutput());
//			}
//		}
//		
//		builder.append('}').append('\n');
		
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
	
	/**
	 * Generate the C prototype for the method header.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void test()
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * will output as "<code>static void test();</code>"<br>
	 * <br>
	 * In essence, this method is just {@link #generateCSourceSignature()}
	 * with a semi-colon attached to the end.
	 * 
	 * @return The C prototype for the method header.
	 */
	public String generateCSourcePrototype()
	{
		return generateCSourceSignature() + ";";
	}
	
	/**
	 * Generate the method signature that will appear in the c source
	 * output.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void test()
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * will output as "<code>static void test()</code>"
	 * 
	 * @return The method signature in the C language.
	 */
	public String generateCSourceSignature()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("static ");
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append('&');
		}
		else if (isPointer())
		{
			builder.append('*');
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
		
		builder.append(generateMethodName()).append('(');
		
		builder.append(getParameterListNode().generateCSourceOutput());
		
		builder.append(')');
		
		return builder.toString();
	}
	
	/**
	 * Generate the C Source output for a method name.
	 * 
	 * @return The C output for a method name.
	 */
	public String generateMethodName()
	{
		return generateMethodName(false);
	}
	
	/**
	 * Generate the C Source output for a method name.
	 * 
	 * @param header Whether or not the output is being created for a
	 * 		header file.
	 * @return The C output for a method name.
	 */
	public String generateMethodName(boolean header)
	{
		if (header)
		{
			return getName();
		}
		
		return "__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__" + getName();
	}
	
	/**
	 * Decode the given statement into a MethodNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public Person findPerson(String name, int age)</li>
	 * 	<li>private int calculateArea(int width, int height)</li>
	 * 	<li>public void doNothing()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		MethodNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a MethodNode.
	 */
	public static MethodNode decodeStatement(TreeNode parent, String statement, Location location)
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
					ParameterNode param = ParameterNode.decodeStatement(parent, parameters[i], location);
					
					if (param == null)
					{
						SyntaxMessage.error("Incorrect parameter definition", location);
						
						return null;
					}
					
					n.getParameterListNode().addChild(param);
				}
			}
			
			n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES);
			
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
		MethodNode node = new MethodNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given MethodNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodNode clone(MethodNode node)
	{
		super.clone(node);
		
		return node;
	}
}
