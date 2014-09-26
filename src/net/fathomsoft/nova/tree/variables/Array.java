package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.BinaryOperation;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Priority;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
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
 * @version	v0.2.32 Sep 26, 2014 at 12:17:33 PM
 */
public class Array extends VariableDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Array(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDecodedChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
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
		generateCTypeCast(builder);
		builder.insert(builder.length() - 1, '*');
		
		builder.append("NOVA_MALLOC(sizeof(").append(generateCTypeClassName()).append(')').append(" * (");
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateCSourceFragment(builder);
		}
		
		return builder.append(')').append(')');
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getName());
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append('[').append(getChild(i).generateNovaInput()).append(']');
		}
		
		return builder;
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
	 * @return The new Array instance if it was able to decode the
	 * 		statement. If not, it will return null.
	 */
	public static Array decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (SyntaxUtils.isArrayInitialization(statement))
		{
			Array n = new Array(parent, location);
			
			int index = statement.indexOf('[');
			
			Location newLoc = location.asNew();
			newLoc.setBounds(location.getStart() + index + 1, location.getEnd());
			
			String idValue = StringUtils.trimSurroundingWhitespace(statement.substring(0, index));
			
			n.setName(idValue);
			n.setType(idValue);
			
			if (n.isGenericType())
			{
				String type = n.getGenericReturnType();
				
				n.setName(type);
				n.setType(type);
			}
			
			if (n.decodeDimensions(statement, index, newLoc, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the dimensions containing the sizes of each dimension
	 * of the array.
	 * 
	 * @param statement The statement containing the dimensions.
	 * @param index The starting index of the first set of brackets.
	 * @param location The location that the dimensions are located at.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the dimensions decoded successfully.
	 */
	private boolean decodeDimensions(String statement, int index, Location location, boolean require)
	{
		while (index++ > 0)
		{
			int    endIndex = statement.indexOf(']', index);
			String length   = StringUtils.trimSurroundingWhitespace(statement.substring(index, endIndex));
			
			decodeLength(length, location, require);
			
			index = statement.indexOf('[', endIndex + 1);
		}
		
		return true;
	}
	
	/**
	 * Decode the identifier or literal that represents the length of
	 * a dimension of the array.
	 * 
	 * @param length The String representing the length.
	 * @param location The location of the length String.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 */
	private void decodeLength(String length, Location location, boolean require)
	{
		Node node = Literal.decodeStatement(this, length, location, require, true);
		
		if (node == null)
		{
			node = SyntaxTree.getUsableExistingNode(this, length, location);
			
			if (node == null)
			{
				node = BinaryOperation.decodeStatement(this, length, location, require);
				
				if (node == null)
				{
					node = Priority.decodeStatement(this, length, location, require);
					
					if (node == null)
					{
						SyntaxMessage.error("Could not parse length '" + length + "' for array initialization ", this, location);
					}
				}
			}
		}
		
		addChild(node);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Array clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Array node = new Array(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Array} with the data that is in the
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
	
	/**
	 * Test the Array class type to make sure everything
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