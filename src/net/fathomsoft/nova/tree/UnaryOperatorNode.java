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

import java.util.HashMap;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;

/**
 * TreeNode extension that represents a unary operator node type.
 * See {@link #decodeStatement(TreeNode, String, Location)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:11 PM
 * @version	v0.2.6 May 24, 2014 at 6:06:20 PM
 */
public class UnaryOperatorNode extends TreeNode
{
	private static final int	LEFT = -1, EITHER = 0, RIGHT = 1;
	
	private static final HashMap<String, Integer>	SIDES;
	
	/**
	 * Initialize the SIDES HashMap.
	 */
	static
	{
		SIDES = new HashMap<String, Integer>();
		
		SIDES.put("-",  LEFT);
		SIDES.put("--", EITHER);
		SIDES.put("++", EITHER);
		SIDES.put("!",  LEFT);
	}
	
	/**
	 * The the TreeNode that represents the variable in the operation.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * var++;</pre></blockquote>
	 * In the previous statement, 'var' is the variable.
	 * 
	 * @return The TreeNode that represents the parameters of the method.
	 */
	public TreeNode getVariableNode()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			if (getChild(i) instanceof OperatorNode == false)
			{
				return getChild(i);
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
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
		return generateCSourceFragment() + ";\n";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a UnaryOperatorNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>asdf.var++</li>
	 * 	<li>++asdf.var</li>
	 * 	<li>asdf.var--</li>
	 * 	<li>--asdf.var</li>
	 * 	<li>var--</li>
	 * 	<li>--var</li>
	 * 	<li>var++</li>
	 * 	<li>++var</li>
	 * 	<li>++array[5]</li>
	 * 	<li>array[5]--</li>
	 * 	<li>!asdf.var</li>
	 * 	<li>!var</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		UnaryOperatorNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a UnaryOperatorNode.
	 */
	public static UnaryOperatorNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		Bounds bounds = StringUtils.findStrings(statement, StringUtils.UNARY_OPERATORS);
		
		if (bounds.getStart() >= 0)
		{
			UnaryOperatorNode n = new UnaryOperatorNode();
				
			String operatorVal  = statement.substring(bounds.getStart(), bounds.getEnd());
			
			OperatorNode operator = new OperatorNode();
			operator.setOperator(operatorVal);
			
			int varStart = 0;
			int varEnd   = 0;
			
			if (bounds.getStart() == 0)
			{
				if (SIDES.get(operatorVal) == RIGHT)
				{
					return null;
				}
				
				varStart = bounds.getEnd();
				varEnd   = statement.length();
				
				n.addChild(operator);
			}
			else if (bounds.getEnd() == statement.length())
			{
				if (SIDES.get(operatorVal) == LEFT)
				{
					return null;
				}
				
				varStart = 0;
				varEnd   = bounds.getStart();
			}
			else
			{
				return null;
			}
			
			String variableName = statement.substring(varStart, varEnd);
			
			IdentifierNode variable = TreeNode.getExistingNode(parent, variableName);
			
			if (variable != null)
			{
				variable = variable.clone();
				
				n.addChild(variable);
				
				if (bounds.getStart() > 0)
				{
					n.addChild(operator);
				}
				
				return n;
			}
			
			SyntaxMessage.error("Undeclared variable '" + variableName + "'", parent.getFileNode(), location, parent.getController());
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public UnaryOperatorNode clone()
	{
		UnaryOperatorNode node = new UnaryOperatorNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given UnaryOperatorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public UnaryOperatorNode cloneTo(UnaryOperatorNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}