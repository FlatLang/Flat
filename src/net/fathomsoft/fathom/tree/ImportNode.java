package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 13, 2014 at 7:56:24 PM
 * @since	v
 * @version	Jan 13, 2014 at 7:56:24 PM
 * @version	v
 */
public class ImportNode extends TreeNode
{
	private boolean	cSource;
	
	private String	importLocation;
	
	public ImportNode()
	{
		
	}
	
	/**
	 * Get whether or not the imported file is a C Source file, or a
	 * Fathom file.
	 * 
	 * @return Whether or not the imported file is a C Source file.
	 */
	public boolean isCSource()
	{
		return cSource;
	}
	
	public String getImportLocation()
	{
		return importLocation;
	}
	
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
		StringBuilder builder = new StringBuilder();
		
		builder.append("#include ").append('"').append(importLocation).append(".h").append('"').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("#include ").append('"').append(importLocation).append(".h").append('"').append('\n');
		
		return builder.toString();
	}
	
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
						n.cSource = true;
						
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
}