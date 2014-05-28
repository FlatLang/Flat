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
 * @version	v0.2.9 May 28, 2014 at 6:44:37 AM
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
	 * Get the VariableNode that corresponds to the array identifier.
	 * 
	 * @return The VariableNode instance.
	 */
	public VariableNode getVariableNode()
	{
		return (VariableNode)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (getChildren().size() <= 1)
		{
			super.addChild(child);
		}
		else
		{
			getVariableNode().addChild(child);
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
		
		VariableNode   varNode    = getVariableNode();
		DimensionsNode dimensions = getDimensionsNode();
		
		builder.append(varNode.generateUseOutput());
		builder.append(dimensions.generateCSourceFragment());
		builder.append(varNode.generateChildrenCSourceFragment());
		
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
	public static ArrayAccessNode decodeStatement(TreeNode parent, String statement, Location location)
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
			
			var = var.clone(n, location);
			
			n.addChild(var);
			n.setName(var.getName());
			n.setType(var.getType());
			
			while (current > 0)
			{
				String data = indexData.substring(indexBounds.getStart(), indexBounds.getEnd());
				
				Location newLoc = new Location(location);
				newLoc.setOffset(idBounds.getEnd() + location.getOffset());
				newLoc.setBounds(identifier.length() + indexBounds.getStart(), identifier.length() + indexBounds.getEnd());
				
				if (SyntaxUtils.isLiteral(data))
				{
					LiteralNode literal = new LiteralNode(null, newLoc);
					literal.setValue(data, false);
					
					n.addDimension(literal);
				}
				else
				{
					TreeNode created = BinaryOperatorNode.decodeStatement(parent, data, newLoc);
					
					if (created == null)
					{
						created = TreeNode.decodeScopeContents(parent, data, newLoc);
					}
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
		if (getChildren().size() <= 1)
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