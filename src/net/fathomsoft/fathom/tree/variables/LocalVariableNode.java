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
package net.fathomsoft.fathom.tree.variables;

import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * VariableNode extension that represents the declaration of a local variable
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:00 PM
 */
public class LocalVariableNode extends VariableNode
{
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return super.generateJavaSourceOutput();
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
		return super.generateCSourceOutput();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return super.generateCSourceFragment();
	}
	
	/**
	 * Decode the given statement into a LocalVariableNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int index</li>
	 * 	<li>constant char c</li>
	 * 	<li>String name</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LocalVariableNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LocalVariableNode.
	 */
	public static LocalVariableNode decodeStatement(TreeNode parentNode, final String statement, Location location)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		
		LocalVariableNode n = new LocalVariableNode()
		{
			private boolean	done;
			
			private String	oldWord;
			
			@Override
			public void interactWord(String word, int wordNumber, Bounds bounds, int numWords)
			{
				if (!done)
				{
					setName(word);
					setType(oldWord);
					setAttribute(word, wordNumber);
					
					int  index = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd());
					
					if (index > 0)
					{
						char c     = statement.charAt(index);
						
						if (c == '.')
						{
							
						}
					}
					
					oldWord = word;
				}
				else
				{
					setArrayDimensions(1);
				}
				
				int firstBracketIndex = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd());
				
				if (firstBracketIndex >= 0)
				{
					char c = statement.charAt(firstBracketIndex);
					
					if (c == '[' && wordNumber == numWords - 2)
					{
						done = true;
					}
				}
				
				if (wordNumber == numWords - 1)
				{
					// If it is an array declaration.
					if (Regex.matches(statement, bounds.getEnd(), Patterns.EMPTY_ARRAY_BRACKETS))
					{
						int dimensions = SyntaxUtils.getArrayDimensions(statement);
						
						setArrayDimensions(dimensions);
					}
				}
			}
		};
		
		n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES);
		
		if (n.getType() == null)
		{
			return null;
		}
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public LocalVariableNode clone()
	{
		LocalVariableNode node = new LocalVariableNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given LocalVariableNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LocalVariableNode clone(LocalVariableNode node)
	{
		super.clone(node);
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
