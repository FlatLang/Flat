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
 * @version	v0.2.2 Apr 29, 2014 at 7:10:32 PM
 */
public class FieldListNode extends TreeNode
{
	/**
	 * Instantiate and initialize default data.
	 */
	public FieldListNode()
	{
		PrivateFieldListNode privateFields       = new PrivateFieldListNode();
		PublicFieldListNode  publicFields        = new PublicFieldListNode();
		PrivateFieldListNode privateStaticFields = new PrivateFieldListNode();
		PublicFieldListNode  publicStaticFields  = new PublicFieldListNode();
		
		addChild(privateFields);
		addChild(publicFields);
		addChild(privateStaticFields);
		addChild(publicStaticFields);
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
	 * Get the PrivateFieldListNode that contains all of the private
	 * FieldNodes for its parent ClassNode.
	 * 
	 * @return The PrivateFieldListNode instance.
	 */
	public PrivateFieldListNode getPrivateStaticFieldListNode()
	{
		return (PrivateFieldListNode)getChild(2);
	}
	
	/**
	 * Get the PublicFieldListNode that contains all of the public
	 * FieldNodes for its parent ClassNode.
	 * 
	 * @return The PublicFieldListNode instance.
	 */
	public PublicFieldListNode getPublicStaticFieldListNode()
	{
		return (PublicFieldListNode)getChild(3);
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
				if (field.isStatic())
				{
					getPrivateStaticFieldListNode().addChild(field);
				}
				else
				{
					getPrivateFieldListNode().addChild(field);
				}
			}
			else if (field.getVisibility() == DeclarationNode.PUBLIC)
			{
				if (field.isStatic())
				{
					getPublicStaticFieldListNode().addChild(field);
				}
				else
				{
					getPublicFieldListNode().addChild(field);
				}
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
		TreeNode nodes[] = new TreeNode[] { getPrivateFieldListNode(), getPrivateStaticFieldListNode(), getPublicFieldListNode(), getPublicStaticFieldListNode() };
		
		for (TreeNode node : nodes)
		{
			FieldNode field = searchFieldList(node, fieldName);
			
			if (field != null)
			{
				return field;
			}
		}
		
		return null;
	}
	
	/**
	 * Search the given TreeNode (which should only contain FieldNode
	 * children) for a FieldNode with the given name.
	 * 
	 * @param fieldList The list of fields to search through.
	 * @param fieldName The name of the field to search for.
	 * @return The FieldNode instance with the given name, if found.
	 */
	private FieldNode searchFieldList(TreeNode fieldList, String fieldName)
	{
		for (int i = 0; i < fieldList.getChildren().size(); i++)
		{
			FieldNode variable = (FieldNode)fieldList.getChild(i);
			
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
	 * Generate the C Header output for the all of the non-static
	 * variables.
	 * 
	 * @return The C Header file output.
	 */
	public String generateNonStaticCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getPublicFieldListNode().generateCHeader());
		
		return builder.toString();
	}
	
	/**
	 * Generate the C Header output for the all of the static variables.
	 * 
	 * @return The C Header file output.
	 */
	public String generateStaticCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getPrivateStaticFieldListNode().generateCHeader());
		builder.append(getPublicStaticFieldListNode().generateCHeader());
		
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