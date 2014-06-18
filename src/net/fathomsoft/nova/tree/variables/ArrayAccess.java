package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Dimensions;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.Program;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Value extension that keeps track of any time an array is being
 * accessed. For example, the statement: "args[34]" is an array access.
 * Obviously the previous code segment does nothing, however these nodes
 * will be intertwined with method calls, assignments, if statements, etc.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2 Mar 24, 2014 at 10:45:29 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ArrayAccess extends Variable
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ArrayAccess(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Dimensions dimensions = new Dimensions(this, locationIn);
		
		addChild(dimensions);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getTypeClass()
	 */
	@Override
	public ClassDeclaration getTypeClass()
	{
		Program program = getProgram();
		String      name    = getTypeClassName(getArrayDimensions() - getNumDimensions());
		
		ClassDeclaration   clazz   = program.getClassDeclaration(name);
		
		return clazz;
	}
	
	/**
	 * Get the number of dimensions that this node contains.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * int i[][][] = new int[5][4][3];</pre></blockquote>
	 * The above array has three dimensions. In general, the number of
	 * dimensions relates to the number of sets of brackets there are
	 * contained within the array declaration.
	 * 
	 * @return The number of dimensions that this node contains.
	 */
	public int getNumDimensions()
	{
		return getDimensions().getNumChildren();
	}
	
	/**
	 * Get the node that represents the dimensions of the array. The
	 * Dimensions class contains information of the index that is
	 * being accessed.
	 * 
	 * @return The node that represents the dimensions being accessed.
	 */
	public Dimensions getDimensions()
	{
		return (Dimensions)getChild(0);
	}
	
	/**
	 * Add a dimension, that contains the index that is being attained,
	 * to the Dimensions instance of the ArrayAccess.
	 * 
	 * @param child The node that describes the index that is being
	 * 		accessed by the array at the specified dimension that is
	 * 		about to be added.
	 */
	public void addDimension(Node child)
	{
		getDimensions().addChild(child);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceFragment());
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder  builder        = new StringBuilder();
		
		Dimensions dimensions = getDimensions();
		
		builder.append(generateUseOutput());
		builder.append(dimensions.generateCSourceFragment());
		builder.append(generateChildrenCSourceFragment());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(super.generateNovaInput(false) + getDimensions().generateNovaInput());
		
		Identifier accessed = getAccessedNode();
		
		if (accessed != null)
		{
			builder.append('.').append(accessed.generateNovaInput());
		}
		
		return builder.toString();
	}

	/**
	 * Decode the given statement into an ArrayAccess if possible.
	 * If it is not possible, the method will return null.<br>
	 * <br>
	 * An example input would be: "args[34]"
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an ArrayAccess.
	 * @param location The location of the statement.
	 * @return The ArrayAccess if it was created, null if not.
	 */
	public static ArrayAccess decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isValidArrayAccess(statement))
		{
			ArrayAccess n  = new ArrayAccess(parent, location);
			
			Bounds idBounds    = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String identifier  = statement.substring(idBounds.getStart(), idBounds.getEnd());
			String indexData   = statement.substring(idBounds.getEnd());
			
			Bounds indexBounds = Regex.boundsOf(indexData, Patterns.ARRAY_BRACKETS_DATA);
			
			int    current     = indexBounds.getEnd() + 1;
			
			Variable var   = SyntaxTree.getExistingNode(parent, identifier);
			
			if (var == null)
			{
				SyntaxMessage.error("Undeclared variable '" + identifier + "'", n);
			}
			
			var.cloneTo(n);
			n.setLocationIn(location);
			
			while (current > 0)
			{
				String data = indexData.substring(indexBounds.getStart(), indexBounds.getEnd());
				
				Location newLoc = new Location(location);
				newLoc.setOffset(idBounds.getEnd() + location.getOffset());
				newLoc.setBounds(identifier.length() + indexBounds.getStart(), identifier.length() + indexBounds.getEnd());
				
				if (SyntaxUtils.isLiteral(data))
				{
					Literal literal = Literal.decodeStatement(n, data, newLoc, require, false);
					
					n.addDimension(literal);
				}
				else
				{
					Node created = SyntaxTree.decodeScopeContents(n, data, newLoc, require, false);
					
					if (created == null)
					{
						if (!require)
						{
							return null;
						}
						
						SyntaxMessage.error("Unknown array access index '" + data + "'", n, newLoc);
					}
					
					n.addDimension(created);
				}
				
				indexBounds = Regex.boundsOf(indexData, current, Patterns.ARRAY_BRACKETS_DATA);
				current     = indexBounds.getEnd() + 1;
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getAccessedNode()
	 */
	public Identifier getAccessedNode()
	{
		if (getNumChildren() <= 1)
		{
			return null;
		}
		
		return (Identifier)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ArrayAccess clone(Node temporaryParent, Location locationIn)
	{
		ArrayAccess node = new ArrayAccess(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArrayAccess with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArrayAccess cloneTo(ArrayAccess node)
	{
		super.cloneTo(node);
		
		return node;
	}
}