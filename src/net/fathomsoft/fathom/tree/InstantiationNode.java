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
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

public class InstantiationNode extends TreeNode
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
			builder.append(getChild(i).generateCSourceOutput());
		}
		
		return builder.toString();
	}
	
	public static InstantiationNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		InstantiationNode n = null;
		
		if (SyntaxUtils.isInstantiation(statement))
		{
			n = new InstantiationNode();
			
			int startIndex = Regex.indexOf(statement, Patterns.POST_INSTANTIATION);
			
			statement = statement.substring(startIndex);
			
			Location newLoc = new Location();
			newLoc.setLineNumber(location.getLineNumber());
			newLoc.setOffset(location.getOffset() + startIndex);
			
			TreeNode child = null;
			
			if (SyntaxUtils.isMethodCall(statement))
			{
				child = MethodCallNode.decodeStatement(parent, statement, newLoc);
			}
			else if (SyntaxUtils.isArrayInitialization(statement))
			{
				child = ArrayNode.decodeStatement(parent, statement, newLoc);
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
		InstantiationNode clone = new InstantiationNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}