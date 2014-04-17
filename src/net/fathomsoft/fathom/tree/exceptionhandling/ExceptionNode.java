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
package net.fathomsoft.fathom.tree.exceptionhandling;

import java.util.HashMap;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Location;

/**
 * TreeNode extension that represents an Exception instance that is to
 * be thrown within a ThrowNode statement.
 * <blockquote><pre>
 * // Instantiate a new Exception type.
 * ExceptionName varName = new ExceptionName(... optional arguments ...);
 * 
 * // Throw the generated Exception type.
 * throw varName;</pre></blockquote>
 * For more information on what
 * it looks like to throw an ExceptionNode, see
 * {@link ThrowNode#decodeStatement(TreeNode, String, Location)}.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 6, 2014 at 8:44:35 PM
 * @version	v0.2 Apr 7, 2014 at 8:07:35 PM
 */
public class ExceptionNode extends TreeNode
{
	private int			id;
	
	private static int	exceptionId	= 1;
	
	private static final HashMap<String, Integer>	ids;
	
	/**
	 * Identifier for the exception data passed to a method.
	 */
	public static final String	EXCEPTION_DATA_IDENTIFIER = "__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__exception_data";
	
	/**
	 * Initialize static data.
	 */
	static
	{
		ids = new HashMap<String, Integer>();
		
		ids.put("Exception", exceptionId++);
	}
	
	/**
	 * Get the ID of the Exception. The ID is needed for implementing
	 * the c version of the exception handling.
	 * 
	 * @return The ID of the Exception.
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Set the type of Exception that is being generated. If the type
	 * is unique to anything created prior to this, the id of the
	 * generated ExceptionNode will also be unique. If the Exception
	 * type has already been used before, it will use the already
	 * generated id that belongs to the ExceptionNode.
	 * 
	 * @param type The name (type) of the Exception that is being
	 * 		generated.
	 */
	public void setType(String type)
	{
		if (ids.containsKey(type))
		{
			id = ids.get(type);
		}
		else
		{
			id = exceptionId++;
			
			ids.put(type, id);
		}
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
		return null;
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
	public ExceptionNode clone()
	{
		ExceptionNode node = new ExceptionNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ExceptionNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExceptionNode clone(ExceptionNode node)
	{
		node.id = id;
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
