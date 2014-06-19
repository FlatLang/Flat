package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.BinaryOperation;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Priority;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Identifier extension that contains the information describing
 * an array instantiation. The getName() method contains the data type
 * of the array. The children that the node embodies list the sizes of
 * each of the dimensions of the array that is being created. For
 * instance, consider the following scenario:<br>
 * <br>
 * The Array encompasses two children. The first child is a
 * Literal that contains the value 56. This means that the first
 * dimension of the array will have the size of 56. The second child
 * is a LocalVariable containing the data for an integer variable
 * that was declared within the method that the array was declared in.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 16, 2014 at 1:13:49 AM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
 */
public class Array extends Variable
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Array(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		builder.append('(').append(getName()).append('*');
		
		if (!isPrimitiveType() && !isExternalType())
		{
			builder.append('*');
		}
		
		builder.append(')');
		
		builder.append("NOVA_MALLOC(sizeof(").append(getName()).append(')').append(" * (");
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			child.generateCSourceFragment(builder);
		}
		
		return builder.append(')').append(')');
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getName());
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append('[').append(child.generateNovaInput()).append(']');
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into an Array instance. If the
	 * given statement cannot be decoded into an Array, then null is
	 * returned.<br>
	 * <br>
	 * An example input would be: "char[length]" <i>(Where as 'length' is
	 * a number variable)</i> or any other class name followed by square
	 * brackets that enclose a size variable or literal.<br>
	 * <br>
	 * Other example inputs:<br>
	 * <ul>
	 * 	<li>String[5][size] <i>(Where as 'size' is a number variable)</i></li>
	 * 	<li>int[names.getSize()]</li>
	 * 	<li>Node[elements.getSize() * (4 + 3) / 2]</li>
	 * </ul>
	 * <br>
	 * Array initializer statements are to be implemented in the future.
	 * Such syntax would consist of the following: "int[] { 1, 6, 3, 1 }"
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an Array instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The new Array instance if it was able to decode the
	 * 		statement. If not, it will return null.
	 */
	public static Array decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isArrayInitialization(statement))
		{
			Array n = new Array(parent, location);
			
			int index   = statement.indexOf('[') + 1;
			
			Location newLoc = new Location(location);
			newLoc.setBounds(location.getStart() + index, location.getEnd());
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String idValue = statement.substring(bounds.getStart(), bounds.getEnd());
			
			n.setName(idValue);
			n.setType(idValue);
			
			while (index > 1)
			{
				int endIndex  = statement.indexOf(']', index);
				
				String length = statement.substring(index, endIndex);
				
				if (SyntaxUtils.isNumber(length))
				{
					Literal node = Literal.decodeStatement(n, length, newLoc, require, false);
					
					n.addChild(node);
				}
				else
				{
					Node node = SyntaxTree.getExistingNode(parent, length);
					
					if (node == null)
					{
						Node binary = BinaryOperation.decodeStatement(parent, length, newLoc, require, false);
						
						if (binary == null)
						{
							binary = Priority.decodeStatement(parent, length, location, require, false);
						}
						if (binary == null)
						{
							SyntaxMessage.error("Could not parse length '" + length + "' for array initialization ", n, newLoc);
						}
						
						node = binary;
					}
					else
					{
						node = node.clone(n, newLoc);
					}
					
					n.addChild(node);
				}
				
				index = statement.indexOf('[', endIndex + 1) + 1;
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Array clone(Node temporaryParent, Location locationIn)
	{
		Array node = new Array(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Array with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Array cloneTo(Array node)
	{
		super.cloneTo(node);
		
		return node;
	}
}