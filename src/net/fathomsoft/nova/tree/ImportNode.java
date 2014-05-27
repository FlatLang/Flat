package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * TreeNode extension that represents the declaration of an
 * "import statement" node type. See {@link net.fathomsoft.nova.tree.ImportNode#decodeStatement(net.fathomsoft.nova.tree.TreeNode, java.lang.String, net.fathomsoft.nova.util.Location) decodeStatement}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 13, 2014 at 7:56:24 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class ImportNode extends TreeNode
{
	private boolean	external;
	
	private String	importLocation;
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public ImportNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the imported file is a C Source file, or a
	 * Nova file.
	 * 
	 * @return Whether or not the imported file is a C Source file.
	 */
	public boolean isExternal()
	{
		return external;
	}
	
	/**
	 * Set whether or not the imported file is a C Source file, or a
	 * Nova file.
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
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
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
		if (Regex.indexOf(statement, Patterns.PRE_IMPORT) == 0)
		{
			ImportNode n = new ImportNode(parent, location);
			
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
						SyntaxMessage.error("Import location ends with unknown extension", parent.getFileNode(), location, parent.getController());
						
						return null;
					}
				}
				
				n.setImportLocation(statement);
			}
			else
			{
				SyntaxMessage.error("Import statement must specify the location of the file", parent.getFileNode(), location, parent.getController());
				
				return null;
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ImportNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ImportNode node = new ImportNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ImportNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ImportNode cloneTo(ImportNode node)
	{
		super.cloneTo(node);
		
		node.setImportLocation(getImportLocation());
		node.external = isExternal();
		
		return node;
	}
}