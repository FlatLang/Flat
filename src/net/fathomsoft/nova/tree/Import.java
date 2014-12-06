package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents the declaration of an
 * "import statement" node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 13, 2014 at 7:56:24 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Import extends Node
{
	private boolean used;
	private boolean	external;
	
	private String  alias;
	private String  location;
	
	public static final String IDENTIFIER    = "import";
	public static final String AS_IDENTIFIER = "as";
	
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

	public String getClassLocation()
	{
		return getClassLocation(false);
	}
	
	public String getClassLocation(boolean aliased)
	{
		if (aliased && alias != null)
		{
			return SyntaxUtils.getClassParentLocation(location) + "/" + alias;
		}
		
		return location;
	}
	
	public ClassDeclaration getClassDeclaration()
	{
		return getProgram().getClassDeclaration(getClassLocation());
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
			return location + ".h";
		}
		
		ClassDeclaration node = getProgram().getClassDeclaration(location);
		
		return node.getFileDeclaration().generateCHeaderName();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return builder.append(location);
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
		
		if (isExternal() || !getFileDeclaration().getName().equals(location))
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
		if (StringUtils.findNextWord(statement).equals(IDENTIFIER))
		{
			Import n = new Import(parent, location);
			
			int quoteStart = StringUtils.findNextNonWhitespaceIndex(statement, IDENTIFIER.length());
			
			if (quoteStart < 0 || statement.charAt(quoteStart) != '"')
			{
				return null;
			}
			
			int quoteEnd = StringUtils.findEndingChar(statement, '"', quoteStart, 1);
			
			if (quoteEnd < 0)
			{
				SyntaxMessage.error("Missing ending quotation for import statement", n);
			}
			
			String importLocation = statement.substring(quoteStart + 1, quoteEnd);
			String alias          = statement.substring(quoteEnd + 1).trim();
			
			if (n.validateImportLocation(importLocation) && n.validateAlias(alias, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	private boolean validateImportLocation(String importLocation)
	{
		location = validateExtension(importLocation);
		
		return true;
	}
	
	private boolean validateAlias(String alias, boolean require)
	{
		if (alias.length() > 0)
		{
			if (!StringUtils.findNextWord(alias).equals(AS_IDENTIFIER))
			{
				return SyntaxMessage.queryError("Unable to decode import statement '" + alias + "'", this, require);
			}
			
			alias = alias.substring(AS_IDENTIFIER.length() + 1).trim();
			
			if (!SyntaxUtils.isValidIdentifier(alias))
			{
				return SyntaxMessage.queryError("Invalid import alias identifier '" + alias + "'", this, require);
			}
			
			this.alias = alias;
		}
		
		return true;
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
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (!isExternal())
		{
			Program    program  = getProgram();
			
			ClassDeclaration clazz = program.getClassDeclaration(location);
			
			if (clazz == null)
			{
					SyntaxMessage.error("Unknown import location '" + location + "'", this, false);
					
					getParent().removeChild(this);
					
					return result.errorOccurred();
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Import clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
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