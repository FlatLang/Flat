package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.error.SyntaxMessage;
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
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class ArrayAccessNode extends VariableNode
{
	public ArrayAccessNode()
	{
		DimensionsNode dimensions = new DimensionsNode();
		
		addChild(dimensions);
	}
	
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
			Bounds idBounds    = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String identifier  = statement.substring(idBounds.getStart(), idBounds.getEnd());
			String indexData   = statement.substring(idBounds.getEnd());
			
			Bounds indexBounds = Regex.boundsOf(indexData, Patterns.ARRAY_BRACKETS_DATA);
			
			int    current     = indexBounds.getEnd() + 1;
			
			VariableNode var   = getExistingNode(parent, identifier);
			
			if (var == null)
			{
				SyntaxMessage.error("Undeclared variable '" + identifier + "'", parent.getFileNode(), location, parent.getController());
				
				return null;
			}
			
			var = var.clone();
			
			ArrayAccessNode node = new ArrayAccessNode();
			node.addChild(var);
			node.setName(var.getName());
			node.setType(var.getType());
			
			while (current > 0)
			{
				String data = indexData.substring(indexBounds.getStart(), indexBounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(identifier.length() + indexBounds.getStart(), identifier.length() + indexBounds.getEnd());
				
				if (SyntaxUtils.isLiteral(data))
				{
					LiteralNode literal = new LiteralNode();
					literal.setValue(data, false);
					
					node.addDimension(literal);
				}
				else
				{
					TreeNode created = TreeNode.decodeScopeContents(parent, data, newLoc);
					
					if (created == null)
					{
						SyntaxMessage.error("Unknown array access index", parent.getFileNode(), newLoc, parent.getController());
						
						return null;
					}
					
					node.addDimension(created);
				}
				
				indexBounds = Regex.boundsOf(indexData, current, Patterns.ARRAY_BRACKETS_DATA);
				current     = indexBounds.getEnd() + 1;
			}
			
			return node;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public ArrayAccessNode clone()
	{
		ArrayAccessNode node = new ArrayAccessNode();
		
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