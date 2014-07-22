package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Dimensions;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Program;
import net.fathomsoft.nova.tree.SyntaxTree;
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
 * @version	v0.2.16 Jul 22, 2014 at 12:47:19 AM
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
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getTypeClass()
	 */
	@Override
	public ClassDeclaration getTypeClass()
	{
		Program program = getProgram();
		String  name    = getTypeClassName(getArrayDimensions() - getNumDimensions());
		
		ClassDeclaration clazz = program.getClassDeclaration(name);
		
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
	 * @see net.fathomsoft.nova.tree.Value#generateCTypeOutput(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCTypeOutput(StringBuilder builder)
	{
		return super.generateCTypeOutput(builder, false);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.Variable#generateCUseOutput(StringBuilder)
	 */
	@Override
	public StringBuilder generateCUseOutput(StringBuilder builder)
	{
		Dimensions dimensions = getDimensions();
		
		super.generateCUseOutput(builder);
		dimensions.generateCSourceFragment(builder);
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		super.generateNovaInput(builder, false).append(getDimensions().generateNovaInput());
		
		if (doesAccess())
		{
			builder.append('.').append(getAccessedNode().generateNovaInput());
		}
		
		return builder;
	}

	/**
	 * Decode the given statement into an ArrayAccess if possible.
	 * If it is not possible, the method will return null.<br>
	 * <br>
	 * An example input would be: "args[34]"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ArrayAccess instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ArrayAccess.
	 */
	public static ArrayAccess decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (SyntaxUtils.isValidArrayAccess(statement))
		{
			ArrayAccess n  = new ArrayAccess(parent, location);
			
			Bounds idBounds    = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String identifier  = statement.substring(idBounds.getStart(), idBounds.getEnd());
			String indexData   = statement.substring(idBounds.getEnd());
			
			Bounds indexBounds = Regex.boundsOf(indexData, Patterns.ARRAY_BRACKETS_DATA);
			
			int    current     = indexBounds.getEnd() + 1;
			
			VariableDeclaration var = SyntaxTree.findDeclaration(parent, identifier);
			
			if (var == null)
			{
				SyntaxMessage.error("Undeclared variable '" + identifier + "'", n);
			}
			
			var.generateUsableVariable(n);
			n.setLocationIn(location);
			
			while (current > 0)
			{
				String data = indexData.substring(indexBounds.getStart(), indexBounds.getEnd());
				
				Location newLoc = new Location(location);
				newLoc.setOffset(idBounds.getEnd() + location.getOffset());
				newLoc.setBounds(identifier.length() + indexBounds.getStart(), identifier.length() + indexBounds.getEnd());
				
				Node created = Literal.decodeStatement(n, data, newLoc, require, true);
				
				if (created == null)
				{
					created = SyntaxTree.decodeScopeContents(n.getAncestorWithScope(), data, newLoc, require);
					
					if (created == null)
					{
						SyntaxMessage.queryError("Unknown array access index '" + data + "'", n, newLoc, require);
						
						return null;
					}
				}
				
				n.addDimension(created);
				
				indexBounds = Regex.boundsOf(indexData, current, Patterns.ARRAY_BRACKETS_DATA);
				current     = indexBounds.getEnd() + 1;
			}
			
			return n;
		}
		
		return null;
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
	
	/**
	 * Test the ArrayAccess class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}