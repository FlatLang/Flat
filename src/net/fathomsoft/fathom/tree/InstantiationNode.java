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
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

public class InstantiationNode extends IdentifierNode
{
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
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceFragment());
		}
		
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
	
	public static InstantiationNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		InstantiationNode n = null;
		
		if (SyntaxUtils.isInstantiation(statement))
		{
			n = new InstantiationNode();
			
			int startIndex = Regex.indexOf(statement, Patterns.POST_INSTANTIATION);
			
			String action = statement.substring(startIndex);
			
			Location newLoc = new Location();
			newLoc.setLineNumber(location.getLineNumber());
			newLoc.setOffset(location.getOffset() + startIndex);
			
			IdentifierNode child = null;
			
			if (SyntaxUtils.isMethodCall(action))
			{
//				Bounds bounds = Regex.boundsOf(action, Patterns.PRE_METHOD_CALL);
//				
//				String type = action.substring(0, bounds.getEnd());
				
				child = MethodCallNode.decodeStatement(parent, action, newLoc, false);
				
				MethodCallNode methodCall = (MethodCallNode)child;
				n.setName(methodCall.getName());
				methodCall.setName("new_" + methodCall.getName());
				
//				LiteralNode literalNode = new LiteralNode();
//				literalNode.setValue(value);
//				
//				child = literalNode;
			}
			else if (SyntaxUtils.isArrayInitialization(action))
			{
				child = ArrayNode.decodeStatement(parent, action, newLoc);
				n.setName(child.getName());
			}
			else
			{
				SyntaxMessage.error("Unable to parse instantiation", newLoc);
				
				return null;
			}
			
			n.addChild(child);
		}
		
		return n;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public InstantiationNode clone()
	{
		InstantiationNode node = new InstantiationNode();
		
		return clone(node);
	}
	
	public InstantiationNode clone(InstantiationNode node)
	{
		node.setName(getName());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}