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
 * TreeNode extension that represents an Identifier. For the rules on
 * what can and cannot be an Identifier, refer to
 * {@link net.fathomsoft.fathom.tree.IdentifierNode#setName(java.lang.String) setName}
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:19 PM
 * @version	v0.2 Apr 2, 2014 at 8:35:16 PM
 */
public class IdentifierNode extends TreeNode
{
	private String	name;
	
	/**
	 * Get the name of the Identifier. For the rules on what can and
	 * cannot be an Identifier, refer to {@link net.fathomsoft.fathom.tree.IdentifierNode#setName(java.lang.String) setName}
	 * 
	 * @return The name of the Identifier.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Set the name of the Identifier. Identifier names consist of the
	 * following character types: [A-Z, a-z, 0-9, _]. However, cannot
	 * start with a number.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>correctGrammar</li>
	 * 	<li>INCORrect_grammaR_but_123123STILL_workz</li>
	 * 	<li>identifierName4</li>
	 * </ul>
	 * <br>
	 * Incorrect inputs include:<br>
	 * <ul>
	 * 	<li>24JeffGordon <i>(Cannot start an identifier with a number)</i></li>
	 * 	<li>This.Doesnt_work <i>(Cannot contain a period (or any other punctuation))</i></li>
	 * 	<li>#omgProgramin <i>(This is not Twitter)</i></li>
	 * </ul>
	 * 
	 * @param name The String containing the name to set as the
	 * 		identifier.
	 */
	public void setName(String name)
	{
//		if (!SyntaxUtils.isValidIdentifier(name))
//		{
//			SyntaxMessage.error("'" + name + "' is not a valid identifier", this);
//		}
		
		this.name = name;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return name;
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
		return name;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return name;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public IdentifierNode clone()
	{
		IdentifierNode node = new IdentifierNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given IdentifierNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IdentifierNode clone(IdentifierNode node)
	{
		super.clone(node);
		
		node.setName(name);
		
		return node;
	}
}