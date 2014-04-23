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

import java.util.ArrayList;

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * try node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:38 PM
 * @version	v0.2 Apr 7, 2014 at 7:55:12 PM
 */
public class TryNode extends ExceptionHandlingNode
{
	private ArrayList<Integer>	codes;
	
	/**
	 * Instantiate and initialize default data.
	 */
	public TryNode()
	{
		codes = new ArrayList<Integer>();
	}
	
	/**
	 * Add the specified exception code to the list of exceptions that
	 * this try exception handling block catches.
	 * 
	 * @param code The type of exception code that is caught.
	 */
	public void addExceptionCode(int code)
	{
		codes.add(code);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("TRY").append('\n');
		builder.append('{').append('\n');
		builder.append(generateExceptionCodes()).append('\n');
		
		builder.append(getScopeNode().generateCSource());
		
		builder.append('}').append('\n');
		
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
	 * Decode the given statement into a TryNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * The only correct input is "try"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		TryNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a TryNode.
	 */
	public static TryNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.TRY))
		{
			TryNode n = new TryNode();
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * Generate a String that adds all of the exception codes that this
	 * try node catches to the exception data instance.
	 * 
	 * @return The generated C language String.
	 */
	private String generateExceptionCodes()
	{
		StringBuilder builder = new StringBuilder();
			
		String variableName = ExceptionNode.EXCEPTION_DATA_IDENTIFIER;
		
		for (int i = 0; i < codes.size(); i++)
		{
			int code = codes.get(i);
			
			builder.append("fathom_ExceptionData_addCode(").append(variableName).append(", ").append(variableName).append(", ").append(code).append(");").append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public TryNode clone()
	{
		TryNode node = new TryNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given TryNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TryNode clone(TryNode node)
	{
		super.clone(node);
		
		return node;
	}
}