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

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.tree.variables.FieldNode;
import net.fathomsoft.nova.tree.variables.StaticFieldListNode;
import net.fathomsoft.nova.tree.variables.InstanceFieldListNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * MethodNode extension that represents the declaration of a Destructor
 * node type. See {@link net.fathomsoft.nova.tree.DestructorNode#decodeStatement(net.fathomsoft.nova.tree.TreeNode, java.lang.String, net.fathomsoft.nova.util.Location) decodeStatement}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:50:43 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class DestructorNode extends MethodNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public DestructorNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			if (getVisibility() == InstanceDeclarationNode.PRIVATE)
			{
				SyntaxMessage.error("Destructor must be public", getFileNode(), getLocationIn(), getController());
				
				return null;
			}
		}
		
		if (isConstant())
		{
			SyntaxMessage.error("Destructor cannot be const", getFileNode(), getLocationIn(), getController());
			
			return null;
		}
		
		if (isReference())
		{
			SyntaxMessage.error("Destructor cannot return a reference", getFileNode(), getLocationIn(), getController());
			
			return null;
		}
		
		builder.append(generateCSourcePrototype()).append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceSignature()).append('\n').append('{').append('\n');

		builder.append(nullChecker()).append('\n');
		
		builder.append(deleteData()).append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getParameterListNode())
			{
				builder.append(child.generateCSource());
			}
		}
		
		builder.append("free(").append('*').append(MethodNode.getObjectReferenceIdentifier()).append(");").append('\n');
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Generate the code needed to check if a variable is null before
	 * trying to free its members.
	 * 
	 * @return The code needed to check whether a variable is null or not.
	 */
	private String nullChecker()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("if (!*").append(MethodNode.getObjectReferenceIdentifier()).append(')').append('\n');
		builder.append('{').append('\n');
		builder.append("return;").append('\n');
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Generate the code needed to delete each member of the class.
	 * 
	 * @return The code needed to delete each member of the class.
	 */
	private String deleteData()
	{
		StringBuilder builder   = new StringBuilder();
		
		ClassNode     classNode = (ClassNode)getAncestorOfType(ClassNode.class);
		
		InstanceFieldListNode privateFields = classNode.getFieldListNode().getPrivateFieldListNode();
		
		for (int i = 0; i < privateFields.getChildren().size(); i++)
		{
			FieldNode field = (FieldNode)privateFields.getChild(i);

			builder.append(generateFreeFieldSource(field)).append('\n');
		}
		
		if (classNode.containsNonStaticPrivateData())
		{
			builder.append(generateFreeMemberSource("prv")).append('\n');
		}
		
		InstanceFieldListNode publicFields = classNode.getFieldListNode().getPublicFieldListNode();
		
		for (int i = 0; i < publicFields.getChildren().size(); i++)
		{
			FieldNode field = (FieldNode)publicFields.getChild(i);
			
			builder.append(field.generateFreeOutput());
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * field variable located within the current class.
	 * 
	 * @param field The node that contains the information of the field.
	 * @return The generated String for the code.
	 */
	private String generateFreeFieldSource(FieldNode field)
	{
		StringBuilder builder = new StringBuilder();
		
		if (field.isPrimitiveType() || field.isExternal())
		{
			if (!field.isPrimitive())
			{//builder.append("printf(\"Before. " + field.generateVariableUseOutput(true) + ": %p\", " + field.generateVariableUseOutput(true) + ");");
				//builder.append("free(").append(field.generateVariableUseOutput(true)).append(");");builder.append("printf(\"Aft2.\");");
			}
		}
		else
		{
			builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_").append(field.getType()).append('(').append('&').append(field.generateUseOutput(true)).append(", ").append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER).append(");");
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * member variable with the given name.
	 * 
	 * @param name The name of the variable to delete.
	 * @return The generated String for the code.
	 */
	private String generateFreeMemberSource(String name)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("free((*").append(MethodNode.getObjectReferenceIdentifier()).append(")->").append(name).append(");");
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodNode#generateCSourcePrototype()
	 */
	public String generateCSourcePrototype()
	{
		return generateCSourceSignature().concat(";");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodNode#generateCSourceSignature()
	 */
	public String generateCSourceSignature()
	{
		StringBuilder builder = new StringBuilder();

		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class);
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType());
		builder.append(' ');
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_del_");
		builder.append(classNode.getName()).append('(');
		
		//builder.append(classNode.getName()).append('*').append(' ').append(MethodNode.getObjectReferenceIdentifier());
		builder.append(getParameterListNode().generateCSource());
		
		builder.append(')');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a DestructorNode instance, if
	 * possible. If it is not possible, this method returns null. A
	 * destructor must have the same name as the class that it is
	 * within preceded by a tilde (A tilde is a '~' located above the tab
	 * key). Destructors also do not have a return value and are always
	 * private. They never accept parameters, because they are never
	 * called programmatically.<br>
	 * <br>
	 * The only acceptable syntax input includes: "private ~ClassName()"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		DestructorNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a DestructorNode.
	 */
	public static DestructorNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		int firstParenthIndex = statement.indexOf('(');
		int lastParenthIndex  = statement.lastIndexOf(')');
		
		if (firstParenthIndex >= 0)
		{
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", parent.getFileNode(), location, parent.getController());
				
				return null;
			}
			
			String parameterList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String parameters[]  = StringUtils.splitCommas(parameterList);
			
			final String signature = statement.substring(0, firstParenthIndex);
			
			DestructorNode n = new DestructorNode(parent)
			{
				public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
				{
					setAttribute(word, wordNumber);
					
					if (wordNumber == numWords - 1)
					{
						if (signature.charAt(bounds.getStart() - 1) == '~')
						{
							setName(word);
						}
					}
				}
			};
			
			n.iterateWords(signature);
			
			ClassNode classNode = (ClassNode)parent.getAncestorOfType(ClassNode.class, true);
			
			if (classNode.getName().equals(n.getName()))
			{
				if (n.getName() == null)
				{
					return null;
				}
				
				if (parameters.length > 0)
				{
					SyntaxMessage.error("Destructors cannot have any parameters", parent.getFileNode(), location, parent.getController());
					
					return null;
				}
				
				n.setLocationIn(location);
				n.setType("void");
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public DestructorNode clone(TreeNode temporaryParent)
	{
		DestructorNode node = new DestructorNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given DestructorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public DestructorNode cloneTo(DestructorNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
