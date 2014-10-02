package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Assignment;
import net.fathomsoft.nova.tree.BinaryOperation;
import net.fathomsoft.nova.tree.Dimensions;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Priority;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.TypeList;
import net.fathomsoft.nova.tree.Value;
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
 * @version	v0.2.33 Sep 29, 2014 at 10:29:33 AM
 */
public class Array extends VariableDeclaration implements ArrayCompatible
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Array(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Dimensions dimensions = new Dimensions(this, locationIn);
		
		addChild(dimensions);
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1 + getChildrenOffset();
	}
	
	private int getChildrenOffset()
	{
		int offset = 0;
		
		if (getInitializerValues() != null)
		{
			offset++;
		}
		
		return offset;
	}
	
	@Override
	public Dimensions getDimensions()
	{
		return (Dimensions)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getAccessedNode()
	 */
	@Override
	public Identifier getAccessedNode()
	{
		if (getNumVisibleChildren() <= super.getNumDefaultChildren() + getChildrenOffset() + 1)
		{
			return null;
		}
		
		return (Identifier)getVisibleChild(super.getNumDefaultChildren() + getChildrenOffset() + 1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDecodedChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	public TypeList<Value> getInitializerValues()
	{
		if (getNumChildren() <= super.getNumDefaultChildren() + 1 ||
				!(getChild(super.getNumDefaultChildren() + 1) instanceof TypeList))
		{
			return null;
		}
		
		return (TypeList<Value>)getChild(super.getNumDefaultChildren() + 1);
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
//		builder.insert(builder.length() - 1, '*');
		
		if (getNumDimensions() > 1)
		{
			builder.append("nova_gen_array(");
		}
		
		builder.append("NOVA_MALLOC(sizeof(").append(generateCTypeClassName());
		
		Dimensions dim = getDimensions();
		
		dim.generateCSourceFragment(builder);
		
		builder.append(')');
		
		if (getNumDimensions() > 1)
		{
			builder.append("), (int[]) { ");
			
			for (int i = 0; i < dim.getNumVisibleChildren(); i++)
			{
				if (i > 0)
				{
					builder.append(", ");
				}
				
				dim.getVisibleChild(i).generateCSourceFragment(builder);
			}
			
			builder.append(" }, 0, ").append(dim.getNumVisibleChildren() - 1).append(", ");
			
			builder.append("sizeof(").append(generateCTypeClassName()).append(')');
		}
		
		return builder.append(')');
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getName());
		
		getDimensions().generateNovaInput(builder);
		
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
		Array n = new Array(parent, location);
		
		if (SyntaxUtils.isArrayInitialization(statement))
		{
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
		else if (n.isInitializer(parent, statement, require))
		{
			if (n.decodeInitializer(statement, require))
			{
				VariableDeclaration declaration = n.getArrayDeclaration();
				
				n.setName(declaration.getType());
				n.setType(n.getName());
				n.setArrayDimensions(declaration.getArrayDimensions());
				
				return n;
			}
		}
		
		return null;
	}
	
	private boolean isInitializer(Node parent, String statement, boolean require)
	{
		if (!(parent instanceof Assignment))
		{
			return SyntaxMessage.queryError("Array initializer is only valid during an assignment", this, require);
		}
		
		return isInitializer(statement);
	}
	
	public static boolean isInitializer(String statement)
	{
		return getInitializerContents(statement) != null;
	}
	
	private static String getInitializerContents(String statement)
	{
		if (statement.length() > 2 && statement.charAt(0) == '[' && statement.charAt(statement.length() - 1) == ']')
		{
			return statement.substring(1, statement.length() - 1).trim();
		}
		
		return null;
	}
	
	private VariableDeclaration getArrayDeclaration()
	{
		return ((Assignment)getParent()).getAssigneeNode().getDeclaration();
	}
	
	private boolean decodeInitializer(String statement, boolean require)
	{
		String contents = getInitializerContents(statement);
		
		if (contents == null)
		{
			return false;
		}
		else if (!getArrayDeclaration().isArray())
		{
			return SyntaxMessage.queryError("Array initializer can only be assigned to arrays", this, require);
		}
		
		TypeList<Value> initValues = new TypeList<Value>(this, getLocationIn().asNew());
		
		String values[] = StringUtils.splitCommas(contents);
		
		for (String value : values)
		{
			Value val = SyntaxTree.decodeValue(initValues, value, getLocationIn().asNew(), require);
			
			if (val == null)
			{
				return SyntaxMessage.queryError("Cannot decode value '" + value + "'", this, require);
			}
			else if (!SyntaxUtils.isTypeCompatible(getArrayDeclaration(), val))
			{
				return SyntaxMessage.queryError("Type '" + val.getType() + "' is not compatible with array type '" + getArrayDeclaration().getType() + "'", this, require);
			}
			
			initValues.addChild(val);
		}
		
		Literal size = Literal.decodeStatement(this, "" + values.length, getLocationIn(), true);
		
		getDimensions().addChild(size);
		
		addChild(initValues);
		
		return true;
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
			
			setArrayDimensions(getArrayDimensions() + 1);
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
		
		getDimensions().addChild(node);
	}
	
	@Override
	public Node validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			TypeList<Value> initValues = getInitializerValues();
			
			if (initValues != null)
			{
				Node base = getBaseNode();
				
				for (int i = initValues.getNumVisibleChildren() - 1; i >= 0; i--)
				{
					Value value = initValues.getVisibleChild(i);
					
					Assignment a = Assignment.decodeStatement(this, getArrayDeclaration().getName() + "[" + i + "] = " + value.generateNovaInput(), getLocationIn(), true);
					
					base.getParent().addChildAfter(base, a);
				}
			}
		}
		
		return super.validate(phase);
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