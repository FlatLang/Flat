package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.tree.variables.VariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * TreeNode extension that keeps track of any time an array is being
 * accessed. For example, the statement: "args[34]" is an array access.
 * Obviously the previous code segment does nothing, however these nodes
 * will be intertwined with method calls, assignments, if statements, etc.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 24, 2014 at 10:45:29 PM
 * @since	v0.1
 * @version	Mar 28, 2014 at 5:54:29 PM
 * @version	v0.2
 */
public class ArrayAccessNode extends TreeNode
{
	/**
	 * Get the VariableNode that corresponds to the array identifier.
	 * 
	 * @return The VariableNode instance.
	 */
	public VariableNode getVariableNode()
	{
		return (VariableNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceFragment());
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		VariableNode varNode = getVariableNode();
		
		builder.append(varNode.generateCSourceFragment());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != varNode)
			{
				builder.append('[').append(child.generateCSourceFragment()).append(']');
			}
		}
		
		return builder.toString();
	}

	/**
	 * Decode the given statement into an ArrayAccessNode if possible.
	 * If it is not possible, the method will return null.<br>
	 * <br>
	 * An example input would be: "args[34]"
	 * 
	 * @param parentNode The parent of the current statement.
	 * @param statement The statement to decode into an ArrayAccessNode.
	 * @param location The location of the statement.
	 * @return The ArrayAccessNode if it was created, null if not.
	 */
	public static ArrayAccessNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		if (SyntaxUtils.isValidArrayAccess(statement))
		{
			Bounds idBounds    = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String identifier  = statement.substring(idBounds.getStart(), idBounds.getEnd());
			String indexData   = statement.substring(idBounds.getEnd());
			
			Bounds indexBounds = Regex.boundsOf(indexData, Patterns.ARRAY_BRACKETS_DATA);
			
			int current = indexBounds.getEnd() + 1;
			
			VariableNode var = (VariableNode)getExistingNode(parentNode, identifier);
			
			if (var == null)
			{
				SyntaxMessage.error("Undeclared variable '" + identifier + "'", location);
			}
			
			var = var.clone();
			
			ArrayAccessNode node = new ArrayAccessNode();
			node.addChild(var);
			
			while (current > 0)
			{
				String data = indexData.substring(indexBounds.getStart(), indexBounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setOffset(identifier.length() + indexBounds.getStart());
				
				if (SyntaxUtils.isLiteral(data))
				{
					LiteralNode literal = new LiteralNode();
					literal.setValue(data, false);
					
					node.addChild(literal);
				}
				else
				{
					IdentifierNode existing = TreeNode.getExistingNode(parentNode, data);
					
					if (existing != null)
					{
						existing = existing.clone();
						
						node.addChild(existing);
					}
					else
					{
						TreeNode created = TreeNode.decodeStatement(parentNode, data, newLoc);
						
						if (created == null)
						{
							SyntaxMessage.error("Unknown array access index", newLoc);
						}
						
						node.addChild(created);
					}
				}
				
				indexBounds = Regex.boundsOf(indexData, current, Patterns.ARRAY_BRACKETS_DATA);
				current     = indexBounds.getEnd() + 1;
			}
			
			return node;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ArrayAccessNode clone()
	{
		ArrayAccessNode node = new ArrayAccessNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ArrayAccessNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArrayAccessNode clone(ArrayAccessNode node)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}