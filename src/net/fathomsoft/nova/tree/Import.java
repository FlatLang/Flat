package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * Node extension that represents the declaration of an
 * "import statement" node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 13, 2014 at 7:56:24 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class Import extends Node
{
	private boolean used;
	private boolean	external;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Import(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the specified Import is actually used within
	 * the File it was imported in.
	 * 
	 * @return Whether or not the Import was used.
	 */
	public boolean isUsed()
	{
		return used;
	}
	
	/**
	 * Mark that the specified Import was used and is required.
	 */
	public void markUsed()
	{
		used = true;
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
	 * Get the Identifier containing the location of the import.
	 * 
	 * @return The Identifier containing the location of the import.
	 */
	public Identifier getLocationNode()
	{
		return (Identifier)getChild(0);
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
		
		ClassDeclaration node = getProgram().getClassDeclaration(getLocationNode().getName());
		
		return node.getFileDeclaration().generateCHeaderName();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return builder.append(getLocation());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCSource(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		builder.append("#include ");
		
		if (isExternal() || !getFileDeclaration().getName().equals(getLocationNode().getName()))
		{
			return builder.append('<').append(getLocation()).append('>').append('\n');
		}
		else
		{
			return builder.append('"').append(getLocation()).append('"').append('\n');
		}
	}
	
	/**
	 * Decode the given statement into an Import instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * Imports can either contain periods or slashes (backslashes or
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
	 * 		Import instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Import.
	 */
	public static Import decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (Regex.indexOf(statement, Patterns.PRE_IMPORT) == 0)
		{
			Import n = new Import(parent, location);
			
			int importStartIndex = Regex.indexOf(statement, Patterns.POST_IMPORT);
			
			if (importStartIndex >= 0)
			{
				int endIndex = statement.length() - 1;
				
				if (statement.charAt(endIndex) != '"')
				{
					SyntaxMessage.error("Import statement missing ending quotation", n);
				}
				
				String importLocation = statement.substring(importStartIndex + 1, endIndex);
				
				importLocation = n.validateExtension(importLocation);
				
				IIdentifier node = new IIdentifier(n, location);
				node.setName(importLocation);
				
				n.addChild(node);
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
	 * Validate that the given importLocation either does not have an
	 * extension, or has a .h extension.
	 * 
	 * @param importLocation The location of the import statement.
	 * @return The location to set as the import location.
	 */
	private String validateExtension(String importLocation)
	{
		int extensionIndex = importLocation.lastIndexOf('.');
		
		if (extensionIndex > 0)
		{
			String extension = importLocation.substring(extensionIndex + 1);
			
			if (extension.equals("h"))
			{
				setExternal(true);
				markUsed();
				getController().addExternalImport(getFileDeclaration(), importLocation);
				
				return importLocation.substring(0, extensionIndex);
			}
			else
			{
				SyntaxMessage.error("Import location ends with unknown extension", this);
			}
		}
		
		return importLocation;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		if (!isExternal())
		{
			Identifier location = getLocationNode();
			
			Program    program  = getProgram();
			
			ClassDeclaration clazz = program.getClassDeclaration(location.getName());
			
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
			
			IIdentifier node = new IIdentifier(this, location.getLocationIn());
			
			location.cloneTo(node);
			
			//replace(location, location);
		}
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Import clone(Node temporaryParent, Location locationIn)
	{
		Import node = new Import(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Import} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Import cloneTo(Import node)
	{
		super.cloneTo(node);
		
		node.external = external;
		
		return node;
	}
	
	/**
	 * Test the Import class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}