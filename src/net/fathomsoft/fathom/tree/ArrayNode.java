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

import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 16, 2014 at 1:13:49 AM
 * @since	v0.1
 * @version	Mar 16, 2014 at 1:13:49 AM
 * @version	v0.1
 */
public class ArrayNode extends IdentifierNode
{
	public ArrayNode()
	{
		
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return null;
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
		
//		IdentifierNode identifier = getIdentifierNode();
		
		builder.append('(').append(getName()).append('*').append(')');
		
		builder.append("malloc(sizeof(").append(getName()).append(')');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(" * ").append(child.generateCSourceOutput());
		}
		
		builder.append(')');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return generateCSourceOutput();
	}
	
	public static ArrayNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (SyntaxUtils.isArrayInitialization(statement))
		{
			ArrayNode n = new ArrayNode();
			
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
	public ArrayNode clone()
	{
		ArrayNode clone = new ArrayNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
	
}