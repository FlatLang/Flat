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

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * TreeNode extension that represents the declaration of an
 * "import statement" node type. See {@link net.fathomsoft.fathom.tree.ImportNode#decodeStatement(net.fathomsoft.fathom.tree.TreeNode, java.lang.String, net.fathomsoft.fathom.util.Location) decodeStatement}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 13, 2014 at 7:56:24 PM
 * @version	v0.2 Apr 2, 2014 at 9:02:32 PM
 */
public class ImportNode extends TreeNode
{
	private boolean	external;
	
	private String	importLocation;
	
	/**
	 * Get whether or not the imported file is a C Source file, or a
	 * Fathom file.
	 * 
	 * @return Whether or not the imported file is a C Source file.
	 */
	public boolean isExternal()
	{
		return external;
	}
	
	/**
	 * Set whether or not the imported file is a C Source file, or a
	 * Fathom file.
	 * 
	 * @param external Whether or not the imported file is a C Source file.
	 */
	public void setExternal(boolean external)
	{
		this.external = external;
	}
	
	/**
	 * Get the location of the import. For example, in the statement:
	 * 'import "net/fathomsoft/String";' the location is the
	 * net/fathomsoft/String section. In other words, the location is the
	 * content that is within the double quotes in an import statement.
	 * 
	 * @return The location of the import.
	 */
	public String getImportLocation()
	{
		return importLocation;
	}
	
	/**
	 * Set the location of the import. The location is where the file
	 * can be found, relative to the buildpath.
	 * 
	 * @param location The new location of the import.
	 */
	public void setImportLocation(String location)
	{
		this.importLocation = location;
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
		return generateCSourceOutput();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("#include ");
		
		if (isExternal())
		{
			builder.append('<').append(importLocation).append(".h").append('>');
		}
		else
		{
			builder.append('"').append(importLocation).append(".h").append('"');
		}
		
		builder.append('\n');
		
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
	 * Decode the given statement into an ImportNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * ImportNodes can either contain periods or slashes (backslashes or
	 * forward slashes) however, cannot contain both in the same import
	 * statement.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>import "net/fathomsoft/String";</li>
	 * 	<li>import "net.fathomSOFT.StRing";</li>
	 * 	<li>import "armadillo"; <i>(If armadillo is a class within the buildpath)</i></li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ImportNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ImportNode.
	 */
	public static ImportNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		ImportNode n = null;
		
		if (Regex.indexOf(statement, Patterns.PRE_IMPORT) == 0)
		{
			n = new ImportNode();
			
			int importStartIndex = Regex.indexOf(statement, Patterns.POST_IMPORT);
			
			if (importStartIndex >= 0)
			{
				int endIndex = statement.length() - 1;
				
				while (statement.charAt(endIndex) != '"')
				{
					endIndex--;
				}
				
				statement = statement.substring(importStartIndex + 1, endIndex);
				
				int extensionIndex = statement.lastIndexOf('.');
				
				if (extensionIndex > 0)
				{
					String extension = statement.substring(extensionIndex + 1);
					
					if (extension.equals("h"))
					{
						n.setExternal(true);
						
						statement = statement.substring(0, extensionIndex);
					}
					else
					{
						SyntaxMessage.error("Import location ends with unknown extension", location);
					}
				}
				
				n.setImportLocation(statement);
			}
			else
			{
				SyntaxMessage.error("Import statement must specify the location of the file", location);
			}
		}
		
		return n;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ImportNode clone()
	{
		ImportNode node = new ImportNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ImportNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ImportNode clone(ImportNode node)
	{
		super.clone(node);
		
		node.setImportLocation(getImportLocation());
		node.external = isExternal();
		
		return node;
	}
}