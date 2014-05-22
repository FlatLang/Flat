/**
 * The Nova Programming Language. Write Unbelievable Code.
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
package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.IdentifierNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * throw node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 11:02:52 PM
 * @version	v0.2.2 Apr 29, 2014 at 7:10:36 PM
 */
public class ThrowNode extends ExceptionHandlingNode
{
	/**
	 * Get the ExceptionNode that contains the information about the type
	 * of exception that was thrown.
	 * 
	 * @return The ExceptionNode instance that contains the information
	 * 		about the exception type.
	 */
	public ExceptionNode getException()
	{
		return (ExceptionNode)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ExceptionNode)
		{
			addChild(1, child);
		}
		else
		{
			super.addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("THROW").append('(').append(getException().getID()).append(')').append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Decode the given statement into a ThrowNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>throw new IOException()</li>
	 * 	<li>throw exceptionInstance;</li>
	 * 	<li>throw new IllegalArgumentException()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ThrowNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ThrowNode.
	 */
	public static ThrowNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.startsWith(statement, Patterns.PRE_THROW))
		{
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_THROW);
			
			if (bounds.getStart() > 0)
			{
				ThrowNode n          = new ThrowNode();
				
				Location  newLoc     = new Location(location);
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				String    thrown     = statement.substring(bounds.getStart(), bounds.getEnd());
				
				TreeNode  thrownNode = TreeNode.decodeStatement(parent, thrown, newLoc);
				
				if (thrownNode instanceof IdentifierNode)
				{
					IdentifierNode node = (IdentifierNode)thrownNode;
					
					ExceptionNode exception = new ExceptionNode();
					exception.setType(node.getName());
					
					n.addChild(exception);
					
					return n;
				}
				
				SyntaxMessage.error("Incorrect form of exception thrown", parent.getFileNode(), newLoc, parent.getController());
				
				return null;
			}
			
			SyntaxMessage.error("Throw statement missing exception type", parent.getFileNode(), location, parent.getController());
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public ThrowNode clone()
	{
		ThrowNode node = new ThrowNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ThrowNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ThrowNode cloneTo(ThrowNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}