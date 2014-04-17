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

/**
 * TreeNode extension that keeps track of all of the arguments that
 * are passed during a method call. The children of this node are
 * all ArgumentNode instances. They are stored in the order that
 * they will be passed to the method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 10, 2014 at 3:12:54 AM
 * @version	v0.2 Mar 28, 2014 at 5:35:54 PM
 */
public class ArgumentListNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
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
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
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
	public TreeNode clone()
	{
		ArgumentListNode node = new ArgumentListNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ArgumentListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArgumentListNode clone(ArgumentListNode node)
	{
		super.clone(node);
		
		return node;
	}
}