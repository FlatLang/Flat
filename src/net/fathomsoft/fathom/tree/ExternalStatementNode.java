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

import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;

/**
 * TreeNode extension that represents an external statement.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.1 Apr 27, 2014 at 11:14:20 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class ExternalStatementNode extends TreeNode
{
	private String	data;
	
	/**
	 * Set the data that the external statement contains.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * external
	 * {
	 * 	int j;
	 * 	Thread* instance;
	 * 	
	 * 	instance = (Thread*)malloc(sizeof(Thread));
	 * }</pre></blockquote>
	 * The data in the external statement node above is:
	 * <blockquote><pre>
	 * int j;
	 * Thread* instance;
	 * 
	 * instance = (Thread*)malloc(sizeof(Thread));</pre></blockquote>
	 * 
	 * @param data The data to set.
	 */
	public void setData(String data)
	{
		data = StringUtils.removeTypeAfterChar(data, '\n', '\t');
		
		this.data = data;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + '\n';
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return data;
	}
	
	/**
	 * Decode the given statement into a ExternalStatementNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>external unsigned int i = 32;</li>
	 * 	<li>external { multiple; lines; of c code; }</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ExternalStatementNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ExternalStatementNode.
	 */
	public static ExternalStatementNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, Patterns.EXTERNAL))
		{
			ExternalStatementNode n = new ExternalStatementNode();
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ExternalStatementNode clone()
	{
		ExternalStatementNode node = new ExternalStatementNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ExternalStatementNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExternalStatementNode clone(ExternalStatementNode node)
	{
		super.clone(node);
		
		node.data = data;
		
		return node;
	}
}