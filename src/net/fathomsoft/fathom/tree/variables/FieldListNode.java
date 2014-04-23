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
package net.fathomsoft.fathom.tree.variables;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.ClassNode;
import net.fathomsoft.fathom.tree.DeclarationNode;
import net.fathomsoft.fathom.tree.TreeNode;

/**
 * TreeNode extension that contains all of the FieldNodes for a ClassNode.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version	v0.2 Apr 7, 2014 at 7:50:32 PM
 */
public class FieldListNode extends TreeNode
{
	/**
	 * Instantiate and initialize default data.
	 */
	public FieldListNode()
	{
		PrivateFieldListNode privateFields = new PrivateFieldListNode();
		PublicFieldListNode  publicFields  = new PublicFieldListNode();
		
		addChild(privateFields);
		addChild(publicFields);
	}
	
	/**
	 * Get the PrivateFieldListNode that contains all of the private
	 * FieldNodes for its parent ClassNode.
	 * 
	 * @return The PrivateFieldListNode instance.
	 */
	public PrivateFieldListNode getPrivateFieldListNode()
	{
		return (PrivateFieldListNode)getChild(0);
	}
	
	/**
	 * Get the PublicFieldListNode that contains all of the public
	 * FieldNodes for its parent ClassNode.
	 * 
	 * @return The PublicFieldListNode instance.
	 */
	public PublicFieldListNode getPublicFieldListNode()
	{
		return (PublicFieldListNode)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode node)
	 */
	@Override
	public void addChild(TreeNode node)
	{
		if (node instanceof FieldNode)
		{
			FieldNode field = (FieldNode)node;
			
			if (field.getVisibility() == DeclarationNode.PRIVATE)
			{
				getPrivateFieldListNode().addChild(field);
			}
			else if (field.getVisibility() == DeclarationNode.PUBLIC)
			{
				getPublicFieldListNode().addChild(field);
			}
			else
			{
				SyntaxMessage.error("Missing visibility declaration", field);
			}
		}
		else
		{
			super.addChild(node);
		}
	}
	
	/**
	 * Get whether or not the FieldListNode contains the FieldNode with
	 * the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsField("age")</code>" would return true.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return Whether or not the FieldListNode contains the FieldNode
	 * 		with the specified name.
	 */
	public boolean containsField(String fieldName)
	{
		return getField(fieldName) != null;
	}
	
	/**
	 * Get the FieldListNode's FieldNode with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	private int age;
	 * 	private String name;
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getField("age")</code>" would return the
	 * FieldNode for the "<code>age</code>" int field.
	 * 
	 * @param fieldName The name of the field to search for.
	 * @return The FieldNode for the field, if it exists.
	 */
	public FieldNode getField(String fieldName)
	{
		PrivateFieldListNode privateFields = getPrivateFieldListNode();
		PublicFieldListNode  publicFields  = getPublicFieldListNode();
		
		for (int i = 0; i < privateFields.getChildren().size(); i++)
		{
			FieldNode variable = (FieldNode)privateFields.getChild(i);
			
			if (variable.getName().equals(fieldName))
			{
				return variable;
			}
		}
		
		for (int i = 0; i < publicFields.getChildren().size(); i++)
		{
			FieldNode variable = (FieldNode)publicFields.getChild(i);
			
			if (variable.getName().equals(fieldName))
			{
				return variable;
			}
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
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		if (getChildren().size() > 0)
		{
			ClassNode parent = (ClassNode)getAncestorOfType(ClassNode.class, true);
			
			if (parent.getMethodListNode().getChildren().size() > 0)
			{
				builder.append('\n');
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCHeader());
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		boolean hasMethods    = false;
		
		if (getChildren().size() > 0)
		{
			ClassNode parent = (ClassNode)getAncestorOfType(ClassNode.class, true);
			
			if (parent.getMethodListNode().getChildren().size() > 0)
			{
				hasMethods = true;
			}
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSource());
		}
		
		if (hasMethods)
		{
			builder.append('\n');
		}
		
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public FieldListNode clone()
	{
		FieldListNode node = new FieldListNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given FieldListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FieldListNode clone(FieldListNode node)
	{
		super.clone(node);
		
		return node;
	}
}