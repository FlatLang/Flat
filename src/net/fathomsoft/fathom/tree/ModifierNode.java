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
 * IdentifierNode extension that represents an identifier that can
 * be a reference or pointer.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:23 PM
 * @version	v0.2 Apr 5, 2014 at 10:13:13 PM
 */
public class ModifierNode extends IdentifierNode
{
	private boolean	referenceVal;
	private boolean	pointerVal;
	
	/**
	 * Get whether or not the identifier is a reference.
	 * 
	 * @return Whether or not the identifier is a reference.
	 */
	public boolean isReference()
	{
		return referenceVal;
	}
	
	/**
	 * Set whether or not the identifier is a reference.
	 * 
	 * @param referenceVal Whether or not the identifier is a reference.
	 */
	public void setReference(boolean referenceVal)
	{
		this.referenceVal = referenceVal;
	}
	
	/**
	 * Get whether or not the identifier is a pointer.
	 * 
	 * @return Whether or not the identifier is a pointer.
	 */
	public boolean isPointer()
	{
		return pointerVal;
	}
	
	/**
	 * Set whether or not the identifier is a pointer.
	 * 
	 * @param pointerVal Whether or not the identifier is a pointer.
	 */
	public void setPointer(boolean pointerVal)
	{
		this.pointerVal = pointerVal;
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
	public ModifierNode clone()
	{
		ModifierNode node = new ModifierNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ModiferNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ModifierNode clone(ModifierNode node)
	{
		super.clone(node);
		
		node.setReference(isReference());
		node.setPointer(isPointer());
		
		return node;
	}
}