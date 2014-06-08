package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.BinaryOperatorNode;
import net.fathomsoft.nova.tree.DimensionsNode;
import net.fathomsoft.nova.tree.LiteralNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * ValueNode extension that keeps track of any time an array is being
 * accessed. For example, the statement: "args[34]" is an array access.
 * Obviously the previous code segment does nothing, however these nodes
 * will be intertwined with method calls, assignments, if statements, etc.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2 Mar 24, 2014 at 10:45:29 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class ArrayAccessNode extends VariableNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ArrayAccessNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		DimensionsNode dimensions = new DimensionsNode(this, locationIn);
		
		addChild(dimensions);
	}
	
	/**
	 * Get the node that represents the dimensions of the array. The
	 * DimensionsNode class contains information of the index that is
	 * being accessed.
	 * 
	 * @return The node that represents the dimensions being accessed.
	 */
	public DimensionsNode getDimensionsNode()
	{
		return (DimensionsNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (getNumChildren() <= 1)
		{
			super.addChild(child);
		}
		else
		{
			addChild(getNumChildren() - 1, child);
		}
	}
	
	/**
	 * Add a dimension, that contains the index that is being attained,
	 * to the DimensionsNode instance of the ArrayAccessNode.
	 * 
	 * @param child The node that describes the index that is being
	 * 		accessed by the array at the specified dimension that is
	 * 		about to be added.
	 */
	public void addDimension(TreeNode child)
	{
		getDimensionsNode().addChild(child);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
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
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder  builder    = new StringBuilder();
		
		DimensionsNode dimensions = getDimensionsNode();
		
		builder.append(generateUseOutput());
		builder.append(dimensions.generateCSourceFragment());
		builder.append(generateChildrenCSourceFragment());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(super.generateNovaInput(false) + getDimensionsNode().generateNovaInput());
		
		VariableNode accessed = getAccessedNode();
		
		if (accessed != null)
		{
			builder.append('.').append(accessed.generateNovaInput());
		}
		
		return builder.toString();
	}

	/**
	 * Decode the given statement into an ArrayAccessNode if possible.
	 * If it is not possible, the method will return null.<br>
	 * <br>
	 * An example input would be: "args[34]"
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an ArrayAccessNode.
	 * @param location The location of the statement.
	 * @return The ArrayAccessNode if it was created, null if not.
	 */
	public static ArrayAccessNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		if (SyntaxUtils.isValidArrayAccess(statement))
		{
			ArrayAccessNode n  = new ArrayAccessNode(parent, location);
			
			Bounds idBounds    = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String identifier  = statement.substring(idBounds.getStart(), idBounds.getEnd());
			String indexData   = statement.substring(idBounds.getEnd());
			
			Bounds indexBounds = Regex.boundsOf(indexData, Patterns.ARRAY_BRACKETS_DATA);
			
			int    current     = indexBounds.getEnd() + 1;
			
			VariableNode var   = getExistingNode(parent, identifier);
			
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
					LiteralNode literal = LiteralNode.decodeStatement(n, data, newLoc, require, false);
					
					n.addDimension(literal);
				}
				else
				{
					TreeNode created = TreeNode.decodeScopeContents(parent, data, newLoc, false);
					
					if (created == null)
					{
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
	 * @see net.fathomsoft.nova.tree.ValueNode#getAccessedNode()
	 */
	public VariableNode getAccessedNode()
	{
		if (getNumChildren() <= 1)
		{
			return null;
		}
		
		return (VariableNode)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ArrayAccessNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ArrayAccessNode node = new ArrayAccessNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArrayAccessNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArrayAccessNode cloneTo(ArrayAccessNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}