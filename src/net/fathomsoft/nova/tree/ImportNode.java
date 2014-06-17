package net.fathomsoft.nova.tree;

import java.io.ObjectInputStream.GetField;

import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * TreeNode extension that represents the declaration of an
 * "import statement" node type. See {@link #decodeStatement(TreeNode, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 13, 2014 at 7:56:24 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ImportNode extends TreeNode
{
	private boolean	external;
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
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
	 * Get the IdentifierNode containing the location of the import.
	 * 
	 * @return The IdentifierNode containing the location of the import.
	 */
	public IdentifierNode getLocationNode()
	{
		return (IdentifierNode)getChild(0);
	}
	
	/**
	 * Get the location of the import. For example, in the statement:
	 * 'import "net/fathomsoft/String";' the location is the
	 * net/fathomsoft/NovaString.h section. In other words, the location is the
	 * content that is within the double quotes in an import statement.
	 * 
	 * @return The location of the import.
	 */
	public String getLocation()
	{
		if (isExternal())
		{
			return getLocationNode().getName() + ".h";
		}
		
		ClassNode node = getProgramNode().getClassNode(getLocationNode().getName());
		
		return node.getFileNode().generateCHeaderName();
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
			builder.append('<').append(getLocation()).append('>');
		}
		else
		{
			builder.append('"').append(getLocation()).append('"');
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
	 * 	<li>import "net/fathomsoft/String"</li>
	 * 	<li>import "armadillo"<i>(If armadillo is a class within the current package)</i></li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ImportNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ImportNode.
	 */
	public static ImportNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
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
						SyntaxMessage.error("Import location ends with unknown extension", n);
					}
				}
				
				IdentifierNode node = new IdentifierNode(n, location);
				node.setName(statement);
				
				n.addChild(node);
				
//				n.setImportLocation(statement);
			}
			else
			{
				SyntaxMessage.error("Import statement must specify the location of the file", n);
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#validate(int)
	 */
	@Override
	public TreeNode validate(int phase)
	{
		if (!isExternal())
		{
			IdentifierNode location = getLocationNode();
			
			ProgramNode    program  = getProgramNode();
			
			ClassNode      clazz    = program.getClassNode(location.getName());
			
			if (clazz == null)
			{
				try
				{
					SyntaxMessage.error("Unknown import location '" + location.getName() + "'", this);
				}
				catch (SyntaxErrorException e)
				{
					getParent().removeChild(this);
					
					return null;
				}
			}
			
			IdentifierNode node = new IdentifierNode(this, location.getLocationIn());
			
			location.cloneTo(node);
			
			replace(location, location);
		}
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
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
		
		node.external = external;
		
		return node;
	}
}