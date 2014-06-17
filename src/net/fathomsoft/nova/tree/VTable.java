package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * IdentifierNode extension that contains the information describing
 * an array instantiation. The getName() method contains the data type
 * of the array. The children that the node embodies list the sizes of
 * each of the dimensions of the array that is being created. For
 * instance, consider the following scenario:<br>
 * <br>
 * The ArrayNode encompasses two children. The first child is a
 * LiteralNode that contains the value 56. This means that the first
 * dimension of the array will have the size of 56. The second child
 * is a LocalVariableNode containing the data for an integer variable
 * that was declared within the method that the array was declared in.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 16, 2014 at 1:13:49 AM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class VTable extends ClassDeclaration
{
	public static final String	VTABLE_IDENTIFIER = "vtable";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VTable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("CLASS");
		
		if (isStatic())
		{
			SyntaxMessage.error("Static classes are not implemented in C yet.", this);
		}
		if (isConstant())
		{
			SyntaxMessage.error("Const classes are not implemented in C yet.", this);
		}
		
		if (isReference())
		{
			SyntaxMessage.error("A class cannot be of a reference type", this);
		}
		else if (isPointer())
		{
			SyntaxMessage.error("A class cannot be of a pointer type", this);
		}
		
		builder.append('\n').append('(').append('\n');
		
		builder.append(getName()).append(", ");
		
		builder.append('\n').append('\n');

		FieldList fields = getFieldListNode();
		
		InstanceFieldList publicFields = fields.getPublicFieldListNode();
		
		if (publicFields.getNumChildren() > 0)
		{
			builder.append(publicFields.generateCHeader()).append('\n');
		}
		
		builder.append(')').append('\n').append('\n');
		
		MethodList constructors = getConstructorListNode();
		builder.append(constructors.generateCHeader());
		
		MethodList destructors = getDestructorListNode();
		builder.append(destructors.generateCHeader());
		
		MethodList methods = getMethodListNode();
		builder.append(methods.generateCHeader());
		
		if (containsStaticData())
		{
			builder.append("extern ").append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		if (containsStaticData())
		{
			builder.append(getName()).append("* ").append("__static__").append(getName()).append(';').append('\n').append('\n');
		}
		
		FieldList fields = getFieldListNode();
		
		InstanceFieldList privateFields = fields.getPrivateFieldListNode();
		
		if (privateFields.getNumChildren() > 0)
		{
			builder.append("PRIVATE").append('\n').append('(').append('\n');
			
			builder.append(privateFields.generateCSource());
			
			builder.append(')');
		}
		else
		{
			builder.append("NO_PRIVATE");
		}
		
		builder.append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != fields)
			{
				builder.append(child.generateCSource());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into an ArrayNode instance. If the
	 * given statement cannot be decoded into an ArrayNode, then null is
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
	 * @param statement The statement to decode into an ArrayNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The new ArrayNode instance if it was able to decode the
	 * 		statement. If not, it will return null.
	 */
	public static VTable decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isArrayInitialization(statement))
		{
			VTable n = new VTable(parent, location);
			
			int index = statement.indexOf('[') + 1;
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String idValue = statement.substring(bounds.getStart(), bounds.getEnd());
			
			n.setName(idValue);
			
			while (index > 1)
			{
				int endIndex  = statement.indexOf(']', index);
				
				String length = statement.substring(index, endIndex);
				
				if (SyntaxUtils.isNumber(length))
				{
					Literal node = Literal.decodeStatement(n, length, location, require, false);
					
					n.addChild(node);
				}
				else
				{
					Identifier node = SyntaxTree.getExistingNode(parent, length).clone(n, location);
					
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
	public VTable clone(Node temporaryParent, Location locationIn)
	{
		VTable node = new VTable(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArrayNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VTable cloneTo(VTable node)
	{
		super.cloneTo(node);
		
		return node;
	}
}