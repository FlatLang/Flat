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

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents an external type of variable or
 * method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 8, 2014 at 6:55:51 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class ExternalTypeNode extends IdentifierNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ';' + '\n';
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return super.generateChildrenCSourceFragment(false);
	}
	
	/**
	 * Decode the given statement into a ExternalTypeNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>externalHeaderName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ExternalTypeNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ExternalTypeNode.
	 */
	public static ExternalTypeNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		FileNode file = parent.getFileNode();
		
		if (file.isExternalImport(statement))
		{
			ExternalTypeNode n = new ExternalTypeNode();
			
			n.setName(statement);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public ExternalTypeNode clone()
	{
		ExternalTypeNode node = new ExternalTypeNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExternalTypeNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExternalTypeNode cloneTo(ExternalTypeNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}