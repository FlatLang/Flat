package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that contains children of the type Import.
 * Contains all of a files imports.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 2, 2014 at 8:49:52 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class ImportList extends List
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ImportList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the given location has been imported.
	 * 
	 * @param importLocation The location of the import.
	 * @return Whether or not the given location has been imported.
	 */
	public boolean containsImport(String importLocation)
	{
		return getImport(importLocation) != null;
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation)
	{
		return getImport(importLocation, true);
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation, boolean absoluteLocation)
	{
		return getImport(importLocation, true, true);
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation, boolean absoluteLocation, boolean aliased)
	{
		if (importLocation == null)
		{
			return null;
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Import child = (Import)getChild(i);
			
			String location = child.getClassLocation(aliased);
			
			if (!absoluteLocation)
			{
				location = SyntaxUtils.getClassName(location);
			}
			
			if (importLocation.equals(location))
			{
				child.markUsed();
				
				return child;
			}
		}
		
		return null;
	}

	public String getAbsoluteClassLocation(String className)
	{
		return getAbsoluteClassLocation(className, false);
	}
	
	public String getAbsoluteClassLocation(String className, boolean aliased)
	{
		if (SyntaxUtils.isAbsoluteClassLocation(className))
		{
			return className;
		}
		
		ClassDeclaration clazz = getFileDeclaration().getClassDeclaration();
		
		if (clazz.getName().equals(className))
		{
			return clazz.getClassLocation();
		}
		
		Import i = getImport(className, false, true);
		
		if (i == null)
		{
			return null;
		}
		
		return i.getClassLocation(aliased);
	}
	
	/**
	 * Get whether or not the given location is an external C import.
	 * 
	 * @return Whether or not the given location is an external C import.
	 */
	public boolean isExternal(String importLocation)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Import child = (Import)getChild(i);
			
			if (child.isExternal() && child.getClassLocation().equals(importLocation))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateCSource(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		FileDeclaration file = getFileDeclaration();

		Import importNode = Import.decodeStatement(this, "import \"" + file.getClassDeclaration().getClassLocation() + "\"", getLocationIn(), true);
		
		return importNode.generateCSource(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ImportList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ImportList node = new ImportList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ImportList cloneTo(ImportList node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ImportList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ImportList cloneTo(ImportList node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the ImportList class type to make sure everything
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