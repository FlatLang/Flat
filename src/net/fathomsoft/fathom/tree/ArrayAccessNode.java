package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 24, 2014 at 10:45:29 PM
 * @since	v
 * @version	Mar 24, 2014 at 10:45:29 PM
 * @version	v
 */
public class ArrayAccessNode extends IdentifierNode
{
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
		
		builder.append(getName());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append('[').append(child.generateCSourceFragment()).append(']');
		}
		
		return builder.toString();
	}
	
	public static ArrayAccessNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		if (SyntaxUtils.isValidArrayAccess(statement))
		{
			Bounds idBounds    = Regex.boundsOf(statement, Patterns.IDENTIFIER);
			
			String identifier  = statement.substring(idBounds.getStart(), idBounds.getEnd());
			String indexData   = statement.substring(idBounds.getEnd());
			
			Bounds indexBounds = Regex.boundsOf(indexData, Patterns.ARRAY_BRACKETS_DATA);
			
			int current = indexBounds.getEnd() + 1;
			
			ArrayAccessNode node = new ArrayAccessNode();
			node.setName(identifier);
			
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
		ArrayAccessNode clone = new ArrayAccessNode();
		clone.setName(getName());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}