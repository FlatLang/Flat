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
import net.fathomsoft.fathom.tree.variables.FieldListNode;
import net.fathomsoft.fathom.tree.variables.PrivateFieldListNode;
import net.fathomsoft.fathom.tree.variables.PublicFieldListNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * IdentifierNode extension that contains the information describing
 * an array instantiation. The getName() method contains the data type
 * of the array. The children that the node embodies list the sizes of
 * each of the dimensions of the array that is being created. For
 * instance, consider the following scenario:<br>
 * <br>
 * The ArrayNode encompasses two children. The first child is a
 * LiteralNode that contains the value 56. This means that the first
 * dimension of the array will have the size of 56. The second child
 * is a LocalVariableNode containing the data for an integer variable
 * that was declared within the method that the array was declared in.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 16, 2014 at 1:13:49 AM
 * @version	v0.2 Mar 28, 2014 at 6:30:49 PM
 */
public class VTableNode extends ClassNode
{
	public static final String	VTABLE_IDENTIFIER = "vtable";
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("CLASS");
		
		if (isStatic())
		{
			SyntaxMessage.error("Static classes are not implemented in C yet.", this);
		}
		if (isConstant())
		{
			SyntaxMessage.error("Const classes are not implemented in C yet.", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("A class cannot be of a reference type", getLocationIn(), getController());
			
			return null;
		}
		else if (isPointer())
		{
			SyntaxMessage.error("A class cannot be of a pointer type", getLocationIn(), getController());
			
			return null;
		}
		
		builder.append('\n').append('(').append('\n');
		
		builder.append(getName()).append(", ");
		
		builder.append('\n').append('\n');

		FieldListNode fields = getFieldListNode();
		
		PublicFieldListNode publicFields = fields.getPublicFieldListNode();
		
		if (publicFields.getChildren().size() > 0)
		{
			builder.append(publicFields.generateCHeader()).append('\n');
		}
		
		builder.append(')').append('\n').append('\n');
		
		MethodListNode constructors = getConstructorListNode();
		builder.append(constructors.generateCHeader());
		
		MethodListNode destructors = getDestructorListNode();
		builder.append(destructors.generateCHeader());
		
		MethodListNode methods = getMethodListNode();
		builder.append(methods.generateCHeader());
		
		if (containsStaticData())
		{
			builder.append("extern ").append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
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
		
		if (containsStaticData())
		{
			builder.append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
		}
		
		FieldListNode fields = getFieldListNode();
		
		PrivateFieldListNode privateFields = fields.getPrivateFieldListNode();
		
		if (privateFields.getChildren().size() > 0)
		{
			builder.append("PRIVATE").append('\n').append('(').append('\n');
			
			builder.append(privateFields.generateCSource());
			
			builder.append(')');
		}
		else
		{
			builder.append("NO_PRIVATE");
		}
		
		builder.append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != fields)
			{
				builder.append(child.generateCSource());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into an ArrayNode instance. If the
	 * given statement cannot be decoded into an ArrayNode, then null is
	 * returned.<br>
	 * <br>
	 * An example input would be: "char[length]" <i>(Where as 'length' is
	 * a number variable)</i> or any other class name followed by square
	 * brackets that enclose a size variable or literal.<br>
	 * <br>
	 * Other example inputs:<br>
	 * <ul>
	 * 	<li>String[5][size] <i>(Where as 'size' is a number variable)</i></li>
	 * 	<li>int[names.getSize()]</li>
	 *  <li>TreeNode[elements.getSize() * (4 + 3) / 2]</li>
	 * </ul>
	 * <br>
	 * Array initializer statements are to be implemented in the future.
	 * Such syntax would consist of the following: "int[] { 1, 6, 3, 1 }"
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an ArrayNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The new ArrayNode instance if it was able to decode the
	 * 		statement. If not, it will return null.
	 */
	public static VTableNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (SyntaxUtils.isArrayInitialization(statement))
		{
			VTableNode n = new VTableNode();
			
			int index = statement.indexOf('[') + 1;
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String idValue = statement.substring(bounds.getStart(), bounds.getEnd());
			
			n.setName(idValue);
			
			while (index > 1)
			{
				int endIndex  = statement.indexOf(']', index);
				
				String length = statement.substring(index, endIndex);
				
				if (SyntaxUtils.isNumber(length))
				{
					LiteralNode node = new LiteralNode();
					node.setValue(length, parent.isWithinExternalContext());
					
					n.addChild(node);
				}
				else
				{
					IdentifierNode node = TreeNode.getExistingNode(parent, length).clone();
					
					n.addChild(node);
				}
				
				index = statement.indexOf('[', endIndex + 1) + 1;
			}
			
			return n;
		}
		
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public VTableNode clone()
	{
		VTableNode node = new VTableNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ArrayNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VTableNode clone(VTableNode node)
	{
		super.clone(node);
		
		return node;
	}
}